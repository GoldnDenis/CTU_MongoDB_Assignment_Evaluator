package cz.cvut.fel.mongodb_assignment_evaluator.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerSingleton {
    private static Logger instance;

    private LoggerSingleton() {
    }

    public static Logger getInstance(Class<?> clazz) {
        synchronized (Logger.class) {
            if (instance == null) {
                instance = LoggerFactory.getLogger(clazz);
            }
        }
        return instance;
    }
}
