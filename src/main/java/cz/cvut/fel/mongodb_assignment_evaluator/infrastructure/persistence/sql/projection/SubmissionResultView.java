package cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.persistence.sql.projection;

public interface SubmissionResultView {
    String getUsername();

    String getName();

    int getScore();
}
