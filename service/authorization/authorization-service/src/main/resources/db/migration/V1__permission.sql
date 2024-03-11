CREATE TABLE t_permission (
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   permission_name VARCHAR(255) NOT NULL,
   id UUID NOT NULL,
   CONSTRAINT pk_t_permission PRIMARY KEY (id)
);

ALTER TABLE t_permission ADD CONSTRAINT uc_permission_permission_name UNIQUE (permission_name);