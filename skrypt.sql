DROP TABLE node;
CREATE TABLE node(
        id INT NOT NULL  PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)
    ,name VARCHAR(11) NOT NULL
    ,JOB VARCHAR(10) NOT NULL);

INSERT INTO node values (100,'kowalski', 'boss');
INSERT INTO node values (200,'mnich', 'manager');
INSERT INTO node values (300,'nowak', 'intern');
INSERT INTO node values (400,'vader', 'developer');