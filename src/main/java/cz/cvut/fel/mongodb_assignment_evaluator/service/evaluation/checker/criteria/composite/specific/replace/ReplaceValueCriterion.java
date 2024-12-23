package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.replace;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.Query;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.ReplaceOneQuery;
import org.bson.BsonDocument;

import java.util.List;

public class ReplaceValueCriterion extends AssignmentCriterion<ReplaceOneQuery> {
    public ReplaceValueCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.REPLACE_VALUE, ReplaceOneQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(ReplaceOneQuery query) {
        List<BsonDocument> found = documentStorage.findDocumentByFilter(query.getCollection(), query.getFilter().getDocument());
        if (found.isEmpty()) {
            return;
        }
        if (!query.getReplacement().isTrivial()) {
            currentScore++;
        }
    }
}
