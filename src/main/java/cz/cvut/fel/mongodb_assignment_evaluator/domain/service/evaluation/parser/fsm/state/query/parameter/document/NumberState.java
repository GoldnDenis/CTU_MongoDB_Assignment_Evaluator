package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.query.parameter.document;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.ExpressionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.iterator.LineIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class NumberState extends ParserState {
    private final StringBuilder buffer;
    private final List<String> tokens;

    public NumberState(ScriptParser context, ParserState previousState) {
        super(context, previousState);
        buffer = new StringBuilder();
        tokens = new ArrayList<>();
    }

    // todo test out
    @Override
    public void process(LineIterator iterator) {
//        if (Character.isDigit(iterator.peek())) {
//            buffer.append(iterator.next());
//        } else if (!buffer.isEmpty()) {
//            if (startsWithExpressionSymbol(iterator)) {
//                addToken(buffer.toString());
//                addToken(String.valueOf(iterator.next()));
//            } else if (iterator.startsWithWhitespace()) {
//                addToken(buffer.toString());
//                iterator.next();
//            } else if (iterator.startsWith(",") || iterator.startsWith("}")) {
//                addToken(buffer.toString());
//                context.accumulate(String.valueOf(ExpressionEvaluator.evaluate(tokens)));
//                context.transition(previousState);
//            } else {
//                 todo exception
//            }
//        }
    }

    private boolean startsWithExpressionSymbol(LineIterator iterator) {
        return iterator.startsWith("**") || iterator.startsWith("*") ||
                iterator.startsWith("+") || iterator.startsWith("-") ||
                iterator.startsWith("/") || iterator.startsWith("%") ||
                iterator.startsWith("(") || iterator.startsWith(")");
    }

    private void addToken(String token) {
        tokens.add(token);
        buffer.setLength(0);
    }
}