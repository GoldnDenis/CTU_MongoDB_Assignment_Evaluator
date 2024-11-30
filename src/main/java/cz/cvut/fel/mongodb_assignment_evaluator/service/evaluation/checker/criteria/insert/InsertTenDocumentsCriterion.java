package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.insert;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionStates;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.PipelineParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;
import org.bson.Document;

import java.util.Collection;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

public class InsertTenDocumentsCriterion extends AssignmentCriterion {
    private final int requiredCountIntoOne;
    private String currentCollection;

    public InsertTenDocumentsCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                CriterionDescription.INSERT_TEN_DOCUMENTS.getDescription(),
                CriterionDescription.INSERT_TEN_DOCUMENTS.getRequiredCount()
        );
        this.requiredCountIntoOne = requiredCount;
        this.currentCollection = "";
    }

    @Override
    protected void evaluate() {
        //todo note that currently evaluates based on createCollection(), so if one of them fails the results may vary
        Collection<Map<String, Document>> collectionValues = mockDb.getCollections().values();
        requiredCount = requiredCountIntoOne * collectionValues.size();
        if (currentCount > 0 ) {
            state = CriterionStates.FULFILLED;
            if (
                    !collectionValues.stream()
                            .filter(docs -> docs.size() < requiredCountIntoOne)
                            .toList().isEmpty()
            ) {
                state = CriterionStates.PARTLY_FULFILLED;
            }
        }
    }

    @Override
    protected String generateQueryRelatedFeedback() {
        return mockDb.getCollections().entrySet().stream()
                .map(entry ->
                        "Documents inserted into '" +
                                entry.getKey() + "': " +
                                entry.getValue().size() + "/" +
                                requiredCountIntoOne + '\n')
                .collect(Collectors.joining()) +
                "Satisfied queries:\n" +
                satisfiedQueries.stream()
                        .map(query -> query.toString() + '\n')
                        .collect(Collectors.joining());
    }

    @Override
    protected void concreteCheck(Query query) {
        currentParameterIdx = 0;
        List<QueryParameter> queryParameters = query.getParameters();
        if (!queryParameters.isEmpty()) {
            QueryParameter firstParameter = queryParameters.get(0);
            if (firstParameter.isTrivial()) {
                return;
            }
            currentCollection = query.getCollection();
            firstParameter.accept(this);
        }
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        if (currentParameterIdx == 0) {
            tryInsertDocument(parameter);
        }
        currentParameterIdx++;
    }

    @Override
    public void visitPipelineParameter(PipelineParameter parameter) {
        if (currentParameterIdx == 0) {
            for (DocumentParameter documentParameter : parameter.getParameterList()) {
                tryInsertDocument(documentParameter);
            }
        }
        currentParameterIdx++;
    }

    private void tryInsertDocument(DocumentParameter parameter) {
        if (mockDb.insertDocument(currentCollection, parameter.getDocument())) {
            currentCount++;
            satisfied = true;
        }
    }
}
