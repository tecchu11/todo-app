USE todo;

DROP TABLE IF EXISTS `todo_user`;
CREATE TABLE `todo_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'User ID',
  `user_name` varchar(30) NOT NULL COMMENT 'User Name',
  `registered_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Registered DateTime',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Whether user has been deleted or not',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `task_status`;
CREATE TABLE `task_status` (
  `status_id` int(11) NOT NULL COMMENT 'Task Status ID',
  `status_name` varchar(10) NOT NULL COMMENT 'Task Status',
  PRIMARY KEY (`status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `task_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Todo Task ID',
  `task_summary` varchar(30) NOT NULL COMMENT 'Task Summary',
  `task_description` text COMMENT 'Task Description',
  `user_id` int(11) NOT NULL COMMENT 'User ID',
  `status_id` int(11) NOT NULL COMMENT 'Task Status ID',
  `registered_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Registered DateTime',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Updated DateTime',
  PRIMARY KEY (`task_id`),
  FOREIGN KEY (`user_id`) REFERENCES `todo_user` (`user_id`),
  FOREIGN KEY (`status_id`) REFERENCES `task_status` (`status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
