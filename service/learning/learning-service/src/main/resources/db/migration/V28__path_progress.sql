ALTER TABLE t_path
    ADD progress_container_id UUID;

ALTER TABLE t_path ADD CONSTRAINT FK_T_PATH_ON_PROGRESS_CONTAINER
    FOREIGN KEY (progress_container_id) REFERENCES t_progress_container (id);