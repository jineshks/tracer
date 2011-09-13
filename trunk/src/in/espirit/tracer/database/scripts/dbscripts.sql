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
DROP TABLE t_mailtemplates;
DROP TABLE t_message;

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
  f_position integer,
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
  f_position integer,
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
  f_position integer,
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

CREATE TABLE t_mailtemplates
(
  f_name character varying(25),
  f_subject character varying(50),
  f_body text,
  CONSTRAINT pk_template PRIMARY KEY (f_name)
);

CREATE TABLE t_message
(
  f_id serial NOT NULL,
  f_from character varying(35),
  f_to character varying(250),
  f_cc character varying(250),
  f_subject character varying(250),
  f_sentdate timestamp,
  f_important integer,
  f_notify integer,
  f_message text,
  f_show character varying(500),
  f_tags character varying(100),
  CONSTRAINT pk_message PRIMARY KEY (f_id)
);

INSERT into t_userdetails(f_userName, f_password, f_displayName, f_approvalStatus, f_role) VALUES('admin','admin','Administrator',1,3)

INSERT into t_mailtemplates (f_name, f_subject, f_body) VALUES ('ticket-edit','Tracer - Ticket is updated','The <type> ticket <a href=\'<applicationhome>/<type>/<id>\'>#<id></a> related to you has been updated by <updater>. <br><br>Project Team<br>------------------------------------<br> Automated mail from tracer application.');
INSERT into t_mailtemplates (f_name, f_subject, f_body) VALUES ('ticket-new','Tracer - Ticket is created','A <type> ticket <a href=\'<applicationhome>/<type>/<id>\'>#<id></a> has been created by <updater>. You are listed as either owner or reporter. <br><br>Project Team<br>------------------------------------<br> Automated mail from tracer application.');
INSERT into t_mailtemplates (f_name, f_subject, f_body) VALUES ('user-new','Tracer - New User Approval','<displayname> has created the user sign up form. User id is <a href=\'<applicationhome>/user/<userid>\'><userid></a>. Kindly approve or reject the same. <br><br>Project Team<br>------------------------------------<br> Automated mail from tracer application.');
INSERT into t_mailtemplates (f_name, f_subject, f_body) VALUES ('user-approve','Tracer - User Application is Approved','Your user application is approved and you can login into <a href=\'<applicationhome>\'>tracer</a> now. Please login and edit the user profile settings. <br><br>Project Team<br>------------------------------------<br> Automated mail from tracer application.');
INSERT into t_mailtemplates (f_name, f_subject, f_body) VALUES ('user-reject','Tracer - User Application is Rejected','Your user application is rejected. If you are interested please fill the application form with appropriate details in the <a href=\'<applicationhome>\'>tracer</a> application. You are welcome to sign up with the same user id - <userid>. <br><br>Project Team<br>------------------------------------<br> Automated mail from tracer application.');
INSERT into t_mailtemplates (f_name, f_subject, f_body) VALUES ('message-new','Tracer - New Message','You have received a new message in <a href=\'<applicationhome>\'>tracer</a> application from the user - <from>.Message content is shown below.<br><br><p><message></p><br><br>Project Team<br>------------------------------------<br> Automated mail from tracer application.');

INSERT INTO t_milestone (f_name, f_description) VALUES ('Incoming','For the newly created tickets. It is default for the newly created tickets')
INSERT INTO t_milestone (f_name, f_description) VALUES ('Backlog','For the backlog tickets')

