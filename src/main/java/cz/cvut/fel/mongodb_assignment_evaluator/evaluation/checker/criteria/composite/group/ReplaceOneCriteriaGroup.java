package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.group;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.specific.replace.ReplaceValueCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.ReplaceOneQuery;

import java.util.List;

public class ReplaceOneCriteriaGroup extends GroupCriterion<ReplaceOneQuery> {
    public ReplaceOneCriteriaGroup(InsertedDocumentStorage documentStorage) {
        super(documentStorage);
    }

    @Override
    protected List<AssignmentCriterion<ReplaceOneQuery>> initCriteria() {
        return List.of(
                new ReplaceValueCriterion(documentStorage)
        );
    }
}