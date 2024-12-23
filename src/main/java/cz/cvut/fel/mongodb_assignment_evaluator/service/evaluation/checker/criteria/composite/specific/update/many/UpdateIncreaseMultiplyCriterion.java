package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.update.many;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.UpdateQuery;
import org.bson.BsonDocument;

import java.util.List;

public class UpdateIncreaseMultiplyCriterion extends AssignmentCriterion<UpdateQuery> {
    public UpdateIncreaseMultiplyCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.UPDATE_INCREASE_MULTIPLY, UpdateQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(UpdateQuery query) {
        if (query.isUpdateMany()
            && query.containsFormulaOperator()) {
            List<BsonDocument> found = documentStorage.findDocumentByFilter(query.getCollection(), query.getFilter().getDocument());
            if (!found.isEmpty()) {
                currentScore++;
            }
        }
    }
}
