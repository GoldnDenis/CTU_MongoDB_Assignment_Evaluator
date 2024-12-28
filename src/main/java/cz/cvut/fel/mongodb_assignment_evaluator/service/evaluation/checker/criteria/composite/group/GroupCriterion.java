package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.group;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.CriterionEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.CriterionNode;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.Query;

import java.util.ArrayList;
import java.util.List;

//public abstract class GroupCriterion implements CriterionNode, QueryParameterVisitor {
    public abstract class GroupCriterion<Q extends Query> implements CriterionNode {
//    @Getter
    protected final InsertedDocumentStorage documentStorage;
//    private final int argumentCount;
    private final List<AssignmentCriterion<Q>> children;
//    protected int currentParameterIndex;

    public GroupCriterion(InsertedDocumentStorage documentStorage) {
//        this.argumentCount = argumentCount;
        this.documentStorage = documentStorage;
        this.children = new ArrayList<>(initCriteria());
//        this.currentParameterIndex = -1;
    }

    protected abstract List<AssignmentCriterion<Q>> initCriteria();

    public void check(Query query) {
        children.forEach(c -> c.check(query));
    }

    public List<CriterionEvaluationResult> evaluate() {
        return children.stream()
                .flatMap(c -> c.evaluate().stream())
                .toList();
    }

//    @Override
//    public void check(Query query) {
//        resetQueryData();
//        checkParameters(query);
//        children.forEach(child -> child.check(query));
//    }
//
//    @Override
//    public List<EvaluationResult> evaluate() {
//        return children.stream()
//                .flatMap(child -> child.evaluate().stream())
//                .toList();
//    }

//    private void checkParameters(Query query) {
//        currentParameterIndex = 0;
//        List<QueryParameter> parameters = query.getParameters();
//        for (QueryParameter parameter : parameters) {
//            parameter.accept(this);
//            currentParameterIndex++;
//            if (currentParameterIndex == argumentCount) {
//                break;
//            }
//        }
//    }

//    protected abstract void resetQueryData();

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
}
