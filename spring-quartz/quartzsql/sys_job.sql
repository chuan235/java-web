CREATE TABLE `sys_job` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(64) DEFAULT NULL,
  `job_group` varchar(64) DEFAULT NULL,
  `job_class_name` varchar(64) DEFAULT NULL,
  `method_name` varchar(64) DEFAULT NULL,
  `method_params` varchar(64) DEFAULT NULL,
  `cron_expression` varchar(64) DEFAULT NULL,
  `misfire_policy` char(1) DEFAULT NULL,
  `status` char(1) DEFAULT NULL,
  `concurrent` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4);

INSERT INTO `test`.`sys_job`(`id`, `job_name`, `job_group`, `job_class_name`, `method_name`, `method_params`, `cron_expression`, `misfire_policy`, `status`, `concurrent`) VALUES (1, 'codeTask', '测试任务打印', 'CodeTask', 'printJob', NULL, '* * * * * ?', '0', '0', '0');
