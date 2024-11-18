package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.fsm.state.query.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.PipelineParameter;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class DocumentParameterState extends ParserState {
    private int depth;
    private int parenthesisCount;
    private Boolean isModifier;
    private Boolean isPipeline;
    private List<DocumentParameter> pipeline;

    public DocumentParameterState(ParserStateMachine context, Boolean isModifier, Boolean isPipeline) {
        super(context);
        this.isModifier = isModifier;
        this.isPipeline = isPipeline;
        this.pipeline = new ArrayList<>();
        resetDocument();
    }

    //todo hashmap first level
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

        DocumentParameter document = new DocumentParameter(Document.parse(value), depth);
        resetDocument();

        if (!isPipeline) {
            context.addParameter(document, isModifier);
            context.setState(new ParameterState(context, isModifier));
        } else {
            pipeline.add(document);
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
