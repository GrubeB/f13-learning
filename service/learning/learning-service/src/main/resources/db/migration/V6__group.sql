CREATE TABLE t_group (
  created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   topic_name VARCHAR(255) NOT NULL,
   topic_content VARCHAR(8000),
   topic_status VARCHAR(255) NOT NULL,
   id UUID NOT NULL,
   CONSTRAINT pk_t_group PRIMARY KEY (id)
);

CREATE TABLE t_group_has_category (
   id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   group_id UUID NOT NULL,
   category_id UUID NOT NULL,
   CONSTRAINT pk_t_group_has_category PRIMARY KEY (id)
);

ALTER TABLE t_group_has_category ADD CONSTRAINT FK_T_GROUP_HAS_CATEGORY_ON_GROUP
    FOREIGN KEY (group_id) REFERENCES t_group (id);

ALTER TABLE t_group_has_category ADD CONSTRAINT FK_T_GROUP_HAS_CATEGORY_ON_CATEGORY
    FOREIGN KEY (category_id) REFERENCES t_category (id);


CREATE TABLE t_group_has_reference (
  id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   group_id UUID NOT NULL,
   reference_id UUID NOT NULL,
   CONSTRAINT pk_t_group_has_reference PRIMARY KEY (id)
);

ALTER TABLE t_group_has_reference ADD CONSTRAINT FK_T_GROUP_HAS_REFERENCE_ON_GROUP
    FOREIGN KEY (group_id) REFERENCES t_group (id);

ALTER TABLE t_group_has_reference ADD CONSTRAINT FK_T_GROUP_HAS_REFERENCE_ON_REFERENCE
    FOREIGN KEY (reference_id) REFERENCES t_reference (id);


CREATE TABLE t_group_has_topic (
  id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   group_id UUID NOT NULL,
   topic_id UUID NOT NULL,
   CONSTRAINT pk_t_group_has_topic PRIMARY KEY (id)
);

ALTER TABLE t_group_has_topic ADD CONSTRAINT FK_T_GROUP_HAS_TOPIC_ON_GROUP
    FOREIGN KEY (group_id) REFERENCES t_group (id);

ALTER TABLE t_group_has_topic ADD CONSTRAINT FK_T_GROUP_HAS_TOPIC_ON_TOPIC
    FOREIGN KEY (topic_id) REFERENCES t_topic (id);

CREATE TABLE t_group_has_group (
  id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   group_id_1 UUID NOT NULL,
   group_id_2 UUID NOT NULL,
   CONSTRAINT pk_t_group_has_group PRIMARY KEY (id)
);

ALTER TABLE t_group_has_group ADD CONSTRAINT FK_T_GROUP_HAS_GROUP_ON_GROUP_ID_1
    FOREIGN KEY (group_id_1) REFERENCES t_group (id);

ALTER TABLE t_group_has_group ADD CONSTRAINT FK_T_GROUP_HAS_GROUP_ON_GROUP_ID_2
    FOREIGN KEY (group_id_2) REFERENCES t_group (id);


CREATE TABLE t_group_snapshot (
   snapshot_id UUID NOT NULL,
   snapshot_number BIGINT NOT NULL,
   owner_id UUID,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   CONSTRAINT pk_t_group_snapshot PRIMARY KEY (snapshot_id)
);