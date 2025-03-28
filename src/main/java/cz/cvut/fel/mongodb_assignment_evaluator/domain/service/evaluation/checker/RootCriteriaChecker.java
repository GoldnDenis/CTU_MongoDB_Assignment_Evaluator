package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.StudentErrorTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.NotDefinedCriteriaQueryType;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.Query;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.result.CriterionEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.result.GradedSubmission;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.StudentAssignmentEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.CheckerNode;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.group.types.*;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.unknown.UnknownQueryChecker;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

@Service
public class RootCriteriaChecker {
    private final InsertedDocumentStorage documentStorage;
    private final Map<QueryTypes, CheckerNode> children;

    public RootCriteriaChecker() {
        documentStorage = new InsertedDocumentStorage();
        children = new LinkedHashMap<>();
    }

    public GradedSubmission checkQueries(List<Query> queryList) {
        initCriteriaGroup();
        for (Query query: queryList) {
            try {
                checkQuery(query);
            } catch (Exception e) {
                StudentAssignmentEvaluator.getErrorCollector().addLog(Level.WARNING, StudentErrorTypes.CHECKER, e.getMessage());
            }
        }
//        return new GradedSubmission(collectAllResults());
        return new GradedSubmission();
    }

    private void checkQuery(Query query) {
        QueryTypes type = query.getType();
        if (!children.containsKey(type)) {
            throw new NotDefinedCriteriaQueryType(type);
        }
//        if (type.isInCollectionScope()
//            && !documentStorage.containsCollection(query.getCollection())) {
//            throw new QueryCollectionNotExists(query);
//        }
        if (type != QueryTypes.UNKNOWN) {
            children.get(QueryTypes.GENERAL).check(query);
        }
        children.get(type).check(query);
    }

    private List<CriterionEvaluationResult> collectAllResults() {
        return children.values().stream()
                .flatMap(child -> child.evaluate().stream())
                .toList();
    }

    private void initCriteriaGroup() {
        children.put(QueryTypes.CREATE_COLLECTION, new CreateCollectionGroupChecker(documentStorage));
        children.put(QueryTypes.INSERT, new InsertGroupChecker(documentStorage));
        children.put(QueryTypes.REPLACE_ONE, new ReplaceOneGroupChecker(documentStorage));
        children.put(QueryTypes.UPDATE, new UpdateGroupChecker(documentStorage));
        children.put(QueryTypes.FIND, new FindGroupChecker(documentStorage));
        children.put(QueryTypes.AGGREGATE, new AggregateGroupChecker(documentStorage));
        children.put(QueryTypes.GENERAL, new GeneralChecker(documentStorage));
        children.put(QueryTypes.UNKNOWN, new UnknownQueryChecker(documentStorage));
    }
}
