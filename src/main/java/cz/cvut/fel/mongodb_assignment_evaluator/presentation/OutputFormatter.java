package cz.cvut.fel.mongodb_assignment_evaluator.presentation;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.CriterionEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.ResultStates;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.log.LogCollector;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.Query;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.result.FinalEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.result.StudentEvaluationResult;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class OutputFormatter {
    public static String formatToString(StudentEvaluationResult studentEvaluationResult) {
        StringBuilder resultBuilder = new StringBuilder();
        int i = 0;
        for (CriterionEvaluationResult criterionEvaluationResult : studentEvaluationResult.getCriteriaMapWithScores().keySet()) {
            i++;
            resultBuilder.append(i)
                    .append(") ")
                    .append(formatToString(criterionEvaluationResult));
        }
        return resultBuilder.toString();
    }

    private static String formatToString(CriterionEvaluationResult criterionEvaluationResult) {
        StringBuilder resultBuilder = new StringBuilder();
        Criteria criterion = criterionEvaluationResult.getCriterion();
        ResultStates resultState = criterionEvaluationResult.evaluateState();
        resultBuilder.append("'")
                .append(criterion.getDescription())
                .append("' ")
                .append(resultState.getText());
        if (criterion != Criteria.UNKNOWN_UNRECOGNISED) {
            resultBuilder.append(" - ")
                    .append(criterionEvaluationResult.getScore())
                    .append("/")
                    .append(criterionEvaluationResult.getRequiredCount());
        }
        if (resultState != ResultStates.NOT_FULFILLED &&
                criterion != Criteria.COMMENT) {
            resultBuilder.append(":")
                    .append("\n")
                    .append(formatToString(criterionEvaluationResult.getSatisfiedQueries().keySet().stream().toList()));
        } else {
            resultBuilder.append(".")
                    .append("\n");
        }
        return resultBuilder.toString();
    }

    private static String formatToString(List<Query> queryList) {
        StringBuilder resultBuilder = new StringBuilder();
        for (int i = 0; i < queryList.size(); i++) {
            resultBuilder.append("\t")
                    .append(i + 1)
                    .append(") ")
                    .append(queryList.get(i).getQuery())
                    .append("\n");
        }
        return resultBuilder.toString();
    }

    public static String formatToString(FinalEvaluationResult finalEvaluationResult) {
        StringBuilder resultBuilder = new StringBuilder();
        boolean init = false;
        for (Map.Entry<String, StudentEvaluationResult> entry : finalEvaluationResult.getStudentResultMap().entrySet()) {
            String student = entry.getKey();
            Map<CriterionEvaluationResult, Integer> criterionCounts = entry.getValue().getCriteriaMapWithScores();
            Set<CriterionEvaluationResult> criteriaResults = criterionCounts.keySet();
            if (!init) {
                init = true;
                resultBuilder.append("StudentName");
                for (CriterionEvaluationResult result : criteriaResults) {
                    resultBuilder.append(",").append(result.getCriterion().name());
                }
                resultBuilder.append("\n");
            }
            resultBuilder.append(student);
            for (CriterionEvaluationResult result : criteriaResults) {
                resultBuilder.append(",").append(String.valueOf(criterionCounts.get(result)));
            }
            resultBuilder.append("\n");
        }
        return resultBuilder.toString();
    }

    public static String formatToString(LogCollector logCollector) {
        return String.join("\n", logCollector.getLogList());
    }
}
