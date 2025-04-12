package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.StudentErrorTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.StudentSubmission;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.QueryTokenAssembler;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.StateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.ScriptState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.iterator.LineIterator;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.logging.Level;

@Service
public class ScriptParser extends StateMachine<ParserState> {
    private StudentSubmission submission;
    @Getter
    private final QueryTokenAssembler assembler;
    private int currentLine;

    public ScriptParser() {
        super();
        transition(new ScriptState(this));
        assembler = new QueryTokenAssembler();
    }

    public void extractQueries(StudentSubmission submission) {
        this.submission = submission;
        currentLine = 1;
        for (int i = 0; i < submission.getScriptLines().size(); i++, currentLine++) {
            LineIterator lineIterator = new LineIterator(submission.getScriptLines().get(i));
            while (lineIterator.hasNext()) {
                try {
//                    if (currentLine == 277) {
//                        System.out.println("ds");
//                    }
                    currentState.process(lineIterator);
                } catch (Exception e) {
                    String errorMessage = "An error has occurred at "
                            + currentLine + "r"
                            + lineIterator.getCurrentIndex() + "c"
                            + ": " + e.getMessage();
                    submission.addLog(Level.WARNING, StudentErrorTypes.PARSER, errorMessage);
                    currentState = new ScriptState(this);
                }
            }
        }
    }

    public void initAssembler(int currentColumn) {
        assembler.resetAccumulators();
        assembler.setCurrentPosition(currentLine, currentColumn);
    }

    public void processAccumulatedWord(Boolean appendFlag) {
        if (appendFlag) {
            assembler.appendRawQuery(wordAccumulator.toString());
        }
        wordAccumulator.setLength(0);
    }

    public void saveQuery() {
        submission.addQuery(assembler.createQuery());
    }
}
