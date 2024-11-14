package cz.cvut.fel.mongodb_assignment_evaluator.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "criteria")
//@NotNull
@Getter
@Setter
public class Criterion extends AbstractEntity {
    private String description;

    @ManyToOne
    private CriterionGroup criterionGroup;

    @OneToMany
    private Set<CriterionFulfillment> criterionFulfillmentSet;

    public Criterion() {}

    public Criterion(String description) {
        this.description = description;
    }

    public Criterion(String description, CriterionGroup criterionGroup) {
        this.description = description;
        this.criterionGroup = criterionGroup;
    }
}
