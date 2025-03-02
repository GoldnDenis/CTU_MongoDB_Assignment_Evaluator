package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.query.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.comment.MultiLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.comment.SingleLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.query.StringState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.preprocessor.StringPreprocessor;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.parameter.PipelineParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.utility.StringUtility;
import org.bson.BsonDocument;
import org.bson.json.JsonParseException;

import java.util.ArrayList;
import java.util.List;

public class DocumentParameterState extends ParserState {
    //    private int depth;
    private int parenthesisCount;
    private final Boolean isModifier;
    private final Boolean isPipeline;
    private final List<BsonDocument> pipeline;

    public DocumentParameterState(ParserStateMachine context, ParserState previousState, Boolean isModifier, Boolean isPipeline) {
        super(context, previousState);
        this.parenthesisCount = 0;
        this.isModifier = isModifier;
        this.isPipeline = isPipeline;
        this.pipeline = new ArrayList<>();
    }

    @Override
    public void process(LineIterator iterator) {
        if (iterator.startsWith("//")) {
            context.transition(new SingleLineCommentState(context, this));
        } else if (iterator.startsWith("/*")) {
            context.transition(new MultiLineCommentState(context, this));
        } else {
            if (iterator.startsWith("{")) {
                parenthesisCount++;
            } else if (iterator.startsWith("}")) {
                parenthesisCount--;
            }

            char nextChar = iterator.next();
            if (parenthesisCount > 0) {
                if (nextChar == '"' || nextChar == '\'') {
                    context.transition(new StringState(context, this, nextChar));
                } else if (isNotDoubleWhitespace(nextChar)) {
                    context.accumulate(nextChar);
                }
            } else if (!context.getAccumulatedWord().isBlank()) {
                processDocumentEnd();
            } else if (nextChar == ']') {
                processPipelineEnd();
            }
        }
    }

    private void processDocumentEnd() throws JsonParseException {
        context.accumulate('}');
        String processedDocument = StringPreprocessor.preprocessEJson(context.getAccumulatedWord());
        BsonDocument document = BsonDocument.parse(processedDocument);
        if (isPipeline) {
            context.processAccumulatedWord(true);
            pipeline.add(document);
        } else {
            assembler.addParameter(new DocumentParameter(document), isModifier);
            context.transition(new QueryParameterState(context, this, isModifier));
        }
    }

    private void processPipelineEnd() {
        context.accumulate(']');
        assembler.addParameter(new PipelineParameter(pipeline), isModifier);
        context.transition(new QueryParameterState(context, this, isModifier));
    }

    private boolean isNotDoubleWhitespace(char nextChar) {
        char lastChar = StringUtility.getLastChar(context.getAccumulatedWord());
        return !Character.isWhitespace(lastChar) || !Character.isWhitespace(nextChar);
    }
}
