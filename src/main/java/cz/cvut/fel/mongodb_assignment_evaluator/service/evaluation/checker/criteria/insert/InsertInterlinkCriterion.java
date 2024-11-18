package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.insert;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.StringParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

import java.util.ArrayList;
import java.util.List;

//todo INTERLINK
public class InsertInterlinkCriterion extends AssignmentCriterion {
    public InsertInterlinkCriterion() {
        super(
                CriterionDescription.INSERT_INTERLINK.getDescription(),
                CriterionDescription.INSERT_INTERLINK.getRequiredCount()
        );
    }

    @Override
    public void concreteCheck(Query query) {
//        List<QueryParameter> queryParameters = query.getParameters();
//        if (!queryParameters.isEmpty()) {
//            queryParameters.get(0).accept(this);
//        }
    }

//    @Override
//    public void visitStringParameter(StringParameter parameter) {
//    }
}
