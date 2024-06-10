DROP TABLE IF EXISTS DUMMY;

CREATE TABLE DUMMY
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS SAMPLE_USERS;

CREATE TABLE SAMPLE_USERS
(
    id INTEGER NOT NULL AUTO_INCREMET,
    name VARCHAR(100) NOT NULL,
    gender VARCHAR(100) NOT NULL,
    deleted_yn BOOLEAN NOT NULL,
    PRIMARY KEY (id)
);