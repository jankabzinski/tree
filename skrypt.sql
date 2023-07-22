drop table if exists node;

create table node
(
    id        serial
        primary key,
    parent_id bigint
                                references node
                                    on delete set null,
    value     integer default 0 not null,
    sum       integer default 0 not null
);


INSERT INTO node (value, sum) VALUES (10, 10);
INSERT INTO node (parent_id, value, sum) VALUES (1, 5, (SELECT COALESCE(SUM(value), 0) + 5 FROM node WHERE id = 1));
INSERT INTO node (parent_id, value, sum) VALUES (2, 7, (SELECT COALESCE(SUM(value), 0) + 7 FROM node WHERE id = 2));
INSERT INTO node (parent_id, value, sum) VALUES (3, 14, (SELECT COALESCE(SUM(value), 0) + 14 FROM node WHERE id = 3));
INSERT INTO node (parent_id, value, sum) VALUES (2, 12, (SELECT COALESCE(SUM(value), 0) + 12 FROM node WHERE id = 2));
INSERT INTO node (parent_id, value, sum) VALUES (4, 132, (SELECT COALESCE(SUM(value), 0) + 132 FROM node WHERE id = 4));
INSERT INTO node (parent_id, value, sum) VALUES (1, 0, (SELECT COALESCE(SUM(value), 0) FROM node WHERE id = 2));
