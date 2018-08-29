/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : seckill

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2018-08-15 13:27:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for seckill
-- ----------------------------
DROP TABLE IF EXISTS `seckill`;
CREATE TABLE `seckill` (
  `seckill_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
  `name` varchar(120) NOT NULL COMMENT '商品名称',
  `number` int(11) NOT NULL COMMENT '库存数量',
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`seckill_id`),
  KEY `idx_start_time` (`start_time`),
  KEY `idx_end_time` (`end_time`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1006 DEFAULT CHARSET=utf8 COMMENT='秒杀库存';

-- ----------------------------
-- Records of seckill
-- ----------------------------
INSERT INTO `seckill` VALUES ('1000', '1000元秒杀iphoneX', '100', '2018-11-01 00:00:00', '2018-11-02 00:00:00', '2018-08-02 11:24:22');
INSERT INTO `seckill` VALUES ('1001', '10元秒杀MARS', '200', '2018-11-01 00:00:00', '2018-11-02 00:00:00', '2018-08-02 11:24:22');
INSERT INTO `seckill` VALUES ('1002', '10000元秒杀gakki', '500', '2018-11-01 00:00:00', '2018-11-02 00:00:00', '2018-08-02 11:24:22');
INSERT INTO `seckill` VALUES ('1003', '999999999元秒杀ronaldo', '600', '2018-11-01 00:00:00', '2018-11-02 00:00:00', '2018-08-02 11:24:22');
INSERT INTO `seckill` VALUES ('1004', '100元秒杀Neymar', '400', '2018-01-01 00:00:00', '2018-05-05 00:00:00', '2018-08-02 11:24:22');
INSERT INTO `seckill` VALUES ('1005', '99元秒杀iphone50', '98', '2018-08-05 16:02:57', '2018-11-02 00:00:00', '2018-08-03 20:53:18');

-- ----------------------------
-- Table structure for success_killed
-- ----------------------------
DROP TABLE IF EXISTS `success_killed`;
CREATE TABLE `success_killed` (
  `seckill_id` bigint(20) NOT NULL,
  `user_phone` bigint(20) NOT NULL,
  `state` tinyint(4) NOT NULL DEFAULT '-1' COMMENT '状态-1表示无效，0表示成功，1表示已付款',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`seckill_id`,`user_phone`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功信息表';

-- ----------------------------
-- Records of success_killed
-- ----------------------------
INSERT INTO `success_killed` VALUES ('1000', '13982665808', '-1', '2018-08-03 09:54:42');
INSERT INTO `success_killed` VALUES ('1001', '13982665808', '0', '2018-08-03 10:27:29');
INSERT INTO `success_killed` VALUES ('1005', '12345678911', '0', '2018-08-05 16:02:57');
INSERT INTO `success_killed` VALUES ('1005', '13982665809', '0', '2018-08-03 20:55:02');
SET FOREIGN_KEY_CHECKS=1;
