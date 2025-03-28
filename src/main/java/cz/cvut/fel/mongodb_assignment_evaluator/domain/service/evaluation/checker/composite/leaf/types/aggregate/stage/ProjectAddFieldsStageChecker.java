package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.aggregate.stage;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Stages;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.AggregateQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.CheckerLeaf;

public class ProjectAddFieldsStageChecker extends CheckerLeaf<AggregateQuery> {
    public ProjectAddFieldsStageChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.STAGE_PROJECT_ADDFIELDS, AggregateQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(AggregateQuery query) {
        if (query.containsStage(Stages.PROJECT) ||
                query.containsStage(Stages.ADD_FIELDS)) {
            currentScore++;
        }
    }
}
