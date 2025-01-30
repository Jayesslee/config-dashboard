CREATE TABLE
    `role` (
        `id` BIGINT NOT NULL AUTO_INCREMENT,
        `role_name` VARCHAR(64) NOT NULL,
        `description` VARCHAR(255) NOT NULL,
        `created_at` TIMESTAMP(6) NOT NULL,
        `created_by` VARCHAR(64) NOT NULL,
        `updated_at` TIMESTAMP(6) DEFAULT NULL,
        `updated_by` VARCHAR(64) DEFAULT NULL,
        PRIMARY KEY (`id`)
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;