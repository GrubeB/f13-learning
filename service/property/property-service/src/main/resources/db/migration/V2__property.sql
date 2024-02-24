CREATE TABLE t_property_address (
  id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   address1 VARCHAR(255),
   address2 VARCHAR(255),
   city VARCHAR(255),
   country VARCHAR(255),
   region VARCHAR(255),
   zip_code VARCHAR(255),
   CONSTRAINT pk_t_property_address PRIMARY KEY (id)
);

CREATE TABLE t_property_details (
   id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   email VARCHAR(255),
   phone VARCHAR(255),
   website VARCHAR(255),
   description VARCHAR(255),
   address_id UUID NOT NULL,
   CONSTRAINT pk_t_property_details PRIMARY KEY (id)
);

ALTER TABLE t_property_details ADD CONSTRAINT FK_T_PROPERTY_DETAILS_ON_ADDRESS
    FOREIGN KEY (address_id) REFERENCES t_property_address (id);

CREATE TABLE t_property (
   id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   property_name VARCHAR(255) NOT NULL,
   type VARCHAR(255),
   check_in_from_time time WITHOUT TIME ZONE,
   check_in_to_time time WITHOUT TIME ZONE,
   check_out_from_time time WITHOUT TIME ZONE,
   check_out_to_time time WITHOUT TIME ZONE,
   property_details_id UUID,
   organization_id UUID NOT NULL,
   CONSTRAINT pk_t_property PRIMARY KEY (id)
);

ALTER TABLE t_property ADD CONSTRAINT FK_T_PROPERTY_ON_ORGANIZATION
    FOREIGN KEY (organization_id) REFERENCES t_organization (id);

ALTER TABLE t_property ADD CONSTRAINT FK_T_PROPERTY_ON_PROPERTY_DETAILS
    FOREIGN KEY (property_details_id) REFERENCES t_property_details (id);