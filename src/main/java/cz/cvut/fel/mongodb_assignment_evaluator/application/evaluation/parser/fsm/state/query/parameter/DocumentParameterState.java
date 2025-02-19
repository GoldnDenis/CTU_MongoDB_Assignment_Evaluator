package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.query.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.StudentEvaluator;
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
    private int depth;
    private int parenthesisCount;
    private Boolean isModifier;
    private Boolean isPipeline;
    private List<BsonDocument> pipeline;

    public DocumentParameterState(ParserStateMachine context, Boolean isModifier, Boolean isPipeline) {
        super(context);
        this.isModifier = isModifier;
        this.isPipeline = isPipeline;
        this.pipeline = new ArrayList<>();
        resetDocument();
    }

    @Override
    public void process(LineIterator iterator) {
        if (Character.isWhitespace(iterator.peek())
                && valueAccumulator.toString().endsWith(" ")) {
            iterator.skipWhitespaces();
            return;
        }

        if (iterator.startsWith("{")) {
            parenthesisCount++;
            depth = Math.max(depth, parenthesisCount);
        } else if (iterator.startsWith("}")) {
            parenthesisCount--;
        }

        String value = "";
        if (iterator.startsWithStringConstruct()) {
            value = iterator.nextStringConstruct();
        }  else if (iterator.startsWith("//")) {
            iterator.nextAll();
            return;
        } else {
            value = String.valueOf(iterator.next());
        }

        if (parenthesisCount > 0) {
            valueAccumulator.append(value);
        } else if (!valueAccumulator.isEmpty()) {
            processDocumentEnd();
        } else if (value.equals("]")) {
            processPipelineEnd();
        }
    }

    private void processDocumentEnd() {
        valueAccumulator.append("}");

        String value = valueAccumulator.toString();
        context.appendToQuery(value);

        try {
            value = StringPreprocessor.preprocessEJson(value);
            DocumentParameter document = new DocumentParameter(BsonDocument.parse(value), depth);
            resetDocument();
            if (!isPipeline) {
                context.addParameter(document, isModifier);
                context.setState(new ParameterState(context, isModifier));
            } else {
                pipeline.add(document.getDocument());
            }
        } catch (JsonParseException e) {
            StudentEvaluator.getErrorCollector().addLog(Level.WARNING, StudentErrorTypes.PARSER, e.getMessage());
            resetDocument();
            context.setState(new ScriptState(context));
        }
    }

    private void processPipelineEnd() {
        context.appendToQuery(']');

        context.addParameter(new PipelineParameter(pipeline), isModifier);
        context.setState(new ParameterState(context, isModifier));
    }

    private void resetDocument() {
        valueAccumulator.setLength(0);
        parenthesisCount = 0;
        depth = 0;
    }
}
