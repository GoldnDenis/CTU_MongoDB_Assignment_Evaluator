package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.CriteriaChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class AssignmentEvaluator {
    public static void evaluate(String path) {
        try {
            List<String> fileLines = Files.readAllLines(Paths.get(path + ".js"));
            List<Query> queryList = ScriptParser.parse(fileLines);

            List<String> feedbackList = new CriteriaChecker().checkQueries(queryList);
            new BufferedWriter(new FileWriter(path + ".csv"))
                    .write(String.join("", feedbackList));
        } catch (IOException e) {
            //todo work out exception logic
            throw new RuntimeException(e);
        }
    }
}
