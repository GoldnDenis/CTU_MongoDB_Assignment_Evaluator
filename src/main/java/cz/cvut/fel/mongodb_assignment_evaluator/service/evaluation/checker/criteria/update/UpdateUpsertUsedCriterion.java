package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.StringParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class UpdateUpsertUsedCriterion extends AssignmentCriterion {
    public UpdateUpsertUsedCriterion() {
        super(
                CriterionDescription.UPDATE_UPSERT_USED.getDescription(),
                CriterionDescription.UPDATE_UPSERT_USED.getRequiredCount()
        );
    }

    @Override
    public void concreteCheck(Query query) {
        List<QueryParameter> parameters = query.getParameters();
        if (parameters.size() >= 3) {
            parameters.get(2).accept(this);
        }
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        Document document = parameter.getDocument();
        if (document.containsKey("$upsert")) {
            if (document.get("$upsert").equals(true) ||
            document.get("$upsert").equals(1)) {
                currentCount++;
                satisfied = true;
            }
        }
    }
}
