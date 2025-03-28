package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="criterionGroups")
//@NotNull
//@Getter
//@Setter
public class CriterionGroup extends AbstractEntity implements Cloneable {
    @Column(unique=true, nullable = false)
    private String name;

    @OneToMany
    private Set<Criterion> criteria;

    public CriterionGroup() {
        criteria = new HashSet<>();
    }

    public CriterionGroup(String name) {
        this.name = name;
        criteria = new HashSet<>();
    }


    @Override
    public CriterionGroup clone() {
        try {
            CriterionGroup clone = (CriterionGroup) super.clone();
            clone.criteria = new HashSet<>(criteria);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
