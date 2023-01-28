USE todo;

INSERT INTO user_account (user_id, name, created_at, updated_at, is_deleted)
		VALUES(1, 'test user name', '2021-08-13 22:17:33', '2021-08-13 22:17:33', 0);

INSERT INTO task_status (status_id, status)
		VALUES(3, 'CLOSED'), (1, 'OPEN'), (2, 'WIP');

INSERT INTO task (id, user_id, summary, description, status_id, created_at, updated_at)
		VALUES('01ARZ3NDEKTSV4RRFFQ69G5FAV', 1, 'test todo task 1', NULL, 1, '2021-08-13 22:22:42', '2021-08-13 22:22:42'),
		('01ARZ3NDEKTSV4RRFFQ69G5FAW', 1, 'test todo task 2', NULL, 2, '2021-08-13 22:23:20', '2021-08-13 22:23:20'),
		('01ARZ3NDEKTSV4RRFFQ69G5FAX', 1, 'test todo task 3', NULL, 3, '2021-08-13 22:23:58', '2021-08-13 22:23:58');
