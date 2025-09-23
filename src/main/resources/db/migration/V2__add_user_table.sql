CREATE SEQUENCE IF NOT EXISTS users_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE users
(
    user_id  BIGINT       NOT NULL,
    username VARCHAR(255) NOT NULL,
    email    VARCHAR(255),
    password VARCHAR(100),
    role     VARCHAR(255),
    CONSTRAINT pk_users PRIMARY KEY (user_id)
);