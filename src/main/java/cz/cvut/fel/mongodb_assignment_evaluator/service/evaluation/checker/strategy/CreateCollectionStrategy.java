package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.collection.CreateOneCollectionCriterion;

import java.util.List;

public class CreateCollectionStrategy extends CheckerStrategy {
    public CreateCollectionStrategy(InsertedDocumentStorage mockDb) {
        super(mockDb, List.of(
                        new CreateOneCollectionCriterion(mockDb)
                ));
    }
}
