
CREATE TABLE t_chapter (
  chapter_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   topic VARCHAR(255) NOT NULL,
   introduction VARCHAR(8000),
   CONSTRAINT pk_t_chapter PRIMARY KEY (chapter_id)
);

CREATE TABLE t_chapter_reference (
  reference_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   reference_name VARCHAR(255),
   link VARCHAR(255) NOT NULL,
   chapter_id UUID,
   CONSTRAINT pk_t_chapter_reference PRIMARY KEY (reference_id)
);

ALTER TABLE t_chapter_reference ADD CONSTRAINT FK_T_CHAPTER_REFERENCE_ON_CHAPTER
    FOREIGN KEY (chapter_id) REFERENCES t_chapter (chapter_id);


CREATE TABLE t_chapter_snapshot (
  snapshot_id UUID NOT NULL,
   snapshot_number BIGINT NOT NULL,
   owner_id UUID,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   topic VARCHAR(255),
   introduction VARCHAR(8000),
   CONSTRAINT pk_t_chapter_snapshot PRIMARY KEY (snapshot_id)
);

CREATE TABLE t_chapter_reference_snapshot (
  snapshot_id UUID NOT NULL,
   snapshot_number BIGINT NOT NULL,
   owner_id UUID,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   reference_name VARCHAR(255),
   link VARCHAR(255),
   chapter_snapshot_id UUID,
   CONSTRAINT pk_t_chapter_reference_snapshot PRIMARY KEY (snapshot_id)
);

ALTER TABLE t_chapter_reference_snapshot ADD CONSTRAINT FK_T_CHAPTER_REFERENCE_SNAPSHOT_ON_CHAPTER_SNAPSHOT
    FOREIGN KEY (chapter_snapshot_id) REFERENCES t_chapter_snapshot (snapshot_id);