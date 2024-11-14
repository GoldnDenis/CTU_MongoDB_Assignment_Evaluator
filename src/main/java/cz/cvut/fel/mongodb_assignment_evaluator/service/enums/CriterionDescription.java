package cz.cvut.fel.mongodb_assignment_evaluator.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CriterionDescription {
    COMMENT("Describe the real-world meaning of all your queries in comments"),
    CREATE_COLLECTION("Explicitly create one or two collections"),

    INSERT_DOCUMENTS("Insert 10 documents into each collection"),

    INTERLINK_DOCUMENTS("Interlink the documents using references"),
    INSERT_ONE_AND_MANY_USED("Use both insertOne and insertMany"),;

    private final String description;
}
