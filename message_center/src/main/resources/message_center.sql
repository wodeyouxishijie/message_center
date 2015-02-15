/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50619
Source Host           : 127.0.0.1:3306
Source Database       : message_center

Target Server Type    : MYSQL
Target Server Version : 50619
File Encoding         : 65001

Date: 2015-02-15 17:08:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for apps
-- ----------------------------
DROP TABLE IF EXISTS `apps`;
CREATE TABLE `apps` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_name` varchar(32) NOT NULL,
  `user` varchar(32) NOT NULL,
  `gmt_create` datetime NOT NULL,
  `user_phone` varchar(16) NOT NULL,
  `gmt_modified` datetime NOT NULL,
  `category_id` varchar(16) NOT NULL,
  `over_plus` bigint(20) NOT NULL,
  `user_name` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `max_length` int(11) NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1005 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of apps
-- ----------------------------
INSERT INTO `apps` VALUES ('1001', 'test', 'test', '2015-02-11 09:27:44', '15168381275', '2015-02-11 09:27:52', '10001', '3341', 'admin', '25d55ad283aa400af464c76d713c07ad', '110', '0');
INSERT INTO `apps` VALUES ('1002', 'project', 'admin', '2015-02-13 11:22:16', '151', '2015-02-13 11:22:16', '10001', '1', 'admin', '1234', '1', '0');
INSERT INTO `apps` VALUES ('1003', 'project', 'admin', '2015-02-13 11:24:04', '151', '2015-02-13 11:24:04', '10001', '1', 'admin', '1234', '1', '0');
INSERT INTO `apps` VALUES ('1004', 'project', 'admin', '2015-02-13 11:24:33', '151', '2015-02-13 11:24:33', '10001', '1', 'admin', '1234', '1', '1');

-- ----------------------------
-- Table structure for app_rules
-- ----------------------------
DROP TABLE IF EXISTS `app_rules`;
CREATE TABLE `app_rules` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_id` bigint(20) NOT NULL,
  `frequency_type` tinyint(1) NOT NULL,
  `max` bigint(20) NOT NULL,
  `gmt_create` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of app_rules
-- ----------------------------
INSERT INTO `app_rules` VALUES ('1', '1001', '3', '5000', '2015-02-12 11:57:38', '2015-02-12 11:57:40');

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(32) NOT NULL,
  `gmt_create` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10002 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('10001', '信息科', '2015-02-13 10:30:08', '2015-02-13 10:30:10', '0');

-- ----------------------------
-- Table structure for message_detail
-- ----------------------------
DROP TABLE IF EXISTS `message_detail`;
CREATE TABLE `message_detail` (
  `id` bigint(20) NOT NULL,
  `content` varchar(1024) NOT NULL,
  `rece_number` varchar(16) NOT NULL,
  `send_date` datetime NOT NULL,
  `send_status` tinyint(2) NOT NULL,
  `callback_status` tinyint(2) NOT NULL,
  `error_msg` varchar(32) DEFAULT NULL,
  `category_id` varchar(16) NOT NULL,
  `app_id` bigint(20) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `gmt_create` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  `counts` int(11) NOT NULL,
  `callback_time` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message_detail
-- ----------------------------

-- ----------------------------
-- Table structure for sequence
-- ----------------------------
DROP TABLE IF EXISTS `sequence`;
CREATE TABLE `sequence` (
  `name` varchar(20) NOT NULL,
  `value` bigint(20) NOT NULL,
  `gmt_modified` datetime NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sequence
-- ----------------------------
INSERT INTO `sequence` VALUES ('message_id', '12401', '2015-02-15 16:58:42');

-- ----------------------------
-- Table structure for template
-- ----------------------------
DROP TABLE IF EXISTS `template`;
CREATE TABLE `template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(32) NOT NULL,
  `TEMPLATE_STR` varchar(512) NOT NULL,
  `PARAM_LIST` varchar(512) NOT NULL,
  `PROJECT_ID` bigint(20) NOT NULL,
  `GMT_CREATE` datetime NOT NULL,
  `GMT_MODIFIED` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of template
-- ----------------------------
INSERT INTO `template` VALUES ('1', 'template', '$!{param}', 'param', '1001', '2015-02-11 09:52:50', '2015-02-11 09:52:53');
