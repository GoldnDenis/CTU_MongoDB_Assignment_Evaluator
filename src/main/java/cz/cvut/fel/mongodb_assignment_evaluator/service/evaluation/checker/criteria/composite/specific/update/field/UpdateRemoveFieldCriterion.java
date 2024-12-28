package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.update.field;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.UpdateGroups;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.UpdateQuery;
import org.bson.BsonDocument;

import java.util.List;

public class UpdateRemoveFieldCriterion extends AssignmentCriterion<UpdateQuery> {
    public UpdateRemoveFieldCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.UPDATE_REMOVE_FIELD, UpdateQuery.class, documentStorage);
    }

    @Override
    protected void concreteCheck(UpdateQuery query) {
        if (!query.containsOneOfUpdates(UpdateGroups.REMOVE)) {
            return;
        }
        if (documentStorage.findDocument(query.getCollection(), query.getFilter().getDocument()).isPresent()) {
            currentScore++;
        }
    }
}
