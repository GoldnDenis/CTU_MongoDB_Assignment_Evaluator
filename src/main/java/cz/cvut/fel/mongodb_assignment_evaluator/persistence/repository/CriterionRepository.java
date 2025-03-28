package cz.cvut.fel.mongodb_assignment_evaluator.persistence.repository;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import org.springframework.data.repository.CrudRepository;

public interface CriterionRepository extends CrudRepository<Criterion, Long> {
//    Optional<Criterion> findByDescription(String description);
//    List<Criterion> findByCriterionGroup(CriterionGroup criterionGroup);
}
