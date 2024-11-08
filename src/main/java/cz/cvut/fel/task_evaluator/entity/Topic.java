package cz.cvut.fel.task_evaluator.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
