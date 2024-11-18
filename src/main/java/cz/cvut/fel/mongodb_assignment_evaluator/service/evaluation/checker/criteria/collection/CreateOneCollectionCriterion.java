package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.collection;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.StringParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

import java.util.ArrayList;
import java.util.List;

public class CreateOneCollectionCriterion extends AssignmentCriterion {
    private final List<String> createdCollections;

    public CreateOneCollectionCriterion() {
        super(
                CriterionDescription.CREATE_ONE_COLLECTION.getDescription(),
                CriterionDescription.CREATE_ONE_COLLECTION.getRequiredCount()
        );
        this.createdCollections = new ArrayList<>();
    }

    @Override
    public void concreteCheck(Query query) {
        List<QueryParameter> queryParameters = query.getParameters();

        if (!queryParameters.isEmpty()) {
            queryParameters.get(0).accept(this);
        }
    }

//    @Override
//    protected boolean isFulfilled() {
//        return !createdCollections.isEmpty();
//    }
//
//    @Override
//    protected void generatePositiveFeedback() {
//        System.out.print("\tcreatedCollections=");
//        System.out.println(createdCollections);
//    }
//
//    @Override
//    protected void generateNegativeFeedback() {
//        System.out.println("No collections were created.");
//    }

    @Override
    public void visitStringParameter(StringParameter parameter) {
        String collection = parameter.getValue();
        if (!createdCollections.contains(collection)) {
            createdCollections.add(parameter.getValue());

            currentCount++;
            satisfied = true;
        }
    }
}
