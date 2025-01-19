package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.StudentEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.group.types.*;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.StudentErrorTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.CheckerNode;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.result.CriterionEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.Query;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.unknown.UnknownQueryChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.result.StudentEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.exception.NotDefinedCriteriaQueryType;
import cz.cvut.fel.mongodb_assignment_evaluator.exception.QueryCollectionNotExists;
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

    public StudentEvaluationResult checkQueries(List<Query> queryList) {
        initCriteriaGroup();
        for (Query query: queryList) {
            try {
                checkQuery(query);
            } catch (Exception e) {
                StudentEvaluator.getErrorCollector().addLog(Level.WARNING, StudentErrorTypes.CHECKER, e.getMessage());
            }
        }
        return new StudentEvaluationResult(collectAllResults());
    }

    private void checkQuery(Query query) {
        QueryTypes type = query.getType();
        if (!children.containsKey(type)) {
            throw new NotDefinedCriteriaQueryType(type);
        }
        if (type.isInCollectionScope()
            && !documentStorage.containsCollection(query.getCollection())) {
            throw new QueryCollectionNotExists(query);
        }
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
