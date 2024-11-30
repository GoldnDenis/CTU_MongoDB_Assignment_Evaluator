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
