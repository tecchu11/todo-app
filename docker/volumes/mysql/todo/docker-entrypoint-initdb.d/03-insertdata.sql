USE todo;

INSERT INTO users (user_id, user_name, registered_at, is_deleted) VALUES
(1, 'test user name', '2021-08-13 22:17:33', 0);

INSERT INTO task_status (status_id, status) VALUES
(3, 'CLOSED'),
(1, 'OPEN'),
(2, 'WIP');

INSERT INTO tasks (task_id, task_summary, task_description, user_id, status_id, registered_at, updated_at) VALUES
('01ARZ3NDEKTSV4RRFFQ69G5FAV', 'test todo task 1', NULL, 1, 1, '2021-08-13 22:22:42', '2021-08-13 22:22:42'),
('01ARZ3NDEKTSV4RRFFQ69G5FAW', 'test todo task 2', NULL, 1, 2, '2021-08-13 22:23:20', '2021-08-13 22:23:20'),
('01ARZ3NDEKTSV4RRFFQ69G5FAX', 'test todo task 3', NULL, 1, 3, '2021-08-13 22:23:58', '2021-08-13 22:23:58');
