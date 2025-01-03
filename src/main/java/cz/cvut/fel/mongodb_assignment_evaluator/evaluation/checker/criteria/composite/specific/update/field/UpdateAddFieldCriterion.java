package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.specific.update.field;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.UpdateGroups;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.UpdateQuery;

public class UpdateAddFieldCriterion extends AssignmentCriterion<UpdateQuery> {
    public UpdateAddFieldCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.UPDATE_ADD_FIELD, UpdateQuery.class, documentStorage);
    }

    @Override
    protected void concreteCheck(UpdateQuery query) {
        if (!query.containsOneOfUpdates(UpdateGroups.ADD)) {
            return;
        }
        if (documentStorage.findDocument(query.getCollection(), query.getFilter().getDocument()).isEmpty()) {
            currentScore++;
        }
    }
}
