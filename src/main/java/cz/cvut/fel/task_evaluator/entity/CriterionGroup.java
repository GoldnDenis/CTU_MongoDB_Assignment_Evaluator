package cz.cvut.fel.task_evaluator.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;

//@Entity
//@Table(name="criterionGroups")
////@NotNull
//@Getter @Setter
//public class CriterionGroup extends AbstractEntity {
//    @Column(unique=true)
//    private String name;
//
//    @OneToMany
//    private Set<Criterion> criteria;
//
//    public CriterionGroup() {}
//
//    public CriterionGroup(String name) {
//        this.name = name;
//    }
//}