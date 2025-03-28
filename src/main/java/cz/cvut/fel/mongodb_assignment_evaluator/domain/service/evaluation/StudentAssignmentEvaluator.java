package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.error.StudentErrorCollector;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.Query;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.result.GradedSubmission;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.CriteriaEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.GradedCriteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.ScriptParser;
import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.util.List;

@Log
@Component
public class StudentAssignmentEvaluator {
    private ScriptParser scriptParser;
    private CriteriaEvaluator criteriaEvaluator;

//    private final String studentName;
    @Getter
    private final static StudentErrorCollector errorCollector = new StudentErrorCollector();

//    public StudentEvaluator(String studentName) {
//        this.studentName = studentName;
//    }

    public StudentAssignmentEvaluator() {
        scriptParser = new ScriptParser();
        criteriaEvaluator = new CriteriaEvaluator();
    }

    public GradedSubmission evaluateScript(List<String> scriptLines) {
//        if (studentName.equals("kulikvl1")) { //todo debug
//            System.out.println(1);
//        }
        List<Query> queryList = scriptParser.parse(scriptLines);
        List<GradedCriteria> gradedCriteria = criteriaEvaluator.evaluateQueries(queryList);

//        log.info("Successfully parsed the script of " + studentName);
//        StudentEvaluationResult studentResult = new RootCriteriaChecker().checkQueries(queryList);
//        StudentEvaluationResult studentResult = new StudentEvaluationResult(Collections.emptyList()); // todo debug
//        log.info("Script of " + studentName + " has been evaluated");
        return new GradedSubmission(gradedCriteria);
    }

//    public StudentEvaluationResult evaluateWork(StudentWork work) {
//        String studentName = work.getUsername();
//        log.info("---------| Started evaluation of '" + studentName + "' |---------");
//        List<Query> queryList = new ScriptParser().parse(work.getScriptLines());
//        log.info("Successfully parsed the script of " + studentName);
//        StudentEvaluationResult studentResult = new RootCriteriaChecker().checkQueries(queryList);
////        StudentEvaluationResult studentResult = new StudentEvaluationResult(Collections.emptyList()); // todo debug
//        log.info("Script of " + studentName + " has been evaluated");
//        return studentResult;
//    }
}
