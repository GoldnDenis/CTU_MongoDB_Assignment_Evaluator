package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "criterion")
@Getter
@Setter
public class Criterion extends AbstractEntity implements Cloneable {
    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(name = "min_score", nullable = false, columnDefinition = "integer default 1")
    private int minCount;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private CriterionGroup group;

    @OneToMany(mappedBy = "criterion")
    private Set<SubmissionResult> submissionResults;

    @ManyToMany
    @JoinTable(
            name = "fulfilledqueries",
            joinColumns = @JoinColumn(name = "criterion_id"),
            inverseJoinColumns = @JoinColumn(name = "query_id")
    )
    private Set<Query> queries;

    public Criterion() {
        this.submissionResults = new HashSet<>();
        this.queries = new HashSet<>();
    }

    public Criterion(String name, String description) {
        this.name = name;
        this.description = description;
        this.minCount = 1;
        this.submissionResults = new HashSet<>();
        this.queries = new HashSet<>();
    }

    public Criterion(String name, String description, int minCount) {
        this.name = name;
        this.description = description;
        this.minCount = minCount;
        this.submissionResults = new HashSet<>();
        this.queries = new HashSet<>();
    }

    public Criterion(String name, String description, int minCount, CriterionGroup group) {
        this.name = name;
        this.description = description;
        this.minCount = minCount;
        this.group = group.clone();
        this.submissionResults = new HashSet<>();
        this.queries = new HashSet<>();
    }

    public Criterion(String name, String description, int minCount, CriterionGroup group, Set<SubmissionResult> submissionResults) {
        this.name = name;
        this.description = description;
        this.minCount = minCount;
        this.group = group.clone();
        this.submissionResults = new HashSet<>(submissionResults);
        this.queries = new HashSet<>();
    }

    public Criterion(String name, String description, int minCount, CriterionGroup group, Set<SubmissionResult> submissionResults, Set<Query> queries) {
        this.name = name;
        this.description = description;
        this.minCount = minCount;
        this.group = group.clone();
        this.submissionResults = new HashSet<>(submissionResults);
        this.queries = new HashSet<>(queries);
    }

    @Override
    public Criterion clone() {
        try {
            Criterion clone = (Criterion) super.clone();
            clone.group = group != null ? group.clone() : null;
            clone.submissionResults = new HashSet<>(submissionResults);
            clone.queries = new HashSet<>(queries);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
