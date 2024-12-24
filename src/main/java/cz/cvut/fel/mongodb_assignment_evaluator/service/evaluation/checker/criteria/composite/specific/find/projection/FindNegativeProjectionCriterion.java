package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.find.projection;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.FindQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.Query;
import org.bson.BsonDocument;
import org.bson.BsonValue;

import java.util.List;

public class FindNegativeProjectionCriterion extends AssignmentCriterion<FindQuery> {
    public FindNegativeProjectionCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.FIND_NEGATIVE_PROJECTION, FindQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(FindQuery query) {
        if (query.projectionIsNegative()) {
            currentScore++;
        }
    }
}
