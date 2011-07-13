CREATE DATABASE tracer
  WITH OWNER = traceradmin
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'English_United States.1252'
       LC_CTYPE = 'English_United States.1252'
       CONNECTION LIMIT = -1;


CREATE SEQUENCE ticketid_sequence
  INCREMENT 1
  MINVALUE 0
  MAXVALUE 9223372036854775807
  START 15
  CACHE 1;
COMMENT ON SEQUENCE ticketid_sequence IS 'For ID Incrementing';

CREATE TABLE comments
(
  username character varying NOT NULL,
  "timestamp" character varying NOT NULL,
  "comment" text,
  ticketid integer
)
WITH (
  OIDS=FALSE
);

CREATE TABLE config
(
  "key" character varying(15) NOT NULL,
  "value" character varying(25),
  "Config Id" serial NOT NULL,
  CONSTRAINT pk_config_pri PRIMARY KEY ("Config Id")
)
WITH (
  OIDS=FALSE
);

CREATE TABLE defectdetails
(
  id integer NOT NULL,
  shortdesc character varying(100),
  description text,
  priority character varying(10),
  status character varying(20),
  reporter character varying(30),
  "owner" character varying(30),
  related character varying(30),
  component character varying(30),
  milestone character varying(30),
  "type" character varying(10) DEFAULT 'defect'::character varying,
  comments text,
  importance character varying(10),
  progress integer,
  CONSTRAINT primarykey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);


CREATE TABLE requirementdetails
(
  id integer NOT NULL,
  shortdesc character varying(100),
  description text,
  priority character varying(10),
  status character varying(20),
  reporter character varying(30),
  "owner" character varying(30),
  related character varying(10),
  component character varying(30),
  milestone character varying(30),
  comments text,
  "type" character varying(12) DEFAULT 'requirement'::character varying,
  importance character varying(10),
  progress integer,
  storypoint integer,
  CONSTRAINT "primary key" PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE taskdetails
(
  id integer NOT NULL,
  shortdesc character varying(100),
  description text,
  priority character varying(10),
  status character varying(20),
  reporter character varying,
  "owner" character varying,
  related character varying(10),
  component character varying(30),
  milestone character varying(30),
  comments text,
  "type" character varying(6) DEFAULT 'task'::character varying,
  importance character varying(10),
  progress integer,
  CONSTRAINT "primaryKey_task" PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE userdetails
(
  username character varying(10) NOT NULL,
  "password" character varying(10),
  email character varying(35),
  CONSTRAINT pk_pri PRIMARY KEY (username)
)
WITH (
  OIDS=FALSE
);