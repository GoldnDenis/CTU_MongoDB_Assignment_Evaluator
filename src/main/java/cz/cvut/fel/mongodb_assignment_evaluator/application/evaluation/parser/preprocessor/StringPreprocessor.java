package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.preprocessor;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringPreprocessor {
    private static final Pattern DATE_PATTERN = Pattern.compile("new Date\\((.*?(\\((.*?)\\).*?)?)\\)");
//    private static final Pattern OBJECT_ID_PATTERN = Pattern.compile("ObjectId\\([a-zA-Z0-9]*\\)");

    public static String preprocessEJson(String eJson) {
        eJson = eJson.replaceAll("\\/\\*.*?\\*\\/", "");
        eJson = eJson.replaceAll("ObjectId\\([a-zA-Z0-9]*\\)", generateHex24());
        Matcher matcher = DATE_PATTERN.matcher(eJson);
        while (matcher.find()) {
            String dateString = matcher.group(1);
            String replacement = "";
            if (dateString == null || dateString.isBlank()) {
                replacement = "\"" + new Date() + "\"";
            } else if ((dateString.startsWith("\"") && dateString.endsWith("\"")) ||
                    (dateString.startsWith("'") && dateString.endsWith("'"))) {
                replacement = matcher.group(1);
            } else {
                String newDate = "\"" + new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss z").format(new Date()) + "\"";
                replacement = matcher.group().replace(matcher.group(1), newDate);
            }
            eJson = eJson.replace(matcher.group(), replacement);
        }
        return eJson;
    }

    private static String generateHex24() {
        final String HEX_CHARS = "0123456789abcdef";
        SecureRandom random = new SecureRandom();
        StringBuilder hexString = new StringBuilder(24);

        hexString.append("\"");
        for (int i = 0; i < 24; i++) {
            int index = random.nextInt(HEX_CHARS.length());
            hexString.append(HEX_CHARS.charAt(index));
        }
        hexString.append("\"");

        return hexString.toString();
    }
}
