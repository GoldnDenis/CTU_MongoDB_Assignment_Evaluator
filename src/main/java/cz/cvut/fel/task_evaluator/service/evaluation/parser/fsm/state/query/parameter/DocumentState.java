package cz.cvut.fel.task_evaluator.service.evaluation.parser.fsm.state.query.parameter;

import cz.cvut.fel.task_evaluator.service.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.task_evaluator.service.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.task_evaluator.service.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.task_evaluator.service.evaluation.query.parameter.DocumentParameter;
import cz.cvut.fel.task_evaluator.service.evaluation.query.parameter.PipelineParameter;

import java.util.ArrayList;
import java.util.List;

public class DocumentState extends ParserState {
    private int depth;
    private int parenthesisCount;
    private Boolean isModifier;
    private Boolean isPipeline;
    private List<DocumentParameter> pipeline;

    public DocumentState(ParserStateMachine context, Boolean isModifier, Boolean isPipeline) {
        super(context);
        this.isModifier = isModifier;
        this.isPipeline = isPipeline;
        resetDocument();
        if (isPipeline) {
            this.pipeline = new ArrayList<>();
        }
    }

    @Override
    public void process(LineIterator iterator) {
        while (iterator.hasNext()) {
            if (iterator.startsWith("'") || iterator.startsWith("\"")) {
                if (iterator.startsWithStringConstruct()) {
                    valueAccumulator.append(iterator.extractStringConstruct());
                    continue;
                }
            }
            char c = iterator.next();
            if (Character.isWhitespace(c) && valueAccumulator.toString().endsWith(" ")) {
                continue;
            }

            if (c == '{') {
                parenthesisCount++;
                depth = Math.max(depth, parenthesisCount);
            } else if (c == '}') {
                parenthesisCount--;
            }

            if (parenthesisCount > 0) {
                valueAccumulator.append(c);
            } else if (!valueAccumulator.isEmpty()) {
                valueAccumulator.append(c);
                DocumentParameter document = new DocumentParameter(valueAccumulator.toString(), depth);
                resetDocument();
                if (!isPipeline) {
                    context.addParameter(document, isModifier);
                    context.setState(new ParameterState(context, isModifier));
                    break;
                }
                pipeline.add(document);
                iterator.skipWhitespaces();
            } else if (c == ']') {
                context.addParameter(new PipelineParameter(pipeline), isModifier);
                context.setState(new ParameterState(context, isModifier));
                break;
            }
        }
    }

    private void resetDocument() {
        valueAccumulator.setLength(0);
        parenthesisCount = 0;
        depth = 0;
    }
}
