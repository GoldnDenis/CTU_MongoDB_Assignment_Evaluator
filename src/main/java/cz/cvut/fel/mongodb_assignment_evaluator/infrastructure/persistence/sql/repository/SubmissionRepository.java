package cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.persistence.sql.repository;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Student;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Submission;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SubmissionRepository extends CrudRepository<Submission, Long> {
    // all CRUD methods are automatically generated by JPA

    Optional<Submission> findByStudentAndUploadDate(Student student, LocalDateTime uploadDate);
}
