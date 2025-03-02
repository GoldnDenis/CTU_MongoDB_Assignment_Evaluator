package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.query;

public class StringLiteralState {
//public class StringLiteralState extends ParserState {
//    private final Boolean isModifier;

//    public StringLiteralState(ParserStateMachine context, Boolean isModifier) {
//        super(context);
//        this.isModifier = isModifier;
//    }

//    @Override
//    public void process(LineIterator iterator) {
//        iterator.skipWhitespaces();
//
//        if (iterator.startsWith(".") || iterator.startsWith("(")) {
//            String value = valueAccumulator.toString();
//
//            if (iterator.startsWith(".")) {
//                if (value.equals("system")) {
//                    processSyntaxError("'system.' prefix isn't allowed. (Reserved for internal use.)", iterator);
//                    return;
//                }
//                context.setCurrentCollection(value);
//            } else if (isModifier) {
//                context.getModifierBuilder().setModifier(value);
//            } else {
//                context.setQueryOperator(value);
//            }
//            context.appendToQuery(value);
//            context.setCurrentState(new QueryBodyState(context, isModifier));
//        } else if (iterator.hasNext()) {
//            char c = iterator.next();
//            if (!Character.isLetterOrDigit(c) && c != '_') {
//                processSyntaxError("'" + c + "' шs not allowed in collection, operation names", iterator);
//                return;
//            }
//            valueAccumulator.append(c);
//        }
//    }
}
