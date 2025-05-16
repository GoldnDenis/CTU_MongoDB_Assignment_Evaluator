package cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.config;

import cz.cvut.fel.mongodb_assignment_evaluator.application.AssignmentEvaluator;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Log
@Component
public class EvaluationRunner implements CommandLineRunner {
    private final AssignmentEvaluator assignmentEvaluator;

    @Value("${root.directory.path}")
    private String rootDirectoryPath;

    public EvaluationRunner(AssignmentEvaluator assignmentEvaluator) {
        this.assignmentEvaluator = assignmentEvaluator;
    }

    @Override
    public void run(String... args) {
        log.info("Starting evaluation for directory: " + rootDirectoryPath);
        assignmentEvaluator.evaluate(rootDirectoryPath);
        System.out.println("Evaluation completed.");
    }
}
