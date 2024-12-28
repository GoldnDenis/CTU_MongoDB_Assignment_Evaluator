package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.CriteriaEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.result.FinalEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.file.DirectoryManager;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.result.StudentEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.Query;
import lombok.extern.java.Log;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log
public class AssignmentEvaluator {
    private static final FinalEvaluationResult finalEvaluationResult = new FinalEvaluationResult();

    public static void evaluate(String rootDirectoryPath) {
        File rootDirectory = DirectoryManager.getValidatedRootDirectory(rootDirectoryPath);
        if (rootDirectory == null) return;
        File resultFolder = DirectoryManager.createResultsFolder(rootDirectory);
        if (resultFolder == null) return;
        File[] subfolders = rootDirectory.listFiles();
        if (subfolders == null) {
            log.severe("No subfolders found in directory: " + rootDirectoryPath);
            return;
        }
        Pattern studentFolderPattern = Pattern.compile("^(f[0-9]+_)([a-zA-Z0-9]+)-mongodb-[0-9]+-[0-9]+");
        log.info("Starting evaluation...");
        for (File folder : subfolders) {
            Matcher matcher = studentFolderPattern.matcher(folder.getName());
            if (matcher.matches()) {
                String studentName = matcher.group(2);
                evaluateStudentWork(resultFolder, folder, studentName);
            }
        }
        log.info("Generating a table with all results...");
        DirectoryManager.generateFinalResultTable(resultFolder, finalEvaluationResult);

        log.info("Evaluation has finished.");
    }

    private static void evaluateStudentWork(File resultFolder, File folder, String studentName) {
        List<String> fileLines = DirectoryManager.readJavaScriptFiles(folder);
        log.info("Parsing the script of " + studentName + "...");
        List<Query> queryList = ScriptParser.parse(fileLines);
        log.info("Evaluating queries...");
        StudentEvaluationResult studentResult = new CriteriaEvaluator().check(queryList);
        finalEvaluationResult.addStudentResult(studentName, studentResult);
        log.info("Writing results of " + studentName + "...");
        String resultToWrite = studentResult.toString();
//        DirectoryManager.writeStudentEvaluationResult(resultFolder, studentName, resultToWrite);
        System.out.println(resultToWrite);
        System.out.println();
    }
}
