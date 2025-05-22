package cz.cvut.fel.mongodb_assignment_evaluator.domain.model;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Submission;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.evaluation.GradedCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.MongoQuery;
import lombok.Getter;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Class that accumulates information about the evaluation. Also contains a link to the database via Submission entity.
 * Here are all logs, extracted queries and criteria evaluation results.
 */
@Log
@Getter
public class StudentSubmission {
    private final Submission submission;
    private final boolean submissionPresent;
    private final long teamCount;

    private final List<String> errorLogs;
    private final List<String> evaluationLogList;

    private List<MongoQuery> extractedQueries;
    private List<GradedCriterion> gradedCriteria;

    /**
     * @param submission entity, link to database
     * @param submissionPresent initially found or not
     * @param teamCount amount of students with the same topic
     */
    public StudentSubmission(Submission submission, boolean submissionPresent, long teamCount) {
        this.submission = submission;
        this.submissionPresent = submissionPresent;
        this.teamCount = teamCount;

        this.extractedQueries = new ArrayList<>();
        this.evaluationLogList = new ArrayList<>();
        this.gradedCriteria = new ArrayList<>();
        this.errorLogs = new ArrayList<>();
    }

    public String getStudentName() {
        return submission.getStudent().getUsername();
    }

    public void addLog(Level level, String message) {
        message = level.getName() + " : " + message;
        log.log(level, message);
        evaluationLogList.add(message);
        if (level.equals(Level.SEVERE) || level.equals(Level.WARNING)) {
            errorLogs.add(message);
        }
    }

    public void setExtractedQueries(List<MongoQuery> extractedQueries) {
        this.extractedQueries = new ArrayList<>(extractedQueries);
    }

    public void setGradedCriteria(List<GradedCriterion> gradedCriteria) {
        this.gradedCriteria = new ArrayList<>(gradedCriteria);
    }
}
