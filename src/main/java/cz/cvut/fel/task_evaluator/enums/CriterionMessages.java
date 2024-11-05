package cz.cvut.fel.task_evaluator.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CriterionMessages {
    COMMENT("Describe the real-world meaning of all your queries in comments."),
    CREATE_COLLECTION("Explicitly create one or two collections.");

    private final String message;
}
