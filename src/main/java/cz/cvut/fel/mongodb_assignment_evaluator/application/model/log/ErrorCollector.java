package cz.cvut.fel.mongodb_assignment_evaluator.application.model.log;

import cz.cvut.fel.mongodb_assignment_evaluator.enums.ErrorTypes;
import lombok.Getter;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Log
@Getter
public class ErrorCollector {
    private final List<String> logList;

    public ErrorCollector() {
        logList = new ArrayList<>();
    }

    public void addLog(Level level, ErrorTypes type, String message) {
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
