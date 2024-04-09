ALTER TABLE t_category
    ADD voting_id UUID;

ALTER TABLE t_category ADD CONSTRAINT FK_T_CATEGORY_ON_VOTING
    FOREIGN KEY (voting_id) REFERENCES t_voting (id);