CREATE TABLE `members` (
                           `id`    BIGINT    NOT NULL    AUTO_INCREMENT    COMMENT 'Auto',
                           `nickname`    VARCHAR(255)    NOT NULL    COMMENT '미 입력시 아이디를 바탕으로 자동 생성',
                           `email`    VARCHAR(255)    NOT NULL,
                           PRIMARY KEY (`id`)
);

CREATE TABLE `login_credentials` (
                                     `id`    BIGINT    NOT NULL    AUTO_INCREMENT    COMMENT 'Auto',
                                     `username`    VARCHAR(255)    NOT NULL,
                                     `password`    VARCHAR(255)    NOT NULL,
                                     `refresh_token`    VARCHAR(255)    NULL,
                                     `is_social_user`    boolean    NOT NULL,
                                     `created_at`    TIMESTAMP    NOT NULL    DEFAULT NOW()    COMMENT '생성 이후로 바뀌지 않음',
                                     `updated_at`    TIMESTAMP    NOT NULL    DEFAULT NOW(),
                                     PRIMARY KEY (`id`)
);

CREATE TABLE `boards` (
                          `id`    BIGINT    NOT NULL    AUTO_INCREMENT    COMMENT 'Auto',
                          `area_id`    BIGINT    NOT NULL,
                          `franchise_id`    BIGINT    NULL,
                          `Field`    VARCHAR(255)    NULL,
                          PRIMARY KEY (`id`)
);

CREATE TABLE `favorite_business_areas` (
                                           `id`    BIGINT    NOT NULL    AUTO_INCREMENT    COMMENT 'Auto',
                                           `area_id`    BIGINT    NOT NULL,
                                           `member_id`    BIGINT    NOT NULL,
                                           `created_at`    TIMESTAMP    NOT NULL    DEFAULT NOW(),
                                           `updated_at`    TIMESTAMP    NOT NULL    DEFAULT NOW(),
                                           PRIMARY KEY (`id`)
);

CREATE TABLE `replies` (
                           `id`    BIGINT    NOT NULL    AUTO_INCREMENT    COMMENT 'Auto',
                           `member_id`    BIGINT    NOT NULL,
                           `post_id`    BIGINT    NOT NULL,
                           `content`    VARCHAR(255)    NOT NULL,
                           `created_at`    TIMESTAMP    NOT NULL    DEFAULT NOW(),
                           `updated_at`    TIMESTAMP    NOT NULL    DEFAULT NOW(),
                           PRIMARY KEY (`id`)
);

CREATE TABLE `sale_store_posts` (
                                    `id`    BIGINT    NOT NULL    AUTO_INCREMENT    COMMENT 'Auto',
                                    `revenue`    BIGINT    NULL,
                                    `startup_price`    BIGINT    NULL,
                                    `rental_price`    BIGINT    NULL,
                                    `size`    BIGINT    NULL,
                                    `franchise_type`    VARCHAR(255)    NULL,
                                    `age_group`    VARCHAR(255)    NULL,
                                    `foot_traffic`    VARCHAR(255)    NULL,
                                    `atmosphere`    VARCHAR(255)    NULL,
                                    `nearby_price`    VARCHAR(255)    NULL,
                                    `desired_sale_price`    BIGINT    NULL,
                                    PRIMARY KEY (`id`)
);

CREATE TABLE `franchises` (
                              `id`    BIGINT    NOT NULL    AUTO_INCREMENT    COMMENT 'Auto',
                              `name`    VARCHAR(255)    NOT NULL,
                              `type`    VARCHAR(255)    NULL,
                              `franchise_fee`    BIGINT    NULL,
                              `education_fee`    BIGINT    NULL,
                              `deposit`    BIGINT    NULL,
                              `other_fee`    BIGINT    NULL,
                              `interior`    BIGINT    NULL,
                              `link`    VARCHAR(511)    NULL,
                              PRIMARY KEY (`id`)
);

CREATE TABLE `favorite_franchises` (
                                       `id`    BIGINT    NOT NULL    AUTO_INCREMENT    COMMENT 'Auto',
                                       `member_id`    BIGINT    NOT NULL,
                                       `franchise_id`    BIGINT    NOT NULL,
                                       `created_at`    TIMESTAMP    NOT NULL    DEFAULT NOW(),
                                       `updated_at`    TIMESTAMP    NOT NULL    DEFAULT NOW(),
                                       PRIMARY KEY (`id`)
);

CREATE TABLE `posts` (
                         `id`    BIGINT    NOT NULL    AUTO_INCREMENT    COMMENT 'Auto',
                         `board_id`    BIGINT    NOT NULL,
                         `member_id`    BIGINT    NOT NULL,
                         `title`    VARCHAR(255)    NOT NULL,
                         `content`    VARCHAR(255)    NULL,
                         `created_at`    TIMESTAMP    NOT NULL    DEFAULT NOW(),
                         `updated_at`    TIMESTAMP    NOT NULL    DEFAULT NOW(),
                         `view`    BIGINT    NULL,
                         PRIMARY KEY (`id`)
);

CREATE TABLE `business_area_evaluations` (
                                             `id`    BIGINT    NOT NULL    AUTO_INCREMENT    COMMENT 'Auto',
                                             `area_id`    BIGINT    NOT NULL,
                                             `member_id`    BIGINT    NOT NULL,
                                             `age_group`    VARCHAR(255)    NOT NULL,
                                             `foot_traffic`    VARCHAR(255)    NOT NULL,
                                             `atmosphere`    VARCHAR(255)    NOT NULL,
                                             `nearby_prices`    VARCHAR(255)    NOT NULL,
                                             `created_at`    TIMESTAMP    NOT NULL    DEFAULT NOW(),
                                             `updated_at`    TIMESTAMP    NOT NULL    DEFAULT NOW(),
                                             PRIMARY KEY (`id`)
);

CREATE TABLE `sales` (
                         `id`    BIGINT    NOT NULL    AUTO_INCREMENT    COMMENT 'Auto',
                         `service_code`    VARCHAR(255)    NOT NULL,
                         `gu_code`    BIGINT    NULL,
                         `dong_code`    BIGINT    NULL,
                         `area_id`    BIGINT    NULL,
                         `year`    INT    NULL,
                         `month_sales`    BIGINT    NULL,
                         `week_sales`    BIGINT    NULL,
                         `weekend_sales`    BIGINT    NULL,
                         `mon_sales`    BIGINT    NULL,
                         `tue_sales`    BIGINT    NULL,
                         `wed_sales`    BIGINT    NULL,
                         `thu_sales`    BIGINT    NULL,
                         `fri_sales`    BIGINT    NULL,
                         `sat_sales`    BIGINT    NULL,
                         `sun_sales`    BIGINT    NULL,
                         `0to6`    BIGINT    NULL,
                         `6to11`    BIGINT    NULL,
                         `11to14`    BIGINT    NULL,
                         `14to17`    BIGINT    NULL,
                         `17to21`    BIGINT    NULL,
                         `21to24`    BIGINT    NULL,
                         `10_sales`    BIGINT    NULL,
                         `20_sales`    BIGINT    NULL,
                         `30_sales`    BIGINT    NULL,
                         `40_sales`    BIGINT    NULL,
                         `50_sales`    BIGINT    NULL,
                         `60_sales`    BIGINT    NULL,
                         `woman_sales`    BIGINT    NULL,
                         `man_sales`    BIGINT    NULL,
                         PRIMARY KEY (`id`)
);

CREATE TABLE `populations` (
                               `id`    BIGINT    NOT NULL    AUTO_INCREMENT    COMMENT 'Auto',
                               `gu_code`    BIGINT    NULL,
                               `dong_code`    BIGINT    NULL,
                               `area_id`    BIGINT    NULL,
                               `total_population`    BIGINT    NULL,
                               `man_popultaion`    BIGINT    NULL,
                               `woman_population`    BIGINT    NULL,
                               `10s_population`    BIGINT    NULL,
                               `20s_population`    BIGINT    NULL,
                               `30s_population`    BIGINT    NULL,
                               `40s_population`    BIGINT    NULL,
                               `50s_population`    BIGINT    NULL,
                               `60s_population`    BIGINT    NULL,
                               `0to6_population`    BIGINT    NULL,
                               `6to11_population`    BIGINT    NULL,
                               `11to14_population`    BIGINT    NULL,
                               `14to17_population`    BIGINT    NULL,
                               `17to21_population`    BIGINT    NULL,
                               `21to24_population`    BIGINT    NULL,
                               `monday_population`    BIGINT    NULL,
                               `tuesday_population`    BIGINT    NULL,
                               `wendsday_population`    BIGINT    NULL,
                               `thursday_population`    BIGINT    NULL,
                               `friday_population`    BIGINT    NULL,
                               `saturday_population`    BIGINT    NULL,
                               `sunday_population`    BIGINT    NULL,
                               PRIMARY KEY (`id`)
);

CREATE TABLE `area` (
                        `id`    BIGINT    NOT NULL    AUTO_INCREMENT    COMMENT 'Auto',
                        `area_name`    VARCHAR(255)    NOT NULL,
                        `dong_code`    BIGINT    NOT NULL,
                        `x_pos`    FLOAT    NOT NULL,
                        `y_pos`    FLOAT    NOT NULL,
                        `polygon`    GEOMETRY    NOT NULL,
                        PRIMARY KEY (`id`)
);

CREATE TABLE `dong` (
                        `id`    BIGINT    NOT NULL    AUTO_INCREMENT    COMMENT 'Auto',
                        `dong_name`    VARCHAR(255)    NOT NULL,
                        `gu_code`    BIGINT    NOT NULL,
                        `x_pos`    FLOAT    NOT NULL,
                        `y_pos`    FLOAT    NOT NULL,
                        `polygon`    GEOMETRY    NOT NULL,
                        PRIMARY KEY (`id`)
);

CREATE TABLE `gu` (
                      `id`    BIGINT    NOT NULL    AUTO_INCREMENT    COMMENT 'Auto',
                      `gu_name`    VARCHAR(255)    NOT NULL,
                      `x_pos`    FLOAT    NOT NULL,
                      `y_pos`    FLOAT    NOT NULL,
                      `polygon`    GEOMETRY    NOT NULL,
                      PRIMARY KEY (`id`)
);

CREATE TABLE `store` (
                         `id`    BIGINT    NOT NULL    AUTO_INCREMENT    COMMENT 'Auto',
                         `service_code`    VARCHAR(255)    NOT NULL,
                         `area_id`    BIGINT    NULL,
                         `dong_code`    BIGINT    NULL,
                         `gu_code`    BIGINT    NULL,
                         `year`    INT    NULL,
                         `store_cnt`    INT    NULL,
                         `sim_busin_cnt`    INT    NULL,
                         `opening_rate`    INT    NULL,
                         `closing_rate`    INT    NULL,
                         `opening_cnt`    INT    NULL,
                         `closing_cnt`    INT    NULL,
                         `fran_cnt`    INT    NULL,
                         PRIMARY KEY (`id`)
);

CREATE TABLE `sales_history` (
                                 `id`    BIGINT    NOT NULL    AUTO_INCREMENT    COMMENT 'Auto',
                                 `service_code`    VARCHAR(255)    NOT NULL,
                                 `area_id`    BIGINT    NULL,
                                 `dong_code`    BIGINT    NULL,
                                 `gu_code`    BIGINT    NULL,
                                 `sales_20231`    BIGINT    NULL,
                                 `sales_20232`    BIGINT    NULL,
                                 `sales_20233`    BIGINT    NULL,
                                 `sales_20234`    BIGINT    NULL,
                                 `sales_20241`    BIGINT    NULL,
                                 PRIMARY KEY (`id`)
);

CREATE TABLE `service` (
                           `service_code`    VARCHAR(255)    NOT NULL,
                           `service_name`    VARCHAR(255)    NOT NULL,
                           PRIMARY KEY (`service_code`)
);

CREATE TABLE `rent` (
                        `id`    BIGINT    NOT NULL    AUTO_INCREMENT    COMMENT 'Auto',
                        `gu_code`    BIGINT    NULL,
                        `dong_code`    BIGINT    NULL,
                        `first_floor`    BIGINT    NULL,
                        `other_floor`    BIGINT    NULL,
                        PRIMARY KEY (`id`)
);



ALTER TABLE `members` ADD CONSTRAINT `FK_login_credentials_TO_members_1` FOREIGN KEY (
                                                                                                `id`
    )
    REFERENCES `login_credentials` (
                          `id`
        );



ALTER TABLE `boards` ADD CONSTRAINT `FK_area_TO_boards_1` FOREIGN KEY (
                                                                       `area_id`
    )
    REFERENCES `area` (
                       `id`
        );

ALTER TABLE `boards` ADD CONSTRAINT `FK_franchises_TO_boards_1` FOREIGN KEY (
                                                                             `franchise_id`
    )
    REFERENCES `franchises` (
                             `id`
        );

ALTER TABLE `favorite_business_areas` ADD CONSTRAINT `FK_area_TO_favorite_business_areas_1` FOREIGN KEY (
                                                                                                         `area_id`
    )
    REFERENCES `area` (
                       `id`
        );

ALTER TABLE `favorite_business_areas` ADD CONSTRAINT `FK_members_TO_favorite_business_areas_1` FOREIGN KEY (
                                                                                                            `member_id`
    )
    REFERENCES `members` (
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

ALTER TABLE `business_area_evaluations` ADD CONSTRAINT `FK_area_TO_business_area_evaluations_1` FOREIGN KEY (
                                                                                                             `area_id`
    )
    REFERENCES `area` (
                       `id`
        );

ALTER TABLE `business_area_evaluations` ADD CONSTRAINT `FK_members_TO_business_area_evaluations_1` FOREIGN KEY (
                                                                                                                `member_id`
    )
    REFERENCES `members` (
                          `id`
        );

ALTER TABLE `sales` ADD CONSTRAINT `FK_service_TO_sales_1` FOREIGN KEY (
                                                                        `service_code`
    )
    REFERENCES `service` (
                          `service_code`
        );

ALTER TABLE `sales` ADD CONSTRAINT `FK_gu_TO_sales_1` FOREIGN KEY (
                                                                   `gu_code`
    )
    REFERENCES `gu` (
                     `id`
        );

ALTER TABLE `sales` ADD CONSTRAINT `FK_dong_TO_sales_1` FOREIGN KEY (
                                                                     `dong_code`
    )
    REFERENCES `dong` (
                       `id`
        );

ALTER TABLE `sales` ADD CONSTRAINT `FK_area_TO_sales_1` FOREIGN KEY (
                                                                     `area_id`
    )
    REFERENCES `area` (
                       `id`
        );

ALTER TABLE `populations` ADD CONSTRAINT `FK_gu_TO_populations_1` FOREIGN KEY (
                                                                               `gu_code`
    )
    REFERENCES `gu` (
                     `id`
        );

ALTER TABLE `populations` ADD CONSTRAINT `FK_dong_TO_populations_1` FOREIGN KEY (
                                                                                 `dong_code`
    )
    REFERENCES `dong` (
                       `id`
        );

ALTER TABLE `populations` ADD CONSTRAINT `FK_area_TO_populations_1` FOREIGN KEY (
                                                                                 `area_id`
    )
    REFERENCES `area` (
                       `id`
        );

ALTER TABLE `area` ADD CONSTRAINT `FK_dong_TO_area_1` FOREIGN KEY (
                                                                   `dong_code`
    )
    REFERENCES `dong` (
                       `id`
        );

ALTER TABLE `dong` ADD CONSTRAINT `FK_gu_TO_dong_1` FOREIGN KEY (
                                                                 `gu_code`
    )
    REFERENCES `gu` (
                     `id`
        );

ALTER TABLE `store` ADD CONSTRAINT `FK_service_TO_store_1` FOREIGN KEY (
                                                                        `service_code`
    )
    REFERENCES `service` (
                          `service_code`
        );

ALTER TABLE `store` ADD CONSTRAINT `FK_area_TO_store_1` FOREIGN KEY (
                                                                     `area_id`
    )
    REFERENCES `area` (
                       `id`
        );

ALTER TABLE `store` ADD CONSTRAINT `FK_dong_TO_store_1` FOREIGN KEY (
                                                                     `dong_code`
    )
    REFERENCES `dong` (
                       `id`
        );

ALTER TABLE `store` ADD CONSTRAINT `FK_gu_TO_store_1` FOREIGN KEY (
                                                                   `gu_code`
    )
    REFERENCES `gu` (
                     `id`
        );

ALTER TABLE `sales_history` ADD CONSTRAINT `FK_service_TO_sales_history_1` FOREIGN KEY (
                                                                                        `service_code`
    )
    REFERENCES `service` (
                          `service_code`
        );

ALTER TABLE `sales_history` ADD CONSTRAINT `FK_area_TO_sales_history_1` FOREIGN KEY (
                                                                                     `area_id`
    )
    REFERENCES `area` (
                       `id`
        );

ALTER TABLE `sales_history` ADD CONSTRAINT `FK_dong_TO_sales_history_1` FOREIGN KEY (
                                                                                     `dong_code`
    )
    REFERENCES `dong` (
                       `id`
        );

ALTER TABLE `sales_history` ADD CONSTRAINT `FK_gu_TO_sales_history_1` FOREIGN KEY (
                                                                                   `gu_code`
    )
    REFERENCES `gu` (
                     `id`
        );

ALTER TABLE `rent` ADD CONSTRAINT `FK_gu_TO_rent_1` FOREIGN KEY (
                                                                 `gu_code`
    )
    REFERENCES `gu` (
                     `id`
        );

ALTER TABLE `rent` ADD CONSTRAINT `FK_dong_TO_rent_1` FOREIGN KEY (
                                                                   `dong_code`
    )
    REFERENCES `dong` (
                       `id`
        );

# # SELECT CONCAT('ALTER TABLE `', table_name, '` DROP FOREIGN KEY `', constraint_name, '`;')
# # FROM information_schema.key_column_usage
# # WHERE referenced_table_schema = 's11p21d108';
#
# DROP TABLE IF EXISTS `members`;
#
# CREATE TABLE `members` (
#                            `id`	BIGINT	NOT NULL AUTO_INCREMENT	COMMENT 'Auto',
#                            `nickname`	VARCHAR(255)	NOT NULL	COMMENT '미 입력시 아이디를 바탕으로 자동 생성',
#                             `email` VARCHAR(255) NOT NULL,
#                            PRIMARY KEY (`id`)
# );
#
# DROP TABLE IF EXISTS `login_credentials`;
#
# CREATE TABLE `login_credentials` (
#                                      `id`	BIGINT	NOT NULL AUTO_INCREMENT COMMENT 'Auto',
#                                      `username`	VARCHAR(255)	NOT NULL,
#                                      `password`	VARCHAR(255)	NOT NULL,
#                                      `refresh_token` VARCHAR(255),
#                                      `created_at` TIMESTAMP NOT NULL DEFAULT CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul'),
#                                      `updated_at`	TIMESTAMP NOT NULL DEFAULT CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul'),
#                                      PRIMARY KEY (`id`)
# );
#
# DROP TABLE IF EXISTS `boards`;
#
# CREATE TABLE `boards` (
#                           `id`	BIGINT	NOT NULL AUTO_INCREMENT	COMMENT 'Auto',
#                           `business_area_id`	BIGINT	NOT NULL	COMMENT 'Auto',
#                           `franchise_id`	BIGINT	NOT NULL	COMMENT 'Auto',
#                           PRIMARY KEY (`id`)
# );
#
# DROP TABLE IF EXISTS `business_areas`;
#
# CREATE TABLE `business_areas` (
#                                   `id`	BIGINT	NOT NULL AUTO_INCREMENT	COMMENT 'Auto',
#                                   `name`	VARCHAR(255)	NOT NULL,
#                                   `description`	VARCHAR(255)	NULL,
#                                   PRIMARY KEY (`id`)
# );
#
# DROP TABLE IF EXISTS `favorite_business_areas`;
#
# CREATE TABLE `favorite_business_areas` (
#                                            `id`	BIGINT	NOT NULL AUTO_INCREMENT	COMMENT 'Auto',
#                                            `member_id`	BIGINT	NOT NULL	COMMENT 'Auto',
#                                            `business_area_id`	BIGINT	NOT NULL	COMMENT 'Auto',
#                                            `created_at` TIMESTAMP NOT NULL DEFAULT CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul'),
#                                            `updated_at`	TIMESTAMP NOT NULL DEFAULT CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul'),
#                                            PRIMARY KEY (`id`)
# );
#
# DROP TABLE IF EXISTS `replies`;
#
# CREATE TABLE `replies` (
#                            `id`	BIGINT	NOT NULL AUTO_INCREMENT	COMMENT 'Auto',
#                            `member_id`	BIGINT	NOT NULL	COMMENT 'Auto',
#                            `post_id`	BIGINT	NOT NULL	COMMENT 'Auto',
#                            `content`	VARCHAR(255)	NOT NULL,
#                            `created_at` TIMESTAMP NOT NULL DEFAULT CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul'),
#                            `updated_at`	TIMESTAMP NOT NULL DEFAULT CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul'),
#                            PRIMARY KEY (`id`)
# );
#
# DROP TABLE IF EXISTS `sale_store_posts`;
#
# CREATE TABLE `sale_store_posts` (
#                                     `id`	BIGINT	NOT NULL	COMMENT 'Auto',
#                                     `revenue`	BIGINT	NULL,
#                                     `startup_price`	BIGINT	NULL,
#                                     `rental_price`	BIGINT	NULL,
#                                     `size`	BIGINT	NULL,
#                                     `view`	BIGINT	NULL,
#                                     `nearby_price`	VARCHAR(255)	NULL,
#                                     `type`	VARCHAR(255)	NULL,
#                                     `age_group`	VARCHAR(255)	NULL,
#                                     `foot_traffic`	VARCHAR(255)	NULL,
#                                     `atmosphere`	VARCHAR(255)	NULL,
#                                     `desired_sale_price`	BIGINT	NULL,
#                                     PRIMARY KEY (`id`)
# );
#
# DROP TABLE IF EXISTS `franchises`;
#
# CREATE TABLE `franchises` (
#                               `id`	BIGINT	NOT NULL AUTO_INCREMENT	COMMENT 'Auto',
#                               `name`	VARCHAR(255)	NOT NULL,
#                               `description`	VARCHAR(255)	NULL,
#                               PRIMARY KEY (`id`)
# );
#
# DROP TABLE IF EXISTS `favorite_franchises`;
#
# CREATE TABLE `favorite_franchises` (
#                                        `id`	BIGINT	NOT NULL AUTO_INCREMENT	COMMENT 'Auto',
#                                        `member_id`	BIGINT	NOT NULL	COMMENT 'Auto',
#                                        `franchise_id`	BIGINT	NOT NULL	COMMENT 'Auto',
#                                        `created_at` TIMESTAMP NOT NULL DEFAULT CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul'),
#                                        `updated_at`	TIMESTAMP NOT NULL DEFAULT CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul'),
#                                        PRIMARY KEY (`id`)
# );
#
# DROP TABLE IF EXISTS `posts`;
#
# CREATE TABLE `posts` (
#                          `id`	BIGINT	NOT NULL AUTO_INCREMENT	COMMENT 'Auto',
#                          `board_id`	BIGINT	NOT NULL	COMMENT 'Auto',
#                          `member_id`	BIGINT	NOT NULL	COMMENT 'Auto',
#                          `title`	VARCHAR(255)	NOT NULL,
#                          `view` BIGINT NOT NULL,
#                          `content`	VARCHAR(255)	NULL,
#                          `created_at` TIMESTAMP NOT NULL DEFAULT CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul'),
#                          `updated_at`	TIMESTAMP NOT NULL DEFAULT CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul'),
#                          PRIMARY KEY (`id`)
# );
#
# DROP TABLE IF EXISTS `franchise_evaluations`;
#
# CREATE TABLE `franchise_evaluations` (
#                                          `id`	BIGINT	NOT NULL AUTO_INCREMENT	COMMENT 'Auto',
#                                          `franchise_id`	BIGINT	NOT NULL	COMMENT 'Auto',
#                                          `member_id`	BIGINT	NOT NULL	COMMENT 'Auto',
#                                          `age_group`	VARCHAR(255)	NOT NULL,
#                                          `foot_traffic`	VARCHAR(255)	NOT NULL,
#                                          `atmosphere`	VARCHAR(255)	NOT NULL,
#                                          `nearby_prices`	VARCHAR(255)	NOT NULL,
#                                          `created_at` TIMESTAMP NOT NULL DEFAULT CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul'),
#                                          `updated_at`	TIMESTAMP NOT NULL DEFAULT CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul'),
#                                          PRIMARY KEY (`id`)
# );
#
# DROP TABLE IF EXISTS `business_area_evaluations`;
#
# CREATE TABLE `business_area_evaluations` (
#                                              `id`	BIGINT	NOT NULL AUTO_INCREMENT COMMENT 'Auto',
#                                              `business_area_id`	BIGINT	NOT NULL	COMMENT 'Auto',
#                                              `member_id`	BIGINT	NOT NULL	COMMENT 'Auto',
#                                              `age_group`	VARCHAR(255)	NOT NULL,                                             `Field`	VARCHAR(255)	NOT NULL,
#                                              `created_at` TIMESTAMP NOT NULL DEFAULT CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul'),
#                                              `updated_at`	TIMESTAMP NOT NULL DEFAULT CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul'),
#                                              PRIMARY KEY (`id`)
# );
#
# ALTER TABLE `boards` ADD CONSTRAINT `FK_business_areas_TO_boards_1` FOREIGN KEY (
#                                                                                  `business_area_id`
#     )
#     REFERENCES `business_areas` (
#                                  `id`
#         );
#
# ALTER TABLE `boards` ADD CONSTRAINT `FK_franchises_TO_boards_1` FOREIGN KEY (
#                                                                              `franchise_id`
#     )
#     REFERENCES `franchises` (
#                              `id`
#         );
#
# ALTER TABLE `favorite_business_areas` ADD CONSTRAINT `FK_members_TO_favorite_business_areas_1` FOREIGN KEY (
#                                                                                                             `member_id`
#     )
#     REFERENCES `members` (
#                           `id`
#         );
#
# ALTER TABLE `favorite_business_areas` ADD CONSTRAINT `FK_business_areas_TO_favorite_business_areas_1` FOREIGN KEY (
#                                                                                                                    `business_area_id`
#     )
#     REFERENCES `business_areas` (
#                                  `id`
#         );
#
# ALTER TABLE `replies` ADD CONSTRAINT `FK_members_TO_replies_1` FOREIGN KEY (
#                                                                             `member_id`
#     )
#     REFERENCES `members` (
#                           `id`
#         );
#
# ALTER TABLE `replies` ADD CONSTRAINT `FK_posts_TO_replies_1` FOREIGN KEY (
#                                                                           `post_id`
#     )
#     REFERENCES `posts` (
#                         `id`
#         );
#
# ALTER TABLE `sale_store_posts` ADD CONSTRAINT `FK_posts_TO_sale_store_posts_1` FOREIGN KEY (
#
#
#                                                                                             `id`
#     )
#     REFERENCES `posts` (
#                         `id`
#         );
#
# ALTER TABLE `favorite_franchises` ADD CONSTRAINT `FK_members_TO_favorite_franchises_1` FOREIGN KEY (
#                                                                                                     `member_id`
#     )
#     REFERENCES `members` (
#                           `id`
#         );
#
# ALTER TABLE `favorite_franchises` ADD CONSTRAINT `FK_franchises_TO_favorite_franchises_1` FOREIGN KEY (
#                                                                                                        `franchise_id`
#     )
#     REFERENCES `franchises` (
#                              `id`
#         );
#
# ALTER TABLE `posts` ADD CONSTRAINT `FK_boards_TO_posts_1` FOREIGN KEY (
#                                                                        `board_id`
#     )
#     REFERENCES `boards` (
#                          `id`
#         );
#
# ALTER TABLE `posts` ADD CONSTRAINT `FK_members_TO_posts_1` FOREIGN KEY (
#                                                                         `member_id`
#     )
#     REFERENCES `members` (
#                           `id`
#         );
#
# ALTER TABLE `franchise_evaluations` ADD CONSTRAINT `FK_franchises_TO_franchise_evaluations_1` FOREIGN KEY (
#                                                                                                            `franchise_id`
#     )
#     REFERENCES `franchises` (
#                              `id`
#         );
#
# ALTER TABLE `franchise_evaluations` ADD CONSTRAINT `FK_members_TO_franchise_evaluations_1` FOREIGN KEY (
#                                                                                                         `member_id`
#     )
#     REFERENCES `members` (
#                           `id`
#         );
#
# ALTER TABLE `business_area_evaluations` ADD CONSTRAINT `FK_business_areas_TO_business_area_evaluations_1` FOREIGN KEY (
#                                                                                                                        `business_area_id`
#     )
#     REFERENCES `business_areas` (
#                                  `id`
#         );
#
# ALTER TABLE `business_area_evaluations` ADD CONSTRAINT `FK_members_TO_business_area_evaluations_1` FOREIGN KEY (
#                                                                                                                 `member_id`
#     )
#     REFERENCES `members` (
#                           `id`
#         );
#
