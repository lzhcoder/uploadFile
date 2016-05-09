/*
Navicat MySQL Data Transfer

Source Server         : btkj
Source Server Version : 50616
Source Host           : botannrds.mysql.rds.aliyuncs.com:3306
Source Database       : botann_marketing_plt

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2016-04-27 17:11:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tmk_fileinfo
-- ----------------------------
DROP TABLE IF EXISTS `tmk_fileinfo`;
CREATE TABLE `tmk_fileinfo` (
  `id` int(11) NOT NULL,
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
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tmk_fileinfo
-- ----------------------------
