package cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.persistence.sql.service;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.StudentSubmission;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.*;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.evaluation.GradedCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.MongoQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.persistence.sql.projection.SubmissionResultView;
import cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.persistence.sql.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
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

    /**
     * Maps metadata to StudentSubmission object.
     * Initially tries to find the student, if it does not, then it inserts a new one.
     * Then, it proceeds to retrieve a submission from the database. If it is not found, generates a new one and returns it.
     * Otherwise, retrieves found record.
     * @param studentName
     * @param studyYear
     * @param date in timestamp format "YYYYMMDDHHMMss", otherwise would throw an exception
     * @return StudentSubmission object, which is linked to database table by Submission entity.
     */
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

    /**
     * A transaction that saves the submission and its data into tables.
     * The saved data: submission, extracted queries with associated execution logs,
     * criteria evaluation results and criteria fulfillment by each query.
     * @param studentSubmission an object that contains all information about the evaluation.
     */
    @Transactional
    public void saveSubmission(StudentSubmission studentSubmission) {
        Submission submission = studentSubmission.getSubmission();
        submissionRepository.save(submission);

        Map<String, Query> savedQueriesByText = new HashMap<>();
        for (MongoQuery token : studentSubmission.getExtractedQueries()) {
            Query query = new Query(token.getQuery(), submission);
            query = queryRepository.save(query);

            Log log = new Log(query, token.getExecutionLog());
            logRepository.save(log);

            savedQueriesByText.put(token.getQuery(), query);
        }

        for (GradedCriterion graded : studentSubmission.getGradedCriteria()) {
            if (graded.getName().equalsIgnoreCase(Criteria.UNRECOGNIZED_QUERY.name())) {
                continue;
            }
            Criterion criterion = graded.getCriterion();
            criterion = criterionRepository.findById(criterion.getId())
                    .orElseThrow();
            SubmissionResult result = new SubmissionResult(criterion, submission, graded.getScore());
            submissionResultRepository.save(result);

            for (MongoQuery token : graded.getFulfilledQueries()) {
                Query relatedQuery = savedQueriesByText.get(token.getQuery());
                criterion.getQueries().add(relatedQuery);
            }
            criterionRepository.save(criterion);
        }
    }

    /**
     *
     * @return returns all records of each student from the most recent submission
     */
    public List<SubmissionResultView> getLatestStudentResults() {
        return submissionResultRepository.fetchLatestStudentCriterionScores();
    }
}
