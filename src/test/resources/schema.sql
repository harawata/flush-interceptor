-- create table
CREATE TABLE Customer (
    Id        NUMBER NOT NULL,
    Name      VARCHAR2(64 CHAR) NOT NULL,
    Version   NUMBER NOT NULL
);

-- index pro PK
CREATE INDEX IDX_ID
    ON Customer(Id);

-- constraint PK
ALTER TABLE Customer
    ADD CONSTRAINT PK_ID
        PRIMARY KEY (Id);