CREATE SEQUENCE user_id_seq START WITH 1;

CREATE TABLE user_table
(
    id            bigint       NOT NULL DEFAULT nextval('user_id_seq'),
    name          VARCHAR(255) NOT NULL,
    email         VARCHAR(255) NOT NULL UNIQUE,
    password      VARCHAR(255) NOT NULL,
    department_id VARCHAR(255),
    role          VARCHAR(255) NOT NULL,

    PRIMARY KEY (id)
);