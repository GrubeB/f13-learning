CREATE TABLE t_topic (
  id UUID NOT NULL,
  created_by VARCHAR(255) NOT NULL,
  created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  last_modified_by VARCHAR(255),
  last_modified_date TIMESTAMP WITHOUT TIME ZONE,
  topic_name VARCHAR(255),
  topic_content VARCHAR(255),
  topic_status VARCHAR(255),
  CONSTRAINT pk_t_topic PRIMARY KEY (id)
);
CREATE TABLE t_topic_has_category (
  id UUID NOT NULL,
  created_by VARCHAR(255) NOT NULL,
  created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  last_modified_by VARCHAR(255),
  last_modified_date TIMESTAMP WITHOUT TIME ZONE,
  topic_id UUID NOT NULL,
  category_id UUID NOT NULL,
  CONSTRAINT pk_t_topic_has_category PRIMARY KEY (id)
);
ALTER TABLE
  t_topic_has_category
ADD
  CONSTRAINT FK_T_TOPIC_HAS_CATEGORY_ON_TOPIC FOREIGN KEY (topic_id) REFERENCES t_topic (id);
ALTER TABLE
  t_topic_has_category
ADD
  CONSTRAINT FK_T_TOPIC_HAS_CATEGORY_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES t_category (id);
CREATE TABLE t_topic_has_reference (
  id UUID NOT NULL,
  created_by VARCHAR(255) NOT NULL,
  created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  last_modified_by VARCHAR(255),
  last_modified_date TIMESTAMP WITHOUT TIME ZONE,
  topic_id UUID NOT NULL,
  reference_id UUID NOT NULL,
  CONSTRAINT pk_t_topic_has_reference PRIMARY KEY (id)
);
ALTER TABLE
  t_topic_has_reference
ADD
  CONSTRAINT FK_T_TOPIC_HAS_REFERENCE_ON_TOPIC FOREIGN KEY (topic_id) REFERENCES t_topic (id);
ALTER TABLE
  t_topic_has_reference
ADD
  CONSTRAINT FK_T_TOPIC_HAS_REFERENCE_ON_REFERENCE FOREIGN KEY (reference_id) REFERENCES t_reference (id);
CREATE TABLE t_topic_snapshot (
  snapshot_id UUID NOT NULL,
  snapshot_number BIGINT NOT NULL,
  owner_id UUID,
  created_by VARCHAR(255) NOT NULL,
  created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  last_modified_by VARCHAR(255),
  last_modified_date TIMESTAMP WITHOUT TIME ZONE,
  topic_name VARCHAR(255),
  topic_content VARCHAR(255),
  topic_status VARCHAR(255),
  CONSTRAINT pk_t_topic_snapshot PRIMARY KEY (snapshot_id)
);
