package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.factory;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.CriterionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.general.CommandCriterionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.UnknownCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.MongoQuery;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class CriteriaFactoryTest {

    @Test
    void test_createEvaluationCriterion_ExistingCriterion() {
        Criterion criterion = new Criterion(
                Criteria.CREATE_ONE_COLLECTION.name(),
                Criteria.CREATE_ONE_COLLECTION.getDescription(),
                1);
        CriteriaFactory criteriaFactory = new CriteriaFactory();
        CriterionEvaluator<? extends MongoQuery> concreteEvaluator = criteriaFactory.createEvaluationCriterion(criterion);

        assertInstanceOf(CommandCriterionEvaluator.class, concreteEvaluator);
        assertEquals(criterion, concreteEvaluator.getCriterion());
    }

    @Test
    void test_createEvaluationCriterion_NotExistingCriterion_ThrownException() {
        Criterion criterion = new Criterion(
                "NEAT_CRITERION",
                "The script has to be cool",
                55);
        CriteriaFactory criteriaFactory = new CriteriaFactory();
        assertThrows(UnknownCriterion.class, () -> {
            criteriaFactory.createEvaluationCriterion(criterion);
        });
    }
}
