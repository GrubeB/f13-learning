ALTER TABLE t_topic_revision DROP CONSTRAINT FK_T_TOPIC_REVISION_ON_TOPIC_REVISION;
DROP TABLE t_topic_revision CASCADE;

CREATE TABLE t_topic_revision (
   revision_id UUID NOT NULL,
   owner_id UUID,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   topic_revision_name VARCHAR(255),
   topic_revision_content VARCHAR(255),
   CONSTRAINT pk_t_topic_revision PRIMARY KEY (revision_id)
);

CREATE TABLE t_topic_has_category_revision (
   revision_id UUID NOT NULL,
   owner_id UUID,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   topic_id UUID NOT NULL,
   category_id UUID NOT NULL,
   CONSTRAINT pk_t_topic_has_category_revision PRIMARY KEY (revision_id)
);

ALTER TABLE t_topic_has_category_revision ADD CONSTRAINT FK_T_TOPIC_HAS_CATEGORY_REVISION_ON_TOPIC
    FOREIGN KEY (topic_id) REFERENCES t_topic_revision (revision_id);