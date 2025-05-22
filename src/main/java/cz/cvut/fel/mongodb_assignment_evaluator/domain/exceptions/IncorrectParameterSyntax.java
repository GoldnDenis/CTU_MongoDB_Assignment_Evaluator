package cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions;

public class IncorrectParameterSyntax extends RuntimeException {
    public IncorrectParameterSyntax(String parameterType, int position, String queryCommand) {
        super(
                parameterType + " parameter is not allowed at "
                        + position + " position for '"
                        + queryCommand + "' query"
        );
    }

    public IncorrectParameterSyntax(String message) {
        super(message);
    }
}
