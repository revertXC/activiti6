/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50716
 Source Host           : 127.0.0.1:3306
 Source Schema         : activiti_m

 Target Server Type    : MySQL
 Target Server Version : 50716
 File Encoding         : 65001

 Date: 22/05/2019 11:38:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_askforleave
-- ----------------------------
DROP TABLE IF EXISTS `t_askforleave`;
CREATE TABLE `t_askforleave`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '请假信息表',
  `pro_instance_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT 'activiti实例ID',
  `task_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '任务ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '请假姓名',
  `start_date` datetime(0) NULL DEFAULT NULL COMMENT '请假开始时间',
  `end_date` datetime(0) NULL DEFAULT NULL COMMENT '请假结束时间',
  `day` int(4) NULL DEFAULT NULL COMMENT '请假天数',
  `approver_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '审批人',
  `approver_result` int(2) NULL DEFAULT NULL COMMENT '审批结果  0未通过 1通过  2协商',
  `approver_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '审批结果 备注',
  `approver_date` datetime(0) NULL DEFAULT NULL COMMENT '审批时间',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(255) NULL DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
