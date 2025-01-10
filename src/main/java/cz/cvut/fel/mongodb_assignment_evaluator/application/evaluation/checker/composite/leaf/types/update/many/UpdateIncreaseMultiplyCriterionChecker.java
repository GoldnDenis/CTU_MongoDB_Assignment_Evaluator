package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.update.many;

import cz.cvut.fel.mongodb_assignment_evaluator.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.UpdateGroups;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.UpdateQuery;

public class UpdateIncreaseMultiplyCriterionChecker extends CheckerLeaf<UpdateQuery> {
    public UpdateIncreaseMultiplyCriterionChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.UPDATE_INCREASE_MULTIPLY, UpdateQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(UpdateQuery query) {
        if (!query.isUpdateMany() ||
            !query.containsOneOfUpdates(UpdateGroups.FORMULA)) {
            return;
        }
        if (documentStorage.findDocument(query.getCollection(), query.getFilter()).isPresent()) {
            currentScore++;
        }
    }
}
