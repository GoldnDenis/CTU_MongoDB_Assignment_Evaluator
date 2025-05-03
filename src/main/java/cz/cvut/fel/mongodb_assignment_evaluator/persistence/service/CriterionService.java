package cz.cvut.fel.mongodb_assignment_evaluator.persistence.service;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.criteria.EvaluationCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.criteria.factory.CriteriaFactory;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.CriteriaNotLoaded;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.UnknownCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.QueryToken;
import cz.cvut.fel.mongodb_assignment_evaluator.persistence.repository.CriterionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Log
@Service
@RequiredArgsConstructor
public class CriterionService {
    private final CriterionRepository criterionRepository;

    public Map<Criterion, EvaluationCriterion<? extends QueryToken>> loadCriteria() {
        CriteriaFactory factory = new CriteriaFactory();
        Map<Criterion, EvaluationCriterion<? extends QueryToken>> criteriaEvaluators = new LinkedHashMap<>();
        for (Criterion criterion : criterionRepository.findAll()) {
            try {
                criteriaEvaluators.put(criterion, factory.createEvaluationCriterion(criterion.getName()));
            } catch (UnknownCriterion e) {
                log.severe(e.getMessage());
            }
        }
        if (criteriaEvaluators.isEmpty()) {
            throw new CriteriaNotLoaded();
        }
        criteriaEvaluators.put(
                new Criterion(Criteria.UNRECOGNIZED_QUERY.name(), Criteria.UNRECOGNIZED_QUERY.getDescription(), Criteria.UNRECOGNIZED_QUERY.getRequiredCount()),
                factory.createEvaluationCriterion(Criteria.UNRECOGNIZED_QUERY.name()));
        return criteriaEvaluators.entrySet().stream()
                .sorted(Comparator.comparingInt(e -> e.getValue().getPriority()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, LinkedHashMap::new));
    }
}
