package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name="criterionFulfillments")
public class CriterionFulfillment {
    @EmbeddedId
    private CriterionFulfillmentId id;

//    @Min(0)
//    @Column(columnDefinition = "INT DEFAULT 0")
//    private int counter;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("criterionId")
    @JoinColumn(name = "criterion_id")
    private Criterion criterion;

    public CriterionFulfillment() {}

    public CriterionFulfillment(Criterion criterion, Student student) {
        this.id = new CriterionFulfillmentId(criterion.getId(), student.getId());
        this.criterion = criterion.clone();
        this.student = student.clone();
    }

//    public CriterionFulfillment(Criterion criterion, Student student, int counter) {
//        this.counter = counter;
//        this.criterion = criterion;
//        this.student = student;
//        this.id = new CriterionFulfillmentId(criterion.getId(), student.getId());
//    }
}
