package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "topics")
public class Topic extends AbstractEntity implements Cloneable {
//    @Column(unique = true)
    @NotNull
    private String name;

    @OneToMany
    private Set<Student> students;

    public Topic() {
        students = new HashSet<>();
    }

    public Topic(String name) {
        this.name = name;
        students = new HashSet<>();
    }

    @Override
    public Topic clone() {
        try {
            Topic clone = (Topic) super.clone();
            clone.students = new HashSet<>(students);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
