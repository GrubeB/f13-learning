CREATE TABLE t_topic_revision (
  created_by VARCHAR(255) NOT NULL,
  created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  last_modified_by VARCHAR(255),
  last_modified_date TIMESTAMP WITHOUT TIME ZONE,
  topic_revision_name VARCHAR(255),
  topic_revision_content VARCHAR(255),
  topic_revision_id UUID NOT NULL,
  id UUID NOT NULL,
  CONSTRAINT pk_t_topic_revision PRIMARY KEY (id)
);
ALTER TABLE
  t_topic_revision
ADD
  CONSTRAINT FK_T_TOPIC_REVISION_ON_TOPIC_REVISION FOREIGN KEY (topic_revision_id) REFERENCES t_topic (id);
