package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.query.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.StringUtility;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.StudentEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.comment.MultiLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.comment.SingleLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.query.StringState;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.StudentErrorTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.ScriptState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.preprocessor.StringPreprocessor;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.parameter.PipelineParameter;
import org.bson.BsonDocument;
import org.bson.json.JsonParseException;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class DocumentParameterState extends ParserState {
//    private int depth;
    private int parenthesisCount;
    private final Boolean isModifier;
    private final Boolean isPipeline;
    private final List<BsonDocument> pipeline;

    public DocumentParameterState(ParserStateMachine context, ParserState previousState, Boolean isModifier, Boolean isPipeline) {
        super(context, previousState);
        this.parenthesisCount = 1;
        this.isModifier = isModifier;
        this.isPipeline = isPipeline;
        this.pipeline = new ArrayList<>();
//        resetDocument();
    }

//    @Override
//    public void process(LineIterator iterator) {
//        if (Character.isWhitespace(iterator.peek())
//                && valueAccumulator.toString().endsWith(" ")) {
//            iterator.skipWhitespaces();
//            return;
//        }
//
//        if (iterator.startsWith("{")) {
//            parenthesisCount++;
////            depth = Math.max(depth, parenthesisCount);
//        } else if (iterator.startsWith("}")) {
//            parenthesisCount--;
//        }
//
//        String value = "";
//        if (iterator.startsWithStringConstruct()) {
//            value = iterator.nextStringConstruct();
//        }  else if (iterator.startsWith("//")) {
//            iterator.nextAll();
//            return;
//        } else {
//            value = String.valueOf(iterator.next());
//        }
//
//        if (parenthesisCount > 0) {
//            valueAccumulator.append(value);
//        } else if (!valueAccumulator.isEmpty()) {
//            processDocumentEnd();
//        } else if (value.equals("]")) {
//            processPipelineEnd();
//        }
//    }

    @Override
    public void process(LineIterator iterator) {
        if (startsWithDoubleWhitespace(iterator)) {
            iterator.next();
            return;
        }

        if (iterator.startsWith("{")) {
            parenthesisCount++;
        } else if (iterator.startsWith("}")) {
            parenthesisCount--;
        } else if (iterator.startsWith("//")) {
            context.transition(new SingleLineCommentState(context, this));
        } else if (iterator.startsWith("/*")) {
            context.transition(new MultiLineCommentState(context, this));
        }

        if (parenthesisCount > 0) {
            if (iterator.startsWithStringQuote()) {
                context.transition(new StringState(context, this, iterator.next()));
            } else {
                context.accumulate(iterator.next());
            }
        } else if (!context.getAccumulatedWord().isBlank()) {
            processDocumentEnd();
        } else if (iterator.startsWith("]")) {
            processPipelineEnd(iterator);
        }
    }

    private void processDocumentEnd () {
        context.accumulate("}");
//        parenthesisCount = 0;
        String processedDocument = StringPreprocessor.preprocessEJson(context.getAccumulatedWord());
        try {
//            DocumentParameter document = new DocumentParameter(BsonDocument.parse(value), depth);
            BsonDocument document = BsonDocument.parse(processedDocument);
            if (isPipeline) {
                context.processAccumulatedWord(true);
                pipeline.add(document);
            } else {
                assembler.addParameter(new DocumentParameter(document), isModifier);
                context.transition(new QueryParameterState(context, this, isModifier));
            }
        } catch (JsonParseException e) {
            StudentEvaluator.getErrorCollector().addLog(Level.WARNING, StudentErrorTypes.PARSER, e.getMessage());
            context.processAccumulatedWord(false);
            context.transition(new ScriptState(context));
        }
    }

    private void processPipelineEnd(LineIterator iterator) {
        context.accumulate(iterator.next());
        assembler.addParameter(new PipelineParameter(pipeline), isModifier);
        context.transition(new QueryParameterState(context, this, isModifier));
    }

    private boolean startsWithDoubleWhitespace(LineIterator iterator) {
        if (!iterator.startsWithWhitespace()) {
            return false;
        }
        char lastChar = StringUtility.getLastChar(context.getAccumulatedWord());
        return Character.isWhitespace(lastChar);
    }
}
