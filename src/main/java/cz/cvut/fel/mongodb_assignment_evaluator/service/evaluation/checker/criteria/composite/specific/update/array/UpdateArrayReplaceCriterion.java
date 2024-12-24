package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.update.array;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.bson.BsonChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.UpdateQuery;
import org.bson.BsonDocument;
import org.bson.BsonValue;

import java.util.*;
import java.util.regex.Pattern;

//todo reformat further
public class UpdateArrayReplaceCriterion extends AssignmentCriterion<UpdateQuery> {
    private final Pattern arrayPattern = Pattern.compile("^[a-zA-Z]+[a-zA-Z0-9_]*(\\.[a-zA-Z]+[a-zA-Z0-9_]*)*(\\.\\$(\\[[a-zA-Z0-9]*\\])?|[0-9]+)+");

    public UpdateArrayReplaceCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.UPDATE_ARRAY_REPLACE, UpdateQuery.class, documentStorage);
    }

    @Override
    protected void concreteCheck(UpdateQuery query) {
        for (DocumentParameter updateDocumentParameter : query.getUpdateDocuments()) {
            List<BsonValue> foundOperatorValues = updateDocumentParameter.getValues(UpdateQuery.ADD_OPERATOR_SET);
            if (foundOperatorValues.isEmpty()) {
                continue;
            }
            List<BsonDocument> foundDocuments = documentStorage.findDocumentByFilter(query.getCollection(), query.getFilter().getDocument());
            if (foundDocuments.isEmpty()) {
                continue;
            }

            for (BsonValue foundValue : foundOperatorValues) {
                if (foundValue.isDocument()) {
                    String key = BsonChecker.findKeyMatchesPattern(
                            foundValue.asDocument(),
                            1,
                            arrayPattern
                    );
                    if (foundDocuments.stream().anyMatch(document -> document.containsKey(key))) {
                        currentScore++;
                        return;
                    }
                }
            }
        }
    }
}
