package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.insert.quantity;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.InsertQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.result.CriterionEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.CheckerLeaf;
import org.bson.BsonDocument;

import java.util.List;

public class InsertDocumentsChecker extends CheckerLeaf<InsertQuery> {
    public InsertDocumentsChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.INSERT_TEN_DOCUMENTS, InsertQuery.class, documentStorage);

    }

    @Override
    protected void concreteCheck(InsertQuery query) {
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
}
