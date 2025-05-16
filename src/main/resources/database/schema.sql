CREATE TABLE IF NOT EXISTS CriterionGroup
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS Criterion
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255) UNIQUE NOT NULL,
    description VARCHAR(664)        NOT NULL,
    min_score   INT DEFAULT 1       NOT NULL,
    priority    INT UNIQUE          NOT NULL,
    group_id    INT,
    FOREIGN KEY (group_id) REFERENCES CriterionGroup (id) ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS Topic
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS Student
(
    id          SERIAL PRIMARY KEY,
    username    VARCHAR(100) NOT NULL,
    study_year  INT          NOT NULL,
    classNumber INT,
    topic_id    INT,
    FOREIGN KEY (topic_id) REFERENCES Topic (id) ON UPDATE CASCADE ON DELETE SET NULL,
    UNIQUE (username, study_year)
);

CREATE TABLE IF NOT EXISTS Submission
(
    id          SERIAL PRIMARY KEY,
    upload_date TIMESTAMP NOT NULL,
    student_id  INT,
    FOREIGN KEY (student_id) REFERENCES Student (id) ON UPDATE CASCADE ON DELETE SET NULL,
    UNIQUE (upload_date, student_id)
);

CREATE TABLE IF NOT EXISTS Query
(
    id            SERIAL PRIMARY KEY,
    query         TEXT NOT NULL,
    submission_id INT,
    FOREIGN KEY (submission_id) REFERENCES Submission (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Log
(
    id       SERIAL PRIMARY KEY,
    output   TEXT NOT NULL,
    query_id INT,
    FOREIGN KEY (query_id) REFERENCES Query (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS SubmissionResult
(
    criterion_id  INT,
    submission_id INT,
    score         INT DEFAULT 0,
    PRIMARY KEY (criterion_id, submission_id),
    FOREIGN KEY (criterion_id) REFERENCES Criterion (id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (submission_id) REFERENCES Submission (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS FulfilledQueries
(
    criterion_id INT,
    query_id     INT,
    PRIMARY KEY (criterion_id, query_id),
    FOREIGN KEY (criterion_id) REFERENCES Criterion (id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (query_id) REFERENCES Query (id) ON UPDATE CASCADE ON DELETE CASCADE
);