package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.StudentSubmission;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.Query;

import java.util.*;

public class CriteriaEvaluator {
    private final Map<Crit, EvaluationCriterion<? extends Query>> criteriaEvaluators;

    public CriteriaEvaluator() {
        CriteriaLoader criteriaLoader = new CriteriaLoader();
        criteriaEvaluators = new LinkedHashMap<>(
                criteriaLoader.loadCriteria()
        );
    }

    public void evaluateQueries(StudentSubmission submission) {
        submission.getQueryList().forEach(query -> criteriaEvaluators.values().stream()
                .filter(criterion -> criterion.getQueryType().isInstance(query))
                .forEach(criterion -> criterion.check(query))
        );
        criteriaEvaluators.entrySet().stream()
                .map(e -> e.getValue().getResult(e.getKey()))
                .forEach(submission::addGradedCriteria);
    }

//    public List<GradedCriteria> evaluateQueries(StudentSubmission submission) {
//        submission.getQueryList().forEach(query -> criteriaEvaluators.values().stream()
//                .filter(criterion -> criterion.getQueryType().isInstance(query))
//                .forEach(criterion -> criterion.check(query))
//        );
//        return criteriaEvaluators.entrySet().stream()
//                .map(e -> e.getValue().getResult(e.getKey()))
//                .toList();
////        return criteriaEvaluators.entrySet().stream()
////                .map(e -> new GradedCriteria(
////                        e.getKey().getName(),
////                        e.getKey().getDescription(),
////                        e.getKey().getRequiredCount(),
////                        e.getValue().getMaxScore(),
////                        e.getValue().getFulfilledQueries())
////                ).sorted(Comparator.comparing(e -> e.getId))
////                .toList();
//    }
}
