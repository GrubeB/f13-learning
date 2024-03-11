CREATE TABLE t_user (
   id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   email VARCHAR(255) NOT NULL,
   password VARCHAR(255) NOT NULL,
   CONSTRAINT pk_t_user PRIMARY KEY (id)
);
ALTER TABLE t_user ADD CONSTRAINT uc_user_email UNIQUE (email);

CREATE TABLE t_user_has_role (
   id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   role_id UUID NOT NULL,
   user_id UUID NOT NULL,
   CONSTRAINT pk_t_user_has_role PRIMARY KEY (id)
);

ALTER TABLE t_user_has_role ADD CONSTRAINT FK_T_USER_HAS_ROLE_ON_ROLE
    FOREIGN KEY (role_id) REFERENCES t_role (id);

ALTER TABLE t_user_has_role ADD CONSTRAINT FK_T_USER_HAS_ROLE_ON_USER
    FOREIGN KEY (user_id) REFERENCES t_user (id);