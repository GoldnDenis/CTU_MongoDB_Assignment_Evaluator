package cz.cvut.fel.mongodb_assignment_evaluator.observability.format;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.StudentSubmission;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.evaluation.GradedCriteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.QueryToken;
import cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.persistence.sql.projection.SubmissionResultView;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutputFormatter {
    public String generateStudentFeedback(StudentSubmission submission) {
        StringBuilder resultBuilder = new StringBuilder();

        resultBuilder.append("--------------------| Criteria fulfillment:")
                .append(System.lineSeparator());
        List<GradedCriteria> gradedCriteria = submission.getGradedCriteria();
        for (GradedCriteria gradedCriterion : gradedCriteria) {
            if (!gradedCriterion.getName().equalsIgnoreCase(Criteria.UNRECOGNIZED_QUERY.name())) {
                resultBuilder.append(gradedCriterion.getScore()).append("/")
                        .append(gradedCriterion.getRequiredScore()).append(" -- ")
                        .append("\"").append(gradedCriterion.getDescription()).append("\"")
                        .append(System.lineSeparator());
            }
        }

        resultBuilder.append("--------------------| Encountered warnings/errors: ")
                .append(System.lineSeparator());
        List<String> errorLogs = submission.getErrorLogs();
        if (errorLogs.isEmpty()) {
            resultBuilder.append("No warnings/errors caught during the evaluation");
        }
        for (int i = 0; i < errorLogs.size(); i++) {
            String errorLog = submission.getErrorLogs().get(i);
            resultBuilder.append((i + 1)).append(") ")
                    .append(errorLog);
        }

        return resultBuilder.toString();
    }

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
                ? "No warnings/errors caught during the evaluation"
                : String.join(System.lineSeparator(), list);
    }

    public String generateExecutionLog(List<QueryToken> queryTokens) {
        StringBuilder resultBuilder = new StringBuilder();
        for (QueryToken query : queryTokens) {
            resultBuilder.append(query.getPrecedingComment())
                    .append(System.lineSeparator())
                    .append("---------| ")
                    .append("Query = '")
                    .append(query.getQuery())
                    .append("' |---------")
                    .append(System.lineSeparator())
                    .append(System.lineSeparator())
                    .append(query.getExecutionLog())
                    .append(System.lineSeparator())
                    .append(System.lineSeparator());
        }
        return resultBuilder.toString();
    }

    public String generateCrossTab(List<SubmissionResultView> results, List<String> criteriaNames) {
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("Student");
        criteriaNames.forEach(name -> resultBuilder.append(",").append(name));
        String lastStudent = "";
        results = results.stream()
                .filter(r -> criteriaNames.contains(r.getName()))
                .toList();
        for (SubmissionResultView result : results) {
            String student = result.getUsername();
            if (!student.equals(lastStudent)) {
                resultBuilder.append(System.lineSeparator())
                        .append(result.getUsername());
                lastStudent = student;
            }
            resultBuilder.append(",").append(result.getScore());
        }
        resultBuilder.append(System.lineSeparator());
        return resultBuilder.toString();
    }
}
