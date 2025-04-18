package cz.cvut.fel.mongodb_assignment_evaluator.domain.model;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.StudentErrorTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.Query;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.GradedCriteria;
import lombok.Getter;
import lombok.extern.java.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Log
@Getter
public class StudentSubmission {
    private final String username;
    private final String year;
    private final String date;
    private final String time;
    private final File folder;

    private final List<String> scriptLines;
    private final List<String> logs;
    private final List<Query> queryList;
    private final List<GradedCriteria> gradedCriteriaList;

    public StudentSubmission(String year, String username, String date, String time, File folder) {
        this.username = username;
        this.year = year;
        this.date = date;
        this.time = time;
        this.folder = folder;

        this.scriptLines = new ArrayList<>();
        this.logs = new ArrayList<>();
        this.queryList = new ArrayList<>();
        this.gradedCriteriaList = new ArrayList<>();
//        this.scriptLines = new ArrayList<>(scriptLines);
    }

    public void addScriptLines(List<String> lines) {
        scriptLines.addAll(lines);
    }

    public void addLog(Level level, StudentErrorTypes type, String message) {
        log.log(level, message);
        logs.add(
                level.getName() +
                " : " +
                type.getMessage() +
                " '" +
                message +
                "'."
        );
    }

    public void addQuery(Query query) {
        queryList.add(query);
    }

    public void addGradedCriteria(GradedCriteria criteria) {
        gradedCriteriaList.add(criteria);
    }
}
