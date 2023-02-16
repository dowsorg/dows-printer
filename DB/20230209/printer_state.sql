/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : dxz

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 12/02/2023 17:36:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for printer_state
-- ----------------------------
DROP TABLE IF EXISTS `printer_state`;
CREATE TABLE `printer_state`  (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `printer_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '打印机编号',
  `online_status` tinyint(1) NULL DEFAULT 0 COMMENT '设备联网状态',
  `work_status` tinyint(1) NULL DEFAULT 5 COMMENT '设备工作状态',
  `dt` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '时间戳',
  `deleted` tinyint(4) NULL DEFAULT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `printer_no`(`printer_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '打印机-状态' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
