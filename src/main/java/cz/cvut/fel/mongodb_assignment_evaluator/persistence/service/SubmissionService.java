package cz.cvut.fel.mongodb_assignment_evaluator.persistence.service;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.StudentSubmission;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.*;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.QueryToken;
import cz.cvut.fel.mongodb_assignment_evaluator.persistence.repository.LogRepository;
import cz.cvut.fel.mongodb_assignment_evaluator.persistence.repository.QueryRepository;
import cz.cvut.fel.mongodb_assignment_evaluator.persistence.repository.StudentRepository;
import cz.cvut.fel.mongodb_assignment_evaluator.persistence.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final StudentRepository studentRepository;
    private final QueryRepository queryRepository;
    private final LogRepository logRepository;

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

    public void saveSubmission(StudentSubmission studentSubmission) {
        Submission submission = studentSubmission.getSubmission();
        for (QueryToken queryToken : studentSubmission.getQueryList()) {
            Query query = new Query(queryToken.getQuery(), submission);
            queryRepository.save(query);
            submission.addQuery(query);
            String output = String.join(System.lineSeparator(), queryToken.getExecutionLogs());
            Log log = new Log(query, output);
            logRepository.save(log);
        }
        submissionRepository.save(submission);
    }
}
