package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.CriterionNode;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.Query;
import lombok.extern.java.Log;

import java.util.List;

@Log
public abstract class AssignmentCriterion<Q extends Query> implements CriterionNode {
    protected Criteria criterion;
    private final Class<Q> queryType;
    protected final InsertedDocumentStorage documentStorage;
    protected int currentScore;
    protected CriterionEvaluationResult criterionEvaluationResult;

    public AssignmentCriterion(Criteria criterion, Class<Q> queryType, InsertedDocumentStorage documentStorage) {
        this.criterionEvaluationResult = new CriterionEvaluationResult(criterion);
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
            criterionEvaluationResult.log(query, currentScore);
        }
    }

    protected abstract void concreteCheck(Q query);

    @Override
    public List<CriterionEvaluationResult> evaluate() {
        return List.of(criterionEvaluationResult);
    }
}
