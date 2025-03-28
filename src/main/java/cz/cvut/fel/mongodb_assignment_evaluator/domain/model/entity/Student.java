package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student extends AbstractEntity implements Cloneable{
    @NotNull
    private String username;
    @NotNull
    private Date studyYear;

    @ManyToOne
    private Topic topic;

    @ManyToMany
    private Set<CriterionFulfillment> criterionFulfillmentSet;

    public Student() {
        criterionFulfillmentSet = new HashSet<>();
    }

    public Student(String username, Date studyYear, Topic topic) {
        this.username = username;
        this.studyYear = (Date) studyYear.clone();
        this.topic = topic.clone();
        criterionFulfillmentSet = new HashSet<>();
    }

    @Override
    public Student clone() {
        try {
            Student clone = (Student) super.clone();
            clone.studyYear = (Date) studyYear.clone();
            clone.topic = topic.clone();
            clone.criterionFulfillmentSet = new HashSet<>(criterionFulfillmentSet);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
