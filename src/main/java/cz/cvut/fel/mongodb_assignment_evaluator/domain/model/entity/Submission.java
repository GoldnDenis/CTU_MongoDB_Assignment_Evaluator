package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "submission")
@Getter
@Setter
public class Submission extends AbstractEntity implements Cloneable {
    @Column(nullable = false)
    private LocalDateTime uploadDate;

    @ManyToOne
    private Student student;

    public Submission() {
    }

    public Submission(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Submission(LocalDateTime uploadDate, Student student) {
        this.uploadDate = uploadDate;
        this.student = student.clone();
    }

    public Submission(LocalDateTime uploadDate, Student student, Set<SubmissionResult> submissionResults) {
        this.uploadDate = uploadDate;
        this.student = student.clone();
    }

    @Override
    public Submission clone() {
        try {
            Submission clone = (Submission) super.clone();
            clone.uploadDate = uploadDate;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
