package cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.persistence.sql.repository;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import org.springframework.data.repository.CrudRepository;

public interface CriterionRepository extends CrudRepository<Criterion, Long> {
    // all CRUD methods are automatically generated by JPA
}
