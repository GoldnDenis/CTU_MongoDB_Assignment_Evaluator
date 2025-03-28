package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.Query;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CriteriaEvaluator {
    private final CriteriaLoader criteriaLoader;
//    private final CriteriaGrader criteriaGrader;

//    private final List<EvaluationCriterion<? extends Query>> criteria;
    private final Map<Crit, EvaluationCriterion<? extends Query>> criteriaEvaluators;

    public CriteriaEvaluator() {
        criteriaLoader = new CriteriaLoader();
//        criteriaGrader = new CriteriaGrader();
//        criteria = new LinkedList<>();
        criteriaEvaluators = new HashMap<>(
                criteriaLoader.loadCriteria()
        );
    }

    public List<GradedCriteria> evaluateQueries(List<Query> queries) {
        queries.forEach(query -> criteriaEvaluators.values().stream()
                .filter(criterion -> criterion.getQueryType().isInstance(query))
                .forEach(criterion -> criterion.check(query))
        );


//        for (Query query : queries) {
//            for (EvaluationCriterion<? extends Query> criterion : criteriaEvaluators.values()) {
//                // todo make a filtering of map values based on query type class
//                criterion.check(query);
//            }
//        }
//        criteriaEvaluators.entrySet().stream()
//                .sorted(Comparator.comparing(e -> e.getKey().getId()))
//                .forEach(e -> {
//                    System.out.println(e.getKey().getName() + ": " + e.getValue().getMaxScore());
//                    e.getValue().reset();
//                });

        return criteriaEvaluators.entrySet().stream()
                .map(e -> new GradedCriteria(
                        e.getKey().getName(),
                        e.getKey().getDescription(),
                        e.getKey().getRequiredCount(),
                        e.getValue().getMaxScore(),
                        e.getValue().getFulfilledQueries())
                ).sorted(Comparator.comparing(e -> e.getId))
                .toList();
    }
}
