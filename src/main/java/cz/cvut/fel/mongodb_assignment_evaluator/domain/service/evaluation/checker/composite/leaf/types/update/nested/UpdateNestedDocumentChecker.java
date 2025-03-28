package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.update.nested;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.UpdateGroups;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.UpdateQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.CheckerLeaf;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.BsonValue;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class UpdateNestedDocumentChecker extends CheckerLeaf<UpdateQuery> {
    private final Pattern nestedPattern = Pattern.compile("^[a-zA-Z]+[a-zA-Z0-9_]*(\\.(\\$(\\[[a-zA-Z0-9]*\\])?|[0-9]+))*(\\.[a-zA-Z]+[a-zA-Z0-9_]*)+");

    public UpdateNestedDocumentChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.UPDATE_NESTED_DOCUMENT, UpdateQuery.class, documentStorage);
    }

    @Override
    protected void concreteCheck(UpdateQuery query) {
        Optional<BsonDocument> optFoundDocument = documentStorage.findDocument(query.getCollection(), query.getFilter());
        if (optFoundDocument.isEmpty()) {
            return;
        }
        BsonDocument foundDocument = optFoundDocument.get();
        for (BsonDocument updateDocument : query.getUpdateDocuments()) {
            List<BsonValue> foundOperatorValues = BsonDocumentChecker.getAllRecursive(updateDocument, UpdateGroups.ADD.getOperations(), 1);
            for (BsonValue operatorValue : foundOperatorValues) {
                if (!operatorValue.isDocument()) {
                    continue;
                }
                Optional<String> foundNestedKey = BsonDocumentChecker.findKeyMatchesPattern(operatorValue.asDocument(), nestedPattern);
                if (foundNestedKey.isEmpty()) {
                    continue;
                }
                if (BsonDocumentChecker.containsRecursive(foundDocument, new BsonString(foundNestedKey.get()), 1)) {
                    currentScore++;
                }
            }
        }
    }
}
