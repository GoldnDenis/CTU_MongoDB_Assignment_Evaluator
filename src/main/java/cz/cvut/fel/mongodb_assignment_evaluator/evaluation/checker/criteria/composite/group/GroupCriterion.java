package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.group;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.CriterionEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.CriterionNode;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.Query;

import java.util.ArrayList;
import java.util.List;

public abstract class GroupCriterion<Q extends Query> implements CriterionNode {
    protected final InsertedDocumentStorage documentStorage;
    private final List<AssignmentCriterion<Q>> children;

    public GroupCriterion(InsertedDocumentStorage documentStorage) {
        this.documentStorage = documentStorage;
        this.children = new ArrayList<>(initCriteria());
    }

    protected abstract List<AssignmentCriterion<Q>> initCriteria();

    public void check(Query query) {
        children.forEach(c -> c.check(query));
    }

    public List<CriterionEvaluationResult> evaluate() {
        return children.stream()
                .flatMap(c -> c.evaluate().stream())
                .toList();
    }
}
