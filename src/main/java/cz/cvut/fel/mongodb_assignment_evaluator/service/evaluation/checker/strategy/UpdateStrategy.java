package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy;

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
    public UpdateStrategy() {
        criteria.add(new UpdateOneDocumentCriterion());
        criteria.add(new UpdateIncreaseMultiplyCriterion());
        criteria.add(new UpdateAddFieldCriterion());
        criteria.add(new UpdateRemoveFieldCriterion());
        criteria.add(new UpdateNestedDocumentCriterion());
        criteria.add(new UpdateArrayReplaceCriterion());
        criteria.add(new UpdateArrayAddCriterion());
        criteria.add(new UpdateArrayRemoveCriterion());
        criteria.add(new UpdateUpsertUsedCriterion());
    }
}
