package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.query.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.query.parameter.document.DocumentKeyState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.MultiLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.SingleLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.PipelineParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.utility.StringUtility;
import org.bson.BsonDocument;
import org.bson.json.JsonParseException;

import java.util.ArrayList;
import java.util.List;

public class DocumentParameterState extends ParserState {
    private final Boolean isModifier;
    private final Boolean isPipeline;
    private final List<BsonDocument> pipeline;

    public DocumentParameterState(ScriptParser context, ParserState previousState, Boolean isModifier, Boolean isPipeline) {
        super(context, previousState);
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
                if (isPipeline) {
                    pipeline.add(document);
                } else {
                    assembler.addParameter(new DocumentParameter(document), isModifier);
                    context.transition(new QueryParameterState(context, this, isModifier));
                }
            } catch (JsonParseException e) {
                System.out.println(e.getMessage());
            }
        } else if (iterator.startsWith("]")) {
            context.accumulate(iterator.next());
            assembler.addParameter(new PipelineParameter(pipeline), isModifier);
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
