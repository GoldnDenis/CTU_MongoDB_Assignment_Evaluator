package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScriptParser {
    // regex for queries db\.([._0-9a-zA-Z]+)\(([\s\S]*?)(\)(\.[\s\S]*?\)))*;
    public List<Query> parse(List<String> fileLines) {
        ParserStateMachine stateMachine = new ParserStateMachine();

        LineIterator iterator = new LineIterator();
        for (int i = 0; i < fileLines.size(); i++) {
            iterator.changeText(fileLines.get(i), i);
            stateMachine.parseLine(iterator);
        }

        return stateMachine.getQueryList();
    }
}
