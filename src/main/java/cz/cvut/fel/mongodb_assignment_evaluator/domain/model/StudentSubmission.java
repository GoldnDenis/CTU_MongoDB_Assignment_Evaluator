package cz.cvut.fel.mongodb_assignment_evaluator.domain.model;

import lombok.Getter;

import java.io.File;

@Getter
public class StudentSubmission {
    private final String username;
    private final String year;
    private final String date;
    private final String time;
//    private final List<String> scriptLines;
    private final File folder;

    public StudentSubmission(String year, String username, String date, String time, File folder) {
        this.username = username;
        this.year = year;
        this.date = date;
        this.time = time;
        this.folder = folder;
//        this.scriptLines = new ArrayList<>(scriptLines);
    }
}
