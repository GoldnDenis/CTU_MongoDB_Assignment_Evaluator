package cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.persistence.sql.repository;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Log;
import org.springframework.data.repository.CrudRepository;

public interface LogRepository extends CrudRepository<Log, Long> {

}
