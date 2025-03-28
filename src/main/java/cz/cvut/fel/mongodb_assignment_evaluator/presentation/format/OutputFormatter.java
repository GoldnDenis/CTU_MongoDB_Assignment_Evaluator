package cz.cvut.fel.mongodb_assignment_evaluator.presentation.format;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.ResultStates;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.error.StudentErrorCollector;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.Query;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.result.CriterionEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.result.FinalEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.result.GradedSubmission;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.GradedCriteria;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class OutputFormatter {
//    public String formatToString(GradedSubmission gradedSubmission) {
//        StringBuilder resultBuilder = new StringBuilder();
//        resultBuilder.append("Criterion")
//                .append(",").append("Description")
//                .append(",").append("Status")
//                .append(",").append("Found Count")
//                .append(",").append("Fulfilled Queries");
//        for (CriterionEvaluationResult criterionEvaluationResult : gradedSubmission.getCriteriaScoreMap().keySet()) {
//            resultBuilder.append("\n");
//            resultBuilder.append(formatToString(criterionEvaluationResult));
//        }
//        resultBuilder.append("\n");
//        return resultBuilder.toString();
//    }

    public String formatToString(GradedSubmission gradedSubmission) {
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("Criterion")
                .append(",").append("Description")
                .append(",").append("Status")
                .append(",").append("Found Count")
                .append(",").append("Fulfilled Queries");
        for (GradedCriteria graded : gradedSubmission.getGradedCriteria()) {
            resultBuilder.append("\n");
            resultBuilder.append(formatToString(graded));
        }
        resultBuilder.append("\n");
        return resultBuilder.toString();
    }

    public String formatToString(GradedCriteria gradedCriteria) {
        String name = gradedCriteria.getName();
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append(name)
                .append(",").append(gradedCriteria.getDescription().replace("\n", " ").replace(",", ";"))
                .append(",").append(gradedCriteria.getResultState().getText());
        resultBuilder.append(",").append(gradedCriteria.getScore());
        if (!name.equalsIgnoreCase("UNRECOGNISED")) {
            resultBuilder.append("/").append(gradedCriteria.getRequiredScore());
        }
        resultBuilder.append(",");
        if (!name.equalsIgnoreCase("COMMENT")) {
            resultBuilder.append(formatToString(gradedCriteria.getFulfilledQueries()));
        }
        return resultBuilder.toString();
    }

    public String formatToString(List<Query> queryList) {
        StringBuilder resultBuilder = new StringBuilder();
        for (int i = 0; i < queryList.size(); i++) {
            if (i != 0) {
                resultBuilder.append(" ");
            }
            resultBuilder.append(i + 1)
                    .append(") ")
                    .append(queryList.get(i).getQuery().replace("\r", " ").replace("\n", " ").replace(",", ";"))
                    .append(";");
        }
        return resultBuilder.toString().replace(";;", ";");
    }

    public String formatToString(FinalEvaluationResult finalEvaluationResult) {
        StringBuilder resultBuilder = new StringBuilder();
//        boolean init = false;
//        for (Map.Entry<String, GradedSubmission> entry : finalEvaluationResult.getStudentResultMap().entrySet()) {
//            String student = entry.getKey();
//            Map<CriterionEvaluationResult, Integer> criterionCounts = entry.getValue().getCriteriaScoreMap();
//            Set<CriterionEvaluationResult> criteriaResults = criterionCounts.keySet();
//            if (!init) {
//                init = true;
//                resultBuilder.append("StudentName");
//                for (CriterionEvaluationResult result : criteriaResults) {
//                    resultBuilder.append(",").append(result.getCriterion().name());
//                }
//                resultBuilder.append("\n");
//            }
//            resultBuilder.append(student);
//            for (CriterionEvaluationResult result : criteriaResults) {
//                resultBuilder.append(",").append(criterionCounts.get(result));
//            }
//            resultBuilder.append("\n");
//        }
        return resultBuilder.toString();
    }

    public String formatToString(CriterionEvaluationResult evaluationResult) {
        StringBuilder resultBuilder = new StringBuilder();
        Criteria criterion = evaluationResult.getCriterion();
        ResultStates resultState = evaluationResult.evaluateState();
        resultBuilder.append(criterion.name())
                .append(",").append(criterion.getDescription().replace("\n", " ").replace(",", ";"))
                .append(",").append(resultState.getText());
        resultBuilder.append(",").append(evaluationResult.getScore());
        if (criterion != Criteria.UNKNOWN_UNRECOGNISED) {
            resultBuilder.append("/").append(evaluationResult.getRequiredCount());
        }
        resultBuilder.append(",");
        if (criterion != Criteria.COMMENT) {
            resultBuilder.append(formatToString(evaluationResult.getSatisfiedQueryScoreMap().keySet().stream().toList()));
        }
        return resultBuilder.toString();
    }

    public String formatToString(StudentErrorCollector studentErrorCollector) {
        return String.join("\n", studentErrorCollector.getLogList());
    }
}
