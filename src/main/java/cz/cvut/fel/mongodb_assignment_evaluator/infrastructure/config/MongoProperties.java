package cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.data.mongodb")
@Setter
@Getter
public class MongoProperties {
    private String host;
    private int port;
    private String database;
    private String username;
    private String password;
    private long timeout;
}
