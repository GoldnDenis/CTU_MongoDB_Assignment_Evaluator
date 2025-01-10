package cz.cvut.fel.mongodb_assignment_evaluator.application.model.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@Getter
public class CriterionFulfillmentId implements Serializable {
    private Long criterionId;
    private Long studentId;

    public CriterionFulfillmentId() {}
}
