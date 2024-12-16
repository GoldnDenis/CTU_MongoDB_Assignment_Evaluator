package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.insert;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.ResultStates;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.PipelineParameter;
import org.bson.BsonDocument;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class InsertTenDocumentsCriterion extends InsertCriterion {
    private final int requiredCountIntoOne;

    public InsertTenDocumentsCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                Criteria.INSERT_TEN_DOCUMENTS.getDescription(),
                Criteria.INSERT_TEN_DOCUMENTS.getRequiredCount()
        );
        this.requiredCountIntoOne = requiredCount;
    }

    @Override
    protected void evaluate() {
        Collection<Map<String, BsonDocument>> collectionValues = mockDb.getCollections().values();
        requiredCount = requiredCountIntoOne * collectionValues.size();
        if (currentCount > 0 ) {
            state = ResultStates.FULFILLED;
            if (
                    !collectionValues.stream()
                            .filter(docs -> docs.size() < requiredCountIntoOne)
                            .toList().isEmpty()
            ) {
                state = ResultStates.PARTLY_FULFILLED;
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
    public void visitDocumentParameter(DocumentParameter parameter) {
        if (curParamIdx == 0) {
            tryInsertDocument(parameter);
        }
    }

    @Override
    public void visitPipelineParameter(PipelineParameter parameter) {
        if (curParamIdx == 0) {
            for (DocumentParameter documentParameter : parameter.getParameterList()) {
                tryInsertDocument(documentParameter);
            }
        }
    }

    private void tryInsertDocument(DocumentParameter parameter) {
        if (mockDb.insertDocument(currentCollection, parameter.getDocument())) {
            currentCount++;
        }
    }
}
