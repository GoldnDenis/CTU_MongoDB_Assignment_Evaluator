package cz.cvut.fel.mongodb_assignment_evaluator.persistence.service;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.StudentSubmission;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.*;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.evaluation.GradedCriteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.QueryToken;
import cz.cvut.fel.mongodb_assignment_evaluator.persistence.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final StudentRepository studentRepository;
    private final QueryRepository queryRepository;
    private final LogRepository logRepository;
    private final SubmissionResultRepository submissionResultRepository;
    private final CriterionRepository criterionRepository;

    public StudentSubmission getStudentSubmission(String studentName, int studyYear, String date) {
        Optional<Student> studentEntry = studentRepository.findByUsernameAndStudyYear(studentName, studyYear);
        Student student = studentEntry.orElseGet(() -> new Student(studentName, studyYear));
        if (studentEntry.isEmpty()) {
            studentRepository.save(student);
        }
        LocalDateTime uploadDate = LocalDateTime.parse(
                date,
                DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        );
        Optional<Submission> submissionEntry = submissionRepository.findByStudentAndUploadDate(student, uploadDate);
        Submission submission = submissionEntry.orElseGet(() -> new Submission(uploadDate, student));
        Topic topic = student.getTopic();
        long teamCount = topic != null ? studentRepository.countByTopic(topic) : 1;
        return new StudentSubmission(submission, submissionEntry.isPresent(), teamCount);
    }

    @Transactional
    public void saveSubmission(StudentSubmission studentSubmission) {
        Submission submission = studentSubmission.getSubmission();
        submissionRepository.save(submission);

        Map<String, Query> savedQueriesByText = new HashMap<>();
        for (QueryToken token : studentSubmission.getQueryList()) {
            Query query = new Query(token.getQuery(), submission);
            query = queryRepository.save(query);

            Log log = new Log(query, String.join(System.lineSeparator(), token.getExecutionLogs()));
            logRepository.save(log);

            savedQueriesByText.put(token.getQuery(), query);
        }

        for (GradedCriteria graded : studentSubmission.getGradedCriteria()) {
            if (graded.getName().equalsIgnoreCase(Criteria.UNRECOGNIZED_QUERY.name())) {
                continue;
            }
            Criterion criterion = graded.getCriterion();
            criterion = criterionRepository.findById(criterion.getId())
                    .orElseThrow();
            SubmissionResult result = new SubmissionResult(criterion, submission, graded.getScore());
            submissionResultRepository.save(result);

            for (QueryToken token: graded.getFulfilledQueries()) {
                Query relatedQuery = savedQueriesByText.get(token.getQuery());
                criterion.getQueries().add(relatedQuery);
            }
            criterionRepository.save(criterion);
        }
    }
}
