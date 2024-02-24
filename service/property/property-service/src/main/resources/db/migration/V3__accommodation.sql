CREATE TABLE t_accommodation_type (
  created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   id UUID NOT NULL,
   property_id UUID NOT NULL,
   CONSTRAINT pk_t_accommodation_type PRIMARY KEY (id)
);

ALTER TABLE t_accommodation_type ADD CONSTRAINT FK_T_ACCOMMODATION_TYPE_ON_PROPERTY
    FOREIGN KEY (property_id) REFERENCES t_property (id);

CREATE TABLE t_accommodation_type_details (
  id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   accommodation_name VARCHAR(255),
   abbreviation VARCHAR(255),
   description VARCHAR(255),
   gender_room_type VARCHAR(255),
   room_type VARCHAR(255),
   accommodation_type_id UUID,
   CONSTRAINT pk_t_accommodation_type_details PRIMARY KEY (id)
);

ALTER TABLE t_accommodation_type_details ADD CONSTRAINT FK_T_ACCOMMODATION_TYPE_DETAILS_ON_ACCOMMODATION_TYPE
    FOREIGN KEY (accommodation_type_id) REFERENCES t_accommodation_type (id);

CREATE TABLE t_accommodation (
  created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   accommodation_name VARCHAR(255) NOT NULL,
   description VARCHAR(255),
   accommodation_type UUID NOT NULL,
   id UUID NOT NULL,
   CONSTRAINT pk_t_accommodation PRIMARY KEY (id)
);

ALTER TABLE t_accommodation ADD CONSTRAINT FK_T_ACCOMMODATION_ON_ACCOMMODATION_TYPE
    FOREIGN KEY (accommodation_type) REFERENCES t_accommodation_type (id);