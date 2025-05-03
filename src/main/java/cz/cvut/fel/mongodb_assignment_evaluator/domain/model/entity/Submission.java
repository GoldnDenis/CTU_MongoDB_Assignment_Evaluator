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

    @OneToMany(mappedBy = "submission")
    private Set<Query> queries;

    @OneToMany(mappedBy = "submission")
    private Set<SubmissionResult> submissionResults;

    public Submission() {
        this.queries = new HashSet<>();
        this.submissionResults = new HashSet<>();
    }

    public Submission(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
        this.queries = new HashSet<>();
        this.submissionResults = new HashSet<>();
    }

    public Submission(LocalDateTime uploadDate, Student student) {
        this.uploadDate = uploadDate;
        this.student = student.clone();
        this.queries = new HashSet<>();
        this.submissionResults = new HashSet<>();
    }

    public Submission(LocalDateTime uploadDate, Student student, Set<SubmissionResult> submissionResults) {
        this.uploadDate = uploadDate;
        this.student = student.clone();
        this.queries = new HashSet<>();
        this.submissionResults = new HashSet<>(submissionResults);
    }

    public void addQuery(Query query) {
        this.queries.add(query.clone());
    }

    @Override
    public Submission clone() {
        try {
            Submission clone = (Submission) super.clone();
            clone.uploadDate = uploadDate;
            clone.queries = new HashSet<>(queries);
            clone.submissionResults = new HashSet<>(submissionResults);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
