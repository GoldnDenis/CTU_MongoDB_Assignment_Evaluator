package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.insert;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

//todo INTERLINK
public class InsertInterlinkCriterion extends AssignmentCriterion {
    public InsertInterlinkCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                Criteria.INSERT_INTERLINK.getDescription(),
                Criteria.INSERT_INTERLINK.getRequiredCount()
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
