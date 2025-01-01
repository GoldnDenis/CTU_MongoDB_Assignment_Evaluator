package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.result;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.CriterionEvaluationResult;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class StudentEvaluationResult {
//    private final List<CriterionEvaluationResult> results;
    private final Map<CriterionEvaluationResult, Integer> criteriaMapWithScores;

    public StudentEvaluationResult(List<CriterionEvaluationResult> results) {
//        this.results = new ArrayList<>(results);
        criteriaMapWithScores = new LinkedHashMap<>();
        results.forEach(c -> criteriaMapWithScores.put(c, c.getScore()));
    }

    @Override
    public String toString() {
        return ResultProcessor.formatToText(criteriaMapWithScores.keySet());
    }

    public Set<String> getCriteriaNames() {
        return criteriaMapWithScores.keySet().stream()
                .map(CriterionEvaluationResult::getCriterion)
                .map(Enum::toString)
                .collect(Collectors.toSet());
    }
}
