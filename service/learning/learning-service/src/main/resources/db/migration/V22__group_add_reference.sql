

ALTER TABLE t_group
    ADD reference_container_id UUID;

ALTER TABLE t_group ADD CONSTRAINT FK_T_GROUP_ON_REFERENCE_CONTAINER
    FOREIGN KEY (reference_container_id) REFERENCES t_reference_container (id);

DROP TABLE t_group_has_reference CASCADE;