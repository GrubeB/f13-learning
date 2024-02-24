CREATE TABLE t_organization (
   id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   name VARCHAR(255) NOT NULL,
   CONSTRAINT pk_t_organization PRIMARY KEY (id)
);