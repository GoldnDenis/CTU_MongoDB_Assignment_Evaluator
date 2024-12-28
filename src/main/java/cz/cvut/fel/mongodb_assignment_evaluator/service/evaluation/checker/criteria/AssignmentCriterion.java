package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.CriterionNode;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.Query;
import lombok.extern.java.Log;

import java.util.List;

@Log
public abstract class AssignmentCriterion<Q extends Query> implements CriterionNode {
//public abstract class AssignmentCriterion<Q extends Query> implements CriterionNode<Q> {
    protected Criteria criterion;
    private final Class<Q> queryType;
//    protected final String assignmentMessage;
//    protected int requiredCount;
    protected final InsertedDocumentStorage documentStorage;
//    protected final int minArgCount;
    protected int currentScore;
//    protected final List<Query> satisfiedQueries;
//    protected final List<Query> failedQueries;
//    protected ResultStates state;
//    protected int curParamIdx;
//    protected boolean satisfied;
    protected CriterionEvaluationResult criterionEvaluationResult;

//    public AssignmentCriterion(Criteria criterion, InsertedDocumentStorage documentStorage, int minArgCount) {
//        this.evaluationResult = new EvaluationResult(criterion);
////        this.documentStorage = documentStorage;
////        this.minArgCount = minArgCount;
////        this.assignmentMessage = assignmentMessage;
////        this.requiredCount = requiredCount;
//        this.currentScore = 0;
////        this.satisfiedQueries = new ArrayList<>();
////        this.failedQueries = new ArrayList<>();
////        this.state = ResultStates.NOT_FULFILLED;
////        this.curParamIdx = -1;
////        this.satisfied = false;
//    }

    public AssignmentCriterion(Criteria criterion, Class<Q> queryType, InsertedDocumentStorage documentStorage) {
        this.criterionEvaluationResult = new CriterionEvaluationResult(criterion);
        this.queryType = queryType;
        this.documentStorage = documentStorage;
        this.currentScore = 0;
//        this.minArgCount = minArgCount;
//        this.assignmentMessage = assignmentMessage;
//        this.requiredCount = requiredCount;
//        this.satisfiedQueries = new ArrayList<>();
//        this.failedQueries = new ArrayList<>();
//        this.state = ResultStates.NOT_FULFILLED;
//        this.curParamIdx = -1;
//        this.satisfied = false;
    }

//    public abstract void check(Q query);

//    public EvaluationResult evaluate() {
//        return evaluationResult;
//    }

    @Override
//    public void check(Q query) {
    public void check(Query query) {
        if (queryType.isInstance(query)) {
            Q q = queryType.cast(query);
            currentScore = 0;
//        resetQueryData();

//        checkParameters(query.getParameters());
            concreteCheck(q);
            criterionEvaluationResult.log(query, currentScore);
        }
//        log(query, lastCount != currentScore);
        // OLD
//        if (satisfied) {
//            satisfiedQueries.add(query);
//        } else {
//            failedQueries.add(query);
//        }
//        satisfied = false;
    }

    protected abstract void concreteCheck(Q query);

    @Override
    public List<CriterionEvaluationResult> evaluate() {
        return List.of(criterionEvaluationResult);
    }

//    protected void checkParameters(List<QueryParameter> parameters) {
//        for (QueryParameter parameter : parameters) {
//            parameter.accept(this);
//            curParamIdx++;
//            if (curParamIdx == minArgCount) {
//                break;
//            }
//        }
//    }
//
//    protected abstract void resetQueryData();
//
//    @Override
//    public void visitDocumentParameter(DocumentParameter parameter) {
//
//    }
//
//    @Override
//    public void visitStringParameter(StringParameter parameter) {
//
//    }
//
//    @Override
//    public void visitPipelineParameter(PipelineParameter parameter) {
//
//    }
//
//    @Override
//    public void visitStringLiteralParameter(FunctionParameter parameter) {
//
//    }
//
//    @Override
//    public void visitEmptyParameter(EmptyParameter parameter) {
//
//    }

//    public AssignmentCriterion(MockMongoDB mockDb, String assignmentMessage, int requiredCount) {
//        this.mockDb = mockDb;
////        this.result = new EvaluationResult();
//        this.assignmentMessage = assignmentMessage;
//        this.requiredCount = requiredCount;
//        this.currentCount = 0;
//        this.satisfiedQueries = new ArrayList<>();
//        this.failedQueries = new ArrayList<>();
//        this.state = ResultStates.NOT_FULFILLED;
//        this.curParamIdx = -1;
////        this.satisfied = false;
//    }

//    public String generateFeedback() {
//        evaluate();
//        StringBuilder feedbackBuilder = new StringBuilder();
//        feedbackBuilder.append('"').append(assignmentMessage).append('"')
//                .append(" - ").append(currentScore).append(" out of ").append(requiredCount)
//                .append(": ").append(state.getState()).append('\n');
//        if (state.equals(ResultStates.FULFILLED) || state.equals(ResultStates.PARTLY_FULFILLED)) {
//            feedbackBuilder.append(generateQueryRelatedFeedback());
//        }
//        return feedbackBuilder.toString();
//    }

//    public EvaluationResult evaluate() {
//        ResultStates state = ResultStates.evaluate(currentScore, requiredCount);
//        return new EvaluationResult(criterion, state, satisfiedQueries, failedQueries);
//    }

//    protected void evaluate() {
//        if (currentScore > 0 ) {
//            state = ResultStates.FULFILLED;
//            if (currentScore < requiredCount) {
//                state = ResultStates.PARTLY_FULFILLED;
//            }
//        }
//    }

//    protected String generateQueryRelatedFeedback() {
//        return "Satisfied queries:\n" +
//                satisfiedQueries.stream()
//                        .map(query -> query.toString() + '\n')
//                        .collect(Collectors.joining());
//    }

//    public void log(Query query, Boolean satisfied) {
//        result.logQuery(query, satisfied);
//        if (satisfied) {
//            satisfiedQueries.add(query);
//        } else {
//            failedQueries.add(query);
//        }
//    }
}
