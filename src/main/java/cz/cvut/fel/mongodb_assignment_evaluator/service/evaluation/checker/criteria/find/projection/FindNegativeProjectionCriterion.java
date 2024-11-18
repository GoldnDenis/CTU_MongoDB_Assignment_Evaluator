package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find.projection;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.BsonChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.StringParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class FindNegativeProjectionCriterion extends AssignmentCriterion {
    public FindNegativeProjectionCriterion() {
        super(
                CriterionDescription.FIND_NEGATIVE_PROJECTION.getDescription(),
                CriterionDescription.FIND_NEGATIVE_PROJECTION.getRequiredCount()
        );
    }

    @Override
    public void concreteCheck(Query query) {
        List<QueryParameter> parameters = query.getParameters();
        if (parameters.size() >= 2) {
            parameters.get(1).accept(this);
        }
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        Document document = parameter.getDocument();
        for (String key : document.keySet()) {
            if (key.equals("_id")) {
                continue;
            }
            if (document.get(key).equals(true) ||
                    document.get(key).equals(1)) {
                return;
            }
        }
        currentCount++;
        satisfied = true;
    }
}