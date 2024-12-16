package cz.cvut.fel.mongodb_assignment_evaluator;

import lombok.extern.java.Log;
import org.bson.BsonDocument;
import org.h2.tools.Script;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Log
public class BsonDocumentParserTest {
    @Test
    void testDateGenerationMethod() {
        final String HEX_CHARS = "0123456789abcdef";
        // Secure random generator for better randomness
        SecureRandom random = new SecureRandom();
        StringBuilder hexString = new StringBuilder(24);

        // Loop to generate each character
        hexString.append("\"");
        for (int i = 0; i < 24; i++) {
            int index = random.nextInt(HEX_CHARS.length());
            hexString.append(HEX_CHARS.charAt(index));
        }
        hexString.append("\"");

        String json = "{_id: ObjectId()}";
        json = json.replace("ObjectId()", hexString);
        try {
            BsonDocument.parse(json);
            log.info(json);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    void testDateFormats() {
        String jsonDateInt = "{date: new Date(\"12\")}";
        String jsonDatePlain = "{date: \"2024-10-01\"}";
        String jsonDateRequired = "{date: \"Sun Dec 15 2024 18:30:00 UTC\"}";
        String jsonDateReqMod1 = "{date: \"Dec 15 2024 18:30:00 UTC\"}";
        String jsonDateReqMod2 = "{date: \"Dec 15 2024 18:30:00\"}";

        List<String> dateJsons = new ArrayList<>(List.of(
                jsonDateInt,
                jsonDatePlain,
                jsonDateRequired,
                jsonDateReqMod1,
                jsonDateReqMod2
        ));

        for (String json : dateJsons) {
            try {
                BsonDocument.parse(json);
                log.info(json);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
