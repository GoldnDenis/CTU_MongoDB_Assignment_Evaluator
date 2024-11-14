package cz.cvut.fel.mongodb_assignment_evaluator.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="criterionFulfillments")
//@NotNull
@Getter
public class CriterionFulfillment {
    @EmbeddedId
    private CriterionFulfillmentId id;

    @Min(0)
    @Column(columnDefinition = "INT DEFAULT 0")
    @Setter
    private int counter;

    @ManyToOne
    @MapsId("criterionId")
    @JoinColumn(name = "criterion_id")
    private Criterion criterion;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    public CriterionFulfillment() {}

    public CriterionFulfillment(Criterion criterion, Student student) {
        this.criterion = criterion;
        this.student = student;
        this.id = new CriterionFulfillmentId(criterion.getId(), student.getId());
    }

    public CriterionFulfillment(Criterion criterion, Student student, int counter) {
        this.counter = counter;
        this.criterion = criterion;
        this.student = student;
        this.id = new CriterionFulfillmentId(criterion.getId(), student.getId());
    }
}
