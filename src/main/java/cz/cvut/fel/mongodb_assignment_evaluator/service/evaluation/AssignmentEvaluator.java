package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.CriteriaChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class AssignmentEvaluator {
    public static void evaluate(String path) {
        try {
            path = path + ".js";

            List<String> fileLines = Files.readAllLines(Paths.get(path));
            List<Query> queryList = ScriptParser.parse(fileLines);
            new CriteriaChecker().checkQueries(queryList);
        } catch (IOException e) {
            //todo work out exception logic
            throw new RuntimeException(e);
        }
    }
}
