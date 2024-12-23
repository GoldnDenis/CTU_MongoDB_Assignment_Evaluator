package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.update.many.UpdateIncreaseMultiplyCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.update.UpdateNestedDocumentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.update.one.UpdateOneDocumentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.update.upsert.UpdateUpsertUsedCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.update.array.UpdateArrayAddCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.update.array.UpdateArrayRemoveCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.update.UpdateArrayReplaceCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.update.field.UpdateAddFieldCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.update.field.UpdateRemoveFieldCriterion;

import java.util.List;

public class UpdateStrategy extends CheckerStrategy{
    public UpdateStrategy(InsertedDocumentStorage mockDb) {
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
