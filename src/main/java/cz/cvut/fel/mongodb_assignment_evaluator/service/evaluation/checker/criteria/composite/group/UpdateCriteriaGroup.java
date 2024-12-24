package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.group;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.update.field.UpdateAddFieldCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.update.field.UpdateRemoveFieldCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.update.array.UpdateArrayAddCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.update.array.UpdateArrayRemoveCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.update.array.UpdateArrayReplaceCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.update.nested.UpdateNestedDocumentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.update.many.UpdateIncreaseMultiplyCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.update.one.UpdateOneDocumentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.update.upsert.UpdateUpsertUsedCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.UpdateQuery;

import java.util.List;


public class UpdateCriteriaGroup extends GroupCriterion<UpdateQuery> {
    public UpdateCriteriaGroup(InsertedDocumentStorage documentStorage) {
        super(documentStorage);
    }

    @Override
    protected List<AssignmentCriterion<UpdateQuery>> initCriteria() {
        return List.of(
                new UpdateOneDocumentCriterion(documentStorage),
                new UpdateIncreaseMultiplyCriterion(documentStorage),
                new UpdateAddFieldCriterion(documentStorage),
                new UpdateRemoveFieldCriterion(documentStorage),
                new UpdateNestedDocumentCriterion(documentStorage),
                new UpdateArrayReplaceCriterion(documentStorage),
                new UpdateArrayAddCriterion(documentStorage),
                new UpdateArrayRemoveCriterion(documentStorage),
                new UpdateUpsertUsedCriterion(documentStorage)
        );
    }
//    private final List<String> updateOperators;
//    private final Boolean shouldFindFields;
//    protected Boolean isUpdateOne;
//    protected BsonDocument filterDocument;
//    protected String collection;
//    protected String id;
//    protected Boolean foundFields;
//    protected Boolean foundOperator;



//    public UpdateCriteriaGroup(InsertedDocumentStorage mockDb, String assignmentMessage, int requiredCount, List<String> updateOperators, boolean shouldFindFields) {
//        super(mockDb, assignmentMessage, requiredCount);
//        this.updateOperators = new ArrayList<>(updateOperators);
//        this.shouldFindFields = shouldFindFields;
//
//        this.isUpdateOne = false;
//        this.filterDocument = BsonDocument.parse("{}");
//        this.collection = "";
//        this.id = "";
//
//        this.foundFields = false;
//        this.foundOperator = false;
//    }

//    @Override
//    public void concreteCheck(Query query) {
//        id = "";
//        curParamIdx = 0;
//        collection = query.getCollection();
//        List<QueryParameter> parameters = query.getParameters();
//        if (parameters.size() >= 2) {
//            isUpdateOne = query.getOperator().equalsIgnoreCase("updateOne");
//            parameters.get(curParamIdx).accept(this);
//            parameters.get(curParamIdx).accept(this);
//        }
//    }
//
//    @Override
//    public void visitDocumentParameter(DocumentParameter parameter) {
//        if (curParamIdx == 0) {
//            filterDocument = parameter.getDocument();
//            Object idObject = parameter.getValue("_id");
//            id = (idObject != null) ? idObject.toString() : "";
//        } else if (curParamIdx == 1) {
//            checkForUpdateOperators(parameter);
//            if (foundOperator &&
//                    foundFields.equals(shouldFindFields)) {
//                currentScore++;
//                satisfied = true;
//            }
//        }
//        curParamIdx++;
//    }
//
//    @Override
//    public void visitPipelineParameter(PipelineParameter parameter) {
//        if (curParamIdx == 1) {
//            for (DocumentParameter documentParameter : parameter.getParameterList()) {
//                checkForUpdateOperators(documentParameter);
//                if (foundOperator &&
//                        foundFields.equals(shouldFindFields)) {
//                    currentScore++;
//                    satisfied = true;
//                    break;
//                }
//            }
//        }
//        curParamIdx++;
//    }
//
//    protected Set<String> getFieldsSet(BsonDocument document) {
//        return document.keySet();
//    }
//
//    protected void checkForUpdateOperators(DocumentParameter parameter) {
//        for (String operator : updateOperators) {
//            BsonValue foundOperatorObject = parameter.getValue(operator, 1);
//            if (foundOperatorObject == null) {
//                foundOperator = false;
//                foundFields = false;
//                continue;
//            }
//            foundOperator = true;
//            if (foundOperatorObject.isDocument()) {
//                Set<String> fields = getFieldsSet(foundOperatorObject.asDocument());
//                if (isUpdateOne) {
//                    containsUpdateOne(fields);
//                } else {
//                    containsUpdateMany(fields);
//                }
//                if (foundFields.equals(shouldFindFields)) {
//                    break;
//                }
//            }
//        }
//    }
//
//    private void containsUpdateOne(Set<String> fields) {
//        if (filterDocument.isEmpty()) {
//            foundFields = documentStorage.containsFieldFirstDocument(collection, fields);
//        } else if (id.isBlank()) {
//            foundFields = documentStorage.containsFieldsAllDocuments(collection, fields);
//        }
//        foundFields = documentStorage.containsFieldsById(collection, id, fields);
//    }
//
//    private void containsUpdateMany(Set<String> fields) {
//        if (!id.isBlank()) {
//            foundFields = documentStorage.containsFieldsById(collection, id, fields);
//        }
//        foundFields = documentStorage.containsFieldsAllDocuments(collection, fields);
//    }
}
