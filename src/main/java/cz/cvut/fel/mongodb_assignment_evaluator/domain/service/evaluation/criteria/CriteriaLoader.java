package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.Query;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.criteria.EvaluationCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.criteria.factory.CriteriaFactory;

import java.util.*;
import java.util.stream.Collectors;

public class CriteriaLoader {
    public Map<Crit, EvaluationCriterion<? extends Query>> loadCriteria() {
        List<Crit> loadedCriteria = new ArrayList<>(
                load()
        );
        CriteriaFactory factory = new CriteriaFactory();
        Map<Crit, EvaluationCriterion<? extends Query>> criteriaEvaluators = new LinkedHashMap<>();
        for (Crit crit : loadedCriteria) {
            try {
                criteriaEvaluators.put(crit, factory.createEvaluationCriterion(crit.getName()));
            } catch (Exception e) {
                // todo some exception
            }
        }
        return criteriaEvaluators.entrySet().stream()
                .sorted(Comparator.comparingInt(e -> e.getValue().getPriority()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, LinkedHashMap::new));
    }

    // todo temp criteria load
    private List<Crit> load() {
        List<Crit> loadedCriteria = new ArrayList<>();
        for (Criteria crit : Criteria.values()) {
            loadedCriteria.add(new Crit(crit.name(), crit.getDescription()));
        }
        return loadedCriteria;
    }
}
