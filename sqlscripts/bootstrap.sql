DROP DATABASE IF EXISTS `assignment`;

CREATE DATABASE `assignment`;
USE `assignment`;

CREATE TABLE `users` (
	`user_id` bigint(20) NOT NULL AUTO_INCREMENT,
	`username` varchar(255) NOT NULL,
	`password` varchar(255) NOT NULL,
	`role` varchar(255) NOT NULL,
	PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `users` (`username`, `password`, `role`) VALUES
	('ufliao1', 'cb9c632f6c827927c85bb1d356534d2ee1e6283f', 'ROLE_USER'),
	('ufliao2', '49e584830e68cf60061b68569723124bffc850f0', 'ROLE_USER');
    
CREATE TABLE `witness_statement` (
	`witness_statement_id` bigint(20) NOT NULL AUTO_INCREMENT,
	`first_name` varchar(255) DEFAULT NULL,
	`last_name` varchar(255) DEFAULT NULL,
	`middle_name` varchar(255) DEFAULT NULL,
	`gender` varchar(255) DEFAULT NULL,
	`date_of_birth` varchar(255) DEFAULT NULL,
	`address` varchar(255) DEFAULT NULL,
	`email` varchar(255) DEFAULT NULL,
	`phone` varchar(255) DEFAULT NULL,
	`witness_statement` varchar(255) DEFAULT NULL,
	PRIMARY KEY (`witness_statement_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

DROP USER IF EXISTS 'springuser'@'localhost';
CREATE USER 'springuser'@'localhost' identified by 'pspringuser';
GRANT SELECT, INSERT, DELETE, UPDATE ON assignment.* to 'springuser'@'localhost';
