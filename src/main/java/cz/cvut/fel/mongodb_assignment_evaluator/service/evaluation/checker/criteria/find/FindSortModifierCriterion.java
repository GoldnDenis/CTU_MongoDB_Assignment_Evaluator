package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.StringParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class FindSortModifierCriterion extends AssignmentCriterion {
    public FindSortModifierCriterion() {
        super(
                CriterionDescription.FIND_SORT_MODIFIER.getDescription(),
                CriterionDescription.FIND_SORT_MODIFIER.getRequiredCount()
        );
    }

    @Override
    public void concreteCheck(Query query) {
        List<QueryModifier> modifiers = query.getModifiers();
        modifiers.stream()
                .filter(modifier -> modifier.getModifier().equals("sort"))
                .forEach(modifier -> modifier.getParameter().accept(this));
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        Document document = parameter.getDocument();
        if (!document.isEmpty()) {
            currentCount++;
            satisfied = true;
        }
    }
}
