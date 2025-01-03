package cz.cvut.fel.mongodb_assignment_evaluator.persistence;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.entity.Student;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.entity.Topic;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StudentRepository extends CrudRepository<Student, Long> {
    Optional<Student> findByUsername(String username);
    Optional<Student> findByTopic(Topic topic);
}
