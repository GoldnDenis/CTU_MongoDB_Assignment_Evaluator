package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "query")
@Getter
@Setter
public class Query extends AbstractEntity implements Cloneable {
    @Column(nullable = false)
    private String query;

    @ManyToOne
    private Submission submission;

    @ManyToMany(mappedBy = "queries")
    private Set<Criterion> criteria;

    public Query() {
        this.criteria = new HashSet<>();
    }

    public Query(String query, Submission submission) {
        this.query = query;
        this.submission = submission.clone();
        this.criteria = new HashSet<>();
    }

    public Query(String query, Submission submission, Set<Criterion> criteria) {
        this.query = query;
        this.submission = submission.clone();
        this.criteria = new HashSet<>(criteria);
    }

    @Override
    public Query clone() {
        try {
            Query clone = (Query) super.clone();
            clone.submission = submission != null ? submission.clone() : null;
            clone.criteria = new HashSet<>(criteria);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
