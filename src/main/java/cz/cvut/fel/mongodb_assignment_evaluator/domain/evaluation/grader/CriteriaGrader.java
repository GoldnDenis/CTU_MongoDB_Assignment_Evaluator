package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.EvaluationCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.CriteriaNotLoaded;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.StudentSubmission;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.QueryToken;
import cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.persistence.sql.service.CriterionService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CriteriaGrader {
    private final CriterionService criterionService;
    private List<EvaluationCriterion<? extends QueryToken>> criteriaEvaluators;

    public CriteriaGrader(CriterionService criterionService) {
        this.criterionService = criterionService;
        this.criteriaEvaluators = new ArrayList<>();
    }

    public void initCriteria() throws CriteriaNotLoaded {
        criteriaEvaluators = new ArrayList<>(
                criterionService.loadCriteria()
        );
    }

    public void evaluateQueries(StudentSubmission submission) {
        List<QueryToken> queryTokens = submission.getQueryList();
        queryTokens.forEach(query -> criteriaEvaluators.stream()
                .filter(criterion -> criterion.getQueryType().isInstance(query))
                .forEach(criterion -> criterion.check(query))
        );
        criteriaEvaluators.forEach(eval -> submission.addGradedCriteria(eval.getResult()));
    }

    public List<Criterion> getLoadedCriteria() {
        return criteriaEvaluators.stream()
                .map(EvaluationCriterion::getCriterion)
                .collect(Collectors.toList()).subList(0, criteriaEvaluators.size() - 1);
    }
}
