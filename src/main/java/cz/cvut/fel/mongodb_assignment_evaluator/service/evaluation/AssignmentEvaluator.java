package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.CriteriaChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;
import lombok.extern.java.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Log
public class AssignmentEvaluator {
    public static void evaluate(String rootDirectoryPath) {
        File rootDirectory = DirectoryManager.getValidatedRootDirectory(rootDirectoryPath);
        if (rootDirectory == null) return;

        File resultFolder = DirectoryManager.createResultsFolder(rootDirectory);
        if (resultFolder == null) return;

        Pattern studentFolderPattern = Pattern.compile("^(f[0-9]+_)([a-zA-Z0-9]+)-mongodb-[0-9]+-[0-9]+");
        File[] subfolders = rootDirectory.listFiles();

        if (subfolders == null) {
            log.severe("No subfolders found in directory: " + rootDirectoryPath);
            return;
        }

        for (File folder : subfolders) {
            Matcher matcher = studentFolderPattern.matcher(folder.getName());
            if (matcher.matches()) {
                String studentName = matcher.group(2);

                List<String> fileLines = DirectoryManager.readJavaScriptFiles(folder);
                List<Query> queryList = ScriptParser.parse(fileLines);
                List<String> feedbackList = new CriteriaChecker().checkQueries(queryList);

                DirectoryManager.writeFeedback(resultFolder, studentName, feedbackList);
            }
        }
    }
}
