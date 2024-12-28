package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.result;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.CriterionEvaluationResult;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class StudentEvaluationResult {
    private final List<CriterionEvaluationResult> results;

    public StudentEvaluationResult(List<CriterionEvaluationResult> results) {
        this.results = new ArrayList<>(results);
    }

    @Override
    public String toString() {
        return ResultProcessor.formatToText(results);
    }
}
