package cz.cvut.fel.task_evaluator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@Getter
public class CriterionFulfillmentId implements Serializable {
    private Long criterionId;
    private Long studentId;

    public CriterionFulfillmentId() {}
}
