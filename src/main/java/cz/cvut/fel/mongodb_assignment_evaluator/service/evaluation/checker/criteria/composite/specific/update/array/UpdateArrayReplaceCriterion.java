package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.update.array;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.UpdateGroups;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.UpdateQuery;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.BsonValue;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class UpdateArrayReplaceCriterion extends AssignmentCriterion<UpdateQuery> {
    private final Pattern arrayPattern = Pattern.compile("^[a-zA-Z]+[a-zA-Z0-9_]*(\\.[a-zA-Z]+[a-zA-Z0-9_]*)*(\\.((\\$(\\[[a-zA-Z0-9]*\\])?)|[0-9]+))+");

    public UpdateArrayReplaceCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.UPDATE_ARRAY_REPLACE, UpdateQuery.class, documentStorage);
    }

    @Override
    protected void concreteCheck(UpdateQuery query) {
        Optional<BsonDocument> optFoundDocument = documentStorage.findDocument(query.getCollection(), query.getFilter().getDocument());
        if (optFoundDocument.isEmpty()) {
            return;
        }
        BsonDocument foundDocument = optFoundDocument.get();
        for (DocumentParameter updateDocumentParameter : query.getUpdateDocuments()) {
            List<BsonValue> foundOperatorValues = BsonDocumentChecker.getAllRecursive(updateDocumentParameter.getDocument(), UpdateGroups.ADD.getOperations(), 1);
            for (BsonValue operatorValue : foundOperatorValues) {
                if (!operatorValue.isDocument()) {
                    continue;
                }
                Optional<String> foundNestedKey = BsonDocumentChecker.findKeyMatchesPattern(operatorValue.asDocument(), arrayPattern);
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
