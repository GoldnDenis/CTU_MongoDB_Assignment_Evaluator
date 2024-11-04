package cz.cvut.fel.task_evaluator.evaluation;

import cz.cvut.fel.task_evaluator.evaluation.parser.ScriptParser;
import cz.cvut.fel.task_evaluator.model.query.Query;
import cz.cvut.fel.task_evaluator.utility.FileReader;

import java.io.IOException;
import java.util.List;
import java.util.Queue;

public class AssignmentEvaluator {
    //todo make multiple evaluation logic

    public static void evaluate(String path) {
        try {
            List<String> fileLines = FileReader.readAllLines(path);
            List<Query> queryList = ScriptParser.parse(fileLines);

            System.out.println(queryList.size());
        } catch (IOException e) {
            //todo work out exception logic
            throw new RuntimeException(e);
        }
    }
}
