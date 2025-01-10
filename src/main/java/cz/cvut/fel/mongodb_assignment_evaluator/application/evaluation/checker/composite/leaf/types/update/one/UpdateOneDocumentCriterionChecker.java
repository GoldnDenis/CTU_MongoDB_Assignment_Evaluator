package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.update.one;

import cz.cvut.fel.mongodb_assignment_evaluator.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.UpdateGroups;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.UpdateQuery;

public class UpdateOneDocumentCriterionChecker extends CheckerLeaf<UpdateQuery> {
    public UpdateOneDocumentCriterionChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.UPDATE_ONE_DOCUMENT, UpdateQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(UpdateQuery query) {
        if (!query.isUpdateOne() ||
                !query.containsOneOfUpdates(UpdateGroups.UPDATE)) {
            return;
        }
        if (documentStorage.findDocument(query.getCollection(), query.getFilter()).isPresent()) {
            currentScore++;
        }
    }
}
