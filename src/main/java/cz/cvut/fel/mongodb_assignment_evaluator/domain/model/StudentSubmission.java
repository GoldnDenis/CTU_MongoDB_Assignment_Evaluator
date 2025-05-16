package cz.cvut.fel.mongodb_assignment_evaluator.domain.model;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Submission;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.evaluation.GradedCriteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.QueryToken;
import lombok.Getter;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Log
@Getter
public class StudentSubmission {
    private final Submission submission;
    private final boolean submissionPresent;
    private final long teamCount;

    private final List<QueryToken> queryList;
    private final List<String> evaluationLogList;
    private final List<GradedCriteria> gradedCriteria;
    private final List<String> errorLogs;

    public StudentSubmission(Submission submission, boolean submissionPresent, long teamCount) {
        this.submission = submission;
        this.submissionPresent = submissionPresent;
        this.teamCount = teamCount;

        this.queryList = new ArrayList<>();
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

    public void addQuery(QueryToken query) {
        queryList.add(query);
    }

    public void addGradedCriteria(GradedCriteria criteria) {
        gradedCriteria.add(criteria);
    }
}
