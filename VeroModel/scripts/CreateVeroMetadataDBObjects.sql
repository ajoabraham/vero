CREATE TABLE V_DATASOURCE (
    ID                     BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    NAME                   VARCHAR(100) UNIQUE,
    DATABASE_VENDOR        VARCHAR(100),
    DATABASE_VERSION       VARCHAR(50),
    HOST_ADDRESS           VARCHAR(100),
    PORT                   INT,
    USER_NAME              VARCHAR(100),
    PASSWORD               VARCHAR(100),
    AUTH_MODE              VARCHAR(100),
    PRE_EXEC_COMMANDS      VARCHAR(500),
    POST_EXEC_COMMANDS     VARCHAR(500),
    DATABASE_NAME          VARCHAR(100)
);

CREATE TABLE V_TABLE (
    ID                     BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    NAME                   VARCHAR(100),
    DESCRIPTION            VARCHAR(500),
    PHYSICAL_NAME          VARCHAR(100),
    TABLE_PREFIX           VARCHAR(25),
    ROW_COUNT              INT,
    TABLE_TYPE             INT,
    DATASOURCE_ID          BIGINT NOT NULL REFERENCES V_DATASOURCE(ID)
);

CREATE TABLE V_COLUMN (
    ID                     BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    NAME                   VARCHAR(100),
    DATA_TYPE              VARCHAR(100),
    DERIVED_DATA_TYPE      VARCHAR(100),
    PRIMARY_KEY            BOOLEAN,
    TABLE_ID               BIGINT NOT NULL REFERENCES V_TABLE(ID)
);

CREATE TABLE V_METRIC (
    ID                     BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    NAME                   VARCHAR(100)
);

CREATE TABLE V_EXPRESSION (
    ID                     BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    EXPRESSION             VARCHAR(500),
    METRIC_ID              BIGINT NOT NULL REFERENCES V_METRIC(ID)
);

CREATE TABLE COLUMNS_EXPRESSIONS (
    COLUMN_ID              BIGINT NOT NULL REFERENCES V_COLUMN(ID),
    EXPRESSION_ID          BIGINT NOT NULL REFERENCES V_EXPRESSION(ID),
    CONSTRAINT COLUMNS_EXPRESSIONS_PK PRIMARY KEY (COLUMN_ID, EXPRESSION_ID)
);

CREATE TABLE V_ATTRIBUTE (
    ID                     BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    NAME                   VARCHAR(100)
);

CREATE TABLE EXPRESSIONS_ATTRIBUTES (
    EXPRESSION_ID          BIGINT NOT NULL REFERENCES V_EXPRESSION(ID),
    ATTRIBUTE_ID           BIGINT NOT NULL REFERENCES V_ATTRIBUTE(ID),
    CONSTRAINT EXPRESSIONS_ATTRIBUTES_PK PRIMARY KEY (EXPRESSION_ID, ATTRIBUTE_ID)
);

CREATE TABLE V_TABLE_JOINT (
    ID                     BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    TABLE_LEFT             VARCHAR(100),
    TABLE_RIGHT            VARCHAR(100),
    JOIN_TYPE              VARCHAR(100),
    JOIN_EXPRESSION        VARCHAR(500)
);

CREATE TABLE V_REPORT (
    ID                     BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    NAME                   VARCHAR(100),
    DATASOURCE_ID          BIGINT NOT NULL REFERENCES V_DATASOURCE(ID)
);

CREATE TABLE V_COMMENT_BLOCK (
    ID                     BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    POSITION               INT NOT NULL,
    COMMENT                LONG VARCHAR,
    REPORT_ID              BIGINT NOT NULL REFERENCES V_REPORT(ID)
);

CREATE TABLE V_QUERY_BLOCK (
    ID                     BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    POSITION               INT NOT NULL,
    NAME                   VARCHAR(100),
    REPORT_ID              BIGINT NOT NULL REFERENCES V_REPORT(ID)
);

CREATE TABLE V_REPORT_BLOCK (
    ID                     BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    NAME                   VARCHAR(100),
    REPORT_ID              BIGINT NOT NULL REFERENCES V_REPORT(ID)
);

CREATE TABLE V_FINAL_BLOCK (
    ID                     BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    POSITION               INT NOT NULL,
    COMMANDS               LONG VARCHAR,
    REPORT_ID              BIGINT NOT NULL REFERENCES V_REPORT(ID)
);