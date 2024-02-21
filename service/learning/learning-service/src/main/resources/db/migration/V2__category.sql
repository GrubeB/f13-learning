CREATE TABLE t_category (
  created_by VARCHAR(255) NOT NULL,
  created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  last_modified_by VARCHAR(255),
  last_modified_date TIMESTAMP WITHOUT TIME ZONE,
  category_name VARCHAR(255) NOT NULL,
  description VARCHAR(8000),
  status VARCHAR(255),
  id UUID NOT NULL,
  CONSTRAINT pk_t_category PRIMARY KEY (id)
);
CREATE TABLE t_category_has_category (
  id UUID NOT NULL,
  created_by VARCHAR(255) NOT NULL,
  created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  last_modified_by VARCHAR(255),
  last_modified_date TIMESTAMP WITHOUT TIME ZONE,
  category_2_id UUID NOT NULL,
  category_1_id UUID NOT NULL,
  CONSTRAINT pk_t_category_has_category PRIMARY KEY (id)
);
ALTER TABLE
  t_category_has_category
ADD
  CONSTRAINT FK_T_CATEGORY_HAS_CATEGORY_ON_CHILD FOREIGN KEY (category_1_id) REFERENCES t_category (id);
ALTER TABLE
  t_category_has_category
ADD
  CONSTRAINT FK_T_CATEGORY_HAS_CATEGORY_ON_PARENT FOREIGN KEY (category_2_id) REFERENCES t_category (id);
