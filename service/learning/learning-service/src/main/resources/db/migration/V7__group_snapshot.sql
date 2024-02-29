ALTER TABLE t_group_snapshot DROP CONSTRAINT pk_t_group_snapshot;
DROP TABLE IF EXISTS t_group_snapshot CASCADE;

CREATE TABLE t_group_snapshot (
   snapshot_id UUID NOT NULL,
   snapshot_number BIGINT NOT NULL,
   owner_id UUID,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   topic_name VARCHAR(255) NOT NULL,
   topic_content VARCHAR(8000),
   topic_status VARCHAR(255) NOT NULL,
   CONSTRAINT pk_t_group_snapshot PRIMARY KEY (snapshot_id)
);

CREATE TABLE t_group_has_category_snapshot (
  snapshot_id UUID NOT NULL,
   snapshot_number BIGINT NOT NULL,
   owner_id UUID,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   group_snapshot_id UUID NOT NULL,
   category_id UUID NOT NULL,
   CONSTRAINT pk_t_group_has_category_snapshot PRIMARY KEY (snapshot_id)
);

ALTER TABLE t_group_has_category_snapshot ADD CONSTRAINT FK_T_GROUP_HAS_CATEGORY_SNAPSHOT_ON_GROUP_SNAPSHOT
    FOREIGN KEY (group_snapshot_id) REFERENCES t_group_snapshot (snapshot_id);

CREATE TABLE t_group_has_reference_snapshot (
  snapshot_id UUID NOT NULL,
   snapshot_number BIGINT NOT NULL,
   owner_id UUID,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   group_snapshot_id UUID NOT NULL,
   reference_id UUID NOT NULL,
   CONSTRAINT pk_t_group_has_reference_snapshot PRIMARY KEY (snapshot_id)
);

ALTER TABLE t_group_has_reference_snapshot ADD CONSTRAINT FK_T_GROUP_HAS_REFERENCE_SNAPSHOT_ON_GROUP_SNAPSHOT
        FOREIGN KEY (group_snapshot_id) REFERENCES t_group_snapshot (snapshot_id);

CREATE TABLE t_group_has_topic_snapshot (
  snapshot_id UUID NOT NULL,
   snapshot_number BIGINT NOT NULL,
   owner_id UUID,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   group_snapshot_id UUID NOT NULL,
   topic_id UUID NOT NULL,
   CONSTRAINT pk_t_group_has_topic_snapshot PRIMARY KEY (snapshot_id)
);

ALTER TABLE t_group_has_topic_snapshot ADD CONSTRAINT FK_T_GROUP_HAS_TOPIC_SNAPSHOT_ON_GROUP_SNAPSHOT
    FOREIGN KEY (group_snapshot_id) REFERENCES t_group_snapshot (snapshot_id);


CREATE TABLE t_group_has_group_snapshot (
  snapshot_id UUID NOT NULL,
   snapshot_number BIGINT NOT NULL,
   owner_id UUID,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   group_snapshot_id UUID NOT NULL,
   group_id_2 UUID NOT NULL,
   CONSTRAINT pk_t_group_has_group_snapshot PRIMARY KEY (snapshot_id)
);

ALTER TABLE t_group_has_group_snapshot ADD CONSTRAINT FK_T_GROUP_HAS_GROUP_SNAPSHOT_ON_GROUP_SNAPSHOT
    FOREIGN KEY (group_snapshot_id) REFERENCES t_group_snapshot (snapshot_id);