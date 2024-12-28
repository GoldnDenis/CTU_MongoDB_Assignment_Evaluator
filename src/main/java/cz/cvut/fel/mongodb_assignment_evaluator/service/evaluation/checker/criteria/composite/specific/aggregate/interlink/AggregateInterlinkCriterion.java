package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.aggregate.interlink;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Stages;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.AggregateQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.InsertQuery;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.BsonValue;

import java.util.List;
import java.util.Optional;

public class AggregateInterlinkCriterion extends AssignmentCriterion<AggregateQuery> {
    public AggregateInterlinkCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.AGGREGATE_INTERLINK, AggregateQuery.class, documentStorage);
    }

    // todo :: open to refactoring
    @Override
    public void concreteCheck(AggregateQuery query) {
        for (DocumentParameter aggregationDocumentParameter: query.getAggregationPipeline()) {
            BsonDocument aggregationDocument = aggregationDocumentParameter.getDocument();
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
