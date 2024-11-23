package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
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
    private boolean isTrivial;

    public FindSortModifierCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                CriterionDescription.FIND_SORT_MODIFIER.getDescription(),
                CriterionDescription.FIND_SORT_MODIFIER.getRequiredCount()
        );
        this.isTrivial = true;
    }

    @Override
    public void concreteCheck(Query query) {
        List<QueryParameter> parameters = query.getParameters();
        if (!parameters.isEmpty()) {
            parameters.get(0).accept(this);
            List<QueryModifier> modifiers = query.getModifiers();
            modifiers.stream()
                    .filter(modifier -> modifier.getModifier().equals("sort"))
                    .forEach(modifier -> modifier.getParameter().accept(this));
        }
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        if (isTrivial) {
            isTrivial = parameter.isTrivial();
            return;
        }

        if (!parameter.isTrivial()) {
            satisfied = true;
            currentCount++;
        }
    }
}
