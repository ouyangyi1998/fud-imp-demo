# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.29)
# Database: fud
# Generation Time: 2021-04-07 10:36:41 +0000
# ************************************************************

CREATE DATABASE IF NOT EXISTS fud;

# Dump of table comment
# ------------------------------------------------------------
USE fud;
DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `commentor` int(11) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `like_count` int(11) DEFAULT '0',
  `content` varchar(200) NOT NULL,
  `comment_count` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;

INSERT INTO `comment` (`id`, `parent_id`, `type`, `commentor`, `create_time`, `like_count`, `content`, `comment_count`)
VALUES
	(1,2,1,1,1614417167175,0,'fsdf',0),
	(2,2,1,1,1614417180296,0,'JKFJ',1),
	(3,2,1,1,1614418240037,0,'afa',2),
	(4,1,1,1,1614418345988,0,'你好啊',0),
	(5,3,2,1,1614506700500,0,'as',1),
	(6,2,2,1,1614508713406,0,'范德萨发生',0),
	(7,3,2,1,1614508726560,0,'发生的范德萨',4),
	(8,7,2,1,1614510396613,0,'we',0),
	(9,3,1,1,1614510880703,0,'sfa',1),
	(10,3,1,1,1614510886484,0,'vcxnvkz',1),
	(11,7,2,1,1614511027502,0,'assd',0),
	(12,5,2,1,1614511036906,0,'qwee',0),
	(13,10,2,1,1614591060803,0,'woaini',0),
	(14,7,2,1,1614591239325,0,'asdf',0),
	(15,4,1,1,1614670052930,0,'asd',0),
	(16,12,1,1,1614679884554,0,'哈哈哈哈哈',0),
	(17,14,1,1,1614913651431,0,'qw',0),
	(18,14,1,1,1615042005405,0,'as\n',0),
	(19,13,1,1,1615042035262,0,'qw',0),
	(20,13,1,1,1615042040729,0,'as',0),
	(21,14,1,1,1615044625652,0,'az\n',0),
	(22,14,1,1,1615044629025,0,'zxc',0),
	(23,14,1,1,1615044632900,0,'ccvx',1),
	(24,9,2,1,1617590293253,0,'你好',0),
	(25,23,2,1,1617590872249,0,'cnm',0),
	(26,3,1,1,1617592047656,0,'sdk',1),
	(27,26,2,3,1617593206488,0,'wer',0),
	(28,3,1,3,1617593365890,0,'qwe\n',0),
	(29,14,1,1,1617608879392,0,'威尔',0),
	(30,3,1,3,1617609081909,0,'123',1),
	(31,7,2,3,1617609926991,0,'ssd',0),
	(32,2,1,1,1617615604553,0,'wwer',0),
	(33,2,1,1,1617615646838,0,'bjk',0),
	(34,3,1,3,1617615671887,0,'123',2),
	(35,34,2,3,1617615679055,0,'123',0),
	(36,34,2,3,1617615740237,0,'hkkj',0),
	(37,30,2,3,1617615748520,0,'ee',0),
	(38,9,1,1,1617616778437,0,'we',0),
	(39,15,1,1,1617618109347,0,'为',0);

/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table download
# ------------------------------------------------------------

DROP TABLE IF EXISTS `download`;

CREATE TABLE `download` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '下载id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `file_id` bigint(20) NOT NULL COMMENT '文件id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='下载信息表';

LOCK TABLES `download` WRITE;
/*!40000 ALTER TABLE `download` DISABLE KEYS */;

INSERT INTO `download` (`id`, `create_time`, `user_id`, `file_id`)
VALUES
	(1,'2021-04-01 16:02:26',1,1),
	(2,'2021-03-02 16:16:46',1,2),
	(3,'2021-03-29 21:39:05',1,15),
	(20,'2021-03-28 21:39:05',1,1),
	(21,'2021-03-28 21:39:05',1,1),
	(22,'2021-03-28 21:39:05',1,1),
	(23,'2021-03-27 21:39:05',1,1),
	(24,'2021-03-27 21:39:05',1,1),
	(25,'2021-03-26 21:39:05',1,1),
	(26,'2021-03-26 21:39:05',1,1),
	(27,'2021-03-26 21:39:05',1,1),
	(28,'2021-03-25 21:39:05',1,1),
	(32,'2021-03-30 21:39:05',1,1),
	(33,'2021-03-31 21:39:05',1,1),
	(34,'2021-03-31 21:39:05',1,1),
	(35,'2021-03-31 21:39:05',1,1);

/*!40000 ALTER TABLE `download` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table file
# ------------------------------------------------------------

DROP TABLE IF EXISTS `file`;

CREATE TABLE `file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '文件id',
  `name` varchar(255) NOT NULL COMMENT '文件名',
  `local_url` varchar(255) NOT NULL COMMENT '文件地址',
  `size` varchar(50) NOT NULL COMMENT '文件大小',
  `download_times` bigint(20) NOT NULL DEFAULT '0' COMMENT '下载次数',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `md5` varchar(50) DEFAULT '' COMMENT 'md5值',
  `suffix` varchar(25) DEFAULT '' COMMENT '文件后缀',
  `backup_url` varchar(255) NOT NULL COMMENT '备份路径',
  `uuid` varchar(32) DEFAULT '' COMMENT 'uuid',
  `status` int(11) DEFAULT '2' COMMENT '文件状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='文件信息表';

LOCK TABLES `file` WRITE;
/*!40000 ALTER TABLE `file` DISABLE KEYS */;

INSERT INTO `file` (`id`, `name`, `local_url`, `size`, `download_times`, `user_id`, `create_time`, `md5`, `suffix`, `backup_url`, `uuid`, `status`)
VALUES
	(2,'wallhaven-mp1gwy','/Users/ouyangyi/Downloads/test/real/1/1614672993083430705/wallhaven-mp1gwy.jpg','645.06KB',1,1,'2021-03-02 16:16:33','276b52cd68c2bd6c019a00653ce16920','.jpg','/Users/ouyangyi/Downloads/test/backup/276b52cd68c2bd6c019a00653ce16920','1614672993083430705',2),
	(6,'2.0','/Users/ouyangyi/Downloads/test/real/1/1614673656738443681/2.0.zip','5.11MB',0,1,'2021-03-02 16:27:37','41800e4f179497fd7625f023bb3432d1','.zip','/Users/ouyangyi/Downloads/test/backup/41800e4f179497fd7625f023bb3432d1','1614673656738443681',2),
	(7,'xmppserver-4.2.0','/Users/ouyangyi/Downloads/test/real/1/1614916034538418735/xmppserver-4.2.0.jar','1.99MB',0,1,'2021-03-05 11:47:15','d32b46b92dc46653861d8c1d68a3659b','.jar','/Users/ouyangyi/Downloads/test/backup/d32b46b92dc46653861d8c1d68a3659b','1614916034538418735',2),
	(9,'IDOCR.PubSdk.Linux.Centos.Ohter.Test-4.0.0','/Users/ouyangyi/Downloads/test/real/1/1614917985192533565/IDOCR.PubSdk.Linux.Centos.Ohter.Test-4.0.0.zip','1.06MB',0,1,'2021-03-05 12:19:45','fda974c75dfe30caaa8cc0817d6ad178','.zip','/Users/ouyangyi/Downloads/test/backup/fda974c75dfe30caaa8cc0817d6ad178','1614917985192533565',2),
	(10,'wallhaven-3k6w76','/Users/ouyangyi/Downloads/test/real/1/1614918000136481912/wallhaven-3k6w76.png','6.71MB',0,1,'2021-03-05 12:20:00','37a835ebe657380954eeb68458aac554','.png','/Users/ouyangyi/Downloads/test/backup/37a835ebe657380954eeb68458aac554','1614918000136481912',2),
	(12,'客服系统测试开发文档','/Users/ouyangyi/Downloads/test/real/1/1614920370491873880/客服系统测试开发文档.pdf','2.65MB',0,1,'2021-03-05 12:59:31','fd9d6bbe596b3c9a316fca2e1ea27db9','.pdf','/Users/ouyangyi/Downloads/test/backup/fd9d6bbe596b3c9a316fca2e1ea27db9','1614920370491873880',2),
	(13,'ithome_macos_7.65','/Users/ouyangyi/Downloads/test/real/1/1614928699509795479/ithome_macos_7.65.dmg','34.28MB',0,1,'2021-03-05 15:18:20','54e0343460d2776a7e102c8acb8c3371','.dmg','/Users/ouyangyi/Downloads/test/backup/54e0343460d2776a7e102c8acb8c3371','1614928699509795479',2),
	(15,'wallhaven-mp1gwy','/Users/ouyangyi/Downloads/test/real/2/1616938736243797399/wallhaven-mp1gwy.jpg','645.06KB',1,2,'2021-03-28 21:38:56','276b52cd68c2bd6c019a00653ce16920','.jpg','/Users/ouyangyi/Downloads/test/backup/276b52cd68c2bd6c019a00653ce16920','1616938736243797399',2),
	(16,'bg','/Users/ouyangyi/Downloads/test/real/1/1617504941668240923/bg.jpg','1.40MB',0,1,'2021-04-04 10:55:42','0cd190a85f98007027fa717c0b535b30','.jpg','/Users/ouyangyi/Downloads/test/backup/0cd190a85f98007027fa717c0b535b30','1617504941668240923',2),
	(18,'wallhaven-mp1gwy','/Users/ouyangyi/Downloads/test/real/3/1617592460813620303/wallhaven-mp1gwy.jpg','645.06KB',0,3,'2021-04-05 11:14:21','276b52cd68c2bd6c019a00653ce16920','.jpg','/Users/ouyangyi/Downloads/test/backup/276b52cd68c2bd6c019a00653ce16920','1617592460813620303',2);

/*!40000 ALTER TABLE `file` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table great
# ------------------------------------------------------------

DROP TABLE IF EXISTS `great`;

CREATE TABLE `great` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `question_id` int(11) NOT NULL,
  `comment_id` int(11) NOT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;

LOCK TABLES `great` WRITE;
/*!40000 ALTER TABLE `great` DISABLE KEYS */;

INSERT INTO `great` (`id`, `user_id`, `question_id`, `comment_id`, `create_time`)
VALUES
	(25,1,14,21,NULL),
	(26,2,14,23,NULL),
	(27,1,14,23,NULL),
	(29,1,4,15,NULL),
	(44,1,14,22,NULL),
	(47,3,3,26,NULL),
	(53,1,3,5,NULL),
	(61,1,3,9,NULL),
	(62,1,3,30,NULL),
	(63,1,3,28,NULL),
	(64,3,3,34,NULL),
	(65,3,3,30,NULL),
	(66,1,1,4,NULL),
	(67,1,15,39,NULL);

/*!40000 ALTER TABLE `great` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table notification
# ------------------------------------------------------------

DROP TABLE IF EXISTS `notification`;

CREATE TABLE `notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `notifier` int(11) NOT NULL,
  `receiver` int(11) NOT NULL,
  `outer_id` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `create_time` bigint(20) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;

INSERT INTO `notification` (`id`, `notifier`, `receiver`, `outer_id`, `type`, `create_time`, `status`)
VALUES
	(1,1,1,2,2,1614417167180,1),
	(2,1,1,2,2,1614417180299,1),
	(3,1,1,2,2,1614418240040,1),
	(4,1,1,1,2,1614418345990,1),
	(5,1,1,3,1,1614506700503,1),
	(6,1,1,2,1,1614508713409,1),
	(7,1,1,3,1,1614508726564,1),
	(8,1,1,7,1,1614510396617,1),
	(9,1,1,3,2,1614510880706,1),
	(10,1,1,3,2,1614510886486,1),
	(11,1,1,7,1,1614511027505,1),
	(12,1,1,5,1,1614511036909,1),
	(13,1,1,10,1,1614591060805,1),
	(14,1,1,7,1,1614591239328,1),
	(15,1,1,4,2,1614670052933,1),
	(16,1,1,12,2,1614679884557,1),
	(17,1,1,14,2,1614913651438,1),
	(18,1,1,14,2,1615042005409,1),
	(19,1,1,13,2,1615042035263,1),
	(20,1,1,13,2,1615042040730,1),
	(21,1,1,14,2,1615044625655,1),
	(22,1,1,14,2,1615044629028,1),
	(23,1,1,14,2,1615044632902,1),
	(24,1,1,9,1,1617590293255,1),
	(25,1,1,23,1,1617590872250,1),
	(26,1,1,3,2,1617592047659,1),
	(27,3,1,26,1,1617593206491,1),
	(28,3,1,3,2,1617593365892,1),
	(29,1,1,14,2,1617608879394,1),
	(30,3,1,3,2,1617609081911,1),
	(31,3,1,7,1,1617609926993,1),
	(32,1,1,2,2,1617615604554,1),
	(33,1,1,2,2,1617615646839,1),
	(34,3,1,3,2,1617615671888,1),
	(35,3,3,34,1,1617615679056,0),
	(36,3,3,34,1,1617615740239,0),
	(37,3,3,30,1,1617615748521,1),
	(38,1,1,9,2,1617616778440,1),
	(39,1,1,15,2,1617618109348,1);

/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table question
# ------------------------------------------------------------

DROP TABLE IF EXISTS `question`;

CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `description` text NOT NULL,
  `create_id` int(11) NOT NULL,
  `comment_count` int(11) NOT NULL DEFAULT '0',
  `view_count` int(11) NOT NULL DEFAULT '0',
  `like_count` int(11) NOT NULL DEFAULT '0',
  `tag` varchar(250) NOT NULL,
  `create_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;

INSERT INTO `question` (`id`, `title`, `description`, `create_id`, `comment_count`, `view_count`, `like_count`, `tag`, `create_time`)
VALUES
	(1,'Android开发教程','hello world',1,1,22,0,'Android',1614418320216),
	(2,'Java开发手册','hello world',1,5,56,0,'Java',1614418337464),
	(3,'你好啊 我爱你','还行啊\r\n**分段锁**\r\n# **bbbb**\r\n> - hh\r\n~~bbb~~',1,6,148,0,'SVN',1617590712435),
	(4,'Spring Boot开发','# 发送富士达\r\n> 富士达\r\n~~发生大放送~~',1,1,15,0,'Java',1614667161588),
	(5,'Android 自学','#### 发生大幅度',1,0,4,0,'Android',1614667571856),
	(6,'Xboxone','#是删除SV',1,0,5,0,'we 12',1614667802752),
	(7,'xxx','fsdfsd',1,0,9,0,'fsdf',1614669742721),
	(8,'vcxv','fsdf',1,0,0,0,'fsdfsd',1614669750453),
	(9,'fsdfsd','fsfsdf',1,1,8,0,'fsfs',1614669758571),
	(10,'fsdfsdf','fsdfsdfsdfs',1,0,4,0,'fsfsd',1614669768922),
	(11,'vxcvxc','vxv',1,0,2,0,'vxvx',1614669778705),
	(12,' 你好','行行行',1,1,17,0,'图片,书籍,系统BUG',1614675145770),
	(13,'qw','2021-03-04 23:44:24 星期四',1,2,16,0,'书籍',1614872670146),
	(14,'我爱Java','发生的范德萨',1,6,103,0,'数码',1614872795148),
	(15,'我的爱好','------------\r\n\r\n1. - ',1,1,10,0,'咨询管理员',1617618912287);

/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(16) NOT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色信息表';

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;

INSERT INTO `role` (`id`, `role_name`)
VALUES
	(1,'USER'),
	(2,'ADMIN'),
	(3,'SUPERVIP');

/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `state` tinyint(4) DEFAULT '0' COMMENT '状态',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `head_picture` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;

INSERT INTO `user` (`id`, `username`, `password`, `create_time`, `state`, `role_id`, `head_picture`)
VALUES
	(1,'admin','4b252ef32f83fdec9ce52366a161dbc0','2020-02-12 17:01:15',0,3,'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimgqn.koudaitong.com%2Fupload_files%2F2015%2F03%2F15%2FFprrhVQiB__Y6bTLcKmtuDQK9Gl0.jpg&refer=http%3A%2F%2Fimgqn.koudaitong.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1620005599&t=8af3bffa5ff3893618cef3ac4395e184'),
	(2,'wer','83fec7de9c629e039750bdf15833995e','2021-03-28 21:38:11',0,1,'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimgqn.koudaitong.com%2Fupload_files%2F2015%2F03%2F15%2FFprrhVQiB__Y6bTLcKmtuDQK9Gl0.jpg&refer=http%3A%2F%2Fimgqn.koudaitong.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1620005599&t=8af3bffa5ff3893618cef3ac4395e184'),
	(3,'123','5b0f54a33556086f615bbdbf202fd55c','2021-04-05 11:13:42',0,1,'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimgqn.koudaitong.com%2Fupload_files%2F2015%2F03%2F15%2FFprrhVQiB__Y6bTLcKmtuDQK9Gl0.jpg&refer=http%3A%2F%2Fimgqn.koudaitong.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1620005599&t=8af3bffa5ff3893618cef3ac4395e184');

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;



