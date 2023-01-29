USE todo;

DROP TABLE IF EXISTS `task`;
DROP TABLE IF EXISTS `task_status`;
DROP TABLE IF EXISTS `user_account`;

CREATE TABLE `user_account` (
  `user_id` int(11) NOT NULL COMMENT 'User id',
  `name` varchar(30) NOT NULL COMMENT 'User name',
  `created_at` timestamp NOT NULL COMMENT 'Record created timestamp',
  `updated_at` timestamp NOT NULL  COMMENT 'Record updated timestamp',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Whether user has been deleted or not',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB;

CREATE TABLE `task_status` (
  `status_id` int(1) NOT NULL COMMENT 'Status Id',
  `status` varchar(10) NOT NULL COMMENT 'Task status',
  PRIMARY KEY (`status_id`)
) ENGINE=InnoDB;

CREATE TABLE `task` (
  `id` char(26) NOT NULL COMMENT 'Task id',
   `user_id` int(11) NOT NULL COMMENT 'User id',
  `summary` varchar(30) NOT NULL COMMENT 'Task summary',
  `description` text COMMENT 'Task description',
  `status_id` int(11) NOT NULL COMMENT 'Status id',
  `created_at` timestamp NOT NULL COMMENT 'Record created timestamp',
  `updated_at` timestamp NOT NULL COMMENT 'Record updated timestamp',
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `user_account` (`user_id`),
  FOREIGN KEY (`status_id`) REFERENCES `task_status` (`status_id`)
) ENGINE=InnoDB;
