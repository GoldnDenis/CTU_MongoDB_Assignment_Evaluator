package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.group;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.specific.insert.quantity.InsertTenDocumentsCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.specific.insert.document.InsertDocumentArraysCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.specific.insert.document.InsertDocumentEmbeddedObjectsCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.specific.insert.many.InsertManyUsedCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.specific.insert.one.InsertOneUsedCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.InsertQuery;

import java.util.List;

//@Getter
public class InsertCriteriaGroup extends GroupCriterion<InsertQuery> {
//    protected StringParameter collection;
//    protected List<DocumentParameter> documents;
//    protected DocumentParameter options;

    public InsertCriteriaGroup(InsertedDocumentStorage documentStorage) {
        super(documentStorage);
    }

    @Override
    protected List<AssignmentCriterion<InsertQuery>> initCriteria() {
        return List.of(
                new InsertTenDocumentsCriterion(documentStorage),
                new InsertDocumentEmbeddedObjectsCriterion(documentStorage),
                new InsertDocumentArraysCriterion(documentStorage),
                new InsertOneUsedCriterion(documentStorage),
                new InsertManyUsedCriterion(documentStorage)
        );
    }

//    @Override
//    public void visitDocumentParameter(DocumentParameter parameter) {
//        if (currentParameterIndex == 0) {
//            documents.add(parameter);
//        } else if (currentParameterIndex == 1) {
//            options = parameter;
//        }
//    }
//
//    @Override
//    public void visitPipelineParameter(PipelineParameter parameter) {
//        if (currentParameterIndex == 0) {
//            documents.addAll(parameter.getParameterList());
//        }
//    }
//
//    @Override
//    protected void resetQueryData() {
//        collection = "";
//        documents.clear();
//        options.clear();
//    }
}