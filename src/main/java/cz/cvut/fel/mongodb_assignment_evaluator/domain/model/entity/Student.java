package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "student",
        uniqueConstraints = {
                @UniqueConstraint(name = "UniqueUsernameAndStudyYear", columnNames = {"username", "studyYear"})
        }
)
@NoArgsConstructor
@Getter
@Setter
public class Student extends AbstractEntity implements Cloneable {
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private int studyYear;

    @ManyToOne
    private Topic topic;

    public Student(String username, int studyYear) {
        this.username = username;
        this.studyYear = studyYear;
    }

    public Student(String username, int studyYear, Topic topic) {
        this.username = username;
        this.studyYear = studyYear;
        this.topic = topic.clone();
    }

    @Override
    public Student clone() {
        try {
            Student clone = (Student) super.clone();
            clone.topic = topic != null ? topic.clone() : null;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
