CREATE TABLE SCHEMA_PROJECT (
    ID                     VARCHAR(36) NOT NULL PRIMARY KEY,
    NAME                   VARCHAR(100) NOT NULL UNIQUE,
    DESCRIPTION            VARCHAR(250),
    LAST_MOD_TS            TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CREATION_TS            TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE SCHEMA_DATABASE (
    ID                     VARCHAR(36) NOT NULL PRIMARY KEY,
    DATABASE_VENDOR        VARCHAR(100),
    DATABASE_VERSION       VARCHAR(50),
    DATABASE_TYPE		   INT,
    HOST_ADDRESS           VARCHAR(100),
    PORT                   INT,
    USER_NAME              VARCHAR(100),
    PASSWORD               VARCHAR(100),
    AUTH_MODE              VARCHAR(100),
    PRE_EXEC_COMMANDS      VARCHAR(500),
    POST_EXEC_COMMANDS     VARCHAR(500),
    DATABASE_NAME          VARCHAR(100),
    LAST_MOD_TS            TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CREATION_TS            TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE SCHEMA_DATASOURCE (
    ID                     VARCHAR(36) NOT NULL PRIMARY KEY,
    NAME                   VARCHAR(100) NOT NULL UNIQUE,
    DESCRIPTION            VARCHAR(250),
    SCHEMA_DATABASE_ID     VARCHAR(36) NOT NULL REFERENCES SCHEMA_DATABASE(ID),
    SCHEMA_PROJECT_ID      VARCHAR(36) NOT NULL REFERENCES SCHEMA_PROJECT(ID),
    LAST_MOD_TS            TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CREATION_TS            TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE SCHEMA_TABLE (
    ID                     VARCHAR(36) NOT NULL PRIMARY KEY,
    NAME                   VARCHAR(100),
    DESCRIPTION            VARCHAR(250),
    PHYSICAL_NAME          VARCHAR(100),
    ROW_COUNT              INT,
    TABLE_TYPE             INT,
    SCHEMA_DATASOURCE_ID   VARCHAR(36) NOT NULL REFERENCES SCHEMA_DATASOURCE(ID),
    LAST_MOD_TS            TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CREATION_TS            TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE SCHEMA_COLUMN (
    ID                     VARCHAR(36) NOT NULL PRIMARY KEY,
    NAME                   VARCHAR(100),
    DATA_TYPE              VARCHAR(100),
    DERIVED_DATA_TYPE      VARCHAR(100),
    KEY_TYPE               INT,
    SCHEMA_TABLE_ID        VARCHAR(36) NOT NULL REFERENCES SCHEMA_TABLE(ID),
   	LAST_MOD_TS            TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CREATION_TS            TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE SCHEMA_METRIC (
    ID                     VARCHAR(36) NOT NULL PRIMARY KEY,
    NAME                   VARCHAR(100),
   	LAST_MOD_TS            TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CREATION_TS            TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE SCHEMA_ATTRIBUTE (
    ID                     VARCHAR(36) NOT NULL PRIMARY KEY,
    NAME                   VARCHAR(100),
    LAST_MOD_TS            TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CREATION_TS            TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE SCHEMA_EXPRESSION (
    ID                     VARCHAR(36) NOT NULL PRIMARY KEY,
    EXPRESSION             VARCHAR(500),
    SCHEMA_METRIC_ID       VARCHAR(36) REFERENCES SCHEMA_METRIC(ID),
    SCHEMA_ATTRIBUTE_ID    VARCHAR(36) REFERENCES SCHEMA_ATTRIBUTE(ID),
   	LAST_MOD_TS            TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CREATION_TS            TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE COLUMNS_EXPRESSIONS (
    SCHEMA_COLUMN_ID              VARCHAR(36) NOT NULL REFERENCES SCHEMA_COLUMN(ID),
    SCHEMA_EXPRESSION_ID          VARCHAR(36) NOT NULL REFERENCES SCHEMA_EXPRESSION(ID),
    CONSTRAINT COLUMNS_EXPRESSIONS_PK PRIMARY KEY (SCHEMA_COLUMN_ID, SCHEMA_EXPRESSION_ID)
);

CREATE TABLE SCHEMA_TABLE_JOINT (
    ID                     VARCHAR(36) NOT NULL PRIMARY KEY,
    TABLE_LEFT             VARCHAR(100),
    TABLE_RIGHT            VARCHAR(100),
    JOIN_TYPE              INT,
    JOIN_EXPRESSION        VARCHAR(500),
    LAST_MOD_TS            TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CREATION_TS            TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE SCHEMA_REPORT (
    ID                     VARCHAR(36) NOT NULL PRIMARY KEY,
    NAME                   VARCHAR(100),
    SCHEMA_PROJECT_ID      VARCHAR(36) NOT NULL REFERENCES SCHEMA_PROJECT(ID),
    LAST_MOD_TS            TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CREATION_TS            TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE SCHEMA_COMMENT_BLOCK (
    ID                     VARCHAR(36) NOT NULL PRIMARY KEY,
    POSITION               INT NOT NULL,
    COMMENT                LONG VARCHAR,
    SCHEMA_REPORT_ID       VARCHAR(36) NOT NULL REFERENCES SCHEMA_REPORT(ID),
   	LAST_MOD_TS            TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CREATION_TS            TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE SCHEMA_QUERY_BLOCK (
    ID                     VARCHAR(36) NOT NULL PRIMARY KEY,
    POSITION               INT NOT NULL,
    NAME                   VARCHAR(100),
    METADATA               LONG VARCHAR,
    SCHEMA_REPORT_ID       VARCHAR(36) NOT NULL REFERENCES SCHEMA_REPORT(ID),
    LAST_MOD_TS            TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CREATION_TS            TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE SCHEMA_REPORT_BLOCK (
    ID                     VARCHAR(36) NOT NULL PRIMARY KEY,
    NAME                   VARCHAR(100),
    SCHEMA_REPORT_ID       VARCHAR(36) NOT NULL REFERENCES SCHEMA_REPORT(ID),
    METADATA               LONG VARCHAR,
    LAST_MOD_TS            TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CREATION_TS            TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE SCHEMA_FINAL_BLOCK (
    ID                     VARCHAR(36) NOT NULL PRIMARY KEY,
    POSITION               INT NOT NULL,
    COMMANDS               LONG VARCHAR,
    SCHEMA_REPORT_ID       VARCHAR(36) NOT NULL REFERENCES SCHEMA_REPORT(ID),
    LAST_MOD_TS            TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CREATION_TS            TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE VIEW SEARCHABLE_TEXT AS
	SELECT ID, NAME, 'DATASOURCE' AS OBJECT_TYPE
	FROM SCHEMA_DATASOURCE
	UNION
	SELECT ID, NAME, 'TABLE' AS OBJECT_TYPE
	FROM SCHEMA_TABLE
	UNION
	SELECT ID, NAME, 'COLUMN' AS OBJECT_TYPE
	FROM SCHEMA_COLUMN
	UNION
	SELECT ID, NAME, 'ATTRIBUTE' AS OBJECT_TYPE
	FROM SCHEMA_ATTRIBUTE
	UNION
	SELECT ID, NAME, 'METRIC' AS OBJECT_TYPE
	FROM SCHEMA_METRIC;