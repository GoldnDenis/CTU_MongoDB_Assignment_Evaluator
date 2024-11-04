package cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.state.query.parameter;

import cz.cvut.fel.task_evaluator.enums.TokenTypes;
import cz.cvut.fel.task_evaluator.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.task_evaluator.model.query.parameter.DocumentParameter;
import cz.cvut.fel.task_evaluator.model.query.parameter.PipelineParameter;
import cz.cvut.fel.task_evaluator.model.query.parameter.StringParameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParameterExtractor {
//    public static StringParameter extractString(LineIterator iterator) {
//            }
//
//    public static PipelineParameter extractPipeline(LineIterator iterator) {
//        List<DocumentParameter> documentList = new ArrayList<>();
//        while (iterator.hasNext()) {
//            if (iterator.startsWith("{")) {
//                documentList.add(extractDocument(iterator));
//            } else if (iterator.startsWith(",") || iterator.startsWith(" ")) {
//                iterator.next();
//            } else if (iterator.startsWith("]")) {
//                break;
//            } else {
//                //todo error
//            }
//        }
//        return new PipelineParameter(documentList);
//    }
//
//    public static DocumentParameter extractDocument(LineIterator iterator) {
//        StringBuilder valueAccumulator = new StringBuilder();
//
//        int depth = 0;
//        int parenthesisCount = 0;
//        while (iterator.hasNext()) {
//            if (iterator.startsWithStringConstruct()) {
//                valueAccumulator.append(iterator.extractStringConstruct());
//                continue;
//            }
//            char c = iterator.next();
//            if (Character.isWhitespace(c) && valueAccumulator.toString().endsWith(" ")) {
//                continue;
//            }
//            valueAccumulator.append(c);
//            if (c == '{') {
//                parenthesisCount++;
//                depth = Math.max(depth, parenthesisCount);
//            } else if (c == '}') {
//                parenthesisCount--;
//            }
//            if (parenthesisCount == 0) {
//                break;
//            }
//        }
//        return new DocumentParameter(valueAccumulator.toString(), depth);
//    }

//    public static DocumentParameter extractDocument(LineIterator iterator) {
//        StringBuilder valueAccumulator = new StringBuilder();
//        Map<String, Object> valueMap = new HashMap<>();
//        int depth = 1;
//        while (iterator.hasNext()) {
//            if (iterator.startsWith("{")) {
//                DocumentParameter document = extractDocument(iterator);
//                depth = Math.max(depth, document.getDepth() + 1);
//            } else if (iterator.startsWith("}")) {
//                break;
//            } else if (iterator.startsWith("new ")) {
//                0
//            }
//
//        }
//        return new DocumentParameter(valueMap, depth);
//    }
//
//    private static String extractObject(LineIterator iterator) {
//        StringBuilder valueAccumulator = new StringBuilder();
//        valueAccumulator.append(iterator.consumeMatch("("));
//        while (iterator.hasNext()) {
//            if (iterator.startsWithStringConstruct()) {
//                valueAccumulator.append(iterator.extractStringConstruct());
//            }
//            valueAccumulator.append(iterator.next());
//            if (valueAccumulator.toString().endsWith(")")) {
//                break;
//            }
//        }
//        return valueAccumulator.toString();
//    }
}
