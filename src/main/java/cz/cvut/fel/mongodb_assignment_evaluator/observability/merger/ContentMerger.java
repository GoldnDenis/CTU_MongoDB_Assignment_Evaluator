package cz.cvut.fel.mongodb_assignment_evaluator.observability.merger;

import lombok.AllArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ContentMerger {
    @AllArgsConstructor
    private enum ScriptPriority {
        COLLECTION(3),
        INSERT(2),
        OTHER(1),
        ;

        private final int priority;
    }

    public static String mergeScriptContentsByPriority(List<String> contentsList) {
        return contentsList.stream()
                .sorted(Comparator.comparingInt(ContentMerger::getPriority))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private static int getPriority(String contents) {
        if (contents.contains(".createCollection")) {
            return ScriptPriority.COLLECTION.priority;
        } else if (contents.contains(".insert")) {
            return ScriptPriority.INSERT.priority;
        } else {
            return ScriptPriority.OTHER.priority;
        }
    }
}
