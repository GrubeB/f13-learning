ALTER TABLE t_group
    ADD comment_container_id UUID;

ALTER TABLE t_group ADD CONSTRAINT FK_T_GROUP_ON_COMMENT_CONTAINER
    FOREIGN KEY (comment_container_id) REFERENCES t_comment_container (id);

ALTER TABLE t_group
    ADD voting_id UUID;

ALTER TABLE t_group ADD CONSTRAINT FK_T_GROUP_ON_VOTING
    FOREIGN KEY (voting_id) REFERENCES t_voting (id);