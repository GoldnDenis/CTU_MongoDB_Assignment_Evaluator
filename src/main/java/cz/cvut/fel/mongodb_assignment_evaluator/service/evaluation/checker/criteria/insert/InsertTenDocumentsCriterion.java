package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.insert;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.PipelineParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class InsertTenDocumentsCriterion extends AssignmentCriterion {
    private final Map<String, Integer> insertedDocumentsMap;
    private String currentCollection;

    public InsertTenDocumentsCriterion() {
        super(
                CriterionDescription.INSERT_TEN_DOCUMENTS.getDescription(),
                CriterionDescription.INSERT_TEN_DOCUMENTS.getRequiredCount()
        );
        this.insertedDocumentsMap = new HashMap<>();
        this.currentCollection = "";
    }

    @Override
    protected void concreteCheck(Query query) {
        List<QueryParameter> queryParameters = query.getParameters();
        if (!queryParameters.isEmpty()) {
            currentCollection = query.getCollection();
            if (currentCollection.isBlank()) {
                return;
            }

            queryParameters.get(0).accept(this);
        }
    }

    @Override
    protected boolean isFulfilled() {
        if (insertedDocumentsMap.isEmpty()) {
            System.out.println(currentCount + "/" + requiredCount);
            return false;
        }

        for (Map.Entry<String, Integer> entry : insertedDocumentsMap.entrySet()) {
            System.out.println(
                    "Documents inserted into '" +
                            entry.getKey() + "': " +
                            entry.getValue() + "/" + requiredCount
            );
        }
        return insertedDocumentsMap.values().stream()
                .filter(count -> count >= 10)
                .toList().size() == insertedDocumentsMap.size();
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        checkDocument(parameter.getDocument());
    }

    @Override
    public void visitPipelineParameter(PipelineParameter parameter) {
        for (DocumentParameter documentParameter: parameter.getParameterList()) {
            checkDocument(documentParameter.getDocument());
        }
    }

    private void checkDocument(Document document) {
        if (!document.isEmpty()) {
            if (!insertedDocumentsMap.containsKey(currentCollection)) {
                insertedDocumentsMap.put(currentCollection, 0);
            }
            insertedDocumentsMap.put(currentCollection, insertedDocumentsMap.get(currentCollection) + 1);
            satisfied = true;
        }
    }
}
