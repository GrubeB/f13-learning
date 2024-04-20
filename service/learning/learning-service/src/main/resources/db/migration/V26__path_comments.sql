ALTER TABLE t_path
    ADD comment_container_id UUID;

ALTER TABLE t_path ADD CONSTRAINT FK_T_PATH_ON_COMMENT_CONTAINER
    FOREIGN KEY (comment_container_id) REFERENCES t_comment_container (id);