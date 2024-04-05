CREATE TABLE t_comment_container (
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   id UUID NOT NULL,
   domain_object_id UUID NOT NULL,
   domain_object_type SMALLINT NOT NULL,
   CONSTRAINT pk_t_comment_container PRIMARY KEY (id)
);

CREATE TABLE t_comment (
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   id UUID NOT NULL,
   content VARCHAR(8000) NOT NULL,
   parent_id UUID,
   container_id UUID,
   user_id UUID NOT NULL,
   voting_id UUID,
   CONSTRAINT pk_t_comment PRIMARY KEY (id)
);

ALTER TABLE t_comment ADD CONSTRAINT FK_T_COMMENT_ON_CONTAINER
    FOREIGN KEY (container_id) REFERENCES t_comment_container (id);

ALTER TABLE t_comment ADD CONSTRAINT FK_T_COMMENT_ON_PARENT
    FOREIGN KEY (parent_id) REFERENCES t_comment (id);