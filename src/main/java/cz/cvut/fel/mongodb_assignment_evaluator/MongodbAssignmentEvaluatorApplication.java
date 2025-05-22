package cz.cvut.fel.mongodb_assignment_evaluator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MongodbAssignmentEvaluatorApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MongodbAssignmentEvaluatorApplication.class, args);
        System.exit(SpringApplication.exit(context));
    }

}
