package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.query.parameter.document;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.PipelineParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.comment.MultiLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.comment.SingleLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.query.parameter.QueryParameterState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.preprocessor.StringPreprocessor;
import cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.utility.StringUtility;
import org.bson.BsonDocument;
import org.bson.json.JsonParseException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DocumentParameterState extends ParserState {
    //    private int depth;
    private int parenthesisCount;
    private final Boolean isModifier;
    private final Boolean isPipeline;
    private final List<BsonDocument> pipeline;

    public DocumentParameterState(ScriptParser context, ParserState previousState, Boolean isModifier, Boolean isPipeline) {
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
        } else if (iterator.startsWith("{")) {
            context.accumulate(iterator.next());
            context.transition(new DocumentKeyState(context, this));
        } else if (iterator.startsWith("}")) {
//            String processedDocument = StringPreprocessor.preprocessEJson(context.getAccumulatedWord());
            context.accumulate(iterator.next());
            try {
                BsonDocument document = BsonDocument.parse(context.getAccumulatedWord());
                if (isPipeline) {
                    context.processAccumulatedWord(true);
                    pipeline.add(document);
                } else {
                    assembler.addParameter(new DocumentParameter(document), isModifier);
                    context.transition(new QueryParameterState(context, this, isModifier));
                }
            } catch (JsonParseException e) {
                System.out.println(e.getMessage());
                // todo bendasta
            }
        } else if (iterator.startsWith("]")) {
            context.accumulate(iterator.next());
            assembler.addParameter(new PipelineParameter(pipeline), isModifier);
            context.transition(new QueryParameterState(context, this, isModifier));
        } else if (iterator.startsWithWhitespace()) {
            iterator.next();
            if (!Character.isWhitespace(StringUtility.getLastChar(context.getAccumulatedWord()))) {
                context.accumulate(" ");
            }
        } else if (iterator.startsWith(",")) {
            context.accumulate(iterator.next());
            context.processAccumulatedWord(true);
        } //todo potentially an else statement
    }
}
