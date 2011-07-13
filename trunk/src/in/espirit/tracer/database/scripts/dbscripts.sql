CREATE DATABASE tracer
 WITH OWNER = postgres
      ENCODING = 'UTF8'
      TABLESPACE = pg_default
      LC_COLLATE = 'English_United States.1252'
      LC_CTYPE = 'English_United States.1252'
      CONNECTION LIMIT = -1;


CREATE TABLE config
(
 "key" character varying(15) NOT NULL,
 "value" character varying(25),
 "Config Id" serial NOT NULL,
 CONSTRAINT pk_config_pri PRIMARY KEY ("Config Id")
);



CREATE TABLE defectdetails
(
 id serial NOT NULL,
 shortdesc character varying(100),
 description text,
 comments text,
 importance character varying(10),
 priority character varying(10),
 status character varying(20),
 reporter character varying(10),
 "owner" character varying(10),
 related character varying(10),
 component character varying(30),
 milestone character varying(30),
 "type" character varying(10) DEFAULT 'defect'::character varying,
 progress integer,
 CONSTRAINT pk_id PRIMARY KEY (id)
);


CREATE TABLE taskdetails
(
 id serial NOT NULL,
 shortdesc character varying(100),
 description text,
 comments text,
 importance character varying(10),
 priority character varying(10),
 status character varying(20),
 reporter character varying(10),
 "owner" character varying(10),
 related character varying(10),
 component character varying(30),
 milestone character varying(30),
  "type" character varying(6) DEFAULT 'task'::character varying,
 progress integer,
 CONSTRAINT pk_task_id PRIMARY KEY (id)
);

CREATE TABLE requirementdetails
(
 id serial NOT NULL,
 shortdesc character varying(100),
 description text,
 priority character varying(10),
 status character varying(20),
 reporter character varying(10),
 "owner" character varying(10),
 related character varying(10),
 component character varying(30),
 milestone character varying(30),
 comments text,
 "type" character varying(12) DEFAULT 'requirement'::character varying,
 importance character varying(10),
 storypoint integer,
 progress integer,
 CONSTRAINT pk_req_id PRIMARY KEY (id)
);

CREATE TABLE userdetails
(
 username character varying(10) NOT NULL,
 "password" character varying(10),
 email character varying(35),
 CONSTRAINT pk_pri PRIMARY KEY (username)
);

CREATE DATABASE tracer
 WITH OWNER = postgres
      ENCODING = 'UTF8'
      TABLESPACE = pg_default
      LC_COLLATE = 'English_United States.1252'
      LC_CTYPE = 'English_United States.1252'
      CONNECTION LIMIT = -1;


CREATE TABLE config
(
 "key" character varying(15) NOT NULL,
 "value" character varying(25),
 "Config Id" serial NOT NULL,
 CONSTRAINT pk_config_pri PRIMARY KEY ("Config Id")
);



CREATE TABLE defectdetails
(
 id serial NOT NULL,
 shortdesc character varying(100),
 description text,
 comments text,
 importance character varying(10),
 priority character varying(10),
 status character varying(20),
 reporter character varying(10),
 "owner" character varying(10),
 related character varying(10),
 component character varying(30),
 milestone character varying(30),
 "type" character varying(10) DEFAULT 'defect'::character varying,
 progress integer,
 CONSTRAINT pk_id PRIMARY KEY (id)
);


CREATE TABLE taskdetails
(
 id serial NOT NULL,
 shortdesc character varying(100),
 description text,
 comments text,
 importance character varying(10),
 priority character varying(10),
 status character varying(20),
 reporter character varying(10),
 "owner" character varying(10),
 related character varying(10),
 component character varying(30),
 milestone character varying(30),
  "type" character varying(6) DEFAULT 'task'::character varying,
 progress integer,
 CONSTRAINT pk_task_id PRIMARY KEY (id)
);

CREATE TABLE requirementdetails
(
 id serial NOT NULL,
 shortdesc character varying(100),
 description text,
 priority character varying(10),
 status character varying(20),
 reporter character varying(10),
 "owner" character varying(10),
 related character varying(10),
 component character varying(30),
 milestone character varying(30),
 comments text,
 "type" character varying(12) DEFAULT 'requirement'::character varying,
 importance character varying(10),
 storypoint integer,
 progress integer,
 CONSTRAINT pk_req_id PRIMARY KEY (id)
);

CREATE TABLE userdetails
(
 username character varying(10) NOT NULL,
 "password" character varying(10),
 email character varying(35),
 CONSTRAINT pk_pri PRIMARY KEY (username)
);

CREATE SEQUENCE ticketid_sequence
INCREMENT 1
MINVALUE 0
MAXVALUE 9223372036854775807
START 11
CACHE 1;
ALTER TABLE ticketid_sequence OWNER TO postgres;
COMMENT ON SEQUENCE ticketid_sequence IS 'For ID Incrementing';
