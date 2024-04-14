CREATE TABLE t_progress_container (
  id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   domain_object_type SMALLINT NOT NULL,
   domain_object_id UUID NOT NULL,
   CONSTRAINT pk_t_progress_container PRIMARY KEY (id)
);
CREATE TABLE t_progress (
  id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   user_id UUID NOT NULL,
   progress_type SMALLINT NOT NULL,
   container_id UUID NOT NULL,
   CONSTRAINT pk_t_progress PRIMARY KEY (id)
);

ALTER TABLE t_progress ADD CONSTRAINT FK_T_PROGRESS_ON_CONTAINER
    FOREIGN KEY (container_id) REFERENCES t_progress_container (id);