package cz.cvut.fel.task_evaluator.service.evaluation;

import cz.cvut.fel.task_evaluator.service.evaluation.checker.CriteriaChecker;
import cz.cvut.fel.task_evaluator.service.evaluation.parser.ScriptParser;
import cz.cvut.fel.task_evaluator.service.evaluation.query.Query;
import cz.cvut.fel.task_evaluator.utility.FileReader;

import java.io.IOException;
import java.util.List;

public class AssignmentEvaluator {
    public static void evaluate(String path) {
        try {
//            for (int i = 0; i < 10; i++) {
//                List<String> fileLines = FileReader.readAllLines(path + i + ".js");
//                List<Query> queryList = ScriptParser.parse(fileLines);
//                new CriteriaChecker().checkQueries(queryList);
//            }
            List<String> fileLines = FileReader.readAllLines(path + ".js");
            List<Query> queryList = ScriptParser.parse(fileLines);
            new CriteriaChecker().checkQueries(queryList);
        } catch (IOException e) {
            //todo work out exception logic
            throw new RuntimeException(e);
        }
    }
}
