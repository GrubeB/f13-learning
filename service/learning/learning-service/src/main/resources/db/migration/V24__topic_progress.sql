ALTER TABLE t_topic
    ADD progress_container_id UUID;

ALTER TABLE t_topic ADD CONSTRAINT FK_T_TOPIC_ON_PROGRESS_CONTAINER
    FOREIGN KEY (progress_container_id) REFERENCES t_progress_container (id);