package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.CriterionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.CriteriaNotLoaded;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.StudentSubmission;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.evaluation.GradedCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.MongoQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.persistence.sql.service.CriterionService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The main class for criteria grading module.
 * It loads an initialized list of criterion evaluators and uses them to process queries.
 * Returns a list of finalized criterion evaluators with computed grade and status.
 */
@Component
public class CriteriaGrader {
    private final CriterionService criterionService;
    private List<CriterionEvaluator<? extends MongoQuery>> criteriaEvaluators;

    public CriteriaGrader(CriterionService criterionService) {
        this.criterionService = criterionService;
        this.criteriaEvaluators = new ArrayList<>();
    }

    /**
     * Invokes a service class to attempt to load the criteria evaluators
     * @throws CriteriaNotLoaded if no criteria were found or matched to concrete evaluators
     */
    public void initCriteria() throws CriteriaNotLoaded {
        criteriaEvaluators = new ArrayList<>(
                criterionService.loadCriteria()
        );
    }

    /**
     * Retrieves extracted queries from the StudentSubmission and runs them through loaded evaluators
     * @param submission stores information about the submission and queries for the evaluation
     * @return a list of evaluated criteria with final grades and fulfillment statuses
     */
    public List<GradedCriterion> evaluateQueries(StudentSubmission submission) {
        List<MongoQuery> queries = submission.getExtractedQueries();
        queries.forEach(query -> criteriaEvaluators.stream()
                .filter(criterion -> criterion.getQueryType().isInstance(query))
                .forEach(criterion -> criterion.check(query))
        );
        return criteriaEvaluators.stream()
                .map(CriterionEvaluator::getResult)
                .toList();
    }

    /**
     * Excludes the unrecognized, since it is not for evaluation purposes
     * @return a list of all criteria loaded from the database.
     */
    public List<Criterion> getLoadedCriteria() {
        return criteriaEvaluators.stream()
                .map(CriterionEvaluator::getCriterion)
                .collect(Collectors.toList()).subList(0, criteriaEvaluators.size() - 1);
    }
}
