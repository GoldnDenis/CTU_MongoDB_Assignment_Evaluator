package cz.cvut.fel.mongodb_assignment_evaluator.persistence.repository;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {
//    Optional<Student> findByUsername(String username);
//    Optional<Student> findByTopic(Topic topic);
}
