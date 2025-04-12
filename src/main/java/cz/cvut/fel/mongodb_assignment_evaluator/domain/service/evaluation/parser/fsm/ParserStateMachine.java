package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.StudentErrorTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.QueryTokenAssembler;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.Query;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.StudentAssignmentEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.ScriptState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.iterator.LineIterator;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ParserStateMachine extends StateMachine<ParserState> {
    private ParserState currentState;
    @Getter
    private final List<Query> queryList;
    private final StringBuilder wordAccumulator;
    @Getter
    private final QueryTokenAssembler assembler;
    private int currentLine;

    public ParserStateMachine() {
//        currentState = new ScriptState(this);
        queryList = new ArrayList<>();
        wordAccumulator = new StringBuilder();
        assembler = new QueryTokenAssembler();
    }

    public String getAccumulatedWord() {
        return wordAccumulator.toString();
    }

    public void accumulate(String subWord) {
        wordAccumulator.append(subWord);
    }

    public void accumulate(Character character) {
        wordAccumulator.append(character);
    }

    public void initAssembler(int currentColumn) {
        assembler.resetAccumulators();
//        System.out.println(currentLine + ":" + currentColumn); //todo
        assembler.setCurrentPosition(currentLine, currentColumn);
    }

    public void parseLine(LineIterator lineIterator) {
        currentLine++;
//        if (currentLine == 225) { // todo debug
//            System.out.println(1);
//        }
        while (lineIterator.hasNext()) {
            try {
                currentState.process(lineIterator);
            } catch (Exception e) {
                String position = (currentLine - 1) + "r" + lineIterator.getCurrentIndex() + "c";
                String errorMessage = "An error has occurred at " + position + ": " + e.getMessage();
//                StudentAssignmentEvaluator.getErrorCollector().addLog(Level.WARNING, StudentErrorTypes.PARSER, errorMessage);
//                currentState = new ScriptState(this);
            }
        }
    }

    @Override
    public void transition(ParserState state) {
        currentState = state;
    }

    public void processAccumulatedWord(Boolean appendFlag) {
        if (appendFlag) {
            assembler.appendRawQuery(wordAccumulator.toString());
        }
        wordAccumulator.setLength(0);
    }

    public void saveQuery() {
        queryList.add(assembler.createQuery());
    }
}
