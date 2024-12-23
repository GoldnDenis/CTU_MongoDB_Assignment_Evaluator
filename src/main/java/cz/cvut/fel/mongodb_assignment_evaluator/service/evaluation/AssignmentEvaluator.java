package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.CriteriaEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.EvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.Query;
import lombok.extern.java.Log;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        log.info("Starting evaluation...");
        for (File folder : subfolders) {
            Matcher matcher = studentFolderPattern.matcher(folder.getName());
            if (matcher.matches()) {
                String studentName = matcher.group(2);

                List<String> fileLines = DirectoryManager.readJavaScriptFiles(folder);
                log.info("Parsing the script of " + studentName + "...");
                List<Query> queryList = ScriptParser.parse(fileLines);
                log.info("Evaluating queries...");
                List<EvaluationResult> evaluationResults = new CriteriaEvaluator().check(queryList);
                log.info("Processing results...");
                String resultToWrite = ResultProcessor.formatToText(evaluationResults);
                log.info("Writing results...");
                DirectoryManager.writeFeedback(resultFolder, studentName, resultToWrite);
            }
        }
        log.info("Evaluation has finished.");
    }
}
