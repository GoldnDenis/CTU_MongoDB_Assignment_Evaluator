package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "submissionresult")
@Getter
@Setter
public class SubmissionResult {
    @EmbeddedId
    private SubmissionResultId id;

    @ManyToOne
    @MapsId("criterionId")
    @JoinColumn(name = "criterion_id")
    private Criterion criterion;

    @ManyToOne
    @MapsId("submissionId")
    @JoinColumn(name = "submission_id")
    private Submission submission;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int score;

    public SubmissionResult() {
        this.id = new SubmissionResultId();
        this.score = 0;
    }

    public SubmissionResult(Criterion criterion, Submission submission) {
        this.id = new SubmissionResultId(criterion.getId(), submission.getId());
        this.criterion = criterion.clone();
        this.submission = submission.clone();
        this.score = 0;
    }

    public SubmissionResult(Criterion criterion, Submission submission, int score) {
        this.id = new SubmissionResultId(criterion.getId(), submission.getId());
        this.criterion = criterion.clone();
        this.submission = submission.clone();
        this.score = score;
    }

    public void setCriterion(Criterion criterion) {
        this.criterion = criterion.clone();
        this.id.setCriterionId(criterion.getId());
    }

    public void setSubmission(Submission submission) {
        this.submission = submission.clone();
        this.id.setSubmissionId(submission.getId());
    }

    @Override
    public SubmissionResult clone() {
        try {
            SubmissionResult clone = (SubmissionResult) super.clone();
            clone.id = new SubmissionResultId(id.getCriterionId(), id.getSubmissionId());
            clone.criterion = criterion != null ? criterion.clone() : null;
            clone.submission = submission != null ? submission.clone() : null;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
