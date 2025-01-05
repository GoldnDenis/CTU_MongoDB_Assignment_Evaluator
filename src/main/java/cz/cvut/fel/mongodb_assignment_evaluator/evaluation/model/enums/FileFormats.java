package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FileFormats {
    CSV(".csv"),
    LOG(".log");

    private final String fileFormat;
}
