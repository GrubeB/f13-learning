ALTER TABLE t_group_revision DROP CONSTRAINT FK_T_GROUP_REVISION_ON_GROUP;
ALTER TABLE t_group_revision DROP COLUMN group_id;
ALTER TABLE t_group_revision RENAME COLUMN snapshot_id TO revision_id;
ALTER TABLE t_group_has_category_revision RENAME COLUMN snapshot_id TO revision_id;
ALTER TABLE t_group_has_group_revision RENAME COLUMN snapshot_id TO revision_id;
ALTER TABLE t_group_has_topic_revision RENAME COLUMN snapshot_id TO revision_id;