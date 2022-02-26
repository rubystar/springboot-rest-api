CREATE TABLE IF NOT EXISTS `employees` (

    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `first_name` varchar(20) not null,
    `last_name` varchar(20) not null,
    `email` varchar(50) not null unique,
    `phone_number` varchar(20) DEFAULT NULL,
    `salary` decimal(8,2) DEFAULT NULL,
    `updated_at` TIMESTAMP NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    `created_at` TIMESTAMP NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;
