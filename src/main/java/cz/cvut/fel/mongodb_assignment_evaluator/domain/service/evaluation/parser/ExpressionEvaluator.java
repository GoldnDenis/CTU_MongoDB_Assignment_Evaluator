package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser;

import cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.utility.StringUtility;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.*;

public class ExpressionEvaluator {
    private static ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");

    public static Object eval(String expr) {
        try {
            return engine.eval(expr);
        } catch (ScriptException e) {
            return expr;
        }
    }

//    private static final Map<String, Integer> PRECEDENCE = Map.of(
//            "**", 4,
//            "*", 3,
//            "/", 3,
//            "%", 3,
//            "+", 2,
//            "-", 2
//    );
//
//    private static final Set<String> OPERATORS = PRECEDENCE.keySet();
//
//    public static double evaluate(List<String> tokens) {
//        Deque<Double> stack = new ArrayDeque<>();
//        for (String token : infixToPostfix(tokens)) {
//            double doubleValue = StringUtility.parseDouble(token);
//            if (!Double.isNaN(doubleValue)) {
//                stack.push(doubleValue);
//            } else {
//                double b = stack.pop();
//                double a = stack.pop();
//                stack.push(applyOperator(a, b, token));
//            }
//        }
//        return stack.pop();
//    }
//
//    private static List<String> infixToPostfix(List<String> tokens) {
//        List<String> output = new ArrayList<>();
//        Deque<String> stack = new ArrayDeque<>();
//
//        for (String token : tokens) {
//            double doubleValue = StringUtility.parseDouble(token);
//            if (!Double.isNaN(doubleValue)) {
//                output.add(token);
//            } else if (OPERATORS.contains(token)) {
//                while (!stack.isEmpty() && OPERATORS.contains(stack.peek())) {
//                    String top = stack.peek();
//                    if ((isLeftAssociative(token) && PRECEDENCE.get(token) <= PRECEDENCE.get(top)) ||
//                            (!isLeftAssociative(token) && PRECEDENCE.get(token) < PRECEDENCE.get(top))) {
//                        output.add(stack.pop());
//                    } else {
//                        break;
//                    }
//                }
//                stack.push(token);
//            } else if (token.equals("(")) {
//                stack.push(token);
//            } else if (token.equals(")")) {
//                while (!stack.isEmpty() && !stack.peek().equals("(")) {
//                    output.add(stack.pop());
//                }
//                stack.pop();
//            }
//        }
//        while (!stack.isEmpty()) {
//            output.add(stack.pop());
//        }
//        return output;
//    }
//
//    private static boolean isLeftAssociative(String op) {
//        return !op.equals("**");
//    }
//
//    private static double applyOperator(double a, double b, String op) {
//        return switch (op) {
//            case "+" -> a + b;
//            case "-" -> a - b;
//            case "*" -> a * b;
//            case "/" -> a / b;
//            case "%" -> a % b;
//            case "**" -> Math.pow(a, b);
//            default -> throw new IllegalArgumentException("Unknown operator: " + op); // todo custom exception
//        };
//    }
}
