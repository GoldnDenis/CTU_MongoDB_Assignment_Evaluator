package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.updateone;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.UpdateCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

import java.util.List;

public class UpdateOneDocumentCriterion extends UpdateCriterion {
    public UpdateOneDocumentCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                CriterionDescription.UPDATE_ONE_DOCUMENT.getDescription(),
                CriterionDescription.UPDATE_ONE_DOCUMENT.getRequiredCount(),
                List.of(
                        "$set", "$rename", "$mul", "$inc",
                        "$min", "$max", "$currentDate"
                ), true
        );
        this.isUpdateOne = true;
    }

    @Override
    public void concreteCheck(Query query) {
        id = "";
        currentParameterIdx = 0;
        if (query.getOperator().equalsIgnoreCase("updateOne")) {
            collection = query.getCollection();
            List<QueryParameter> parameters = query.getParameters();
            if (parameters.size() >= 2) {
                parameters.get(currentParameterIdx).accept(this);
                parameters.get(currentParameterIdx).accept(this);
            }
        }
    }
}