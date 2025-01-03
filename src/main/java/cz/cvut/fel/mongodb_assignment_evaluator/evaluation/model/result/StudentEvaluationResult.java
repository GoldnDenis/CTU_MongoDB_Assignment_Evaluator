package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.result;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.CriterionEvaluationResult;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class StudentEvaluationResult {
    private final Map<CriterionEvaluationResult, Integer> criteriaMapWithScores;

    public StudentEvaluationResult(List<CriterionEvaluationResult> results) {
        criteriaMapWithScores = new LinkedHashMap<>();
        results.forEach(c -> criteriaMapWithScores.put(c, c.getScore()));
    }
}
