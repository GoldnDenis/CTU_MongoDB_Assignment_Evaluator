package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find.search;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.BsonChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import org.bson.BsonDocument;

import java.util.Set;

public class FindConditionCriterion extends FindFilterCriterion {
    public FindConditionCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                Criteria.FIND_CONDITION.getDescription(),
                Criteria.FIND_CONDITION.getRequiredCount(),
                Set.of("$gt", "$lt", "$gte", "$lte")
        );
    }

    @Override
    protected boolean inspectFilter(BsonDocument filterDocument, int maxDepth) {
        Object found = BsonChecker.getValue(filterDocument, maxDepth, expectedOperators);
        return found != null;
    }
}
