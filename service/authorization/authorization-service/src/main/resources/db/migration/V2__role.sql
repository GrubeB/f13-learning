CREATE TABLE t_role (
   id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   role_name VARCHAR(255),
   CONSTRAINT pk_t_role PRIMARY KEY (id)
);
ALTER TABLE t_role ADD CONSTRAINT uc_role_role_name UNIQUE (role_name);

CREATE TABLE t_role_has_permission (
   id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   permission_id UUID NOT NULL,
   role_id UUID NOT NULL,
   CONSTRAINT pk_t_role_has_permission PRIMARY KEY (id)
);

ALTER TABLE t_role_has_permission ADD CONSTRAINT FK_T_ROLE_HAS_PERMISSION_ON_PERMISSION
    FOREIGN KEY (permission_id) REFERENCES t_permission (id);

ALTER TABLE t_role_has_permission ADD CONSTRAINT FK_T_ROLE_HAS_PERMISSION_ON_ROLE
    FOREIGN KEY (role_id) REFERENCES t_role (id);