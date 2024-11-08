package cz.cvut.fel.task_evaluator.service.evaluation.checker.strategy;

import cz.cvut.fel.task_evaluator.service.evaluation.checker.criteria.general.CommentCriterion;

public class GeneralStrategy extends CheckerStrategy{
    public GeneralStrategy() {
        criteria.add(new CommentCriterion());
    }
}
