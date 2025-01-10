package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.bson;

import org.bson.BsonValue;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

public class BsonValueChecker {
    public static boolean isISODate(BsonValue value) {
        if (value == null) {
            return false;
        }
        if (value.isDateTime()) {
            return true;
        }
        if (!value.isString()) {
            return false;
        }
        try {
            ZonedDateTime.parse(value.asString().getValue());
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
