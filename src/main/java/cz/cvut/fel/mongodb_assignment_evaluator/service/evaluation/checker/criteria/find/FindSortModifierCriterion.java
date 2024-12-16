package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

import java.util.List;

public class FindSortModifierCriterion extends AssignmentCriterion {
    public FindSortModifierCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                Criteria.FIND_SORT_MODIFIER.getDescription(),
                Criteria.FIND_SORT_MODIFIER.getRequiredCount()
        );
    }

    @Override
    public void concreteCheck(Query query) {
        List<QueryParameter> parameters = query.getParameters();
        if (!parameters.isEmpty()) {
            if (parameters.get(0).isTrivial()) {
                return;
            }
            List<QueryModifier> modifiers = query.getModifiers();
            modifiers.stream()
                    .filter(modifier -> modifier.getModifier().equalsIgnoreCase("sort"))
                    .forEach(modifier -> modifier.getParameter().accept(this));
        }
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        if (!parameter.isTrivial()) {
            satisfied = true;
            currentCount++;
        }
    }
}
