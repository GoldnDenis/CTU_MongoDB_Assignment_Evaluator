package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.Query;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.iterator.LineIterator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScriptParser {
    public List<Query> parse(List<String> fileLines) {
        ParserStateMachine stateMachine = new ParserStateMachine();
        for (String fileLine : fileLines) {
            stateMachine.parseLine(new LineIterator(fileLine));
        }
        return stateMachine.getQueryList();
    }
}
