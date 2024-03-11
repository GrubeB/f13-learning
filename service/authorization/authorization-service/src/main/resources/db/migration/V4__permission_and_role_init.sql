INSERT INTO public.t_permission(id, created_by, created_date, last_modified_by, last_modified_date, permission_name) VALUES
(gen_random_uuid(), '-', NOW(), '-', NOW(), 'permission:read'),
(gen_random_uuid(), '-', NOW(), '-', NOW(), 'permission:write'),
(gen_random_uuid(), '-', NOW(), '-', NOW(), 'role:read'),
(gen_random_uuid(), '-', NOW(), '-', NOW(), 'role:write'),
(gen_random_uuid(), '-', NOW(), '-', NOW(), 'user:read'),
(gen_random_uuid(), '-', NOW(), '-', NOW(), 'user:write');

INSERT INTO public.t_role(id, created_by, created_date, last_modified_by, last_modified_date, role_name) VALUES
(gen_random_uuid(), '-', NOW(), '-', NOW(), 'USER'),
(gen_random_uuid(), '-', NOW(), '-', NOW(), 'ADMIN');

INSERT INTO t_role_has_permission(id, created_by, created_date, last_modified_by, last_modified_date, role_id, permission_id) SELECT gen_random_uuid(), '-', NOW(), '-', NOW(), t_role.id, t_permission.id FROM t_role, t_permission
WHERE t_role.role_name = 'USER' AND t_permission.permission_name = 'permission:read';
INSERT INTO t_role_has_permission(id, created_by, created_date, last_modified_by, last_modified_date, role_id, permission_id) SELECT gen_random_uuid(), '-', NOW(), '-', NOW(), t_role.id, t_permission.id FROM t_role, t_permission
WHERE t_role.role_name = 'USER' AND t_permission.permission_name = 'role:read';
INSERT INTO t_role_has_permission(id, created_by, created_date, last_modified_by, last_modified_date, role_id, permission_id) SELECT gen_random_uuid(), '-', NOW(), '-', NOW(), t_role.id, t_permission.id FROM t_role, t_permission
WHERE t_role.role_name = 'USER' AND t_permission.permission_name = 'user:read';

INSERT INTO t_role_has_permission(id, created_by, created_date, last_modified_by, last_modified_date, role_id, permission_id) SELECT gen_random_uuid(), '-', NOW(), '-', NOW(), t_role.id, t_permission.id FROM t_role, t_permission
WHERE t_role.role_name = 'ADMIN' AND t_permission.permission_name = 'permission:read';
INSERT INTO t_role_has_permission(id, created_by, created_date, last_modified_by, last_modified_date, role_id, permission_id) SELECT gen_random_uuid(), '-', NOW(), '-', NOW(), t_role.id, t_permission.id FROM t_role, t_permission
WHERE t_role.role_name = 'ADMIN' AND t_permission.permission_name = 'permission:write';
INSERT INTO t_role_has_permission(id, created_by, created_date, last_modified_by, last_modified_date, role_id, permission_id) SELECT gen_random_uuid(), '-', NOW(), '-', NOW(), t_role.id, t_permission.id FROM t_role, t_permission
WHERE t_role.role_name = 'ADMIN' AND t_permission.permission_name = 'role:read';
INSERT INTO t_role_has_permission(id, created_by, created_date, last_modified_by, last_modified_date, role_id, permission_id) SELECT gen_random_uuid(), '-', NOW(), '-', NOW(), t_role.id, t_permission.id FROM t_role, t_permission
WHERE t_role.role_name = 'ADMIN' AND t_permission.permission_name = 'role:write';
INSERT INTO t_role_has_permission(id, created_by, created_date, last_modified_by, last_modified_date, role_id, permission_id) SELECT gen_random_uuid(), '-', NOW(), '-', NOW(), t_role.id, t_permission.id FROM t_role, t_permission
WHERE t_role.role_name = 'ADMIN' AND t_permission.permission_name = 'user:read';
INSERT INTO t_role_has_permission(id, created_by, created_date, last_modified_by, last_modified_date, role_id, permission_id) SELECT gen_random_uuid(), '-', NOW(), '-', NOW(), t_role.id, t_permission.id FROM t_role, t_permission
WHERE t_role.role_name = 'ADMIN' AND t_permission.permission_name = 'user:write';