CREATE TABLE t_path (
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   path_name VARCHAR(255),
   path_content VARCHAR(255),
   path_status VARCHAR(255),
   id UUID NOT NULL,
   CONSTRAINT pk_t_path PRIMARY KEY (id)
);

CREATE TABLE t_path_item (
  id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   item_number BIGINT,
   item_type VARCHAR(255),
   entity_type VARCHAR(255),
   path_id UUID NOT NULL,
   entity_id UUID NOT NULL,
   CONSTRAINT pk_t_path_item PRIMARY KEY (id)
);

ALTER TABLE t_path_item ADD CONSTRAINT FK_T_PATH_ITEM_ON_PATH
    FOREIGN KEY (path_id) REFERENCES t_path (id);

CREATE TABLE t_path_has_category (
  id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   path_id UUID NOT NULL,
   category_id UUID NOT NULL,
   CONSTRAINT pk_t_path_has_category PRIMARY KEY (id)
);

ALTER TABLE t_path_has_category ADD CONSTRAINT FK_T_PATH_HAS_CATEGORY_ON_PATH
    FOREIGN KEY (path_id) REFERENCES t_path (id);