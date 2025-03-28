package cz.cvut.fel.mongodb_assignment_evaluator.persistence.repository;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.CriterionFulfillment;
import org.springframework.data.repository.CrudRepository;

public interface CriterionFulfillmentRepository extends CrudRepository<CriterionFulfillment, Long> {
//    Optional<CriterionFulfillment> findByCriterionIdAndStudentId(Long criterionId, Long studentId);
//    List<CriterionFulfillment> findByStudentId(Long studentId);
//    List<CriterionFulfillment> findByCriterionId(Long criterionId);
}
