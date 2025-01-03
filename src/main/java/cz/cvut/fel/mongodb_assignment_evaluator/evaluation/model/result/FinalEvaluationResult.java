package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.result;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class FinalEvaluationResult {
    private final Map<String, StudentEvaluationResult> studentResultMap;

    public FinalEvaluationResult() {
        studentResultMap = new HashMap<>();
    }

    public void addStudentResult(String studentName, StudentEvaluationResult studentResult) {
        studentResultMap.put(studentName, studentResult);
    }
}
