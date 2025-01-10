package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.bson;

import org.bson.BsonValue;

import java.util.Comparator;

public class BsonValueComparator implements Comparator<BsonValue> {
    @Override
    public int compare(BsonValue value1, BsonValue value2) {
        if (value1 == null || value2 == null) {
            throw new IllegalArgumentException("Field does not exist in one or both documents");
        }
        return compareBsonValues(value1, value2);
    }

    private int compareBsonValues(BsonValue value1, BsonValue value2) {
        if (value1.getBsonType() != value2.getBsonType()) {
            return value1.getBsonType().compareTo(value2.getBsonType());
        }
        return switch (value1.getBsonType()) {
            case INT32 -> Integer.compare(value1.asInt32().getValue(), value2.asInt32().getValue());
            case INT64 -> Long.compare(value1.asInt64().getValue(), value2.asInt64().getValue());
            case DOUBLE -> Double.compare(value1.asDouble().getValue(), value2.asDouble().getValue());
            case STRING -> value1.asString().getValue().compareTo(value2.asString().getValue());
            case BOOLEAN -> Boolean.compare(value1.asBoolean().getValue(), value2.asBoolean().getValue());
            case DATE_TIME -> Long.compare(value1.asDateTime().getValue(), value2.asDateTime().getValue());
            case DECIMAL128 -> value1.asDecimal128().getValue().compareTo(value2.asDecimal128().getValue());
            case OBJECT_ID ->
                    value1.asObjectId().getValue().toHexString().compareTo(value2.asObjectId().getValue().toHexString());
            case NULL -> 0; // Both are null, treat as equal
            case ARRAY -> throw new UnsupportedOperationException("Comparison for arrays is not supported");
            case DOCUMENT ->
                    throw new UnsupportedOperationException("Comparison for embedded documents is not supported");
            default ->
                    throw new UnsupportedOperationException("Comparison for type " + value1.getBsonType() + " is not implemented");
        };
    }
}
