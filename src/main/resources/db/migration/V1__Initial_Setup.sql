CREATE TABLE customer
(
    customerId SERIAL PRIMARY KEY,
    name       VARCHAR NOT NULL,
    email      VARCHAR NOT NULL,
    age        INT  NOT NULL
);