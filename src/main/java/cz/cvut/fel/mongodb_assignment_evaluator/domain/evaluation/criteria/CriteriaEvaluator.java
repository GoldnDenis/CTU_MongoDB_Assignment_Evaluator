package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.criteria.EvaluationCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.CriteriaNotLoaded;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.StudentSubmission;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.QueryToken;
import cz.cvut.fel.mongodb_assignment_evaluator.persistence.service.CriterionService;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class CriteriaEvaluator {
    private final CriterionService criterionService;
    private Map<Criterion, EvaluationCriterion<? extends QueryToken>> criteriaEvaluators;

    public CriteriaEvaluator(CriterionService criterionService) {
        this.criterionService = criterionService;
        this.criteriaEvaluators = new LinkedHashMap<>();
    }

    public void initCriteria() throws CriteriaNotLoaded {
        criteriaEvaluators = new LinkedHashMap<>(
                criterionService.loadCriteria()
        );
    }

    public void evaluateQueries(StudentSubmission submission) {
        List<QueryToken> queryTokens = submission.getQueryList();
        queryTokens.forEach(query -> criteriaEvaluators.values().stream()
                .filter(criterion -> criterion.getQueryType().isInstance(query))
                .forEach(criterion -> criterion.check(query))
        );
        for (Map.Entry<Criterion, EvaluationCriterion<? extends QueryToken>> entry : criteriaEvaluators.entrySet()) {
            submission.addGradedCriteria(entry.getValue().getResult(entry.getKey()));
        }
    }

    public Set<Criterion> getLoadedCriteria() {
        return criteriaEvaluators.keySet();
    }
}
