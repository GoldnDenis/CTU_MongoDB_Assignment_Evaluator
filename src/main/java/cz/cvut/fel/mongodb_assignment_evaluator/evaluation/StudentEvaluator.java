package cz.cvut.fel.mongodb_assignment_evaluator.evaluation;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.CriteriaEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.LogTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.log.LogCollector;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.Query;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.result.StudentEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.parser.ScriptParser;
import lombok.Getter;
import lombok.extern.java.Log;

import java.io.File;
import java.util.List;
import java.util.logging.Level;

@Log
public class StudentEvaluator {
    private final String studentName;
    @Getter
    public final static LogCollector studentLogCollector = new LogCollector();

    public StudentEvaluator(String studentName) {
        this.studentName = studentName;
    }

    public StudentEvaluationResult evaluateScriptLines(List<String> scriptLines) {
        log.info("Parsing the script of " + studentName + "...");
        List<Query> queryList = ScriptParser.parse(scriptLines);

        log.info("Evaluating queries...");
        StudentEvaluationResult studentResult = new CriteriaEvaluator().check(queryList);

        return studentResult;
    }
}
