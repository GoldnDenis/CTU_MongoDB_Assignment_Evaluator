package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.BsonChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.StringParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class FindLogicalOperatorCriterion extends AssignmentCriterion {
    public FindLogicalOperatorCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                CriterionDescription.FIND_LOGICAL_OPERATOR.getDescription(),
                CriterionDescription.FIND_LOGICAL_OPERATOR.getRequiredCount()
        );
    }

    @Override
    public void concreteCheck(Query query) {
        List<QueryParameter> parameters = query.getParameters();
        if (!parameters.isEmpty()) {
            parameters.get(0).accept(this);
        }
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        if (contains(parameter ,"$and", 1) ||
                contains(parameter ,"$or", 1) ||
                contains(parameter ,"$not")) {
            currentCount++;
            satisfied = true;
        }
    }

    public boolean contains(DocumentParameter parameter, String operator, int level) {
        Object found = parameter.getValue(operator, level);
        if (found instanceof List<?>) {
            if (!((List<?>) found).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(DocumentParameter parameter, String operator) {
        Object found = parameter.getValue(operator);
        if (found instanceof Document) {
            if (!((Document) found).isEmpty()) {
                return true;
            }
        } else if (found instanceof List<?>) {
            if (!((List<?>) found).isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
