package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.find.projection;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.FindQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.CheckerLeaf;
import org.bson.BsonDocument;

import java.util.Map;

public class NegativeProjectionChecker extends CheckerLeaf<FindQuery> {
    public NegativeProjectionChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.NEGATIVE_PROJECTION, FindQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(FindQuery query) {
        BsonDocument projection = query.getProjection();
        if (projection.isEmpty()) {
            return;
        }
        if (projection.entrySet().stream()
                .filter(e -> !e.getKey().equalsIgnoreCase("_id"))
                .map(Map.Entry::getValue)
                .allMatch(e ->
                        (e.isBoolean() && !e.asBoolean().getValue()) ||
                                (e.isNumber() && e.asInt32().getValue() == 0)
                )) {
            currentScore++;
        }
        if (query.projectionIsNegative()) {
            currentScore++;
        }
    }
}
