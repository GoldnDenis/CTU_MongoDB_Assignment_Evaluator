package cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.persistence.sql.projection;

/**
 * Projection record of (student.username, criterion.name, submissionresult.score)
 */
public interface SubmissionResultView {
    String getUsername();

    String getName();

    int getScore();
}
