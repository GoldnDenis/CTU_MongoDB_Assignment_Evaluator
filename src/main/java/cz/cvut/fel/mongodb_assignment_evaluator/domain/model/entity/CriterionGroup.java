package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "criteriongroup")
@Getter
@Setter
public class CriterionGroup extends AbstractEntity implements Cloneable {
    @Column(unique = true, nullable = false)
    private String name;

    public CriterionGroup() {
        super();
    }

    public CriterionGroup(String name) {
        this.name = name;
    }

    @Override
    public CriterionGroup clone() {
        try {
            return (CriterionGroup) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
