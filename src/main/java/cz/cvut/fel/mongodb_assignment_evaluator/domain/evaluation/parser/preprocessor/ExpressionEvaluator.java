package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.preprocessor;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

/**
 * Evaluates an expression by using the Javascript engine
 */
public class ExpressionEvaluator {
    public static String eval(String expr) {
        try (Context context = Context.newBuilder("js").allowAllAccess(true).build()) {
            Value value = context.eval("js", expr);
            return value.toString();
        } catch (Exception e) {
            return expr;
        }
    }
}
