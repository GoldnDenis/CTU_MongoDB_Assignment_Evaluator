package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Operators;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.StateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.ScriptState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.StudentSubmission;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.QueryTokenAssembler;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.QueryToken;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public void extractQueries(StudentSubmission studentSubmission, String script) {
        submission = studentSubmission;
        currentLine = 1;
        List<String> scriptLines = script.lines().toList();
        for (int i = 0; i < scriptLines.size(); i++, currentLine++) {
            LineIterator iterator = new LineIterator(scriptLines.get(i));
            while (iterator.hasNext()) {
                try {
                    currentState.process(iterator);
                } catch (Exception e) {
                    String errorMessage = "Parsing error at "
                            + currentLine + "r"
                            + iterator.getCurrentIndex() + "c"
                            + ": " + e.getMessage();
                    wordAccumulator.setLength(0);
                    submission.addLog(Level.WARNING, errorMessage);
                    currentState = new ScriptState(this);
                }
            }
        }
    }

    public void initAssembler(int currentColumn) {
        assembler.resetAccumulators();
        wordAccumulator.setLength(0);
        assembler.setCurrentPosition(currentLine, currentColumn);
    }

    public void appendToRawQuery(String append) {
        assembler.appendRawQuery(append);
    }

    public String getRawQuery() {
        return assembler.getRawQuery();
    }

    public void processAccumulatedWord(Boolean appendFlag) {
        if (appendFlag) {
            assembler.appendRawQuery(wordAccumulator.toString());
        }
        wordAccumulator.setLength(0);
    }

    public void saveQuery() {
        QueryToken token = assembler.createQuery();
        if (submission.getQueryList().stream()
                .map(QueryToken::getQuery)
                .anyMatch(q -> q.equalsIgnoreCase(token.getQuery()))
        ) {
            submission.addLog(Level.WARNING, "Duplicate query: '" + token.getQuery() + "'");
        } else {
            if (token.getOperator().equalsIgnoreCase(Operators.INSERT.getOperator())) {
                submission.addLog(Level.WARNING, "'.insert' is deprecated in newer versions of MongoDB");
            }
            submission.addQuery(token);
        }
    }
}
