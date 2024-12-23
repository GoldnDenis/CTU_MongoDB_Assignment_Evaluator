package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.find;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.Query;
import org.bson.BsonValue;

import java.util.List;

public class FindElemMatchUsedCriterion extends AssignmentCriterion {
    public FindElemMatchUsedCriterion(InsertedDocumentStorage mockDb) {
        super(
                mockDb,
                Criteria.FIND_ELEM_MATCH_USED.getDescription(),
                Criteria.FIND_ELEM_MATCH_USED.getRequiredCount()
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
            BsonValue found = parameter.getValue("$elemMatch");
            if (found != null && found.isDocument()) {
                if (!found.asDocument().isEmpty()) {
                    currentScore++;
                    satisfied = true;
                }
            }
        }
        curParamIdx++;
    }
}
