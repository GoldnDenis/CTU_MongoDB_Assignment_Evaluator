package cz.cvut.fel.mongodb_assignment_evaluator.persistence.repository;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.CriterionGroup;
import org.springframework.data.repository.CrudRepository;

public interface CriteriaGroupRepository extends CrudRepository<CriterionGroup, Long> {
//    Optional<CriterionGroup> findByName(String name);
}
