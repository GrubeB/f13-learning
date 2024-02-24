CREATE TABLE t_accommodation_type_availability (
  created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   id UUID NOT NULL,
   property UUID NOT NULL,
   accommodation_type UUID NOT NULL,
   CONSTRAINT pk_t_accommodation_type_availability PRIMARY KEY (id)
);
ALTER TABLE t_accommodation_type_availability ADD CONSTRAINT FK_T_ACCOMMODATION_TYPE_AVAILABILITY_ON_PROPERTY
    FOREIGN KEY (property) REFERENCES t_property (id);
ALTER TABLE t_accommodation_type_availability ADD CONSTRAINT FK_T_ACCOMMODATION_TYPE_AVAILABILITY_ON_ACCOMMODATION_TYPE
    FOREIGN KEY (accommodation_type) REFERENCES t_accommodation_type (id);

CREATE TABLE t_accommodation_availability (
  created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   accommodation_type_availability UUID NOT NULL,
   id UUID NOT NULL,
   accommodation_id UUID NOT NULL,
   CONSTRAINT pk_t_accommodation_availability PRIMARY KEY (id)
);

ALTER TABLE t_accommodation_availability ADD CONSTRAINT FK_TACCOMMODATIONAVAILABILITY_ON_ACCOMMODATIONTYPEAVAILABILITY
    FOREIGN KEY (accommodation_type_availability) REFERENCES t_accommodation_type_availability (id);

CREATE TABLE t_accommodation_restriction (
  id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   restriction_status VARCHAR(255) NOT NULL,
   accommodation_availability UUID NOT NULL,
   from_date DATE NOT NULL,
   to_date DATE NOT NULL,
   CONSTRAINT pk_t_accommodation_restriction PRIMARY KEY (id)
);

ALTER TABLE t_accommodation_restriction ADD CONSTRAINT FK_T_ACCOMMODATION_RESTRICTION_ON_ACCOMMODATION_AVAILABILITY
    FOREIGN KEY (accommodation_availability) REFERENCES t_accommodation_availability (id);


CREATE TABLE t_accommodation_type_reservation (
  created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   assigned_status VARCHAR(255) NOT NULL,
   accommodation_type_availability UUID NOT NULL,
   id UUID NOT NULL,
   from_date DATE NOT NULL,
   to_date DATE NOT NULL,
   CONSTRAINT pk_t_accommodation_type_reservation PRIMARY KEY (id)
);

ALTER TABLE t_accommodation_type_reservation ADD CONSTRAINT FK_TACCOMMODATIONTYPERESERVATIO_ON_ACCOMMODATIONTYPEAVAILABILIT
    FOREIGN KEY (accommodation_type_availability) REFERENCES t_accommodation_type_availability (id);


CREATE TABLE t_accommodation_type_reservation_item (
  id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   accommodation_availability UUID,
   accommodation_restriction UUID,
   accommodation_type_reservation UUID NOT NULL,
   CONSTRAINT pk_t_accommodation_type_reservation_item PRIMARY KEY (id)
);

ALTER TABLE t_accommodation_type_reservation_item ADD CONSTRAINT FK_TACCOMMODATIONTYPERESERVATIONITE_ON_ACCOMMODATIONRESTRICTION
    FOREIGN KEY (accommodation_restriction) REFERENCES t_accommodation_restriction (id);

ALTER TABLE t_accommodation_type_reservation_item ADD CONSTRAINT FK_TACCOMMODATIONTYPERESERVATIONIT_ON_ACCOMMODATIONAVAILABILITY
    FOREIGN KEY (accommodation_availability) REFERENCES t_accommodation_availability (id);

ALTER TABLE t_accommodation_type_reservation_item ADD CONSTRAINT FK_TACCOMMODATIONTYPERESERVATIO_ON_ACCOMMODATIONTYPERESERVATION
    FOREIGN KEY (accommodation_type_reservation) REFERENCES t_accommodation_type_reservation (id);