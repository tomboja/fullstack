CREATE TABLE customer
(
    customerId BIGSERIAL PRIMARY KEY,
    name       VARCHAR NOT NULL,
    email      VARCHAR NOT NULL,
    age        INT  NOT NULL
);