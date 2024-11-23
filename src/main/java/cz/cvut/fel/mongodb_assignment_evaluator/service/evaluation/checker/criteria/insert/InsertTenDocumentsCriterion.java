package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.insert;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.PipelineParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

// TODO REFACTOR 10 INSERTS
public class InsertTenDocumentsCriterion extends AssignmentCriterion {
    private final Map<String, Integer> insertedDocumentsMap;
    private final int requiredCountIntoOne;
    private String currentCollection;

    public InsertTenDocumentsCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                CriterionDescription.INSERT_TEN_DOCUMENTS.getDescription(),
                CriterionDescription.INSERT_TEN_DOCUMENTS.getRequiredCount()
        );
        this.insertedDocumentsMap = new HashMap<>();
        this.requiredCountIntoOne = requiredCount;
        this.currentCollection = "";
    }

    @Override
    protected String satisfiedQueriesToString() {
        return insertedDocumentsMap.entrySet().stream()
                .map(entry ->
                        "Documents inserted into '" +
                                entry.getKey() + "': " +
                                entry.getValue() + "/" +
                                requiredCountIntoOne + '\n')
                .collect(Collectors.joining()) +
                "Satisfied queries:\n" +
                satisfiedQueries.stream()
                        .map(query -> query.toString() + '\n')
                        .collect(Collectors.joining());
    }

    @Override
    protected void concreteCheck(Query query) {
        List<QueryParameter> queryParameters = query.getParameters();
        if (!queryParameters.isEmpty()) {
            currentCollection = query.getCollection();
            if (currentCollection.isBlank()) {
                return;
            }

            if (!insertedDocumentsMap.containsKey(currentCollection)) {
                insertedDocumentsMap.put(currentCollection, 0);
                requiredCount = insertedDocumentsMap.size() * requiredCountIntoOne;
            }

            queryParameters.get(0).accept(this);
        }
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        checkDocumentParameter(parameter);
    }

    @Override
    public void visitPipelineParameter(PipelineParameter parameter) {
        for (DocumentParameter documentParameter: parameter.getParameterList()) {
            checkDocumentParameter(documentParameter);
        }
    }

    private void checkDocumentParameter(DocumentParameter parameter) {
        if (!parameter.isTrivial()) {
//                && mockDb.insertDocument(currentCollection, parameter.getDocument())) {
            insertedDocumentsMap.put(currentCollection, insertedDocumentsMap.get(currentCollection) + 1);
            currentCount++;
            satisfied = true;
        }
    }
}
