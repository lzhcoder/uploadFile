/*
Navicat MySQL Data Transfer

Source Server         : botanndesign
Source Server Version : 50616
Source Host           : botannrds.mysql.rds.aliyuncs.com:3306
Source Database       : botanndesign

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2016-11-08 16:22:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_fileinfo
-- ----------------------------
DROP TABLE IF EXISTS `t_fileinfo`;
CREATE TABLE `t_fileinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vid` varchar(128) DEFAULT NULL,
  `registerDate` date DEFAULT NULL,
  `uploadDate` date DEFAULT NULL,
  `relativePath` varchar(256) DEFAULT NULL,
  `fullPath` varchar(256) DEFAULT NULL,
  `extName` varchar(4) DEFAULT NULL,
  `origin` varchar(2) DEFAULT NULL,
  `del` int(11) DEFAULT '0',
  `fileSize` int(11) DEFAULT '0',
  `note` varchar(256) DEFAULT NULL,
  `uploadIP` varchar(16) DEFAULT NULL,
  `originName` varchar(255) DEFAULT NULL COMMENT '原始文件名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1299 DEFAULT CHARSET=utf8;
