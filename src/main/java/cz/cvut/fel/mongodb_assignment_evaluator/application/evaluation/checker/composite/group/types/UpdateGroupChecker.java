package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.group.types;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.group.CriteriaGroupChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.update.field.UpdateAddFieldCriterionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.update.field.UpdateRemoveFieldCriterionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.update.array.UpdateArrayAddCriterionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.update.array.UpdateArrayRemoveCriterionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.update.array.UpdateArrayReplaceCriterionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.update.nested.UpdateNestedDocumentCriterionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.update.many.UpdateIncreaseMultiplyCriterionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.update.one.UpdateOneDocumentCriterionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.update.upsert.UpdateUpsertUsedCriterionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.UpdateQuery;

import java.util.List;


public class UpdateGroupChecker extends CriteriaGroupChecker<UpdateQuery> {
    public UpdateGroupChecker(InsertedDocumentStorage documentStorage) {
        super(documentStorage);
    }

    @Override
    protected List<CheckerLeaf<UpdateQuery>> initCriteria() {
        return List.of(
                new UpdateOneDocumentCriterionChecker(documentStorage),
                new UpdateIncreaseMultiplyCriterionChecker(documentStorage),
                new UpdateAddFieldCriterionChecker(documentStorage),
                new UpdateRemoveFieldCriterionChecker(documentStorage),
                new UpdateNestedDocumentCriterionChecker(documentStorage),
                new UpdateArrayReplaceCriterionChecker(documentStorage),
                new UpdateArrayAddCriterionChecker(documentStorage),
                new UpdateArrayRemoveCriterionChecker(documentStorage),
                new UpdateUpsertUsedCriterionChecker(documentStorage)
        );
    }
}
