package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.result;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.ResultStates;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.CriterionEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.Query;

import java.util.List;

public class ResultProcessor {
    public static String formatToText(List<CriterionEvaluationResult> criterionEvaluationResults) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < criterionEvaluationResults.size(); i++) {
            CriterionEvaluationResult criterionEvaluationResult = criterionEvaluationResults.get(i);
            ResultStates resultState = criterionEvaluationResult.evaluateState();
            Criteria criterion = criterionEvaluationResult.getCriterion();
            text.append(i + 1)
                    .append(") '")
                    .append(criterion.getDescription())
                    .append("' ")
                    .append(resultState.getText())
                    .append("'");
            if (criterion != Criteria.UNKNOWN_UNRECOGNISED) {
                text.append(" - ")
                        .append(criterionEvaluationResult.getScore())
                        .append("/")
                        .append(criterionEvaluationResult.getRequiredCount());
            }
            if (resultState != ResultStates.NOT_FULFILLED &&
                    criterion != Criteria.COMMENT) {
                text.append(":")
                        .append("\n")
                        .append(formatQueryList(criterionEvaluationResult.getSatisfiedQueries().keySet().stream().toList()));
            } else {
                text.append(".")
                        .append("\n");
            }
        }
        return text.toString();
    }

    private static String formatQueryList(List<Query> queryList) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < queryList.size(); i++) {
            text.append("\t")
                    .append(i + 1)
                    .append(") ")
                    .append(queryList.get(i).getQuery())
                    .append("\n");
        }
        return text.toString();
    }
}
