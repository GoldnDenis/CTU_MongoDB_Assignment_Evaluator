package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.criteria.find;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.FindQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.EvaluationCriterion;
import org.bson.BsonDocument;

import java.util.Map;

public class ProjectionCriterion extends EvaluationCriterion<FindQuery> {
    private final boolean isPositive;

    public ProjectionCriterion(boolean isPositive) {
        super(FindQuery.class);
        this.isPositive = isPositive;
    }

    @Override
    protected void evaluate(FindQuery query) {
        BsonDocument projection = query.getProjection();
        if (isPositive) {
            processPositiveProjection(projection);
        } else {
            processNegativeProjection(projection);
        }
    }

    private void processPositiveProjection(BsonDocument projection) {
        if (projection.entrySet().stream()
                .filter(e -> !e.getKey().equalsIgnoreCase("_id"))
                .map(Map.Entry::getValue)
                .allMatch(e ->
                        (e.isBoolean() && e.asBoolean().getValue()) ||
                                (e.isNumber() && e.asInt32().getValue() == 1)
                )) {
            currentScore++;
        }
    }

    private void processNegativeProjection(BsonDocument projection) {
        if (projection.entrySet().stream()
                .filter(e -> !e.getKey().equalsIgnoreCase("_id"))
                .map(Map.Entry::getValue)
                .allMatch(e ->
                        (e.isBoolean() && !e.asBoolean().getValue()) ||
                                (e.isNumber() && e.asInt32().getValue() == 0)
                )) {
            currentScore++;
        }
    }
}
