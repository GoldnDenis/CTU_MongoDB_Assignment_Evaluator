# Automatic Evaluation of
Homework Assignments on
MongoDB

This project is designed to provide maximum assistance and reduce the assessment time for MongoDB homework submissions in the Database Systems 2 course. It involves parsing student submissions, grading queries based on predefined criteria, executing each query via the Mongo Shell, and generating evaluation logs and reports.

The application is implemented in Java 21 and uses a PostgreSQL database to load grading criteria and store evaluation data.

The program has been tested on 64 student submissions to ensure its reliability and accuracy.

# Run the evaluation tool
To use the evaluation tool, the following steps must be completed:
* Since the application requires access to both PostgreSQL and MongoDB (via the Mongo Shell), credentials must be obtained from the course instructor.
* Once the credentials are acquired, replace the placeholder values for database connectivity in the ./config/application.properties file. Optionally, adjust other configuration settings as needed.
By default, the project includes two SQL scripts: one for initializing the database schema and another for inserting the grading criteria.
* Ensure that all submission folders within the scripts directory (default: ./scripts) are named according to the following pattern:
```f<YY>-<username>-mongodb-<YYYYMMDD>-<HHMMss>```
Example: f24_student123-mongodb-20241115-142922

Once these steps are completed, you can run the evaluation tool with the following command:

```java -jar evaluator.jar```