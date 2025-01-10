package cz.cvut.fel.mongodb_assignment_evaluator.application.model.result;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
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
