package cz.cvut.fel.mongodb_assignment_evaluator.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "topics")
//@NotNull
@Getter
public class Topic extends AbstractEntity {
    @Column(unique = true)
    @Setter
    private String title;

    @OneToMany
    private Set<Student> students;

    public Topic() {}

    public Topic(String title) {
        this.title = title;
    }
}
