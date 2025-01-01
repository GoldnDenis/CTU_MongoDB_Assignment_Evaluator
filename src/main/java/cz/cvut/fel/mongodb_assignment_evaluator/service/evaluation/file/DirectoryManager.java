package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.file;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.CriterionEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.result.FinalEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.result.StudentEvaluationResult;
import lombok.extern.java.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Log
public class DirectoryManager {
    public static File getValidatedRootDirectory(String rootDirectoryPath) {
        File rootDirectory = new File(rootDirectoryPath);
        if (!rootDirectory.isDirectory()) {
            log.severe("Root directory is not a directory: " + rootDirectoryPath);
            return null;
        }
        return rootDirectory;
    }

    public static File createResultsFolder(File rootDirectory) {
        File resultFolder = new File(rootDirectory, "results");
        if (!resultFolder.exists() && !resultFolder.mkdirs()) {
            log.severe("Failed to create directory: " + resultFolder.getAbsolutePath());
            return null;
        }
        return resultFolder;
    }

    public static List<String> readJavaScriptFiles(File folder) {
        List<String> fileLines = new ArrayList<>();
        File[] scripts = folder.listFiles((dir, name) -> name.endsWith(".js"));
        if (scripts != null) {
            for (File script : scripts) {
                try {
                    fileLines.addAll(Files.readAllLines(script.toPath()));
                } catch (IOException e) {
                    log.severe("Error reading file: " + script.getName());
                }
            }
        }
        return fileLines;
    }

    public static void writeStudentEvaluationResult(File resultFolder, String studentName, String result) {
        File resultFile = new File(resultFolder, studentName + ".csv");
        try (FileWriter writer = new FileWriter(resultFile)) {
            writer.write(result);
        } catch (IOException e) {
            log.severe("Error writing feedback for " + studentName);
        }
    }

    public static void generateFinalResultTable(File resultFolder, FinalEvaluationResult finalEvaluationResult) {
        File finalResultFile = new File(resultFolder, "final_table.csv");
        try (FileWriter writer = new FileWriter(finalResultFile)) {
            boolean init = false;
            for (Map.Entry<String, StudentEvaluationResult> entry : finalEvaluationResult.getStudentResultMap().entrySet()) {
                String student = entry.getKey();
                Map<CriterionEvaluationResult, Integer> criterionCounts = entry.getValue().getCriteriaMapWithScores();
                Set<CriterionEvaluationResult> criteriaResults = criterionCounts.keySet();
                if (!init) {
                    init = true;
                    writer.append("StudentName");
                    for (CriterionEvaluationResult result : criteriaResults) {
                        writer.append(",").append(result.getCriterion().name());
                    }
                    writer.append("\n");
                }
                writer.append(student);
                for (CriterionEvaluationResult result : criteriaResults) {
                    writer.append(",").append(String.valueOf(criterionCounts.get(result)));
                }
                writer.append("\n");
            }
        } catch (IOException e) {
            log.severe("Error writing cross-tabulation table");
        }
    }

//    public static void writeStudentEvaluationResult(File resultFolder, String studentName, List<String> feedbackList) {
//        File resultFile = new File(resultFolder, studentName + ".csv");
//        try (FileWriter writer = new FileWriter(resultFile)) {
//            writer.write(String.join("\n", feedbackList));
//        } catch (IOException e) {
//            log.severe("Error writing feedback for " + studentName);
//        }
//    }
}
