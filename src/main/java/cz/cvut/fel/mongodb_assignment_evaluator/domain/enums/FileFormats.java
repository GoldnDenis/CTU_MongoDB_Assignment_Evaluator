package cz.cvut.fel.mongodb_assignment_evaluator.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FileFormats {
    CSV(".csv"),
    LOG(".log"),
    JS(".js"),
    TXT(".txt"),
    ;

    private final String fileFormat;

    public String getFileNameWithFormat(String fileName) {
        return fileName + fileFormat;
    }

    public boolean ofFormat(String fileName) {
        return fileName.endsWith(fileFormat);
    }
}
