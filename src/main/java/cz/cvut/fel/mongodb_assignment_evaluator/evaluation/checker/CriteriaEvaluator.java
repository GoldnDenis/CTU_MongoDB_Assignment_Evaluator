package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.StudentEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.group.*;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.LogTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.CriterionNode;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.CriterionEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.Query;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.specific.unknown.UnknownQueryCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.result.StudentEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.exception.NotDefinedCriteriaQueryType;
import cz.cvut.fel.mongodb_assignment_evaluator.exception.QueryCollectionNotExists;
import lombok.extern.java.Log;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

@Log
public class CriteriaEvaluator {
    private final InsertedDocumentStorage documentStorage;
    private final Map<QueryTypes, CriterionNode> children;

    public CriteriaEvaluator() {
        documentStorage = new InsertedDocumentStorage();
        children = new LinkedHashMap<>();
        initCriteriaGroup();
    }

    public StudentEvaluationResult check(List<Query> queries) {
        for (Query query: queries) {
            try {
                checkQuery(query);
            } catch (Exception e) {
                log.warning(e.getMessage());
                StudentEvaluator.studentLogCollector.addLog(Level.WARNING, LogTypes.CHECKER, e.getMessage());
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
        children.put(QueryTypes.CREATE_COLLECTION, new CreateCollectionGroupCriterion(documentStorage));
        children.put(QueryTypes.INSERT, new InsertCriteriaGroup(documentStorage));
        children.put(QueryTypes.REPLACE_ONE, new ReplaceOneCriteriaGroup(documentStorage));
        children.put(QueryTypes.UPDATE, new UpdateCriteriaGroup(documentStorage));
        children.put(QueryTypes.FIND, new FindGroupCriterion(documentStorage));
        children.put(QueryTypes.AGGREGATE, new AggregateGroupCriterion(documentStorage));
        children.put(QueryTypes.GENERAL, new GeneralCriterionGroup(documentStorage));
        children.put(QueryTypes.UNKNOWN, new UnknownQueryCriterion(documentStorage));
    }
}
