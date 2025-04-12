package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.result;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.GradedCriteria;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GradedSubmission {
    private final List<GradedCriteria> gradedCriteria;

    public GradedSubmission(List<GradedCriteria> gradedCriteria) {
        this.gradedCriteria = new ArrayList<>(gradedCriteria);
//        this.criteriaScoreMap = new LinkedHashMap<>();
    }

    public GradedSubmission() {
        this.gradedCriteria = new ArrayList<>();
//        this.criteriaScoreMap = new LinkedHashMap<>();
    }

//    private final Map<CriterionEvaluationResult, Integer> criteriaScoreMap;
//
//    public GradedSubmission(List<CriterionEvaluationResult> results) {
//        criteriaScoreMap = new LinkedHashMap<>();
//        results.forEach(c -> criteriaScoreMap.put(c, c.getScore()));
//    }
//
//    public GradedSubmission() { //todo
//        criteriaScoreMap = new LinkedHashMap<>();
//    }
}
