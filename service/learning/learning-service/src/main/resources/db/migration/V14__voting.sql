CREATE TABLE t_voting (
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   likes_number BIGINT,
   dislikes_number BIGINT,
   domain_object_type SMALLINT NOT NULL,
   id UUID NOT NULL,
   domain_object_id UUID NOT NULL,
   CONSTRAINT pk_t_voting PRIMARY KEY (id)
);

CREATE TABLE t_voting_vote (
   id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   user_id UUID NOT NULL,
   vote_type SMALLINT NOT NULL,
   voting_id UUID NOT NULL,
   CONSTRAINT pk_t_voting_vote PRIMARY KEY (id)
);

ALTER TABLE t_voting_vote ADD CONSTRAINT FK_T_VOTING_VOTE_ON_VOTING
    FOREIGN KEY (voting_id) REFERENCES t_voting (id);