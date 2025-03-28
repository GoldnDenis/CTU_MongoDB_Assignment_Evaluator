package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.replace;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.ReplaceOneQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.CheckerLeaf;

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
