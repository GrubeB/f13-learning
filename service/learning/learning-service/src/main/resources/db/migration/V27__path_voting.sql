ALTER TABLE t_path
    ADD voting_id UUID;

ALTER TABLE t_path ADD CONSTRAINT FK_T_PATH_ON_VOTING
    FOREIGN KEY (voting_id) REFERENCES t_voting (id);