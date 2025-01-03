package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.parser;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.Query;

import java.util.List;

public class ScriptParser {
    // regex for queries db\.([._0-9a-zA-Z]+)\(([\s\S]*?)(\)(\.[\s\S]*?\)))*;
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
