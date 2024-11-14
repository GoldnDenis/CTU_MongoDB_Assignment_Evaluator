package cz.cvut.fel.mongodb_assignment_evaluator.repository;

import cz.cvut.fel.mongodb_assignment_evaluator.entity.Topic;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TopicRepository extends CrudRepository<Topic, Long> {
    Optional<Topic> findByTitle(String title);
}
