package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.MongoCommands;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.StateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.ScriptState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.query.QueryEndState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.IncorrectParserSyntax;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.StudentSubmission;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.QueryAssembler;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.MongoQuery;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * A Scannerless parser that uses the concept of State Machine, but with modifications such as memory.
 * The Parser receives text of a script and extract from it syntactically correct MongoDB queries.
 */
@Service
public class ScriptParser extends StateMachine {
    private List<MongoQuery> extractedQueries;
    private StudentSubmission submission;
    @Getter
    private final QueryAssembler assembler;
    private int totalScriptLines;
    private int currentLine;

    public ScriptParser() {
        super();
        transition(new ScriptState(this));
        extractedQueries = new ArrayList<>();
        assembler = new QueryAssembler();
        totalScriptLines = -1;
        currentLine = -1;
    }

    /**
     * Goes through the script and identifies MongoDB queries. Extracts all syntactically correct queries.
     * Catches all warnings/errors arose during the parsing.
     * @param studentSubmission contains all information about the submission.
     * @param script contents of the script that would be parsed
     * @return all extracted MongoQuery objects that are already differentiated into different groups
     */
    public List<MongoQuery> parse(StudentSubmission studentSubmission, String script) {
        submission = studentSubmission;
        extractedQueries.clear();
        currentLine = 1;
        List<String> scriptLines = script.lines().toList();
        totalScriptLines = scriptLines.size();
        for (int i = 0; i < totalScriptLines; i++, currentLine++) {
            LineIterator iterator = new LineIterator(scriptLines.get(i));
            while (iterator.hasNext()) {
                try {
                    currentState.process(iterator);
                    verifyIfScriptEndedCorrectly(iterator);
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
        return extractedQueries;
    }

    /**
     * Resets all QueryToken accumulators and prepares Parser to receive a new Token.
     * @param currentColumn position from which new QueryToken accumulation begins
     */
    public void initAssembler(int currentColumn) {
        assembler.resetAccumulators();
        wordAccumulator.setLength(0);
        assembler.setCurrentPosition(currentLine, currentColumn);
    }

    /**
     * Accumulates raw part of the query without any modification
     * @param append a part of the query from the script
     */
    public void appendToRawQuery(String append) {
        assembler.appendRawQuery(append);
    }

    public String getRawQuery() {
        return assembler.getRawQuery();
    }

    /**
     * Resets the accumulated word.
     * If the flag is true, the accumulated word is saved before the reset.
     * @param appendFlag
     */
    public void processAccumulatedWord(Boolean appendFlag) {
        if (appendFlag) {
            assembler.appendRawQuery(wordAccumulator.toString());
        }
        wordAccumulator.setLength(0);
    }

    /**
     * Invokes the creation of a QueryToken and saves it.
     * Here is defined some warning regarding the created QueryToken
     */
    public void saveQuery() {
        MongoQuery extractedQuery = assembler.createQuery();
        if (submission.getExtractedQueries().stream()
                .map(MongoQuery::getQuery)
                .anyMatch(q -> q.equalsIgnoreCase(extractedQuery.getQuery()))
        ) {
            submission.addLog(Level.WARNING, "Duplicate query: '" + extractedQuery.getQuery() + "'");
        } else {
            if (extractedQuery.getCommand().equalsIgnoreCase(MongoCommands.INSERT.getCommand())) {
                submission.addLog(Level.WARNING, "'.insert' is deprecated in newer versions of MongoDB");
            }
            extractedQueries.add(extractedQuery);
        }
    }

    private void verifyIfScriptEndedCorrectly(LineIterator iterator) {
        if (totalScriptLines != currentLine || iterator.hasNext()) {
            return;
        }
        if (currentState instanceof QueryEndState) {
            throw new IncorrectParserSyntax("Expected a semicolon (;) or the start of a modifier (.) at the end of the query");
        } else if (!(currentState instanceof ScriptState)) {
            throw new IncorrectParserSyntax("Query is incomplete: the script ended before reaching a valid termination (e.g., semicolon or modifier)");
        }
    }
}
