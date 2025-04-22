package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.execution;

import java.io.IOException;

public class MongoScriptRunner {
    public void run(String script) {
//        try {
//            // Try mongosh first, fallback to mongo
//            String[] commands = {
//                    "mongosh", scriptPath
//            };
//
//            ProcessBuilder processBuilder = new ProcessBuilder(commands);
//            processBuilder.redirectErrorStream(true);
//            Process process = processBuilder.start();
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line;
//
//            System.out.println("=== MongoDB Script Output ===");
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }
//
//            int exitCode = process.waitFor();
//            System.out.println("=== Script execution finished with exit code: " + exitCode + " ===");
//
//        } catch (IOException e) {
//            System.out.println("Failed to execute script: " + e.getMessage());
//        } catch (InterruptedException e) {
//            System.out.println("Execution interrupted: " + e.getMessage());
//            Thread.currentThread().interrupt();
//        }
    }
}
