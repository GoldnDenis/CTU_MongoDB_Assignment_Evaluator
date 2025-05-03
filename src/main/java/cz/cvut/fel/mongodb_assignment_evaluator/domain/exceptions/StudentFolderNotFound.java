package cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions;

public class StudentFolderNotFound extends RuntimeException {
    public StudentFolderNotFound(String studentName, String folderType) {
        super("The " + folderType + " folder of " + studentName + " was not found.");
    }
}
