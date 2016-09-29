CREATE TABLE `TBL_MEMBER` (`id` int(10) NOT NULL AUTO_INCREMENT COMMENT '用户表',`nickname` varchar(20) NOT NULL COMMENT '昵称',`avatar_img` varchar(200) NOT NULL COMMENT '头像地址',`mobile` varchar(11) NULL,`channel` varchar(10) NOT NULL COMMENT '用户来源 qq wechat webo yqb',`channel_id` varchar(200) NULL,`type` varchar(1) NOT NULL COMMENT 'N=普通用户 A=认证用户 O=认证组织',`status` varchar(1) NOT NULL COMMENT 'A / I',PRIMARY KEY (`id`) ,UNIQUE INDEX `member_nickname_unique_index` (`nickname`))ENGINE=InnoDBDEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin;CREATE TABLE `YQB_REGION` (`id` int(10) NOT NULL AUTO_INCREMENT,`parent` int(10) NOT NULL,`regionName` varchar(10) NOT NULL,`regionType` int(1) NOT NULL,`status` varchar(1) NOT NULL COMMENT '地区表',PRIMARY KEY (`id`) )ENGINE=InnoDBDEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin;CREATE TABLE `TBL_MEMBER_ACCOUNT` (`id` int(10) NOT NULL AUTO_INCREMENT,`member_id` int(10) NOT NULL,`timebank` int(10) NOT NULL COMMENT '时间银行',`exp` int(10) NOT NULL,`level` int(10) NOT NULL,PRIMARY KEY (`id`) ,UNIQUE INDEX `member_account_unique_index` (`member_id`))ENGINE=InnoDBDEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin;CREATE TABLE `TBL_LEVEL_EXP_MAP` (`id` int(10) NOT NULL AUTO_INCREMENT,`exp` int(10) NOT NULL,`level` int(10) NOT NULL,PRIMARY KEY (`id`) )ENGINE=InnoDBDEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin;CREATE TABLE `TBL_MEMBER_FOLLOW` (`id` int(10) NOT NULL AUTO_INCREMENT,`member_id` int(10) NOT NULL,`follow_id` int(10) NOT NULL,PRIMARY KEY (`id`) ,UNIQUE INDEX `yqb_member_follow_unique_index` (`member_id`, `follow_id`))ENGINE=InnoDBDEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin;CREATE TABLE `TBL_FEEDBACK` (`id` int(10) NOT NULL AUTO_INCREMENT,`member_id` int(10) NOT NULL,`content` varchar(255) NOT NULL,PRIMARY KEY (`id`) )ENGINE=InnoDBDEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin;CREATE TABLE `TBL_CONFIG` (`id` int(10) NOT NULL AUTO_INCREMENT,`key` varchar(30) NULL,`value` varchar(200) NULL,PRIMARY KEY (`id`) )ENGINE=InnoDBDEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin;CREATE TABLE `TBL_MEMBER_DEVICE` (`id` int(10) NOT NULL AUTO_INCREMENT,`member_id` int(10) NOT NULL,`device_type` varchar(10) NOT NULL COMMENT 'android / ios',`device_id` varchar(50) NOT NULL,PRIMARY KEY (`id`) )ENGINE=InnoDBDEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin;CREATE TABLE `TBL_OPERATION_HIST` (`id` int(10) NOT NULL AUTO_INCREMENT,`member_id` int(10) NOT NULL,`action` varchar(20) NOT NULL,PRIMARY KEY (`id`) ,INDEX `operation_hist_member_id_index` (`member_id`))ENGINE=InnoDBDEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin;CREATE TABLE `TBL_MEMBER_ACCOUNT_HIST` (`id` int(10) NOT NULL AUTO_INCREMENT,`member_id` int(10) NOT NULL,`num` int(10) NOT NULL COMMENT '数量',`action_type` varchar(20) NOT NULL COMMENT '操作类型',`type` varchar(5) NOT NULL COMMENT 'TB = 时间银行 ,  EXP = 经验值',PRIMARY KEY (`id`) )ENGINE=InnoDBDEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin;ALTER TABLE `YQB_MEMBER_ACCOUNT` ADD CONSTRAINT `fk_member_account_1` FOREIGN KEY (`member_id`) REFERENCES `YQB_MEMBER` (`id`);ALTER TABLE `YQB_MEMBER_ACCOUNT_HIST` ADD CONSTRAINT `fk_member_account_hist_1` FOREIGN KEY (`member_id`) REFERENCES `YQB_MEMBER` (`id`);ALTER TABLE `YQB_FEEDBACK` ADD CONSTRAINT `fk_feedback_1` FOREIGN KEY (`member_id`) REFERENCES `YQB_MEMBER` (`id`);ALTER TABLE `YQB_MEMBER_DEVICE` ADD CONSTRAINT `fk_member_device_1` FOREIGN KEY (`member_id`) REFERENCES `YQB_MEMBER` (`id`);ALTER TABLE `YQB_MEMBER_FOLLOW` ADD CONSTRAINT `fk_member_follow_1` FOREIGN KEY (`member_id`) REFERENCES `YQB_MEMBER` (`id`);ALTER TABLE `YQB_MEMBER_FOLLOW` ADD CONSTRAINT `fk_member_follow_2` FOREIGN KEY (`follow_id`) REFERENCES `YQB_MEMBER` (`id`);ALTER TABLE `YQB_OPERATION_HIST` ADD CONSTRAINT `fk_operation_hist_1` FOREIGN KEY (`member_id`) REFERENCES `YQB_MEMBER` (`id`);