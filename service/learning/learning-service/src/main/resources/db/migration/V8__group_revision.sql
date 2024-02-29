CREATE TABLE t_group_revision (
  snapshot_id UUID NOT NULL,
   owner_id UUID,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   topic_name VARCHAR(255) NOT NULL,
   topic_content VARCHAR(8000),
   topic_status VARCHAR(255) NOT NULL,
   group_id UUID NOT NULL,
   CONSTRAINT pk_t_group_revision PRIMARY KEY (snapshot_id)
);

ALTER TABLE t_group_revision ADD CONSTRAINT FK_T_GROUP_REVISION_ON_GROUP
    FOREIGN KEY (group_id) REFERENCES t_group (id);

CREATE TABLE t_group_has_category_revision (
  snapshot_id UUID NOT NULL,
   owner_id UUID,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   group_id UUID NOT NULL,
   category_id UUID NOT NULL,
   CONSTRAINT pk_t_group_has_category_revision PRIMARY KEY (snapshot_id)
);

ALTER TABLE t_group_has_category_revision ADD CONSTRAINT FK_T_GROUP_HAS_CATEGORY_REVISION_ON_GROUP
    FOREIGN KEY (group_id) REFERENCES t_group_revision (snapshot_id);

CREATE TABLE t_group_has_group_revision (
  snapshot_id UUID NOT NULL,
   owner_id UUID,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   group_id_1 UUID NOT NULL,
   group_id_2 UUID NOT NULL,
   CONSTRAINT pk_t_group_has_group_revision PRIMARY KEY (snapshot_id)
);

ALTER TABLE t_group_has_group_revision ADD CONSTRAINT FK_T_GROUP_HAS_GROUP_REVISION_ON_GROUP_ID_1
    FOREIGN KEY (group_id_1) REFERENCES t_group_revision (snapshot_id);

CREATE TABLE t_group_has_topic_revision (
  snapshot_id UUID NOT NULL,
   owner_id UUID,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   group_id UUID NOT NULL,
   topic_id UUID NOT NULL,
   CONSTRAINT pk_t_group_has_topic_revision PRIMARY KEY (snapshot_id)
);

ALTER TABLE t_group_has_topic_revision ADD CONSTRAINT FK_T_GROUP_HAS_TOPIC_REVISION_ON_GROUP
    FOREIGN KEY (group_id) REFERENCES t_group_revision (snapshot_id);