package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.StudentSubmission;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.CriteriaEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.ScriptParser;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Log
@Component
public class StudentAssignmentEvaluator {
    private ScriptParser scriptParser;
    private CriteriaEvaluator criteriaEvaluator;

//    private final String studentName;
//    @Getter
//    private final static StudentErrorCollector errorCollector = new StudentErrorCollector();

//    public StudentEvaluator(String studentName) {
//        this.studentName = studentName;
//    }

    public StudentAssignmentEvaluator() {
        scriptParser = new ScriptParser();
        criteriaEvaluator = new CriteriaEvaluator();
    }

    public void evaluateStudent(StudentSubmission submission) {
        scriptParser.extractQueries(submission);
        criteriaEvaluator.evaluateQueries(submission);

//        if (submission.getUsername().equalsIgnoreCase("banertam")) {
//            System.out.println();
//        }

//        log.info("Successfully parsed the script of " + studentName);
//        StudentEvaluationResult studentResult = new RootCriteriaChecker().checkQueries(queryList);
//        StudentEvaluationResult studentResult = new StudentEvaluationResult(Collections.emptyList()); // todo debug
//        log.info("Script of " + studentName + " has been evaluated");
//        return new GradedSubmission(gradedCriteria);
    }

//    public StudentEvaluationResult evaluateWork(StudentWork work) {
//        String studentName = work.getUsername();
//        log.info("---------| Started evaluation of '" + studentName + "' |---------");
//        List<Query> queryList = new ScriptParser().extractQueries(work.getScriptLines());
//        log.info("Successfully parsed the script of " + studentName);
//        StudentEvaluationResult studentResult = new RootCriteriaChecker().checkQueries(queryList);
////        StudentEvaluationResult studentResult = new StudentEvaluationResult(Collections.emptyList()); // todo debug
//        log.info("Script of " + studentName + " has been evaluated");
//        return studentResult;
//    }
}
