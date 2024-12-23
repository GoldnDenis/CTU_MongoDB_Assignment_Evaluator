package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.find;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.Query;
import org.bson.BsonDocument;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class FindFilterCriterion extends AssignmentCriterion {
    protected final Set<String> expectedOperators;
    protected String collection;

    public FindFilterCriterion(InsertedDocumentStorage mockDb, String assignmentMessage, int requiredCount, Set<String> expectedOperators) {
        super(mockDb, assignmentMessage, requiredCount);
        this.expectedOperators = new HashSet<>(expectedOperators);
        this.collection = "";
    }

    @Override
    protected void concreteCheck(Query query) {
        curParamIdx = 0;
        List<QueryParameter> parameters = query.getParameters();
        if (parameters.size() >= 1) {
            collection = query.getCollection();
            parameters.get(curParamIdx).accept(this);
        }
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        if (curParamIdx == 0) {
            if (inspectFilter(parameter.getDocument(), parameter.getDepth())) {
                currentScore++;
                satisfied = true;
            }
        }
        curParamIdx++;
    }

    protected abstract boolean inspectFilter(BsonDocument filterDocument, int maxDepth);
}
