# SELECT CONCAT('ALTER TABLE `', table_name, '` DROP FOREIGN KEY `', constraint_name, '`;')
# FROM information_schema.key_column_usage
# WHERE referenced_table_schema = 's11p21d108';

DROP TABLE IF EXISTS `members`;

CREATE TABLE `members` (
                           `id`	BIGINT	NOT NULL AUTO_INCREMENT	COMMENT 'Auto',
                           `nickname`	VARCHAR(255)	NOT NULL	COMMENT '미 입력시 아이디를 바탕으로 자동 생성',
                           PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `login_credentials`;

CREATE TABLE `login_credentials` (
                                     `id`	BIGINT	NOT NULL AUTO_INCREMENT COMMENT 'Auto',
                                     `username`	VARCHAR(255)	NOT NULL,
                                     `password`	VARCHAR(255)	NOT NULL,
                                     `created_at` TIMESTAMP NOT NULL DEFAULT CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul'),
                                     `updated_at`	TIMESTAMP NOT NULL DEFAULT CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul'),
                                     PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `boards`;

CREATE TABLE `boards` (
                          `id`	BIGINT	NOT NULL AUTO_INCREMENT	COMMENT 'Auto',
                          `business_area_id`	BIGINT	NOT NULL	COMMENT 'Auto',
                          `franchise_id`	BIGINT	NOT NULL	COMMENT 'Auto',
                          PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `business_areas`;

CREATE TABLE `business_areas` (
                                  `id`	BIGINT	NOT NULL AUTO_INCREMENT	COMMENT 'Auto',
                                  `name`	VARCHAR(255)	NOT NULL,
                                  `description`	VARCHAR(255)	NULL,
                                  `business_area_id` VARCHAR(255) NULL,
                                  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `favorite_business_areas`;

CREATE TABLE `favorite_business_areas` (
                                           `id`	BIGINT	NOT NULL AUTO_INCREMENT	COMMENT 'Auto',
                                           `member_id`	BIGINT	NOT NULL	COMMENT 'Auto',
                                           `business_area_id`	BIGINT	NOT NULL	COMMENT 'Auto',
                                           `created_at` TIMESTAMP NOT NULL DEFAULT CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul'),
                                           `updated_at`	TIMESTAMP NOT NULL DEFAULT CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul'),
                                           PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `replies`;

CREATE TABLE `replies` (
                           `id`	BIGINT	NOT NULL AUTO_INCREMENT	COMMENT 'Auto',
                           `member_id`	BIGINT	NOT NULL	COMMENT 'Auto',
                           `post_id`	BIGINT	NOT NULL	COMMENT 'Auto',
                           `content`	VARCHAR(255)	NOT NULL,
                           `created_at` TIMESTAMP NOT NULL DEFAULT CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul'),
                           `updated_at`	TIMESTAMP NOT NULL DEFAULT CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul'),
                           PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `sale_store_posts`;

CREATE TABLE `sale_store_posts` (
                                    `id`	BIGINT	NOT NULL	COMMENT 'Auto',
                                    `revenue`	BIGINT	NULL,
                                    `startup_price`	BIGINT	NULL,
                                    `rental_price`	BIGINT	NULL,
                                    `size`	BIGINT	NULL,
                                    `view`	BIGINT	NULL,
                                    `nearby_price`	VARCHAR(255)	NULL,
                                    `type`	VARCHAR(255)	NULL,
                                    `age_group`	VARCHAR(255)	NULL,
                                    `foot_traffic`	VARCHAR(255)	NULL,
                                    `atmosphere`	VARCHAR(255)	NULL,
                                    `desired_sale_price`	BIGINT	NULL,
                                    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `franchises`;

CREATE TABLE `franchises` (
                              `id`	BIGINT	NOT NULL AUTO_INCREMENT	COMMENT 'Auto',
                              `name`	VARCHAR(255)	NOT NULL,
                              `description`	VARCHAR(255)	NULL,
                              PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `favorite_franchises`;

CREATE TABLE `favorite_franchises` (
                                       `id`	BIGINT	NOT NULL AUTO_INCREMENT	COMMENT 'Auto',
                                       `member_id`	BIGINT	NOT NULL	COMMENT 'Auto',
                                       `franchise_id`	BIGINT	NOT NULL	COMMENT 'Auto',
                                       `created_at` TIMESTAMP NOT NULL DEFAULT CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul'),
                                       `updated_at`	TIMESTAMP NOT NULL DEFAULT CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul'),
                                       PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `posts`;

CREATE TABLE `posts` (
                         `id`	BIGINT	NOT NULL AUTO_INCREMENT	COMMENT 'Auto',
                         `board_id`	BIGINT	NOT NULL	COMMENT 'Auto',
                         `member_id`	BIGINT	NOT NULL	COMMENT 'Auto',
                         `title`	VARCHAR(255)	NOT NULL,
                         `view` BIGINT NOT NULL,
                         `content`	VARCHAR(255)	NULL,
                         `created_at` TIMESTAMP NOT NULL DEFAULT CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul'),
                         `updated_at`	TIMESTAMP NOT NULL DEFAULT CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul'),
                         PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `franchise_evaluations`;

CREATE TABLE `franchise_evaluations` (
                                         `id`	BIGINT	NOT NULL AUTO_INCREMENT	COMMENT 'Auto',
                                         `franchise_id`	BIGINT	NOT NULL	COMMENT 'Auto',
                                         `member_id`	BIGINT	NOT NULL	COMMENT 'Auto',
                                         `age_group`	VARCHAR(255)	NOT NULL,
                                         `foot_traffic`	VARCHAR(255)	NOT NULL,
                                         `atmosphere`	VARCHAR(255)	NOT NULL,
                                         `nearby_prices`	VARCHAR(255)	NOT NULL,
                                         `created_at` TIMESTAMP NOT NULL DEFAULT CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul'),
                                         `updated_at`	TIMESTAMP NOT NULL DEFAULT CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul'),
                                         PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `business_area_evaluations`;

CREATE TABLE `business_area_evaluations` (
                                             `id`	BIGINT	NOT NULL AUTO_INCREMENT COMMENT 'Auto',
                                             `business_area_id`	BIGINT	NOT NULL	COMMENT 'Auto',
                                             `member_id`	BIGINT	NOT NULL	COMMENT 'Auto',
                                             `age_group`	VARCHAR(255)	NOT NULL,                                             `Field`	VARCHAR(255)	NOT NULL,
                                             `created_at` TIMESTAMP NOT NULL DEFAULT CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul'),
                                             `updated_at`	TIMESTAMP NOT NULL DEFAULT CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul'),
                                             PRIMARY KEY (`id`)
);

ALTER TABLE `boards` ADD CONSTRAINT `FK_business_areas_TO_boards_1` FOREIGN KEY (
                                                                                 `business_area_id`
    )
    REFERENCES `business_areas` (
                                 `id`
        );

ALTER TABLE `boards` ADD CONSTRAINT `FK_franchises_TO_boards_1` FOREIGN KEY (
                                                                             `franchise_id`
    )
    REFERENCES `franchises` (
                             `id`
        );

ALTER TABLE `favorite_business_areas` ADD CONSTRAINT `FK_members_TO_favorite_business_areas_1` FOREIGN KEY (
                                                                                                            `member_id`
    )
    REFERENCES `members` (
                          `id`
        );

ALTER TABLE `favorite_business_areas` ADD CONSTRAINT `FK_business_areas_TO_favorite_business_areas_1` FOREIGN KEY (
                                                                                                                   `business_area_id`
    )
    REFERENCES `business_areas` (
                                 `id`
        );

ALTER TABLE `replies` ADD CONSTRAINT `FK_members_TO_replies_1` FOREIGN KEY (
                                                                            `member_id`
    )
    REFERENCES `members` (
                          `id`
        );

ALTER TABLE `replies` ADD CONSTRAINT `FK_posts_TO_replies_1` FOREIGN KEY (
                                                                          `post_id`
    )
    REFERENCES `posts` (
                        `id`
        );

ALTER TABLE `sale_store_posts` ADD CONSTRAINT `FK_posts_TO_sale_store_posts_1` FOREIGN KEY (
                                                                                            `id`
    )
    REFERENCES `posts` (
                        `id`
        );

ALTER TABLE `favorite_franchises` ADD CONSTRAINT `FK_members_TO_favorite_franchises_1` FOREIGN KEY (
                                                                                                    `member_id`
    )
    REFERENCES `members` (
                          `id`
        );

ALTER TABLE `favorite_franchises` ADD CONSTRAINT `FK_franchises_TO_favorite_franchises_1` FOREIGN KEY (
                                                                                                       `franchise_id`
    )
    REFERENCES `franchises` (
                             `id`
        );

ALTER TABLE `posts` ADD CONSTRAINT `FK_boards_TO_posts_1` FOREIGN KEY (
                                                                       `board_id`
    )
    REFERENCES `boards` (
                         `id`
        );

ALTER TABLE `posts` ADD CONSTRAINT `FK_members_TO_posts_1` FOREIGN KEY (
                                                                        `member_id`
    )
    REFERENCES `members` (
                          `id`
        );

ALTER TABLE `franchise_evaluations` ADD CONSTRAINT `FK_franchises_TO_franchise_evaluations_1` FOREIGN KEY (
                                                                                                           `franchise_id`
    )
    REFERENCES `franchises` (
                             `id`
        );

ALTER TABLE `franchise_evaluations` ADD CONSTRAINT `FK_members_TO_franchise_evaluations_1` FOREIGN KEY (
                                                                                                        `member_id`
    )
    REFERENCES `members` (
                          `id`
        );

ALTER TABLE `business_area_evaluations` ADD CONSTRAINT `FK_business_areas_TO_business_area_evaluations_1` FOREIGN KEY (
                                                                                                                       `business_area_id`
    )
    REFERENCES `business_areas` (
                                 `id`
        );

ALTER TABLE `business_area_evaluations` ADD CONSTRAINT `FK_members_TO_business_area_evaluations_1` FOREIGN KEY (
                                                                                                                `member_id`
    )
    REFERENCES `members` (
                          `id`
        );

