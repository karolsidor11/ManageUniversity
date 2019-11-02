CREATE SCHEMA IF NOT EXISTS prod;

CREATE TABLE `student` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `zip_code` varchar(255) DEFAULT NULL,
  `house_number` int(11) DEFAULT NULL,
  `phone_number` int(11) DEFAULT NULL,
  `is_student` BOOLEAN DEFAULT NULL,
  `student_group` double DEFAULT NULL,
  `ratings_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKoo029cejwq8cvor3ye71srmdd` (`ratings_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;


CREATE TABLE STUDENT (
  id           BIGINT(20)   NOT NULL,
  name          VARCHAR(255) NOT NULL,
  last_name     VARCHAR(255) NOT NULL,
  date_of_birth DATE         DEFAULT NULL,
  eamil        VARCHAR(255) NOT NULL,
  city         VARCHAR(255) DEFAULT NULL,
  street        VARCHAR(255) DEFAULT NULL,
  zip_code      INT(11)      DEFAULT NULL,
  house_number  INT(11)      DEFAULT NULL,
  phone_number  INT(11)      NOT NULL,
  is_student   BOOLEAN      DEFAULT FALSE,
  student_group DOUBLE       DEFAULT NULL,
  ratings_id    BIGINT(20),
  PRIMARY KEY (ID)
);

CREATE TABLE LECTURER
(
  ID           BIGINT(255) NOT NULL,
  NAME         VARCHAR(50) NOT NULL,
  LAST_NAME    VARCHAR(50) NOT NULL,
  EMAIL        VARCHAR(50) NOT NULL,
  CITY         VARCHAR(60),
  STREET       VARCHAR(60),
  ZIP_CODE     VARCHAR(60),
  HOUSE_NUMBER INT         NOT NULL,
  PHONE_NUMBER INTEGER(20),
  DEGREE       VARCHAR(20),
  SUBJECT_ID   BIGINT(255),
  PRIMARY KEY (ID)
);

CREATE TABLE SUBJECT
(
  ID          BIGINT(255)  NOT NULL,
  NAME        VARCHAR(255) NOT NULL,
  SUBJECT_ID  BIGINT(255)  NOT NULL,
  ROOM_NUMBER INT(20),
  START_TIME  TIME,
  END_TIME    TIME,
  PRIMARY KEY (ID)
);

CREATE TABLE SCHEDULE
(
  ID            BIGINT(255) NOT NULL,
  DAY_OF_WEEK   VARCHAR(255),
  STUDENT_GROUP DOUBLE      NOT NULL,
  WEEK_NUMBER   INT         NOT NULL,
  SCHEDULE_ID   BIGINT(255) NOT NULL,
  PRIMARY KEY (ID)
);

CREATE TABLE STUDENT_RATINGS_CARD
(
  ID          BIGINT(20) NOT NULL,
  STUDENT_ID  BIGINT(20),
  STUDY_YEAR  INT(20) DEFAULT NULL,
  STUDY_TERM  INT(20) DEFAULT NULL,
  STUDY_GROUP DOUBLE  DEFAULT NULL,
  PRIMARY KEY (ID)
);

CREATE TABLE RATING_SET
(
  ID          BIGINT(255) NOT NULL,
  LECTURER_ID BIGINT(255),
  SUBJECT_ID  BIGINT(255),
  RATING      DOUBLE(20, 0),
  PRIMARY KEY (ID)
);

CREATE TABLE STUDENT_CARD_2_STUDENT_RATING
(
  STUDENT_CARD_ID   BIGINT(255),
  STUDENT_RATING_ID BIGINT(255)
);

CREATE TABLE SCHEDULE_2_SUBJECT
(
  SCHEDULE_ID BIGINT(255),
  SUBJECT_ID  BIGINT(255),
  LECTURER_ID BIGINT(255)
);






