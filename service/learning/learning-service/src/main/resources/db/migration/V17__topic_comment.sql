ALTER TABLE t_topic
    ADD comment_container_id UUID;

ALTER TABLE t_topic ADD CONSTRAINT FK_T_TOPIC_ON_COMMENT_CONTAINER
    FOREIGN KEY (comment_container_id) REFERENCES t_comment_container (id);