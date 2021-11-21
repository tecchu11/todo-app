INSERT INTO users (user_id, user_name, registered_at, is_deleted) VALUES
(1, 'test user', default, 0);

INSERT INTO task_status (status_id, status) VALUES
(3, 'CLOSED'),
(1, 'OPEN'),
(2, 'WIP');

INSERT INTO tasks (task_id, task_summary, task_description, user_id, status_id, registered_at, updated_at) VALUES
('AAAAAAAAAAAAABBBBBBBBBBBBB', 'test todo task', NULL, 1, 1, "2021-01-01 12:00:00", "2021-01-01 13:00:00");
