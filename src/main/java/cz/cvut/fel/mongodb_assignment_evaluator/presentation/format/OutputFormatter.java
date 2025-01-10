package cz.cvut.fel.mongodb_assignment_evaluator.presentation.format;

import cz.cvut.fel.mongodb_assignment_evaluator.application.model.result.CriterionEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.ResultStates;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.log.ErrorCollector;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.Query;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.result.FinalEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.result.StudentEvaluationResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class OutputFormatter {
    public String formatToString(StudentEvaluationResult studentEvaluationResult) {
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("Criterion")
                .append(",").append("Description")
                .append(",").append("Status")
                .append(",").append("Found Count")
                .append(",").append("Fulfilled Queries");
        for (CriterionEvaluationResult criterionEvaluationResult : studentEvaluationResult.getCriteriaScoreMap().keySet()) {
            resultBuilder.append("\n");
            resultBuilder.append(formatToString(criterionEvaluationResult));
        }
        resultBuilder.append("\n");
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
                    .append(queryList.get(i).getQuery().replace("\n", " ").replace(",", ";"))
                    .append(";");
        }
        return resultBuilder.toString().replace(";;", ";");
    }

    public String formatToString(FinalEvaluationResult finalEvaluationResult) {
        StringBuilder resultBuilder = new StringBuilder();
        boolean init = false;
        for (Map.Entry<String, StudentEvaluationResult> entry : finalEvaluationResult.getStudentResultMap().entrySet()) {
            String student = entry.getKey();
            Map<CriterionEvaluationResult, Integer> criterionCounts = entry.getValue().getCriteriaScoreMap();
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
                resultBuilder.append(",").append(criterionCounts.get(result));
            }
            resultBuilder.append("\n");
        }
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

    public String formatToString(ErrorCollector errorCollector) {
        return String.join("\n", errorCollector.getLogList());
    }
}
