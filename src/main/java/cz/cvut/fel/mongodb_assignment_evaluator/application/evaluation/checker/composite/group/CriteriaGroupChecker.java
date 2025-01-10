package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.group;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.result.CriterionEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.CheckerNode;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.Query;

import java.util.ArrayList;
import java.util.List;

public abstract class CriteriaGroupChecker<Q extends Query> implements CheckerNode {
    private final List<CheckerNode> children;
    protected final InsertedDocumentStorage documentStorage;

    public CriteriaGroupChecker(InsertedDocumentStorage documentStorage) {
        this.documentStorage = documentStorage;
        this.children = new ArrayList<>(initCriteria());
    }

    protected abstract List<CheckerLeaf<Q>> initCriteria();

    public void check(Query query) {
        children.forEach(c -> c.check(query));
    }

    public List<CriterionEvaluationResult> evaluate() {
        return children.stream()
                .flatMap(c -> c.evaluate().stream())
                .toList();
    }
}
