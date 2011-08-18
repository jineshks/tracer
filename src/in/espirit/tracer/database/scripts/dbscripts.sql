CREATE DATABASE tracer
  WITH OWNER = traceradmin;

DROP TABLE t_sequence;
DROP TABLE t_comments;
DROP TABLE t_alert;
DROP TABLE t_config;
DROP TABLE t_defectdetails;
DROP TABLE t_requirementdetails;
DROP TABLE t_taskdetails;
DROP TABLE t_userdetails;
DROP TABLE t_milestone;
DROP TABLE t_attachments;
DROP TABLE t_descriptionhistory;
DROP TABLE t_burndowndata;
DROP TABLE t_link;

CREATE TABLE t_sequence
(
  f_name character varying(10),
  f_id Integer
);


CREATE TABLE t_comments
(
  f_username character varying(35),
  f_timestamp character varying(25),
  f_comment text,
  f_ticketid integer
);


CREATE TABLE t_alert
(
  f_id serial NOT NULL,
  f_name character varying(250),
  f_desc character varying(500),
  f_startDateTime timestamp,
  f_endDateTime timestamp,
  CONSTRAINT pk_alert PRIMARY KEY (f_id)
);


CREATE TABLE t_config
(
  f_key character varying(30) NOT NULL,
  f_value character varying(30),
  f_id serial NOT NULL,
  CONSTRAINT pk_config PRIMARY KEY (f_id)
);


CREATE TABLE t_defectdetails
(
  f_id integer NOT NULL,
  f_title character varying(250),
  f_description text,
  f_priority character varying(30),
  f_status character varying(30),
  f_reporter character varying(35),
  f_owner character varying(35),
  f_parentticket integer,
  f_component character varying(30),
  f_milestone character varying(30),
  f_type character varying(6) DEFAULT 'defect',
  f_importance character varying(30),
  f_progress integer,
  f_tags character varying(250),
  f_phase character varying(30),
  CONSTRAINT pk_defect PRIMARY KEY (f_id)
);


CREATE TABLE t_requirementdetails
(
  f_id integer NOT NULL,
  f_title character varying(250),
  f_description text,
  f_priority character varying(30),
  f_status character varying(30),
  f_reporter character varying(35),
  f_owner character varying(35),
  f_parentticket integer,
  f_component character varying(30),
  f_milestone character varying(30),
  f_type character varying(11) DEFAULT 'requirement',
  f_importance character varying(30),
  f_progress integer,
  f_storypoint integer,
  f_tags character varying(250),
  f_phase character varying(30),
  CONSTRAINT pk_requirement PRIMARY KEY (f_id)
);


CREATE TABLE t_taskdetails
(
  f_id integer NOT NULL,
  f_title character varying(250),
  f_description text,
  f_priority character varying(30),
  f_status character varying(30),
  f_reporter character varying(35),
  f_owner character varying(35),
  f_parentticket integer,
  f_component character varying(30),
  f_milestone character varying(30),
  f_type character varying(4) DEFAULT 'task',
  f_importance character varying(30),
  f_progress integer,
  f_tags character varying(250),
  f_phase character varying(30),
  CONSTRAINT pk_task PRIMARY KEY (f_id)
);


CREATE TABLE t_userdetails
(
  f_username character varying(35) NOT NULL,
  f_password character varying(15),
  f_email character varying(35),
  f_displayname character varying(35),
  f_emailsecond character varying(35),
  f_phone character varying(20),
  f_chatid character varying(20),
  f_web character varying(35),
  f_status character varying(200),
  f_skills character varying(100),
  f_passion character varying(200),
  f_team character varying(100),
  f_whoami character varying(200),
  f_approvalstatus integer DEFAULT 0,
  f_role integer,
  CONSTRAINT pk_user PRIMARY KEY (f_username)
);


CREATE TABLE t_activity
(
  f_id serial NOT NULL,
  f_username character varying(35),
  f_timestamp character varying(25),
  f_activity text,
  CONSTRAINT pk_activity PRIMARY KEY (f_id)
);


CREATE TABLE t_milestone
(
  f_id serial NOT NULL,
  f_name character varying(35),
  f_description character varying(100),
  f_startdate character varying(30),
  f_enddate character varying(30),
  f_current boolean,
  CONSTRAINT pk_milestone PRIMARY KEY (f_id)
);


CREATE TABLE t_attachments
(
  f_username character varying(35),
  f_timestamp character varying(25),
  f_name text,
  f_ticketid integer
);

CREATE TABLE t_link
(
  f_id serial NOT NULL,
  f_name character varying(250),
  f_desc character varying(500),
  f_target character varying(250),
  f_teamvisible integer,
  f_tags character varying(250),
  f_username character varying(35),
  f_position serial,
  CONSTRAINT pk_link PRIMARY KEY (f_id)
)

CREATE TABLE t_descriptionhistory
(
  f_username character varying(35),
  f_timestamp character varying(25),
  f_desc text,
  f_ticketid integer
);

CREATE TABLE t_burndowndata
(
  f_timestamp timestamp,
  f_milestone character varying(25),
  f_progress integer
);

INSERT into t_userdetails(f_userName, f_password, f_displayName, f_approvalStatus, f_role) VALUES('admin','admin','Administrator',1,3)

