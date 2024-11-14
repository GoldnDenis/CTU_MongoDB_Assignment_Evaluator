package cz.cvut.fel.mongodb_assignment_evaluator.repository;

import cz.cvut.fel.mongodb_assignment_evaluator.entity.CriterionFulfillment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CriterionFulfillmentRepository extends CrudRepository<CriterionFulfillment, Long> {
    Optional<CriterionFulfillment> findByCriterionIdAndStudentId(Long criterionId, Long studentId);
    List<CriterionFulfillment> findByStudentId(Long studentId);
    List<CriterionFulfillment> findByCriterionId(Long criterionId);
}
