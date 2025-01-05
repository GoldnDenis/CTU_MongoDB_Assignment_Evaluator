package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.log;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.LogTypes;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Getter
public class LogCollector {
    private final List<String> logList;

    public LogCollector() {
        logList = new ArrayList<>();
    }

    public void addLog(Level level, LogTypes type, String message) {
        String logBuilder = level.getName() +
                " : " +
                type.getMessage() +
                " '" +
                message +
                "'.";
        logList.add(logBuilder);
    }
}
