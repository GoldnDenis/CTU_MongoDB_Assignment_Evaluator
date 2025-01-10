package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.insert.quantity;

import cz.cvut.fel.mongodb_assignment_evaluator.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.result.CriterionEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.InsertQuery;
import org.bson.BsonDocument;

import java.util.List;

public class InsertTenDocumentsCriterionChecker extends CheckerLeaf<InsertQuery> {
//    private final InsertCriteriaGroup parent;
//    private final int requiredCountIntoOne;

    public InsertTenDocumentsCriterionChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.INSERT_TEN_DOCUMENTS, InsertQuery.class, documentStorage);
//        this.requiredCountIntoOne = criterion.getRequiredCount();
//        this.parent = parent;
    }

    @Override
    protected void concreteCheck(InsertQuery query) {
//        InsertedDocumentStorage documentStorage = parent.getDocumentStorage();
        for (BsonDocument document: query.getNonTrivialInsertedDocuments()) {
            if (documentStorage.insertDocument(query.getCollection(), document)) {
                currentScore++;
            }
        }
    }

    @Override
    public List<CriterionEvaluationResult> evaluate() {
        evaluationResult.setCriterionModifier(documentStorage.getCollectionDocumentsMap().size());
        return List.of(evaluationResult);
    }

//    @Override
//    protected void checkQueries() {
//        Collection<Map<String, BsonDocument>> collectionValues = documentStorage.getCollections().values();
//        requiredCount = requiredCountIntoOne * collectionValues.size();
//        if (currentScore > 0 ) {
//            state = ResultStates.FULFILLED;
//            if (
//                    !collectionValues.stream()
//                            .filter(docs -> docs.size() < requiredCountIntoOne)
//                            .toList().isEmpty()
//            ) {
//                state = ResultStates.PARTLY_FULFILLED;
//            }
//        }
//    }

//    @Override
//    protected String generateQueryRelatedFeedback() {
//        return documentStorage.getCollections().entrySet().stream()
//                .map(entry ->
//                        "Documents inserted into '" +
//                                entry.getKey() + "': " +
//                                entry.getValue().size() + "/" +
//                                requiredCountIntoOne + '\n')
//                .collect(Collectors.joining()) +
//                "Satisfied queries:\n" +
//                satisfiedQueries.stream()
//                        .map(query -> query.toString() + '\n')
//                        .collect(Collectors.joining());
//    }

//    @Override
//    public void visitDocumentParameter(DocumentParameter parameter) {
//        if (curParamIdx == 0) {
//            tryInsertDocument(parameter);
//        }
//    }
//
//    @Override
//    public void visitPipelineParameter(PipelineParameter parameter) {
//        if (curParamIdx == 0) {
//            for (DocumentParameter documentParameter : parameter.getParameterList()) {
//                tryInsertDocument(documentParameter);
//            }
//        }
//    }
}
