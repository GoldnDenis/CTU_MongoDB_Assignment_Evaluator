package cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.persistence.sql.service;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.EvaluationCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.factory.CriteriaFactory;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.CriteriaNotLoaded;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.UnknownCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.QueryToken;
import cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.persistence.sql.repository.CriterionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Log
@Service
@RequiredArgsConstructor
public class CriterionService {
    private final CriterionRepository criterionRepository;

    public List<EvaluationCriterion<? extends QueryToken>> loadCriteria() {
        CriteriaFactory factory = new CriteriaFactory();
        List<EvaluationCriterion<? extends QueryToken>> criteriaEvaluators = new ArrayList<>();
        for (Criterion criterion : criterionRepository.findAll()) {
            try {
                criteriaEvaluators.add(factory.createEvaluationCriterion(criterion));
            } catch (UnknownCriterion e) {
                log.severe(e.getMessage());
            }
        }
        if (criteriaEvaluators.isEmpty()) {
            throw new CriteriaNotLoaded();
        }
        Criterion unrecognised = new Criterion(Criteria.UNRECOGNIZED_QUERY.name(), Criteria.UNRECOGNIZED_QUERY.getDescription(), Integer.MAX_VALUE, Criteria.UNRECOGNIZED_QUERY.getRequiredCount());
        criteriaEvaluators.add(factory.createEvaluationCriterion(unrecognised));
        return criteriaEvaluators.stream()
                .sorted(Comparator.comparingInt(eval -> eval.getCriterion().getPriority()))
                .collect(Collectors.toList());
    }
}
