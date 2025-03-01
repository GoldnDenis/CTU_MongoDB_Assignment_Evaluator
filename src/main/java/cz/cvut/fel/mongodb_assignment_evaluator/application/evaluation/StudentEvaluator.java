package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.RootCriteriaChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.error.StudentErrorCollector;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.Query;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.result.StudentEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.ScriptParser;
import lombok.Getter;
import lombok.extern.java.Log;

import java.util.Collections;
import java.util.List;

@Log
public class StudentEvaluator {
    private final String studentName;
    @Getter
    private final static StudentErrorCollector errorCollector = new StudentErrorCollector();

    public StudentEvaluator(String studentName) {
        this.studentName = studentName;
    }

    public StudentEvaluationResult evaluateScript(List<String> scriptLines) {
        if (studentName.equals("kulikvl1")) { //todo
            System.out.println(1);
        }
        List<Query> queryList = new ScriptParser().parse(scriptLines);
        log.info("Successfully parsed the script of " + studentName);
//        StudentEvaluationResult studentResult = new RootCriteriaChecker().checkQueries(queryList);
        StudentEvaluationResult studentResult = new StudentEvaluationResult(Collections.emptyList());
        log.info("Script of " + studentName + " has been evaluated");
        return studentResult;
    }
}
