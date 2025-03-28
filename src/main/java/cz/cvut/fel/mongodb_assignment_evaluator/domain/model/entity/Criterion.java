package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "criteria")
//@NotNull
public class Criterion extends AbstractEntity implements Cloneable {
    @Column(unique = true, nullable = false)
    private String name;
    @NotNull
    private String description;
//    private int requiredCount;

    @ManyToOne
    private CriterionGroup criterionGroup;

    @OneToMany
    private Set<CriterionFulfillment> criterionFulfillmentSet;

    public Criterion() {
        criterionFulfillmentSet = new HashSet<>();
    }

//    public Criterion(String description, int requiredCount) {
//        this.description = description;
//        this.requiredCount = requiredCount;
//    }

    public Criterion(String name, String description, CriterionGroup criterionGroup) {
        this.name = name;
        this.description = description;
        this.criterionGroup = criterionGroup.clone();
        criterionFulfillmentSet = new HashSet<>();
    }

    @Override
    public Criterion clone() {
        try {
            Criterion clone = (Criterion) super.clone();
            clone.criterionGroup = criterionGroup.clone();
            clone.criterionFulfillmentSet = new HashSet<>(criterionFulfillmentSet);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

//    public Criterion(String description, int requiredCount, CriterionGroup criterionGroup) {
//        this.description = description;
//        this.requiredCount = requiredCount;
//        this.criterionGroup = criterionGroup;
//    }
}
