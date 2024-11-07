package cz.cvut.fel.task_evaluator.evaluation.checker.strategy;

import cz.cvut.fel.task_evaluator.evaluation.checker.criteria.general.CommentCriterion;

public class GeneralStrategy extends CheckerStrategy{
    public GeneralStrategy() {
        criteria.add(new CommentCriterion());
    }
}
