package cz.cvut.fel.mongodb_assignment_evaluator.repository;

import cz.cvut.fel.mongodb_assignment_evaluator.entity.Student;
import cz.cvut.fel.mongodb_assignment_evaluator.entity.Topic;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StudentRepository extends CrudRepository<Student, Long> {
    Optional<Student> findByUsername(String username);
    Optional<Student> findByTopic(Topic topic);
}
