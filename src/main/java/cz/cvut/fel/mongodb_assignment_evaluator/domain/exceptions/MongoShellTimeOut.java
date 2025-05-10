package cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions;

public class MongoShellTimeOut extends RuntimeException {
    public MongoShellTimeOut(long timeout) {
        super("Time has run out, the process was killed after waiting for " + timeout + " milliseconds.");
    }

    public MongoShellTimeOut(long timeout, String suggestion) {
        super("Time has run out, the process was killed after waiting for " + timeout + " milliseconds. " + suggestion + ".");
    }
}
