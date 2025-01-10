package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.update.upsert;

import cz.cvut.fel.mongodb_assignment_evaluator.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.UpdateQuery;

public class UpdateUpsertUsedCriterionChecker extends CheckerLeaf<UpdateQuery> {
    public UpdateUpsertUsedCriterionChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.UPDATE_UPSERT_USED, UpdateQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(UpdateQuery query) {
        if (query.upsertIsPositive()) {
            currentScore++;
        }
    }
}
