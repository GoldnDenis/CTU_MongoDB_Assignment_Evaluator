package cz.cvut.fel.mongodb_assignment_evaluator.presentation.format;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.StudentSubmission;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.evaluation.GradedCriteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.QueryToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutputFormatter {
    public String generateTable(List<GradedCriteria> criteria) {
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("Criterion")
                .append(",").append("Description")
                .append(",").append("Status")
                .append(",").append("Score")
                .append(",").append("Fulfilled Queries");
        for (GradedCriteria criterion : criteria) {
            String name = criterion.getName();
            String score = String.valueOf(criterion.getScore());
            String scoreOutput = name.equalsIgnoreCase(Criteria.UNRECOGNIZED_QUERY.name()) ? score : score + "/" + criterion.getRequiredScore();
            String formattedQueries = name.equalsIgnoreCase(Criteria.COMMENT.name()) ? "" : formatQueryList(criterion.getFulfilledQueries());
            resultBuilder.append("\n").append(name)
                    .append(",").append(criterion.getDescription().replace("\n", " ").replace(",", ";"))
                    .append(",").append(criterion.getResultState().getText())
                    .append(",").append(scoreOutput)
                    .append(",").append(formattedQueries);
        }
        resultBuilder.append("\n");
        return resultBuilder.toString();
    }

    public String formatQueryList(List<QueryToken> queryList) {
        StringBuilder resultBuilder = new StringBuilder();
        for (int i = 0; i < queryList.size(); i++) {
            String queryEnd = (i + 1 < queryList.size()) ? "; " : ";";
            resultBuilder.append(i + 1)
                    .append(") ")
                    .append(queryList.get(i).getQuery().replace("\r", " ").replace("\n", " ").replace(",", ";"))
                    .append(queryEnd);
        }
        return resultBuilder.toString().replace(";;", ";");
    }

    public String generateEvaluationLog(List<String> list) {
        return list.isEmpty()
                ? "No errors caught during the evaluation"
                : String.join(System.lineSeparator(), list);
    }

    public String generateExecutionLog(List<QueryToken> queryTokens) {
        StringBuilder resultBuilder = new StringBuilder();
        for (QueryToken query : queryTokens) {
            resultBuilder.append("---------| Query = '")
                    .append(query.getQuery())
                    .append("' |---------")
                    .append(System.lineSeparator())
                    .append(System.lineSeparator())
                    .append(String.join(System.lineSeparator(), query.getExecutionLogs()))
                    .append(System.lineSeparator())
                    .append(System.lineSeparator());
        }
        return resultBuilder.toString();
    }

    public String generateCrossTab(List<StudentSubmission> submissions, List<String> criteriaNames) {
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("Student");
        criteriaNames.forEach(name -> resultBuilder.append(",").append(name));
        for (StudentSubmission submission : submissions) {
            resultBuilder.append("\n")
                    .append(submission.getStudentName());
            submission.getGradedCriteria().forEach(criterion -> resultBuilder.append(",").append(criterion.getScore()));
        }
        resultBuilder.append("\n");
        return resultBuilder.toString();
    }
}
