CREATE TABLE t_reference_voting (
  id UUID NOT NULL,
  created_by VARCHAR(255) NOT NULL,
  created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  last_modified_by VARCHAR(255),
  last_modified_date TIMESTAMP WITHOUT TIME ZONE,
  likes_number BIGINT,
  dislikes_number BIGINT,
  CONSTRAINT pk_t_reference_voting PRIMARY KEY (id)
);
CREATE TABLE t_reference_voting_vote (
  id UUID NOT NULL,
  created_by VARCHAR(255) NOT NULL,
  created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  last_modified_by VARCHAR(255),
  last_modified_date TIMESTAMP WITHOUT TIME ZONE,
  user_id UUID NOT NULL,
  vote_type SMALLINT NOT NULL,
  reference_voting_id UUID,
  CONSTRAINT pk_t_reference_voting_vote PRIMARY KEY (id)
);
ALTER TABLE
  t_reference_voting_vote
ADD
  CONSTRAINT FK_T_REFERENCE_VOTING_VOTE_ON_REFERENCE_VOTING FOREIGN KEY (reference_voting_id) REFERENCES t_reference_voting (id);
create TABLE t_reference (
  created_by VARCHAR(255) NOT NULL,
  created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  last_modified_by VARCHAR(255),
  last_modified_date TIMESTAMP WITHOUT TIME ZONE,
  author VARCHAR(255),
  title VARCHAR(255),
  publication_date date,
  description VARCHAR(255),
  link VARCHAR(255),
  status SMALLINT,
  reference_voting_id UUID,
  id UUID NOT NULL,
  CONSTRAINT pk_t_reference PRIMARY KEY (id)
);
alter table
  t_reference
add
  CONSTRAINT FK_T_REFERENCE_ON_REFERENCE_VOTING FOREIGN KEY (reference_voting_id) REFERENCES t_reference_voting (id);
