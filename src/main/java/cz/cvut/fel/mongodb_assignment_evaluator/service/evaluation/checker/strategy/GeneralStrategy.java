package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.general.CommentCriterion;

import java.util.List;

public class GeneralStrategy extends CheckerStrategy{
    public GeneralStrategy(InsertedDocumentStorage mockDb) {
        super(mockDb, List.of(
                new CommentCriterion(mockDb)
        ));
    }
}
