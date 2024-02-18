DROP TABLE IF EXISTS t_topic CASCADE;
CREATE TABLE t_topic (
  id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   name VARCHAR(255),
   content VARCHAR(255),
   category VARCHAR(255),
   CONSTRAINT pk_t_topic PRIMARY KEY (id)
);