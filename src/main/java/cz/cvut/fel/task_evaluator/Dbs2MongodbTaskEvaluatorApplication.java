package cz.cvut.fel.task_evaluator;

import cz.cvut.fel.task_evaluator.service.evaluation.AssignmentEvaluator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Dbs2MongodbTaskEvaluatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(Dbs2MongodbTaskEvaluatorApplication.class, args);

//        String uri = "";
//        String name = "";

//		String path = "t1.json";
//        String path = "t7.js";
        String path = "scripts\\script";

        AssignmentEvaluator.evaluate(path);
//        AssignmentEvaluatorOld assignmentEvaluatorOld = new AssignmentEvaluatorOld();
//        assignmentEvaluatorOld.evaluate(path);
    }
}
