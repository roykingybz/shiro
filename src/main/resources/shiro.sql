/*
 Navicat Premium Data Transfer

 Source Server         : 虚拟机
 Source Server Type    : MySQL
 Source Server Version : 50633
 Source Host           : 192.168.241.128:3306
 Source Schema         : shiro

 Target Server Type    : MySQL
 Target Server Version : 50633
 File Encoding         : 65001

 Date: 07/10/2018 19:15:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `id` int(11) NOT NULL,
  `role_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_croatian_ci NULL DEFAULT NULL,
  `permission_text` varchar(32) CHARACTER SET utf8 COLLATE utf8_croatian_ci NULL DEFAULT NULL,
  `user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_croatian_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_croatian_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL,
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci NULL DEFAULT NULL,
  `pass_word` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_croatian_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int(11) NOT NULL,
  `user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_croatian_ci NULL DEFAULT NULL,
  `role_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_croatian_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_croatian_ci COMMENT = '用户角色' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
