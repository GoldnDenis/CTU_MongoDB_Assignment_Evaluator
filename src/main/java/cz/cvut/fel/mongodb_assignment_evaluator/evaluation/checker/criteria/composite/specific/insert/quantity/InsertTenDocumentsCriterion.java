package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.specific.insert.quantity;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.CriterionEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.InsertQuery;

import java.util.List;

public class InsertTenDocumentsCriterion extends AssignmentCriterion<InsertQuery> {
//    private final InsertCriteriaGroup parent;
//    private final int requiredCountIntoOne;

    public InsertTenDocumentsCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.INSERT_TEN_DOCUMENTS, InsertQuery.class, documentStorage);
//        this.requiredCountIntoOne = criterion.getRequiredCount();
//        this.parent = parent;
    }

    @Override
    protected void concreteCheck(InsertQuery query) {
//        InsertedDocumentStorage documentStorage = parent.getDocumentStorage();
        for (DocumentParameter document: query.getNonTrivialInsertedDocuments()) {
            if (documentStorage.insertDocument(query.getCollection(), document.getDocument())) {
                currentScore++;
            }
        }
    }

    @Override
    public List<CriterionEvaluationResult> evaluate() {
        criterionEvaluationResult.setCriterionModifier(documentStorage.getCollections().size());
        return List.of(criterionEvaluationResult);
    }

//    @Override
//    protected void evaluate() {
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
