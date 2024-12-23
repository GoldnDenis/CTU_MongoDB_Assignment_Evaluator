package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.insert;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.InsertQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.Query;

//todo INTERLINK
public class InsertInterlinkCriterion extends AssignmentCriterion<InsertQuery> {
    public InsertInterlinkCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.INSERT_INTERLINK, InsertQuery.class, documentStorage);
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
