package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.aggregate.interlink;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Stages;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.AggregateQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.CheckerLeaf;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.BsonValue;

import java.util.Optional;

public class InterlinkChecker extends CheckerLeaf<AggregateQuery> {
    public InterlinkChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.INTERLINK, AggregateQuery.class, documentStorage);
    }

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
