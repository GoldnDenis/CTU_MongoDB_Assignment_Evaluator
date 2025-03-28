package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.update.array;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.UpdateGroups;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.UpdateQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.CheckerLeaf;

public class UpdateArrayRemoveChecker extends CheckerLeaf<UpdateQuery> {
    public UpdateArrayRemoveChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.UPDATE_ARRAY_REMOVE, UpdateQuery.class, documentStorage);
    }

    @Override
    protected void concreteCheck(UpdateQuery query) {
        if (!query.containsOneOfUpdates(UpdateGroups.ARRAY_REMOVE)) {
            return;
        }
        if (documentStorage.findDocument(query.getCollection(), query.getFilter()).isPresent()) {
            currentScore++;
        }
    }
}
