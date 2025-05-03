package cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions;

public class IncorrectParameterSyntax extends RuntimeException {
    public IncorrectParameterSyntax(String parameterType, int position, String queryOperator) {
        super(
                parameterType + " parameter is not allowed at "
                        + position + " position for '"
                        + queryOperator + "' query"
        );
    }

    public IncorrectParameterSyntax(String message) {
        super(message);
    }
}
