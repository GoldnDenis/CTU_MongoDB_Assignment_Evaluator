package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.BsonChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;
import org.bson.BsonValue;
import org.bson.Document;

import java.util.List;

public class FindElemMatchUsedCriterion extends AssignmentCriterion {
    public FindElemMatchUsedCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                CriterionDescription.FIND_ELEM_MATCH_USED.getDescription(),
                CriterionDescription.FIND_ELEM_MATCH_USED.getRequiredCount()
        );
    }

    @Override
    public void concreteCheck(Query query) {
        currentParameterIdx = 0;
        List<QueryParameter> parameters = query.getParameters();
        if (!parameters.isEmpty()) {
            parameters.get(0).accept(this);
        }
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        if (currentParameterIdx == 0) {
            BsonValue found = parameter.getValue("$elemMatch");
            if (found != null && found.isDocument()) {
                if (!found.asDocument().isEmpty()) {
                    currentCount++;
                    satisfied = true;
                }
            }
        }
        currentParameterIdx++;
    }
}
