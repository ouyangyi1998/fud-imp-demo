DROP SCHEMA IF EXISTS `fud`;

CREATE SCHEMA IF NOT EXISTS `fud` DEFAULT CHARACTER SET utf8;

USE `fud`;


DROP TABLE IF EXISTS `fud`.`role`;
CREATE TABLE IF NOT EXISTS `fud`.`role`(
                                           `id` INT NOT NULL AUTO_INCREMENT COMMENT '角色id',
                                           `role_name` VARCHAR(16) NOT NULL COMMENT '角色名称',
                                           PRIMARY KEY(`id`)
)ENGINE = InnoDB DEFAULT CHARSET utf8 COMMENT '角色信息表';

INSERT INTO `fud`.`role`(role_name) VALUES('USER');
INSERT INTO `fud`.`role`(role_name) VALUES('ADMIN');
INSERT INTO `fud`.`role`(role_name) VALUES('SUPERVIP');

DROP TABLE IF EXISTS `fud`.`user`;
CREATE TABLE IF NOT EXISTS `fud`.`user`(
                                           `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户id',
                                           `username` VARCHAR(50) NOT NULL COMMENT '用户名',
                                           `password` VARCHAR(255) NOT NULL COMMENT '密码',
                                           `create_time` DATETIME NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
                                           `state` TINYINT(4) DEFAULT 0 COMMENT '状态',
                                           `role_id` INT COMMENT '角色id',
                                           PRIMARY KEY(`id`)
)ENGINE = InnoDB DEFAULT CHARSET utf8 COMMENT '用户信息表';

INSERT INTO `fud`.`user`(username, password, create_time, state, role_id) VALUES('admin', '4b252ef32f83fdec9ce52366a161dbc0', '2020-02-12 17:01:15', 0, 3);

DROP TABLE IF EXISTS `fud`.`file`;
CREATE TABLE IF NOT EXISTS `fud`.`file`(
                                           `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '文件id',
                                           `name` VARCHAR(255) NOT NULL COMMENT '文件名',
                                           `local_url` VARCHAR(255) NOT NULL COMMENT '文件地址',
                                           `size` VARCHAR(50) NOT NULL COMMENT '文件大小',
                                           `download_times` BIGINT NOT NULL DEFAULT 0 COMMENT '下载次数',
                                           `user_id` BIGINT NOT NULL COMMENT '用户id',
                                           `create_time` DATETIME NOT NULL DEFAULT current_timestamp COMMENT '创建日期',
                                           `md5` VARCHAR(50) DEFAULT '' COMMENT 'md5值',
                                           `suffix` varchar(25) DEFAULT ''COMMENT '文件后缀',
                                           `backup_url` varchar(255) NOT NULL COMMENT '备份路径',
                                           `uuid` varchar(32) DEFAULT '' COMMENT 'uuid',
                                           `status` int(11) DEFAULT 2 COMMENT '文件状态',
                                           PRIMARY KEY(`id`)
)ENGINE = InnoDB DEFAULT CHARSET utf8 COMMENT '文件信息表';

DROP TABLE IF EXISTS `fud`.`download`;
CREATE TABLE IF NOT EXISTS `fud`.`download`(
                                               `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '下载id',
                                               `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                               `user_id` BIGINT NOT NULL COMMENT '用户id',
                                               `file_id` BIGINT NOT NULL COMMENT '文件id',
                                               PRIMARY KEY(`id`)
)ENGINE = InnoDB DEFAULT CHARSET utf8 COMMENT '下载信息表';







