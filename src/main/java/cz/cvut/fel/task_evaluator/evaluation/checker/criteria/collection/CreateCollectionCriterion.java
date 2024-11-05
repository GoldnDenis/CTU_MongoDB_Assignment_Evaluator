package cz.cvut.fel.task_evaluator.evaluation.checker.criteria.collection;

import cz.cvut.fel.task_evaluator.enums.CriterionMessages;
import cz.cvut.fel.task_evaluator.evaluation.checker.criteria.Criterion;
import cz.cvut.fel.task_evaluator.model.query.Query;
import cz.cvut.fel.task_evaluator.model.query.parameter.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CreateCollectionCriterion extends Criterion {
    private final Set<String> createdCollections;

    public CreateCollectionCriterion() {
        super(CriterionMessages.CREATE_COLLECTION.getMessage());
        this.createdCollections = new HashSet<>();
    }

    @Override
    public void check(Query query) {
        List<QueryParameter> queryParameters = query.getParameters();
        if (!queryParameters.isEmpty()) {
            queryParameters.get(0).accept(this);
        }
    }

    @Override
    public void generateFeedback() {
        System.out.println();
        System.out.print("Criteria - \"");
        System.out.print(assignmentMessage);
        System.out.print("\": ");
        if (isFulfilled()) {
            System.out.print("\nCreated collections: [ ");
            for (String collectionName : createdCollections) {
                System.out.print(collectionName + " ");
            }
            System.out.println("]");
        } else {
            System.err.println("No collections were created.");
        }
    }

    @Override
    protected boolean isFulfilled() {
        return !createdCollections.isEmpty();
    }

    @Override
    public void visitStringParameter(StringParameter parameter) {
        createdCollections.add(parameter.getValue());
    }
}
