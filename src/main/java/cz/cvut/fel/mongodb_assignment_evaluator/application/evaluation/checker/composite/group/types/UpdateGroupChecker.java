package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.group.types;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.group.CriteriaGroupChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.update.field.UpdateAddFieldChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.update.field.UpdateRemoveFieldChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.update.array.UpdateArrayAddChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.update.array.UpdateArrayRemoveChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.update.array.UpdateArrayReplaceChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.update.nested.UpdateNestedDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.update.many.IncreaseMultiplyChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.update.one.UpdateOneChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.update.upsert.UpdateUpsertChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.UpdateQuery;

import java.util.List;


public class UpdateGroupChecker extends CriteriaGroupChecker<UpdateQuery> {
    public UpdateGroupChecker(InsertedDocumentStorage documentStorage) {
        super(documentStorage);
    }

    @Override
    protected List<CheckerLeaf<UpdateQuery>> initCriteria() {
        return List.of(
                new UpdateOneChecker(documentStorage),
                new IncreaseMultiplyChecker(documentStorage),
                new UpdateAddFieldChecker(documentStorage),
                new UpdateRemoveFieldChecker(documentStorage),
                new UpdateNestedDocumentChecker(documentStorage),
                new UpdateArrayReplaceChecker(documentStorage),
                new UpdateArrayAddChecker(documentStorage),
                new UpdateArrayRemoveChecker(documentStorage),
                new UpdateUpsertChecker(documentStorage)
        );
    }
}
