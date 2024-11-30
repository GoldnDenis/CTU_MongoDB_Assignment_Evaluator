package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find.search;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.BsonChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import org.bson.Document;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class FindConditionCriterion extends FindFilterCriterion {
    public FindConditionCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                CriterionDescription.FIND_CONDITION.getDescription(),
                CriterionDescription.FIND_CONDITION.getRequiredCount(),
                Set.of("$gt", "$lt", "$gte", "$lte")
        );
    }

    @Override
    protected boolean inspectFilter(Document filterDocument, int maxDepth) {
        Object found = BsonChecker.getValue(filterDocument, maxDepth, expectedOperators);
        return found != null;
    }
}
