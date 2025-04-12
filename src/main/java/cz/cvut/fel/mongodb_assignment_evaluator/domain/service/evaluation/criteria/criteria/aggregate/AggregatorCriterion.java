package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.criteria.aggregate;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Aggregators;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.AggregateQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.EvaluationCriterion;

public class AggregatorCriterion extends EvaluationCriterion<AggregateQuery> {
    private final Aggregators requiredAggregator;

    public AggregatorCriterion(int priority, Aggregators requiredAggregator) {
        super(AggregateQuery.class, priority);
        this.requiredAggregator = requiredAggregator;
//        this.expectedKeywords = new HashSet<>(keywords);
//        this.level = 0;
    }

//    public AggregatorCriterion(Set<String> keywords, int level) {
//        super(AggregateQuery.class);
//        this.expectedKeywords = new HashSet<>(keywords);
//        this.level = level;
//    }

    // todo revisit scoring logic
    @Override
    protected void evaluate(AggregateQuery query) {
//        if (query.isTrivial()) {
//            return;
//        }
//        List<BsonDocument> aggregationPipeline = query.getAggregationPipeline();
//        if (level == 0) {
//            processAggregator(aggregationPipeline);
//        } else {
//            processKeyword(aggregationPipeline);
//        }
    }

//    private void processAggregator(List<BsonDocument> aggregationPipeline) {
//        for (String aggregator: expectedKeywords) {
//            if (aggregationPipeline.stream()
//                    .anyMatch(d -> BsonDocumentChecker.getRecursive(d, aggregator).isPresent())) {
//                currentScore++;
//                break;
//            }
//        }
//    }
//
//    private void processKeyword(List<BsonDocument> aggregationPipeline) {
//        for (String aggregator: expectedKeywords) {
//            if (aggregationPipeline.stream()
//                    .anyMatch(d -> BsonDocumentChecker.getRecursive(d, aggregator, level).isPresent())) {
//                currentScore++;
//                break;
//            }
//        }
//    }
}
