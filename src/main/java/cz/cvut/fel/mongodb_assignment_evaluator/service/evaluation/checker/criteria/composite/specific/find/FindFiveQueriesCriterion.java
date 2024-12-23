package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.find;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.Query;

import java.util.List;

public class FindFiveQueriesCriterion extends AssignmentCriterion {
    public FindFiveQueriesCriterion(InsertedDocumentStorage mockDb) {
        super(
                mockDb,
                Criteria.FIND_FIVE_QUERIES.getDescription(),
                Criteria.FIND_FIVE_QUERIES.getRequiredCount()
        );
    }

    @Override
    public void concreteCheck(Query query) {
        List<QueryParameter> parameters = query.getParameters();
        if (!parameters.isEmpty()) {
            if (!parameters.get(0).isTrivial()) {
                currentScore++;
                satisfied = true;
            }
        }
    }
}
