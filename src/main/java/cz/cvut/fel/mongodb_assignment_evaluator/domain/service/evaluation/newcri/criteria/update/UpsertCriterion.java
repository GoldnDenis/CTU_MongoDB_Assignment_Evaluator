package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.criteria.update;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.UpdateQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.EvaluationCriterion;
import org.bson.BsonDocument;
import org.bson.BsonValue;

import java.util.Optional;

public class UpsertCriterion extends EvaluationCriterion<UpdateQuery> {
    private final boolean isPositive;

    public UpsertCriterion(boolean isPositive) {
        super(UpdateQuery.class);
        this.isPositive = isPositive;
    }

    protected void evaluate(UpdateQuery query) {
        BsonDocument options = query.getOptions();
        boolean found = isPositive ? processPositiveUpsert(options) : processNegativeUpsert(options);
        if (found) {
            currentScore++;
        }
    }

    private boolean processPositiveUpsert(BsonDocument options) {
        if (options.isEmpty()) {
            return false;
        }
        Optional<BsonValue> optUpsertValue = BsonDocumentChecker.getRecursive(options, "upsert", 1);
        if (optUpsertValue.isEmpty()) {
            return false;
        }
        BsonValue upsertValue = optUpsertValue.get();
        return (upsertValue.isBoolean() && upsertValue.asBoolean().getValue()) ||
                (upsertValue.isInt32() && upsertValue.asInt32().getValue() == 1);
    }

    private boolean processNegativeUpsert(BsonDocument options) {
        if (options.isEmpty()) {
            return true;
        }
        Optional<BsonValue> optUpsertValue = BsonDocumentChecker.getRecursive(options, "upsert", 1);
        if (optUpsertValue.isEmpty()) {
            return true;
        }
        BsonValue upsertValue = optUpsertValue.get();
        return (upsertValue.isBoolean() && !upsertValue.asBoolean().getValue()) ||
                (upsertValue.isInt32() && upsertValue.asInt32().getValue() == 0);
    }
}
