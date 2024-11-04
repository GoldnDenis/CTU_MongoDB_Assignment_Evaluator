package cz.cvut.fel.task_evaluator.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Criteria {
    INSERTED_DOCUMENTS("Insert 10 documents into each collection."),
    INSERT_ONE_MANY("Use both insertOne and insertMany.");

    private final String message;
}
