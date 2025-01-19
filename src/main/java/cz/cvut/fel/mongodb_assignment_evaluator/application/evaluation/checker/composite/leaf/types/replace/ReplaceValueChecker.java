package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.replace;

import cz.cvut.fel.mongodb_assignment_evaluator.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.ReplaceOneQuery;

public class ReplaceValueChecker extends CheckerLeaf<ReplaceOneQuery> {
    public ReplaceValueChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.REPLACE_VALUE, ReplaceOneQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(ReplaceOneQuery query) {
        if (documentStorage.findDocument(query.getCollection(), query.getFilter()).isEmpty()) {
            return;
        }
        if (!query.getReplacement().isEmpty()) {
            currentScore++;
        }
    }
}
