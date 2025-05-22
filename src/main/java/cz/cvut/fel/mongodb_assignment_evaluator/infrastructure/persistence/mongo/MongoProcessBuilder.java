package cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.persistence.mongo;

import cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.config.MongoProperties;

import java.io.IOException;
import java.util.List;

/**
 * Creates a process of mongo shell and connects it to the database by using the data from configurations - 'application.properties'
 */
public class MongoProcessBuilder {
    private final MongoProperties properties;
    private final ProcessBuilder processBuilder;

    public MongoProcessBuilder(MongoProperties mongoProperties, boolean redirectErrorStream) {
        this.properties = mongoProperties;
        this.processBuilder = new ProcessBuilder(List.of(
                "mongosh",
                "mongodb://" + mongoProperties.getUsername() + ":" + mongoProperties.getPassword() + "@" +
                        mongoProperties.getHost() + ":" + mongoProperties.getPort() + "/" + mongoProperties.getDatabase()
        ));
        processBuilder.redirectErrorStream(redirectErrorStream);
    }

    public Process start() throws IOException {
        return processBuilder.start();
    }
}
