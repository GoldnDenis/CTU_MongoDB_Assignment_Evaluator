package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.group;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.specific.general.CommentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.Query;

import java.util.List;

public class GeneralCriterionGroup extends GroupCriterion<Query> {
    public GeneralCriterionGroup(InsertedDocumentStorage documentStorage) {
        super(documentStorage);
    }

    @Override
    protected List<AssignmentCriterion<Query>> initCriteria() {
        return List.of(
                new CommentCriterion(documentStorage)
        );
    }
}
