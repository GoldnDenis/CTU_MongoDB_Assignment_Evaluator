package cz.cvut.fel.task_evaluator.service.evaluation.parser;

import cz.cvut.fel.task_evaluator.service.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.task_evaluator.service.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.task_evaluator.service.evaluation.query.Query;

import java.util.List;

public class ScriptParser {
    public static List<Query> parse(List<String> fileLines) {
        ParserStateMachine stateMachine = new ParserStateMachine();

        LineIterator iterator = new LineIterator();
        for (int i = 0; i < fileLines.size(); i++) {
            iterator.changeText(fileLines.get(i), i);
            stateMachine.parseLine(iterator);
        }

        return stateMachine.getQueryList();
    }
}
