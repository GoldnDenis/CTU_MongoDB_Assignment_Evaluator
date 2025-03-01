package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.StudentEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.StudentErrorTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.ScriptState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.Query;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ParserStateMachine implements StateMachine<ParserState> {
    private ParserState currentState;
    @Getter
    private final List<Query> queryList;
    private final StringBuilder wordAccumulator;
    @Getter
    private final QueryTokenAssembler assembler;

    //    @Getter
//    private QueryBuilder queryBuilder;
//    @Getter
//    private ModifierBuilder modifierBuilder;
//    @Setter
//    private String lastQueryOperation;
//    @Setter
//    private String currentCollection;
    private int currentLine;
//    private int currentRow;
//
//    private final StringBuilder queryAccumulator;
//    private final StringBuilder lastCommentBuilder;

    public ParserStateMachine() {
        currentState = new ScriptState(this);
        queryList = new ArrayList<>();

        wordAccumulator = new StringBuilder();
        assembler = new QueryTokenAssembler();

//        this.queryBuilder = new QueryBuilder();
//        this.modifierBuilder = new ModifierBuilder();
//
//        lastQueryOperation = "";
//
//        this.currentCollection = "";
//        currentLine = 0;
//        currentRow = 0;
//
//        this.lastCommentBuilder = new StringBuilder();
//        this.queryAccumulator = new StringBuilder();
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
        System.out.println(currentLine + ":" + currentColumn); //todo
        assembler.setCurrentPosition(currentLine, currentColumn);
    }

    public void parseLine(LineIterator lineIterator) {
        currentLine++;
        if (currentLine == 225) { // todo
            System.out.println(1);
        }
        while (lineIterator.hasNext()) {
//            context.appendToQuery(value);
            try {
                currentState.process(lineIterator);
            } catch (Exception e) {
                String position = (currentLine - 1) + "r" + lineIterator.getCurrentIndex() + "c";
                String errorMessage = "An error has occurred at " + position + ": " + e.getMessage();
                StudentEvaluator.getErrorCollector().addLog(Level.WARNING, StudentErrorTypes.PARSER, errorMessage);
                currentState = new ScriptState(this);
            }
        }
    }

//    public void parseLine(String line) {
//        currentLine++;
//        for (Character c: line.toCharArray()) {
//            currentRow++;
//            currentState.process(c);
//        }
//    }

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

//    protected void processWhitespace(LineIterator iterator) {
//        char nextChar = iterator.next();
//        int lastIndex = valueAccumulator.length() - 1;
//        char lastChar = valueAccumulator.charAt(lastIndex);
//        if (!Character.isWhitespace(lastChar)) {
//            valueAccumulator.append(nextChar);
//        }
//    }

//    @Override
//    public void transition(ParserStates state) {
//        ParserStates current = currentState.getEnumeration();
//        switch (state) {
//            case INITIAL, SCRIPT -> {
//                currentState = new ScriptState(this, current);
//            }
//            case SINGLE_LINE_COMMENT -> {
//                currentState = new SingleLineCommentState(this, current);
//            }
//            case MULTI_LINE_COMMENT -> {
//                currentState = new MultiLineCommentState(this, current);
//            }
//            case QUERY_BODY -> {
//                currentState = new QueryBodyState(this, current);
//            }
//            case QUERY_END -> {
//                currentState = new QueryEndState(this, current);
//            }
//            case STRING -> {
//                currentState = new StringState(this, current);
//            }
//            case QUERY_PARAMETER -> {
//                currentState = new QueryParameterState(this, current);
//            }
//            case FUNCTION -> {
//                currentState = new FunctionParameterState(this, current);
//            }
//            case DOCUMENT -> {
//                currentState = new DocumentParameterState(this, current);
//            }
//        }
//    }

//    public void appendToComment(String comment) {
//        if (!lastQueryOperation.isEmpty()) {
//            lastCommentBuilder.setLength(0);
//        }
//        if (!lastCommentBuilder.isEmpty()) {
//            lastCommentBuilder.accumulate("\n");
//        }
//        lastCommentBuilder.accumulate(comment);
//    }
//
//    public void appendToQuery(String string) {
//        queryTokenAssembler.appendRawQuery(string);
//    }
//
//    public void appendToQuery(Character character) {
//        queryTokenAssembler.appendRawQuery(Character.toString(character));
//    }

//    public void setQueryOperator(String operator) {
//        if (!lastQueryOperation.isBlank() && !lastQueryOperation.equals(operator)) {
//            lastCommentBuilder.setLength(0);
//        }
//        lastQueryOperation = operator;
//
//        QueryTypes type = QueryTypes.fromString(operator);
//        queryBuilder = QueryBuilderFactory.create(type);
//
//        queryBuilder.setPosition(currentLine, currentRow)
//                .setCollection(currentCollection)
//                .setOperator(operator)
//                .setType(type);
//    }

//    public void addParameter(QueryParameter parameter, Boolean isModifier) {
//        try {
////            if (isModifier) {
////                modifierBuilder.setParameter(parameter);
////            } else {
////                queryBuilder.addParameter(parameter);
////            }
//        } catch (IllegalArgumentException e) {
//            StudentEvaluator.getErrorCollector().addLog(Level.WARNING, StudentErrorTypes.PARSER, e.getMessage());
//            currentState = new ScriptState(this);
//            resetAccumulators();
//        }
//    }

//    public void resetAccumulators() {
//        queryBuilder = new QueryBuilder();
//        modifierBuilder = new ModifierBuilder();
//        queryAccumulator.setLength(0);
//        currentCollection = "";
//        currentRow = -1;
//        currentLine = -1;
//    }
//
//    public void setCurrentPosition(LineIterator iterator) {
//        currentLine = iterator.getRowIndex() + 1;
//        currentRow = iterator.getCurrentIndex() + 1;
//    }

//    public void saveModifier() {
//        queryBuilder.addModifier(modifierBuilder.build());
//        this.modifierBuilder = new ModifierBuilder();
//    }

    public void saveQuery() {
        queryList.add(assembler.createQuery());
//        queryList.add(
//                queryBuilder.setComment(lastCommentBuilder.toString())
//                        .setQuery(queryAccumulator.toString())
//                        .build()
//        );
//        resetAccumulators();
    }
}
