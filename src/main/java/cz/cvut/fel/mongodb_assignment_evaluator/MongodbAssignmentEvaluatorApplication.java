package cz.cvut.fel.mongodb_assignment_evaluator;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.AssignmentEvaluator;
import org.bson.Document;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MongodbAssignmentEvaluatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongodbAssignmentEvaluatorApplication.class, args);

        String path = "scripts\\script";
        AssignmentEvaluator.evaluate(path);
    }

}
