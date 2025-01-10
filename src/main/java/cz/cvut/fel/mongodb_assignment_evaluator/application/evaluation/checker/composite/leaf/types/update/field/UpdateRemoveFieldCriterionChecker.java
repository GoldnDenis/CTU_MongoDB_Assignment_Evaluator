package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.update.field;

import cz.cvut.fel.mongodb_assignment_evaluator.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.UpdateGroups;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.UpdateQuery;

public class UpdateRemoveFieldCriterionChecker extends CheckerLeaf<UpdateQuery> {
    public UpdateRemoveFieldCriterionChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.UPDATE_REMOVE_FIELD, UpdateQuery.class, documentStorage);
    }

    @Override
    protected void concreteCheck(UpdateQuery query) {
        if (!query.containsOneOfUpdates(UpdateGroups.REMOVE)) {
            return;
        }
        if (documentStorage.findDocument(query.getCollection(), query.getFilter()).isPresent()) {
            currentScore++;
        }
    }
}
