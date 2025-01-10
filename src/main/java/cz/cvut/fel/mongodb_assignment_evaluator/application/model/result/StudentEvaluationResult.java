package cz.cvut.fel.mongodb_assignment_evaluator.application.model.result;

import lombok.Getter;

import java.util.*;

@Getter
public class StudentEvaluationResult {
    private final Map<CriterionEvaluationResult, Integer> criteriaScoreMap;

    public StudentEvaluationResult(List<CriterionEvaluationResult> results) {
        criteriaScoreMap = new LinkedHashMap<>();
        results.forEach(c -> criteriaScoreMap.put(c, c.getScore()));
    }
}
