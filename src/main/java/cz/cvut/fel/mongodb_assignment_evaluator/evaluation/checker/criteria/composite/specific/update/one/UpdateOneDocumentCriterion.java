package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.specific.update.one;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.UpdateGroups;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.UpdateQuery;

public class UpdateOneDocumentCriterion extends AssignmentCriterion<UpdateQuery> {
    public UpdateOneDocumentCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.UPDATE_ONE_DOCUMENT, UpdateQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(UpdateQuery query) {
        if (!query.isUpdateOne() ||
                !query.containsOneOfUpdates(UpdateGroups.UPDATE)) {
            return;
        }
        if (documentStorage.findDocument(query.getCollection(), query.getFilter().getDocument()).isPresent()) {
            currentScore++;
        }
    }
}
