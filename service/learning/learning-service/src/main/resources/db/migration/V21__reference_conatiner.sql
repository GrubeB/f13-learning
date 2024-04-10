CREATE TABLE t_reference_container (
   id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   domain_object_id UUID,
   domain_object_type SMALLINT,
   CONSTRAINT pk_t_reference_container PRIMARY KEY (id)
);

ALTER TABLE t_reference
    ADD container_id UUID;

ALTER TABLE t_reference ADD CONSTRAINT FK_T_REFERENCE_ON_CONTAINER
    FOREIGN KEY (container_id) REFERENCES t_reference_container (id);

ALTER TABLE t_topic
    ADD reference_container_id UUID;

ALTER TABLE t_topic ADD CONSTRAINT FK_T_TOPIC_ON_REFERENCE_CONTAINER
    FOREIGN KEY (reference_container_id) REFERENCES t_reference_container (id);

DROP TABLE t_topic_has_reference CASCADE;