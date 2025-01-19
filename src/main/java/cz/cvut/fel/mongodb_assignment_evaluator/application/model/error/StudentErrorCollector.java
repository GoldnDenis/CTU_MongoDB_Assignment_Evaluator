package cz.cvut.fel.mongodb_assignment_evaluator.application.model.error;

import cz.cvut.fel.mongodb_assignment_evaluator.enums.StudentErrorTypes;
import lombok.Getter;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Log
@Getter
public class StudentErrorCollector {
    private final List<String> logList;

    public StudentErrorCollector() {
        logList = new ArrayList<>();
    }

    public void addLog(Level level, StudentErrorTypes type, String message) {
        log.log(level, message);
        String logBuilder = level.getName() +
                " : " +
                type.getMessage() +
                " '" +
                message +
                "'.";
        logList.add(logBuilder);
    }
}
