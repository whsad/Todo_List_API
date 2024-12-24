/*
 Navicat Premium Data Transfer

 Source Server         : Localhost_MySql
 Source Server Type    : MySQL
 Source Server Version : 80037
 Source Host           : localhost:3306
 Source Schema         : todolist

 Target Server Type    : MySQL
 Target Server Version : 80037
 File Encoding         : 65001

 Date: 24/12/2024 20:51:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for todo
-- ----------------------------
DROP TABLE IF EXISTS `todo`;
CREATE TABLE `todo`  (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_bin NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_bin NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_bin NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of todo
-- ----------------------------
INSERT INTO `todo` VALUES ('05903a4e72ab4f42912a8c7177ec0ef5', '123', '123132');
INSERT INTO `todo` VALUES ('154a5f0e92da40cc8e90f4684df04526', 'test', 'test');
INSERT INTO `todo` VALUES ('23d8e919d8c34f778300ede8f12abae8', '123', '123132');
INSERT INTO `todo` VALUES ('32966b956a02486e9b4906dfd3a66f98', '123', '123132');
INSERT INTO `todo` VALUES ('6ab64efae4ad410eace3bbaace0da6dc', '123', '123132');
INSERT INTO `todo` VALUES ('76655b6eff4f4e90be4ddb7a80276284', '123', '123132');
INSERT INTO `todo` VALUES ('7982f5a2660d42c5911b2d0b63e2a6fb', '123', '123132');
INSERT INTO `todo` VALUES ('847f2483b3094087884f817ad057fd6d', '123', '123132');
INSERT INTO `todo` VALUES ('8d640c281897402387468d8d5c10a845', '123', '123132');
INSERT INTO `todo` VALUES ('9b2d880e55ac442f8545ff88575ea508', '123', '123132');
INSERT INTO `todo` VALUES ('9b9cfdd4dbe940f7aebaa7b469a12f6f', '123', '123132');
INSERT INTO `todo` VALUES ('dd638f6cef8d47679e2a66e237006ff3', '123', '123132');
INSERT INTO `todo` VALUES ('eea921190c4f4cf297900cfdbf765b8e', '123', '123132');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_bin NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_bin NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_bin NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_bin NOT NULL,
  `authority` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_bin NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('423242714d824669b4656f22cd789edc', 'kirito', 'kirito1231@163.com', '$2a$10$.sh5ibmdOjuyXYUNXAook.sFNFRFVQPtFG6G2ROLKMg7U4DczIC52', 'user');
INSERT INTO `user` VALUES ('cdc64170957348b890eceeb9e95e801e', 'kirito', 'kirito123@163.com', '$2a$10$5DVAaravWrqMpWJLILOgB.rXBUM.Xbzm6DKTia6zp9P8NRskLwqxu', 'admin');

SET FOREIGN_KEY_CHECKS = 1;
