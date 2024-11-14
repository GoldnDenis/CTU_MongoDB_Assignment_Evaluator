package cz.cvut.fel.mongodb_assignment_evaluator.repository;

import cz.cvut.fel.mongodb_assignment_evaluator.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.entity.CriterionGroup;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CriterionRepository extends CrudRepository<Criterion, Long> {
    Optional<Criterion> findByDescription(String description);
    List<Criterion> findByCriterionGroup(CriterionGroup criterionGroup);
}
