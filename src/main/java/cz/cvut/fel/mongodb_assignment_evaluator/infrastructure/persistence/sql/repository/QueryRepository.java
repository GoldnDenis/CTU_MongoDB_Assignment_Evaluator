package cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.persistence.sql.repository;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Query;
import org.springframework.data.repository.CrudRepository;

public interface QueryRepository extends CrudRepository<Query, Long> {
}
