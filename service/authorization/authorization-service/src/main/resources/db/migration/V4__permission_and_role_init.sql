INSERT INTO public.t_permission(id, created_by, created_date, last_modified_by, last_modified_date, permission_name) VALUES
(gen_random_uuid(), '-', NOW(), '-', NOW(), 'PERMISSION_READ'),
(gen_random_uuid(), '-', NOW(), '-', NOW(), 'PERMISSION_WRITE'),
(gen_random_uuid(), '-', NOW(), '-', NOW(), 'ROLE_READ'),
(gen_random_uuid(), '-', NOW(), '-', NOW(), 'ROLE_WRITE'),
(gen_random_uuid(), '-', NOW(), '-', NOW(), 'USER_READ'),
(gen_random_uuid(), '-', NOW(), '-', NOW(), 'USER_WRITE');

INSERT INTO public.t_role(id, created_by, created_date, last_modified_by, last_modified_date, role_name) VALUES
(gen_random_uuid(), '-', NOW(), '-', NOW(), 'USER'),
(gen_random_uuid(), '-', NOW(), '-', NOW(), 'ADMIN');

INSERT INTO t_role_has_permission(id, created_by, created_date, last_modified_by, last_modified_date, role_id, permission_id) SELECT gen_random_uuid(), '-', NOW(), '-', NOW(), t_role.id, t_permission.id FROM t_role, t_permission
WHERE t_role.role_name = 'USER' AND t_permission.permission_name = 'PERMISSION_READ';
INSERT INTO t_role_has_permission(id, created_by, created_date, last_modified_by, last_modified_date, role_id, permission_id) SELECT gen_random_uuid(), '-', NOW(), '-', NOW(), t_role.id, t_permission.id FROM t_role, t_permission
WHERE t_role.role_name = 'USER' AND t_permission.permission_name = 'PERMISSION_WRITE';
INSERT INTO t_role_has_permission(id, created_by, created_date, last_modified_by, last_modified_date, role_id, permission_id) SELECT gen_random_uuid(), '-', NOW(), '-', NOW(), t_role.id, t_permission.id FROM t_role, t_permission
WHERE t_role.role_name = 'USER' AND t_permission.permission_name = 'ROLE_READ';
INSERT INTO t_role_has_permission(id, created_by, created_date, last_modified_by, last_modified_date, role_id, permission_id) SELECT gen_random_uuid(), '-', NOW(), '-', NOW(), t_role.id, t_permission.id FROM t_role, t_permission
WHERE t_role.role_name = 'USER' AND t_permission.permission_name = 'ROLE_WRITE';

INSERT INTO t_role_has_permission(id, created_by, created_date, last_modified_by, last_modified_date, role_id, permission_id) SELECT gen_random_uuid(), '-', NOW(), '-', NOW(), t_role.id, t_permission.id FROM t_role, t_permission
WHERE t_role.role_name = 'ADMIN' AND t_permission.permission_name = 'PERMISSION_READ';
INSERT INTO t_role_has_permission(id, created_by, created_date, last_modified_by, last_modified_date, role_id, permission_id) SELECT gen_random_uuid(), '-', NOW(), '-', NOW(), t_role.id, t_permission.id FROM t_role, t_permission
WHERE t_role.role_name = 'ADMIN' AND t_permission.permission_name = 'PERMISSION_WRITE';
INSERT INTO t_role_has_permission(id, created_by, created_date, last_modified_by, last_modified_date, role_id, permission_id) SELECT gen_random_uuid(), '-', NOW(), '-', NOW(), t_role.id, t_permission.id FROM t_role, t_permission
WHERE t_role.role_name = 'ADMIN' AND t_permission.permission_name = 'ROLE_READ';
INSERT INTO t_role_has_permission(id, created_by, created_date, last_modified_by, last_modified_date, role_id, permission_id) SELECT gen_random_uuid(), '-', NOW(), '-', NOW(), t_role.id, t_permission.id FROM t_role, t_permission
WHERE t_role.role_name = 'ADMIN' AND t_permission.permission_name = 'ROLE_WRITE';
INSERT INTO t_role_has_permission(id, created_by, created_date, last_modified_by, last_modified_date, role_id, permission_id) SELECT gen_random_uuid(), '-', NOW(), '-', NOW(), t_role.id, t_permission.id FROM t_role, t_permission
WHERE t_role.role_name = 'ADMIN' AND t_permission.permission_name = 'USER_READ';
INSERT INTO t_role_has_permission(id, created_by, created_date, last_modified_by, last_modified_date, role_id, permission_id) SELECT gen_random_uuid(), '-', NOW(), '-', NOW(), t_role.id, t_permission.id FROM t_role, t_permission
WHERE t_role.role_name = 'ADMIN' AND t_permission.permission_name = 'USER_WRITE';