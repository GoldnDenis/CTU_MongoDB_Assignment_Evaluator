package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.updatemany.UpdateIncreaseMultiplyCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.nested.UpdateNestedDocumentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.updateone.UpdateOneDocumentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.upsert.UpdateUpsertUsedCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.array.UpdateArrayAddCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.array.UpdateArrayRemoveCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.array.UpdateArrayReplaceCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.UpdateAddFieldCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.UpdateRemoveFieldCriterion;

import java.util.List;

public class UpdateStrategy extends CheckerStrategy{
    public UpdateStrategy(MockMongoDB mockDb) {
        super(mockDb, List.of(
                new UpdateOneDocumentCriterion(mockDb),
                new UpdateIncreaseMultiplyCriterion(mockDb),
                new UpdateAddFieldCriterion(mockDb),
                new UpdateRemoveFieldCriterion(mockDb),
                new UpdateNestedDocumentCriterion(mockDb),
                new UpdateArrayReplaceCriterion(mockDb),
                new UpdateArrayAddCriterion(mockDb),
                new UpdateArrayRemoveCriterion(mockDb),
                new UpdateUpsertUsedCriterion(mockDb)
        ));
    }
}
