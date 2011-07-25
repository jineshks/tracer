CREATE DATABASE tracer
  WITH OWNER = traceradmin;

  
DROP TABLE t_comments;
DROP TABLE t_config;
DROP TABLE t_defectdetails;
DROP TABLE t_requirementdetails;
DROP TABLE t_taskdetails;
DROP TABLE t_userdetails;
DROP TABLE t_attachments;


CREATE SEQUENCE sequence_ticketid
  INCREMENT 1
  MINVALUE 0
  MAXVALUE 9223372036854775807
  START 0
  CACHE 1;
COMMENT ON SEQUENCE sequence_ticketid IS 'For ID Incrementing';

CREATE TABLE t_comments
(
  f_username character varying(35),
  f_timestamp character varying(25),
  f_comment text,
  f_ticketid integer
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
  f_title character varying,
  f_description text,
  f_priority character varying(30),
  f_status character varying(30),
  f_reporter character varying(35),
  f_owner character varying(35),
  f_parentticket integer,
  f_component character varying(30),
  f_milestone character varying(30),
  f_type character varying(6) DEFAULT 'defect'::character varying,
  f_importance character varying(30),
  f_progress integer,
  f_tags character varying,
  f_phase character varying(30),
  CONSTRAINT pk_defect PRIMARY KEY (f_id)
);


CREATE TABLE t_requirementdetails
(
  f_id integer NOT NULL,
  f_title character varying,
  f_description text,
  f_priority character varying(30),
  f_status character varying(30),
  f_reporter character varying(35),
  f_owner character varying(35),
  f_parentticket integer,
  f_component character varying(30),
  f_milestone character varying(30),
  f_type character varying(11) DEFAULT 'requirement'::character varying,
  f_importance character varying(30),
  f_progress integer,
  f_storypoint integer,
  f_tags character varying,
  f_phase character varying(30),
  CONSTRAINT pk_requirement PRIMARY KEY (f_id)
);

CREATE TABLE t_taskdetails
(
  f_id integer NOT NULL,
  f_title character varying,
  f_description text,
  f_priority character varying(30),
  f_status character varying(30),
  f_reporter character varying(35),
  f_owner character varying(35),
  f_parentticket integer,
  f_component character varying(30),
  f_milestone character varying(30),
  f_type character varying(4) DEFAULT 'task'::character varying,
  f_importance character varying(30),
  f_progress integer,
  f_tags character varying,
  f_phase character varying(30),
  CONSTRAINT pk_task PRIMARY KEY (f_id)
);

CREATE TABLE t_userdetails
(
  f_username character varying(35) NOT NULL,
  f_password character varying(15),
  f_email character varying(35),
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
  f_description character varying,
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
