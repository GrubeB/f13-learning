ALTER TABLE t_group
    ADD progress_container_id UUID;

ALTER TABLE t_group ADD CONSTRAINT FK_T_GROUP_ON_PROGRESS_CONTAINER
    FOREIGN KEY (progress_container_id) REFERENCES t_progress_container (id);