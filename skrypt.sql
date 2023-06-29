DROP TABLE employee;
CREATE TABLE employee(
                         ID INTEGER NOT NULL PRIMARY KEY
    ,name VARCHAR(11) NOT NULL
    ,JOB VARCHAR(10) NOT NULL);

INSERT INTO employee values (100,'kowalski', 'boss');
INSERT INTO employee values (200,'mnich', 'manager');
INSERT INTO employee values (300,'nowak', 'intern');
INSERT INTO employee values (400,'vader', 'developer');