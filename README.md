# Assignment:
Title in English: Automatic evaluation of homework assignments on MongoDB
Title in Czech: Automatické vyhodnocování domácích úkolů z MongoDB
Problem definition:
This project aims to analyze the possibility of automatically assessing homework assignments on MongoDB in the Database Systems 2 course. The source document for the evaluation is a .js script containing queries in MongoDB. The evaluation must detect the presence and correctness of all the constructs required in the assignment, check the execution of the queries, and assign a grade.
Guidelines:
Analyze the script in the source file. Identify the constructs required in the assignment.
Create a document to record the progress of the task check.
If any required constructs are missing, indicate this in the document.
If the script contains errors, describe them in the document.
Execute each query and record the result in the document.
If there were errors or warnings during execution, describe them in the document.
Create a table of results. For each requirement, indicate its presence and correctness.
Literature:
MongoDB. https://www.mongodb.com.
Paul Done. Practical MongoDB Aggregations: The official guide to developing optimal aggregation pipelines with MongoDB 7.0. Packt Publishing, 2023. 312 p.
Kristina Chodorow, Michael Dirolf. MongoDB: The Definitive Guide.

# Introduction
In the Database Systems 2 (DS2) course, students learn to work with four widely used NoSQL databases: Redis, MongoDB, Cassandra, and Neo4j. As part of the coursework, they are tasked to write scripts with queries that utilize each of the aforementioned database systems. These assignments are evaluated by DS2 course instructors, who spend a significant amount of time reviewing each work and identifying a recurring set of similar issues each year.
This project focuses specifically on the MongoDB segment of the course and explores the possibilities for text analysis-based automation. The primary objective is to reduce teachers’ workload by automating the assessment of homework assignments.
The result of this work is an application capable of detecting students’ scripts, parsing queries out of them, assessing these queries against predefined criteria, and generating both individual and overall evaluation reports.
Research:
  Similar work analysis
Parsing methods

# Problem analysis:
 Requirements:
This subchapter covers requirements for the application that were identified from a problem definition and discussions with the Database Systems 2 (DS2) course instructor.
User description:
Teacher - a DS2 course instructor who reviews assignments. The main user of the application.
Business goals:
BG01 - Workload reduction for teachers
As a teacher, I want to spend an adequate amount of time on homework assessments.
BG01 - Creation of the application for automatic assessment of homework assignments on MongoDB in the DS2 course
As a teacher, I want to spend an adequate amount of time detecting the presence and correctness of all the constructs required in the assignment, because it will allow me to give feedback faster and have more time for my other responsibilities.
Business requirements:
BRQ101 - Assessment of JavaScript files containing MongoDB queries
As a teacher, I need an application capable of checking JavaScript files containing MongoDB queries against the criteria required in the assignment.
Functional requirements:
FRQ101 - directory traversal script identification
FRQ101 - Reading scripts containing MongoDB queries
The system will allow the user to read scripts containing MongoDB queries in a JavaScript format.
FRQ102 -  Extraction of MongoDB queries from JavaScript files
The system will allow the user to extract MongoDB queries from JavaScript files.
FRQ103 - Checking MongoDB queries against the required criteria
The system will allow the user to detect the presence and correctness of all the constructs required in the assignment
FRQ104 - Generation of individual evaluation reports
The system will allow the user to generate evaluation reports for each student's work based on criteria fulfillment.
FRQ105 - Generation of individual log files containing warnings and errors
The system will allow the user to generate log files with all warnings and errors met in each student’s work.
FRQ106 - Generation of an overall evaluation report
The system will allow the user to combine all individual evaluation results into an overall evaluation report.
Non-functional requirements:
NRQ01 - Human-readable output in a CSV format
The application will generate a human-readable output in a CSV format.
NRQ02 - Overall evaluation report as a cross-tabulation
The application will generate an overall evaluation report as a cross-tabulation of each student and all required criteria. 
Data
Software architecture
Key submodules and design details
# Solution implementation:

# Testing:

# Conclusion:
This project’s objective was to implement a script evaluation based on text analysis which enabled the generation of assessment reports that quantify how well each student meets the assignment criteria. To analyze scripts containing MongoDB queries a parser was required. Through thorough manual testing and debugging, the custom parser—realized using a Deterministic Finite Automaton (DFA)—has proven effective, though further refinement will enhance its accuracy and efficiency.
The next phase of the project will focus on two critical extensions. First, the integration with a MongoDB server. This would allow for an automated feedback generation by a log collection with a following reformatting process. Second, an SQL database will be introduced to support the application’s internal requirements, such as managing student names, assignment criteria, and previously evaluated results.
With these planned developments, the project is well-positioned to deliver a robust solution for the automated assessment of MongoDB assignments in the Database Systems 2 course.
# Bibliography:
MongoDB. https://www.mongodb.com.
https://www.mongodb.com/docs/drivers/java/sync/v5.2/
https://mongodb.github.io/mongo-java-driver/5.2/apidocs/bson/org/bson/package-summary.html
https://www.mongodb.com/docs/manual/reference/mongodb-extended-json/
Gabriele Tomassetti, Parsing in Java: Tools and Libraries: https://tomassetti.me/parsing-in-java/
Paul Done. Practical MongoDB Aggregations: The official guide to developing optimal aggregation pipelines with MongoDB 7.0. Packt Publishing, 2023. 312 p.
Kristina Chodorow, Michael Dirolf. MongoDB: The Definitive Guide.
https://www.oreilly.com/library/view/software-architecture-patterns/9781491971437/ch01.html