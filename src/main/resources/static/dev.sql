CREATE SCHEMA IF NOT EXISTS dev;

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE `lecturer` (
  `id` bigint(20) NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `house_number` int(11) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `zip_code` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `degree` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone_number` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE `lecturer_subject` (
  `lecturer_id` bigint(20) NOT NULL,
  `subject_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_e5g0bx827y3w9aryrjkhvaelm` (`subject_id`),
  KEY `FKdm1ggjoj2ltt68giwrv46k6ci` (`lecturer_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE `rating_set` (
  `id` bigint(20) NOT NULL,
  `rating` double NOT NULL,
  `lecturer_id` bigint(20) DEFAULT NULL,
  `subject_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1qyd8twosnq964i5v06ov35oe` (`lecturer_id`),
  KEY `FK1kk1a294e15x06dxak247tg84` (`subject_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE `schedule` (
  `id` bigint(20) NOT NULL,
  `day_of_week` varchar(255) NOT NULL,
  `student_group` double DEFAULT NULL,
  `week_number` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE `schedule_2_subject` (
  `subject_id` bigint(20) NOT NULL,
  `lecturer_id` bigint(20) NOT NULL,
  `schedule_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_lwv37qbl50wx6ny7kb8abyspu` (`lecturer_id`),
  UNIQUE KEY `UK_38bcitoy5kou26k9ofb8mueqh` (`subject_id`),
  KEY `FK7l9ia6gt33fpyk1xbxp4jw8h0` (`schedule_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE `student` (
  `id` bigint(20) NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `house_number` int(11) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `zip_code` varchar(255) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_student` tinyint(1) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone_number` int(11) NOT NULL,
  `student_group` double DEFAULT NULL,
  `ratings_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKoo029cejwq8cvor3ye71srmdd` (`ratings_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE `student_card_2_student_rating` (
  `student_card_id` bigint(20) NOT NULL,
  `student_rating_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_s9w8ju7chcsnrc225fdv3kb8q` (`student_rating_id`),
  KEY `FKhw4fr67oytsdk9i5xjcfb2u75` (`student_card_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE `student_ratings_card` (
  `id` bigint(20) NOT NULL,
  `study_group` double DEFAULT NULL,
  `study_term` int(11) DEFAULT NULL,
  `study_year` int(11) DEFAULT NULL,
  `student_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdfqthxvu0fc1knqdoo9vi2xis` (`student_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE `subject` (
  `id` bigint(20) NOT NULL,
  `end_time` time DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `room_number` int(11) NOT NULL,
  `start_time` time DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;




