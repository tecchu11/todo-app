DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL COMMENT 'User ID',
  `user_name` varchar(30) NOT NULL COMMENT 'User Name',
  `registered_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Registered DateTime',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Whether user has been deleted or not',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `task_status`;
CREATE TABLE `task_status` (
  `status_id` int(1) NOT NULL COMMENT 'Status Id',
  `status` varchar(10) NOT NULL COMMENT 'Task Status',
  PRIMARY KEY (`status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `tasks`;
CREATE TABLE `tasks` (
  `task_id` char(26) NOT NULL COMMENT 'Todo Task ID',
  `task_summary` varchar(30) NOT NULL COMMENT 'Task Summary',
  `task_description` text COMMENT 'Task Description',
  `user_id` int(11) NOT NULL COMMENT 'User ID',
  `status_id` int(11) NOT NULL COMMENT 'Status ID',
  `registered_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Registered DateTime',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Updated DateTime',
  PRIMARY KEY (`task_id`),
  FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  FOREIGN KEY (`status_id`) REFERENCES `task_status` (`status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO users (user_id, user_name, registered_at, is_deleted) VALUES
(1, 'test user', default, 0);

INSERT INTO task_status (status_id, status) VALUES
(3, 'CLOSED'),
(1, 'OPEN'),
(2, 'WIP');

INSERT INTO tasks (task_id, task_summary, task_description, user_id, status_id, registered_at, updated_at) VALUES
('AAAAAAAAAAAAABBBBBBBBBBBBB', 'test todo task', NULL, 1, 1, "2021-01-01 12:00:00", "2021-01-01 13:00:00");
