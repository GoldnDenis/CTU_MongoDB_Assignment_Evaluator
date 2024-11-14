package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.general.CommentCriterion;

public class GeneralStrategy extends CheckerStrategy{
    public GeneralStrategy() {
        criteria.add(new CommentCriterion());
    }
}
