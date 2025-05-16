package cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.persistence.sql.repository;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Student;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Topic;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StudentRepository extends CrudRepository<Student, Long> {
    Optional<Student> findByUsernameAndStudyYear(String username, int studyYear);

    long countByTopic(Topic topic);
}
