package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.updatemany;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.UpdateCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

import java.util.List;

public class UpdateIncreaseMultiplyCriterion extends UpdateCriterion {
    public UpdateIncreaseMultiplyCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                Criteria.UPDATE_INCREASE_MULTIPLY.getDescription(),
                Criteria.UPDATE_INCREASE_MULTIPLY.getRequiredCount(),
                List.of("$mul", "$inc"),
                true
        );
        this.isUpdateOne = false;
    }

    @Override
    public void concreteCheck(Query query) {
        id = "";
        curParamIdx = 0;
        if (query.getOperator().equalsIgnoreCase("updateMany")) {
            collection = query.getCollection();
            List<QueryParameter> parameters = query.getParameters();
            if (parameters.size() >= 2) {
                parameters.get(curParamIdx).accept(this);
                parameters.get(curParamIdx).accept(this);
            }
        }
    }
}
