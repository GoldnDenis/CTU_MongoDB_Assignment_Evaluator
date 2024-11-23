package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.UpdateIncreaseMultiplyCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.UpdateNestedDocumentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.UpdateOneDocumentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.UpdateUpsertUsedCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.array.UpdateArrayAddCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.array.UpdateArrayRemoveCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.array.UpdateArrayReplaceCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.field.UpdateAddFieldCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.field.UpdateRemoveFieldCriterion;

public class UpdateStrategy extends CheckerStrategy{
    public UpdateStrategy(MockMongoDB mockDb) {
        super(mockDb);
        criteria.add(new UpdateOneDocumentCriterion(mockDb));
        criteria.add(new UpdateIncreaseMultiplyCriterion(mockDb));
        criteria.add(new UpdateAddFieldCriterion(mockDb));
        criteria.add(new UpdateRemoveFieldCriterion(mockDb));
        criteria.add(new UpdateNestedDocumentCriterion(mockDb));
        criteria.add(new UpdateArrayReplaceCriterion(mockDb));
        criteria.add(new UpdateArrayAddCriterion(mockDb));
        criteria.add(new UpdateArrayRemoveCriterion(mockDb));
        criteria.add(new UpdateUpsertUsedCriterion(mockDb));
    }
}
