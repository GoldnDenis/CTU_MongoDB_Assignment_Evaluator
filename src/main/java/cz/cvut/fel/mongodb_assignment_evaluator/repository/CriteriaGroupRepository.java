package cz.cvut.fel.mongodb_assignment_evaluator.repository;

import cz.cvut.fel.mongodb_assignment_evaluator.entity.CriterionGroup;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CriteriaGroupRepository extends CrudRepository<CriterionGroup, Long> {
    Optional<CriterionGroup> findByName(String name);
}
