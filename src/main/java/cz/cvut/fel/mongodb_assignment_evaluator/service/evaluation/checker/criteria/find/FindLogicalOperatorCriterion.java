package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;
import org.bson.BsonValue;

import java.util.List;

public class FindLogicalOperatorCriterion extends AssignmentCriterion {
    public FindLogicalOperatorCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                Criteria.FIND_LOGICAL_OPERATOR.getDescription(),
                Criteria.FIND_LOGICAL_OPERATOR.getRequiredCount()
        );
    }

    @Override
    public void concreteCheck(Query query) {
        curParamIdx = 0;
        List<QueryParameter> parameters = query.getParameters();
        if (!parameters.isEmpty()) {
            parameters.get(0).accept(this);
        }
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        if (curParamIdx == 0) {
            if (contains(parameter, "$and", 1) ||
                    contains(parameter, "$or", 1) ||
                    contains(parameter, "$not")) {
                currentCount++;
                satisfied = true;
            }
        }
        curParamIdx++;
    }

    public boolean contains(DocumentParameter parameter, String operator, int level) {
        BsonValue found = parameter.getValue(operator, level);
        if (found != null && found.isArray()) {
            if (!found.asArray().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(DocumentParameter parameter, String operator) {
        BsonValue found = parameter.getValue(operator);
        if (found == null) {
            return false;
        }
        if (found.isDocument()) {
            if (!found.asDocument().isEmpty()) {
                return true;
            }
        } else if (found.isArray()) {
            if (!found.asArray().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
