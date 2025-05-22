package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.query.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.MultiLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.SingleLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.query.QueryParameterState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.query.parameter.document.DocumentKeyState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.DocumentSyntaxError;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.ArrayParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.utility.StringUtility;
import org.bson.BsonDocument;

import java.util.ArrayList;
import java.util.List;

/**
 * Starts the accumulation of JSON-like documents and then passes them into BsonDocument parser to generate an AST.
 * If flagged, capable of processing an array of documents (pipeline).
 * Transitions to DocumentKeyState to start accumulating documents as a string.
 * Upon consuming document, it transitions back to QueryParameterState.
 */
public class DocumentParameterState extends ParserState {
    private final Boolean isModifier;
    private final Boolean isArray;
    private final List<BsonDocument> array;

    public DocumentParameterState(ScriptParser context, ParserState previousState, Boolean isModifier, Boolean isArray) {
        super(context, previousState);
        this.isModifier = isModifier;
        this.isArray = isArray;
        this.array = new ArrayList<>();
    }

    @Override
    public void process(LineIterator iterator) {
        if (iterator.startsWith("//")) {
            context.transition(new SingleLineCommentState(context, this));
        } else if (iterator.startsWith("/*")) {
            context.transition(new MultiLineCommentState(context, this));
        } else if (iterator.startsWith("{")) {
            char next = iterator.next();
            context.accumulate(next);
            context.appendToRawQuery(String.valueOf(next));
            context.transition(new DocumentKeyState(context, this));
        } else if (iterator.startsWith("}")) {
            char next = iterator.next();
            context.accumulate(next);
            context.appendToRawQuery(String.valueOf(next));
            try {
                BsonDocument document = BsonDocument.parse(context.getAccumulatedWord());
                context.processAccumulatedWord(false);
                if (isArray) {
                    array.add(document);
                } else {
                    assembler.addParameter(new DocumentParameter(document), isModifier);
                    context.transition(new QueryParameterState(context, this, isModifier));
                }
            } catch (Exception e) {
                throw new DocumentSyntaxError(e.getMessage());
            }
        } else if (iterator.startsWith("]")) {
            context.accumulate(iterator.next());
            assembler.addParameter(new ArrayParameter(array), isModifier);
            context.transition(new QueryParameterState(context, this, isModifier));
        } else if (iterator.startsWithWhitespace()) {
            iterator.next();
            if (!Character.isWhitespace(StringUtility.getLastChar(context.getAccumulatedWord()))) {
                context.accumulate(" ");
                context.appendToRawQuery(" ");
            }
        } else if (iterator.startsWith(",")) {
            char next = iterator.next();
            context.accumulate(next);
            context.appendToRawQuery(String.valueOf(next));
            context.processAccumulatedWord(false);
        }
    }
}
