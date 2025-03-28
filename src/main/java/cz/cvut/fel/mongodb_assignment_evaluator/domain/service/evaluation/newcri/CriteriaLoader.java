package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.Query;

import java.security.Key;
import java.util.*;

public class CriteriaLoader {
    public Map<Crit, EvaluationCriterion<? extends Query>> loadCriteria() {
        List<Crit> loadedCriteria = new ArrayList<>(
                load()
        );

        CriteriaFactory factory = new CriteriaFactory();
        Map<Crit, EvaluationCriterion<? extends Query>> criteriaEvaluators = new HashMap<>();
        for (Crit crit : loadedCriteria) {
            try {
                criteriaEvaluators.put(crit, factory.createEvaluationCriterion(crit.getName()));
            } catch (Exception e) {
                // undefined
            }
        }
        return criteriaEvaluators;
    }

    // todo temp criteria load
    private List<Crit> load() {
        List<Crit> loadedCriteria = new ArrayList<>();
        for (Criteria crit: Criteria.values()) {
            loadedCriteria.add(new Crit(crit.name(), crit.getDescription()));
        }
        return loadedCriteria;
    }
}
