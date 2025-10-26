START TRANSACTION;

-- Insert action items (no id column, audit fields included)
INSERT INTO creatip_action_item
    (caption, router_link, view_applicable, add_applicable, edit_applicable, delete_applicable, print_applicable, created_by, created_date)
VALUES
    ('Dashboard', '/dashboard', 1, 0, 0, 0, 0, 'seed', '2025-10-25 12:00:00'),
    ('Orders', '/orders', 1, 1, 1, 0, 1, 'seed', '2025-10-25 12:00:00'),
    ('Menu', '/menu', 1, 0, 0, 0, 0, 'seed', '2025-10-25 12:00:00'),
    ('Administration', '/admin', 1, 0, 0, 0, 0, 'seed', '2025-10-25 12:00:00'),
    ('Reports', '/reports', 1, 0, 0, 1, 1, 'seed', '2025-10-25 12:00:00'),
    -- Administration sub-menus (3 submenus)
    ('User Management', '/admin/users', 1, 1, 1, 1, 0, 'seed', '2025-10-25 12:00:00'),
    ('Roles', '/admin/roles', 1, 1, 1, 0, 0, 'seed', '2025-10-25 12:00:00'),
    ('Settings', '/admin/settings', 1, 0, 1, 0, 0, 'seed', '2025-10-25 12:00:00'),
    -- Orders sub-menus (3 submenus)
    ('New Order', '/orders/new', 1, 1, 0, 0, 0, 'seed', '2025-10-25 12:00:00'),
    ('Order List', '/orders/list', 1, 0, 0, 0, 0, 'seed', '2025-10-25 12:00:00'),
    ('Order Reports', '/orders/reports', 1, 0, 0, 0, 1, 'seed', '2025-10-25 12:00:00');

-- Insert ordering rows using the action_item rows we just created.
-- Use INSERT ... SELECT for safety (resolves item_id by router_link)
INSERT INTO creatip_action_item_order
    (order_no, item_type, path, item_id, item_parent_id, created_by, created_date)
SELECT 1, 'ITEM', '/dashboard', ai.id, NULL, 'seed', '2025-10-25 12:00:00' FROM creatip_action_item ai WHERE ai.router_link = '/dashboard';

INSERT INTO creatip_action_item_order
    (order_no, item_type, path, item_id, item_parent_id, created_by, created_date)
SELECT 2, 'GROUP', '/admin', ai.id, NULL, 'seed', '2025-10-25 12:00:00' FROM creatip_action_item ai WHERE ai.router_link = '/admin';

-- Admin children (parent = /admin)
INSERT INTO creatip_action_item_order
    (order_no, item_type, path, item_id, item_parent_id, created_by, created_date)
SELECT 1, 'ITEM', '/admin/users', ai.id, (SELECT id FROM creatip_action_item WHERE router_link = '/admin'), 'seed', '2025-10-25 12:00:00'
FROM creatip_action_item ai WHERE ai.router_link = '/admin/users';

INSERT INTO creatip_action_item_order
    (order_no, item_type, path, item_id, item_parent_id, created_by, created_date)
SELECT 2, 'ITEM', '/admin/roles', ai.id, (SELECT id FROM creatip_action_item WHERE router_link = '/admin'), 'seed', '2025-10-25 12:00:00'
FROM creatip_action_item ai WHERE ai.router_link = '/admin/roles';

INSERT INTO creatip_action_item_order
    (order_no, item_type, path, item_id, item_parent_id, created_by, created_date)
SELECT 3, 'ITEM', '/admin/settings', ai.id, (SELECT id FROM creatip_action_item WHERE router_link = '/admin'), 'seed', '2025-10-25 12:00:00'
FROM creatip_action_item ai WHERE ai.router_link = '/admin/settings';

-- Orders group + children
INSERT INTO creatip_action_item_order
    (order_no, item_type, path, item_id, item_parent_id, created_by, created_date)
SELECT 3, 'ITEM', '/orders', ai.id, NULL, 'seed', '2025-10-25 12:00:00' FROM creatip_action_item ai WHERE ai.router_link = '/orders';

INSERT INTO creatip_action_item_order
    (order_no, item_type, path, item_id, item_parent_id, created_by, created_date)
SELECT 1, 'ITEM', '/orders/new', ai.id, (SELECT id FROM creatip_action_item WHERE router_link = '/orders'), 'seed', '2025-10-25 12:00:00'
FROM creatip_action_item ai WHERE ai.router_link = '/orders/new';

INSERT INTO creatip_action_item_order
    (order_no, item_type, path, item_id, item_parent_id, created_by, created_date)
SELECT 2, 'ITEM', '/orders/list', ai.id, (SELECT id FROM creatip_action_item WHERE router_link = '/orders'), 'seed', '2025-10-25 12:00:00'
FROM creatip_action_item ai WHERE ai.router_link = '/orders/list';

INSERT INTO creatip_action_item_order
    (order_no, item_type, path, item_id, item_parent_id, created_by, created_date)
SELECT 3, 'ITEM', '/orders/reports', ai.id, (SELECT id FROM creatip_action_item WHERE router_link = '/orders'), 'seed', '2025-10-25 12:00:00'
FROM creatip_action_item ai WHERE ai.router_link = '/orders/reports';

-- Other top-levels
INSERT INTO creatip_action_item_order
    (order_no, item_type, path, item_id, item_parent_id, created_by, created_date)
SELECT 4, 'ITEM', '/menu', ai.id, NULL, 'seed', '2025-10-25 12:00:00' FROM creatip_action_item ai WHERE ai.router_link = '/menu';

INSERT INTO creatip_action_item_order
    (order_no, item_type, path, item_id, item_parent_id, created_by, created_date)
SELECT 5, 'ITEM', '/reports', ai.id, NULL, 'seed', '2025-10-25 12:00:00' FROM creatip_action_item ai WHERE ai.router_link = '/reports';

-- Insert role permissions. Use INSERT ... SELECT to resolve item_id by router_link.

-- ROLE_ADMIN: full privileges for all items (list each by router_link)
INSERT INTO creatip_action_role
    (role_code, item_id, view_enable, add_enable, edit_enable, delete_enable, print_enable, created_by, created_date)
SELECT 'ROLE_ADMIN', ai.id, 1,1,1,1,1, 'seed', '2025-10-25 12:00:00' FROM creatip_action_item ai WHERE ai.router_link = '/dashboard';

INSERT INTO creatip_action_role
(role_code, item_id, view_enable, add_enable, edit_enable, delete_enable, print_enable, created_by, created_date)
SELECT 'ROLE_ADMIN', ai.id, 1,1,1,1,1, 'seed', '2025-10-25 12:00:00' FROM creatip_action_item ai WHERE ai.router_link = '/orders';

INSERT INTO creatip_action_role
(role_code, item_id, view_enable, add_enable, edit_enable, delete_enable, print_enable, created_by, created_date)
SELECT 'ROLE_ADMIN', ai.id, 1,1,1,1,1, 'seed', '2025-10-25 12:00:00' FROM creatip_action_item ai WHERE ai.router_link = '/menu';

INSERT INTO creatip_action_role
(role_code, item_id, view_enable, add_enable, edit_enable, delete_enable, print_enable, created_by, created_date)
SELECT 'ROLE_ADMIN', ai.id, 1,1,1,1,1, 'seed', '2025-10-25 12:00:00' FROM creatip_action_item ai WHERE ai.router_link = '/admin';

INSERT INTO creatip_action_role
(role_code, item_id, view_enable, add_enable, edit_enable, delete_enable, print_enable, created_by, created_date)
SELECT 'ROLE_ADMIN', ai.id, 1,1,1,1,1, 'seed', '2025-10-25 12:00:00' FROM creatip_action_item ai WHERE ai.router_link = '/reports';

-- Admin children
INSERT INTO creatip_action_role
(role_code, item_id, view_enable, add_enable, edit_enable, delete_enable, print_enable, created_by, created_date)
SELECT 'ROLE_ADMIN', ai.id, 1,1,1,1,1, 'seed', '2025-10-25 12:00:00' FROM creatip_action_item ai WHERE ai.router_link = '/admin/users';

INSERT INTO creatip_action_role
(role_code, item_id, view_enable, add_enable, edit_enable, delete_enable, print_enable, created_by, created_date)
SELECT 'ROLE_ADMIN', ai.id, 1,1,1,1,1, 'seed', '2025-10-25 12:00:00' FROM creatip_action_item ai WHERE ai.router_link = '/admin/roles';

INSERT INTO creatip_action_role
(role_code, item_id, view_enable, add_enable, edit_enable, delete_enable, print_enable, created_by, created_date)
SELECT 'ROLE_ADMIN', ai.id, 1,1,1,1,1, 'seed', '2025-10-25 12:00:00' FROM creatip_action_item ai WHERE ai.router_link = '/admin/settings';

-- Orders children
INSERT INTO creatip_action_role
(role_code, item_id, view_enable, add_enable, edit_enable, delete_enable, print_enable, created_by, created_date)
SELECT 'ROLE_ADMIN', ai.id, 1,1,1,1,1, 'seed', '2025-10-25 12:00:00' FROM creatip_action_item ai WHERE ai.router_link = '/orders/new';

INSERT INTO creatip_action_role
(role_code, item_id, view_enable, add_enable, edit_enable, delete_enable, print_enable, created_by, created_date)
SELECT 'ROLE_ADMIN', ai.id, 1,1,1,1,1, 'seed', '2025-10-25 12:00:00' FROM creatip_action_item ai WHERE ai.router_link = '/orders/list';

INSERT INTO creatip_action_role
(role_code, item_id, view_enable, add_enable, edit_enable, delete_enable, print_enable, created_by, created_date)
SELECT 'ROLE_ADMIN', ai.id, 1,1,1,1,1, 'seed', '2025-10-25 12:00:00' FROM creatip_action_item ai WHERE ai.router_link = '/orders/reports';

-- ROLE_MANAGER: selected permissions
INSERT INTO creatip_action_role
(role_code, item_id, view_enable, add_enable, edit_enable, delete_enable, print_enable, created_by, created_date)
SELECT 'ROLE_MANAGER', ai.id, 1,0,0,0,0, 'seed', '2025-10-25 12:00:00' FROM creatip_action_item ai WHERE ai.router_link = '/dashboard';

INSERT INTO creatip_action_role
(role_code, item_id, view_enable, add_enable, edit_enable, delete_enable, print_enable, created_by, created_date)
SELECT 'ROLE_MANAGER', ai.id, 1,1,1,0,1, 'seed', '2025-10-25 12:00:00' FROM creatip_action_item ai WHERE ai.router_link = '/orders';

INSERT INTO creatip_action_role
(role_code, item_id, view_enable, add_enable, edit_enable, delete_enable, print_enable, created_by, created_date)
SELECT 'ROLE_MANAGER', ai.id, 1,1,0,0,0, 'seed', '2025-10-25 12:00:00' FROM creatip_action_item ai WHERE ai.router_link = '/orders/new';

INSERT INTO creatip_action_role
(role_code, item_id, view_enable, add_enable, edit_enable, delete_enable, print_enable, created_by, created_date)
SELECT 'ROLE_MANAGER', ai.id, 1,0,0,0,0, 'seed', '2025-10-25 12:00:00' FROM creatip_action_item ai WHERE ai.router_link = '/orders/list';

INSERT INTO creatip_action_role
(role_code, item_id, view_enable, add_enable, edit_enable, delete_enable, print_enable, created_by, created_date)
SELECT 'ROLE_MANAGER', ai.id, 1,0,0,0,1, 'seed', '2025-10-25 12:00:00' FROM creatip_action_item ai WHERE ai.router_link = '/orders/reports';

INSERT INTO creatip_action_role
(role_code, item_id, view_enable, add_enable, edit_enable, delete_enable, print_enable, created_by, created_date)
SELECT 'ROLE_MANAGER', ai.id, 1,0,0,0,0, 'seed', '2025-10-25 12:00:00' FROM creatip_action_item ai WHERE ai.router_link = '/menu';

INSERT INTO creatip_action_role
(role_code, item_id, view_enable, add_enable, edit_enable, delete_enable, print_enable, created_by, created_date)
SELECT 'ROLE_MANAGER', ai.id, 1,0,0,0,0, 'seed', '2025-10-25 12:00:00' FROM creatip_action_item ai WHERE ai.router_link = '/admin';

INSERT INTO creatip_action_role
(role_code, item_id, view_enable, add_enable, edit_enable, delete_enable, print_enable, created_by, created_date)
SELECT 'ROLE_MANAGER', ai.id, 1,1,1,0,0, 'seed', '2025-10-25 12:00:00' FROM creatip_action_item ai WHERE ai.router_link = '/admin/users';

INSERT INTO creatip_action_role
(role_code, item_id, view_enable, add_enable, edit_enable, delete_enable, print_enable, created_by, created_date)
SELECT 'ROLE_MANAGER', ai.id, 1,0,0,0,0, 'seed', '2025-10-25 12:00:00' FROM creatip_action_item ai WHERE ai.router_link = '/admin/roles';

INSERT INTO creatip_action_role
(role_code, item_id, view_enable, add_enable, edit_enable, delete_enable, print_enable, created_by, created_date)
SELECT 'ROLE_MANAGER', ai.id, 1,0,1,0,0, 'seed', '2025-10-25 12:00:00' FROM creatip_action_item ai WHERE ai.router_link = '/admin/settings';

INSERT INTO creatip_action_role
(role_code, item_id, view_enable, add_enable, edit_enable, delete_enable, print_enable, created_by, created_date)
SELECT 'ROLE_MANAGER', ai.id, 1,0,0,0,1, 'seed', '2025-10-25 12:00:00' FROM creatip_action_item ai WHERE ai.router_link = '/reports';

-- ROLE_USER: basic privileges
INSERT INTO creatip_action_role
(role_code, item_id, view_enable, add_enable, edit_enable, delete_enable, print_enable, created_by, created_date)
SELECT 'ROLE_USER', ai.id, 1,0,0,0,0, 'seed', '2025-10-25 12:00:00' FROM creatip_action_item ai WHERE ai.router_link = '/dashboard';

INSERT INTO creatip_action_role
(role_code, item_id, view_enable, add_enable, edit_enable, delete_enable, print_enable, created_by, created_date)
SELECT 'ROLE_USER', ai.id, 1,1,0,0,0, 'seed', '2025-10-25 12:00:00' FROM creatip_action_item ai WHERE ai.router_link = '/orders';

INSERT INTO creatip_action_role
(role_code, item_id, view_enable, add_enable, edit_enable, delete_enable, print_enable, created_by, created_date)
SELECT 'ROLE_USER', ai.id, 1,1,0,0,0, 'seed', '2025-10-25 12:00:00' FROM creatip_action_item ai WHERE ai.router_link = '/orders/new';

INSERT INTO creatip_action_role
(role_code, item_id, view_enable, add_enable, edit_enable, delete_enable, print_enable, created_by, created_date)
SELECT 'ROLE_USER', ai.id, 1,0,0,0,0, 'seed', '2025-10-25 12:00:00' FROM creatip_action_item ai WHERE ai.router_link = '/orders/list';

-- user has no access to order reports, admin children or reports
-- (skip inserting entries where the role has no permission, or insert explicit 0 rows if you prefer)

COMMIT;