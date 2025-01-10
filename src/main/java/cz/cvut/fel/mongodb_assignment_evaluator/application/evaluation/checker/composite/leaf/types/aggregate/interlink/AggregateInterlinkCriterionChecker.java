package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.aggregate.interlink;

import cz.cvut.fel.mongodb_assignment_evaluator.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.Stages;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.AggregateQuery;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.BsonValue;

import java.util.Optional;

public class AggregateInterlinkCriterionChecker extends CheckerLeaf<AggregateQuery> {
    public AggregateInterlinkCriterionChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.AGGREGATE_INTERLINK, AggregateQuery.class, documentStorage);
    }

    // todo :: open to refactoring
    @Override
    public void concreteCheck(AggregateQuery query) {
        for (BsonDocument aggregationDocument: query.getAggregationPipeline()) {
            Optional<BsonValue> optLookupValue = BsonDocumentChecker.getRecursive(aggregationDocument, Stages.LOOKUP.getValue(), Stages.LOOKUP.getLevel());
            if (optLookupValue.isEmpty()) {
                continue;
            }
            BsonValue lookupValue = optLookupValue.get();
            if (lookupValue.isDocument()) {
                BsonDocument lookupDocument = lookupValue.asDocument();
                if (!lookupDocument.containsKey("from") || !lookupDocument.containsKey("localField") || !lookupDocument.containsKey("foreignField")) {
                    continue;
                }
                BsonString from = lookupDocument.getString("from");
                BsonString localField = lookupDocument.getString("localField");
                BsonString foreignField = lookupDocument.getString("foreignField");
                if (documentStorage.getCollectionDocuments(query.getCollection()).stream().anyMatch(d -> d.containsKey(localField.getValue())) &&
                        documentStorage.getCollectionDocuments(from.getValue()).stream().anyMatch(d -> d.containsKey(foreignField.getValue()))) {
                    currentScore++;
                    break;
                }
            }
        }
    }
}
