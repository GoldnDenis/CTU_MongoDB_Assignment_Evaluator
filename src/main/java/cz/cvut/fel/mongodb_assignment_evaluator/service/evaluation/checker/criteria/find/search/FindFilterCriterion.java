package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find.search;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;
import org.bson.BsonDocument;
import org.bson.Document;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class FindFilterCriterion extends AssignmentCriterion {
    protected final Set<String> expectedOperators;
    protected String collection;

    public FindFilterCriterion(MockMongoDB mockDb, String assignmentMessage, int requiredCount, Set<String> expectedOperators) {
        super(mockDb, assignmentMessage, requiredCount);
        this.expectedOperators = new HashSet<>(expectedOperators);
        this.collection = "";
    }

    @Override
    protected void concreteCheck(Query query) {
        currentParameterIdx = 0;
        List<QueryParameter> parameters = query.getParameters();
        if (parameters.size() >= 1) {
            collection = query.getCollection();
            parameters.get(currentParameterIdx).accept(this);
        }
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        if (currentParameterIdx == 0) {
            if (inspectFilter(parameter.getDocument(), parameter.getDepth())) {
                currentCount++;
                satisfied = true;
            }
        }
        currentParameterIdx++;
    }

    protected abstract boolean inspectFilter(BsonDocument filterDocument, int maxDepth);
}
