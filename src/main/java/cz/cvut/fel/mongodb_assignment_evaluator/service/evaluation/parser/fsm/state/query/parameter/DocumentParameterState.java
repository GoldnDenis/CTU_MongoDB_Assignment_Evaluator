package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.fsm.state.query.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.fsm.state.ScriptState;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.PipelineParameter;
import org.bson.BsonDocument;
import org.bson.json.JsonParseException;


import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String old = value;

        try {
            value = preprocessEJson(value);
            DocumentParameter document = new DocumentParameter(BsonDocument.parse(value), depth);
            resetDocument();
            if (!isPipeline) {
                context.addParameter(document, isModifier);
                context.setState(new ParameterState(context, isModifier));
            } else {
                pipeline.add(document);
            }
        } catch (JsonParseException e) {
            System.err.println(e.getMessage());
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

    private static final Pattern DATE_PATTERN = Pattern.compile("new Date\\((.*?(\\((.*?)\\).*?)?)\\)");
//    private static final Pattern OBJECT_ID_PATTERN = Pattern.compile("ObjectId\\([a-zA-Z0-9]*\\)");

    private String preprocessEJson(String eJson) {
        eJson = eJson.replaceAll("\\/\\*.*?\\*\\/", "");
        eJson = eJson.replaceAll("ObjectId\\([a-zA-Z0-9]*\\)", generateHex24());
        Matcher matcher = DATE_PATTERN.matcher(eJson);
        while (matcher.find()) {
            String dateString = matcher.group(1);
            String replacement = "";
            if (dateString == null || dateString.isBlank()) {
                replacement = "\"" + new Date() + "\"";
            } else if ((dateString.startsWith("\"") && dateString.endsWith("\"")) ||
                    (dateString.startsWith("'") && dateString.endsWith("'"))) {
                replacement = matcher.group(1);
            } else {
                String newDate = "\"" + new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss z").format(new Date())  + "\"";
                replacement = matcher.group().replace(matcher.group(1), newDate);
            }
            eJson = eJson.replace(matcher.group(), replacement);
        }
        return eJson;
    }

    public static String generateHex24() {
        final String HEX_CHARS = "0123456789abcdef";
        SecureRandom random = new SecureRandom();
        StringBuilder hexString = new StringBuilder(24);

        hexString.append("\"");
        for (int i = 0; i < 24; i++) {
            int index = random.nextInt(HEX_CHARS.length());
            hexString.append(HEX_CHARS.charAt(index));
        }
        hexString.append("\"");

        return hexString.toString();
    }
}
