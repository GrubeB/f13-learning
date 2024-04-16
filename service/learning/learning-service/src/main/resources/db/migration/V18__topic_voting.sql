ALTER TABLE t_topic
    ADD voting_id UUID;

ALTER TABLE t_topic ADD CONSTRAINT FK_T_TOPIC_ON_VOTING
    FOREIGN KEY (voting_id) REFERENCES t_voting (id);