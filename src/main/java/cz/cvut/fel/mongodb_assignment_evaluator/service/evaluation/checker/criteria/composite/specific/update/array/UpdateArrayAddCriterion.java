package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.update.array;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.group.UpdateCriteriaGroup;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.UpdateQuery;
import org.bson.BsonDocument;

import java.util.List;

public class UpdateArrayAddCriterion extends AssignmentCriterion<UpdateQuery> {
    public UpdateArrayAddCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.UPDATE_ARRAY_ADD, UpdateQuery.class, documentStorage);
    }

    // todo :: potentially incorrect finding logic
    @Override
    protected void concreteCheck(UpdateQuery query) {
        if (query.containsArrayUpdateOperator()) {
            List<BsonDocument> found = documentStorage.findDocumentByFilter(query.getCollection(), query.getFilter().getDocument());
            if (!found.isEmpty()) {
                currentScore++;
            }
        }
    }
}
