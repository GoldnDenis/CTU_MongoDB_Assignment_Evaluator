package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.update.one;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.UpdateQuery;
import org.bson.BsonDocument;

import java.util.List;

public class UpdateOneDocumentCriterion extends AssignmentCriterion<UpdateQuery> {
    public UpdateOneDocumentCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.UPDATE_ONE_DOCUMENT, UpdateQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(UpdateQuery query) {
        if (query.isUpdateOne()) {
            if (query.getUpdateDocuments().size() != 1) {
                return;
            }
            if (!query.containsUpdateOperator()) {
                return;
            }
            List<BsonDocument> found = documentStorage.findDocumentByFilter(query.getCollection(), query.getFilter().getDocument());
            if (!found.isEmpty()) {
                currentScore++;
            }
        }
    }

//    public UpdateOneDocumentCriterion(InsertedDocumentStorage mockDb) {
//        super(
//                mockDb,
//                Criteria.UPDATE_ONE_DOCUMENT.getDescription(),
//                Criteria.UPDATE_ONE_DOCUMENT.getRequiredCount(),
//                List.of(
//                        "$set", "$rename", "$mul", "$inc",
//                        "$min", "$max", "$currentDate"
//                ), true
//        );
//        this.isUpdateOne = true;
//    }
//    @Override
//    public void concreteCheck(UpdateQuery query) {
//        id = "";
//        curParamIdx = 0;
//        if (query.getOperator().equalsIgnoreCase("updateOne")) {
//            collection = query.getCollection();
//            List<QueryParameter> parameters = query.getParameters();
//            if (parameters.size() >= 2) {
//                parameters.get(curParamIdx).accept(this);
//                parameters.get(curParamIdx).accept(this);
//            }
//        }
//    }
}
