package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.PipelineParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public abstract class UpdateCriterion extends AssignmentCriterion {
    private final List<String> updateOperators;
    private final Boolean shouldFindFields;

    protected Boolean isUpdateOne;
    protected Document filterDocument;
    protected String collection;
    protected String id;

    protected Boolean foundFields;
    protected Boolean foundOperator;

    public UpdateCriterion(MockMongoDB mockDb, String assignmentMessage, int requiredCount, List<String> updateOperators, boolean shouldFindFields) {
        super(mockDb, assignmentMessage, requiredCount);
        this.updateOperators = new ArrayList<>(updateOperators);
        this.shouldFindFields = shouldFindFields;

        this.isUpdateOne = false;
        this.filterDocument = Document.parse("{}");
        this.collection = "";
        this.id = "";

        this.foundFields = false;
        this.foundOperator = false;
    }

    @Override
    public void concreteCheck(Query query) {
        id = "";
        currentParameterIdx = 0;
        collection = query.getCollection();
        List<QueryParameter> parameters = query.getParameters();
        if (parameters.size() >= 2) {
            isUpdateOne = query.getOperator().equalsIgnoreCase("updateOne");
            parameters.get(currentParameterIdx).accept(this);
            parameters.get(currentParameterIdx).accept(this);
        }
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        if (currentParameterIdx == 0) {
            filterDocument = parameter.getDocument();
            Object idObject = parameter.getValue("_id");
            id = (idObject != null) ? idObject.toString() : "";
        } else if (currentParameterIdx == 1) {
            checkForUpdateOperators(parameter);
            if (foundOperator &&
                    foundFields.equals(shouldFindFields)) {
                currentCount++;
                satisfied = true;
            }
        }
        currentParameterIdx++;
    }

    @Override
    public void visitPipelineParameter(PipelineParameter parameter) {
        if (currentParameterIdx == 1) {
            for (DocumentParameter documentParameter : parameter.getParameterList()) {
                checkForUpdateOperators(documentParameter);
                if (foundOperator &&
                        foundFields.equals(shouldFindFields)) {
                    currentCount++;
                    satisfied = true;
                    break;
                }
            }
        }
        currentParameterIdx++;
    }

    protected Set<String> getFieldsSet(Document document) {
        return document.keySet();
    }

    protected void checkForUpdateOperators(DocumentParameter parameter) {
        for (String operator : updateOperators) {
            Object foundOperatorObject = parameter.getValue(operator, 1);
            if (!(foundOperatorObject instanceof Document)) {
                foundOperator = false;
                foundFields = false;
                continue;
            }
            foundOperator = true;

            Set<String> fields = getFieldsSet((Document) foundOperatorObject);
            if (isUpdateOne) {
                containsUpdateOne(fields);
            } else {
                containsUpdateMany(fields);
            }
            if (foundFields.equals(shouldFindFields)) {
                break;
            }
        }
    }

    private void containsUpdateOne(Set<String> fields) {
        if (filterDocument.isEmpty()) {
            foundFields = mockDb.containsFieldFirstDocument(collection, fields);
        } else if (id.isBlank()) {
            foundFields = mockDb.containsFieldsAllDocuments(collection, fields);
        }
        foundFields = mockDb.containsFieldsById(collection, id, fields);
    }

    private void containsUpdateMany(Set<String> fields) {
        if (!id.isBlank()) {
            foundFields = mockDb.containsFieldsById(collection, id, fields);
        }
        foundFields = mockDb.containsFieldsAllDocuments(collection, fields);
    }
}
