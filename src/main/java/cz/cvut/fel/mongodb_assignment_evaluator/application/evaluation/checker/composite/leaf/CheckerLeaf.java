package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf;

import cz.cvut.fel.mongodb_assignment_evaluator.application.model.result.CriterionEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.CheckerNode;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.Query;
import lombok.extern.java.Log;

import java.util.List;

@Log
public abstract class CheckerLeaf<Q extends Query> implements CheckerNode {
    protected Criteria criterion;
    private final Class<Q> queryType;
    protected final InsertedDocumentStorage documentStorage;
    protected int currentScore;
    protected CriterionEvaluationResult evaluationResult;

    public CheckerLeaf(Criteria criterion, Class<Q> queryType, InsertedDocumentStorage documentStorage) {
        this.evaluationResult = new CriterionEvaluationResult(criterion);
        this.queryType = queryType;
        this.documentStorage = documentStorage;
        this.currentScore = 0;
    }

    @Override
    public void check(Query query) {
        if (queryType.isInstance(query)) {
            Q q = queryType.cast(query);
            currentScore = 0;
            concreteCheck(q);
            evaluationResult.saveQuery(query, currentScore);
        }
    }

    protected abstract void concreteCheck(Q query);

    @Override
    public List<CriterionEvaluationResult> evaluate() {
        return List.of(evaluationResult);
    }
}
