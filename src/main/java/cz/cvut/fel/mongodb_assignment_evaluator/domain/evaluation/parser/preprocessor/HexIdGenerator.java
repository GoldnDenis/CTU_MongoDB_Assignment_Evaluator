package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.preprocessor;

import java.security.SecureRandom;

/**
 * Generates hex-decimal id-like values
 */
public class HexIdGenerator {
    private final static String HEX_CHARS = "0123456789abcdef";

    public static String generateHexId(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder hexString = new StringBuilder(length);
        for (int i = 0; i < 24; i++) {
            int index = random.nextInt(HEX_CHARS.length());
            hexString.append(HEX_CHARS.charAt(index));
        }
        return hexString.toString();
    }
}
