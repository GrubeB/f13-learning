CREATE TABLE t_topic_has_category_snapshot (
   snapshot_id UUID NOT NULL,
   snapshot_number BIGINT NOT NULL,
   owner_id UUID,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   topic_id UUID NOT NULL,
   category_id UUID NOT NULL,
   CONSTRAINT pk_t_topic_has_category_snapshot PRIMARY KEY (snapshot_id)
);

ALTER TABLE t_topic_has_category_snapshot ADD CONSTRAINT FK_T_TOPIC_HAS_CATEGORY_SNAPSHOT_ON_TOPIC
    FOREIGN KEY (topic_id) REFERENCES t_topic_snapshot (snapshot_id);