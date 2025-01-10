package cz.cvut.fel.mongodb_assignment_evaluator.application.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Set;

@Entity
@Table(name = "students")
//@NotNull
@Getter
public class Student extends AbstractEntity {
    @Column(unique = true)
    private String username;

    @OneToMany
    private Set<CriterionFulfillment> criterionFulfillmentSet;

    @ManyToOne
    private Topic topic;

    public Student() {}

    public Student(String username) {
        this.username = username;
    }

    public Student(String username, Topic topic) {
        this.username = username;
        this.topic = topic;
    }
}
