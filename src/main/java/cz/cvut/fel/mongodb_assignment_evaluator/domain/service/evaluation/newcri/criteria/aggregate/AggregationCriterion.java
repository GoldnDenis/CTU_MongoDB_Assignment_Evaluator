package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.criteria.aggregate;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.AggregateQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.EvaluationCriterion;
import org.bson.BsonDocument;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class AggregationCriterion extends EvaluationCriterion<AggregateQuery> {
    private final Set<String> expectedKeywords;
    private final int level;

    public AggregationCriterion(Set<String> keywords) {
        super(AggregateQuery.class);
        this.expectedKeywords = new HashSet<>(keywords);
        this.level = 0;
    }

    public AggregationCriterion(Set<String> keywords, int level) {
        super(AggregateQuery.class);
        this.expectedKeywords = new HashSet<>(keywords);
        this.level = level;
    }

    // todo revisit scoring logic
    @Override
    protected void evaluate(AggregateQuery query) {
        if (query.isTrivial()) {
            return;
        }
        List<BsonDocument> aggregationPipeline = query.getAggregationPipeline();
        if (level == 0) {
            processAggregator(aggregationPipeline);
        } else {
            processKeyword(aggregationPipeline);
        }
    }

    private void processAggregator(List<BsonDocument> aggregationPipeline) {
        for (String aggregator: expectedKeywords) {
            if (aggregationPipeline.stream()
                    .anyMatch(d -> BsonDocumentChecker.getRecursive(d, aggregator).isPresent())) {
                currentScore++;
                break;
            }
        }
    }

    private void processKeyword(List<BsonDocument> aggregationPipeline) {
        for (String aggregator: expectedKeywords) {
            if (aggregationPipeline.stream()
                    .anyMatch(d -> BsonDocumentChecker.getRecursive(d, aggregator, level).isPresent())) {
                currentScore++;
                break;
            }
        }
    }
}
