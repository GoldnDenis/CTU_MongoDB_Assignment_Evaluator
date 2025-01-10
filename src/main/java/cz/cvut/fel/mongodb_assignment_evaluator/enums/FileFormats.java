package cz.cvut.fel.mongodb_assignment_evaluator.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FileFormats {
    CSV(".csv"),
    LOG(".log"),
    JS(".js");

    private final String fileFormat;

    public String getFileNameWithFormat(String fileName) {
        return fileName + fileFormat;
    }

    public boolean ofFormat(String fileName) {
        return fileName.endsWith(fileFormat);
    }
}
