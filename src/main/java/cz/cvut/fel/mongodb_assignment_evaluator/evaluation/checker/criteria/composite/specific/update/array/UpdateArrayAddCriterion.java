package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.specific.update.array;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.UpdateGroups;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.UpdateQuery;

public class UpdateArrayAddCriterion extends AssignmentCriterion<UpdateQuery> {
    public UpdateArrayAddCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.UPDATE_ARRAY_ADD, UpdateQuery.class, documentStorage);
    }

    @Override
    protected void concreteCheck(UpdateQuery query) {
        if (!query.containsOneOfUpdates(UpdateGroups.ARRAY_UPDATE)) {
            return;
        }
        if (documentStorage.findDocument(query.getCollection(), query.getFilter().getDocument()).isPresent()) {
            currentScore++;
        }
    }
}
