package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.update.field;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.UpdateGroups;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.UpdateQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.CheckerLeaf;

public class UpdateAddFieldChecker extends CheckerLeaf<UpdateQuery> {
    public UpdateAddFieldChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.UPDATE_ADD_FIELD, UpdateQuery.class, documentStorage);
    }

    @Override
    protected void concreteCheck(UpdateQuery query) {
        if (!query.containsOneOfUpdates(UpdateGroups.ADD)) {
            return;
        }
        if (documentStorage.findDocument(query.getCollection(), query.getFilter()).isEmpty()) {
            currentScore++;
        }
    }
}
