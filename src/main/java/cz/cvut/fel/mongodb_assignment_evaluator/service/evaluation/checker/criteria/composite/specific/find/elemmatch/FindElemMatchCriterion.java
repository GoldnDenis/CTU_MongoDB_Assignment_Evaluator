package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.find.elemmatch;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.QuerySelectors;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.FindQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.Query;
import org.bson.BsonValue;

import java.util.List;

public class FindElemMatchCriterion extends AssignmentCriterion<FindQuery> {
    public FindElemMatchCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.FIND_ELEM_MATCH, FindQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(FindQuery query) {
        if (query.filterContainsSelector(QuerySelectors.ELEM_MATCH)) {
            currentScore++;
        }
    }
}
