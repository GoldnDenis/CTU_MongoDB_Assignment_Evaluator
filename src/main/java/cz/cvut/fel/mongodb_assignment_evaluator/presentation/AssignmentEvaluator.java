package cz.cvut.fel.mongodb_assignment_evaluator.presentation;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.CriteriaEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.result.FinalEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.result.StudentEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.Query;
import lombok.extern.java.Log;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log
public class AssignmentEvaluator {
    private static final Pattern STUDENT_FOLDER_PATTERN = Pattern.compile("^(f[0-9]+_)([a-zA-Z0-9]+)-mongodb-[0-9]+-[0-9]+");
    private static final FinalEvaluationResult finalEvaluationResult = new FinalEvaluationResult();

    public static void evaluate(String rootDirectoryPath) {
        try {
            DirectoryManager directoryManager = new DirectoryManager(rootDirectoryPath);

            log.info("Starting evaluation...");
            for (File folder : directoryManager.getDirectorySubfolders()) {
                Matcher matcher = STUDENT_FOLDER_PATTERN.matcher(folder.getName());
                if (matcher.matches()) {
                    String studentName = matcher.group(2);
                    evaluateStudentWork(directoryManager, folder, studentName);
                }
            }
            log.info("Generating an overall result table...");
            String finalResultTable = ResultFormatter.formatToString(finalEvaluationResult);
            directoryManager.writeCSV("final_table", finalResultTable);

            log.info("Evaluation has finished.");
        } catch (Exception e) {
            log.severe(e.getMessage());
        }
    }

    private static void evaluateStudentWork(DirectoryManager directoryManager, File folder, String studentName) {
        log.info("Reading the script of " + studentName + "...");
        List<String> fileLines = DirectoryManager.readJavaScriptFiles(folder);

        log.info("Parsing the script of " + studentName + "...");
        List<Query> queryList = ScriptParser.parse(fileLines);

        log.info("Evaluating queries...");
        StudentEvaluationResult studentResult = new CriteriaEvaluator().check(queryList);

        log.info("Writing results of " + studentName + "...");
        String studentResultReport = ResultFormatter.formatToString(studentResult);
        directoryManager.writeCSV(studentName, studentResultReport);

        finalEvaluationResult.addStudentResult(studentName, studentResult);
    }
}
