package cz.cvut.fel.mongodb_assignment_evaluator.persistence.repository;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.SubmissionResult;
import org.springframework.data.repository.CrudRepository;

public interface SubmissionResultRepository extends CrudRepository<SubmissionResult, Long> {
}
