/*
 Navicat Premium Dump SQL

 Source Server         : SRIC
 Source Server Type    : MySQL
 Source Server Version : 90200 (9.2.0)
 Source Host           : localhost:3306
 Source Schema         : sric_pms_public

 Target Server Type    : MySQL
 Target Server Version : 90200 (9.2.0)
 File Encoding         : 65001

 Date: 23/05/2025 17:08:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category_library
-- ----------------------------
DROP TABLE IF EXISTS `category_library`;
CREATE TABLE `category_library` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 类别名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='类别表\r\n 书库的类别';

-- ----------------------------
-- Records of category_library
-- ----------------------------
BEGIN;
INSERT INTO `category_library` (`id`, `name`) VALUES (1, '吃的');
INSERT INTO `category_library` (`id`, `name`) VALUES (3, 'vue');
INSERT INTO `category_library` (`id`, `name`) VALUES (4, 'springboot');
INSERT INTO `category_library` (`id`, `name`) VALUES (5, 'swift');
COMMIT;

-- ----------------------------
-- Table structure for category_resources
-- ----------------------------
DROP TABLE IF EXISTS `category_resources`;
CREATE TABLE `category_resources` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 类别名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='类别表\r\n 资源的类别';

-- ----------------------------
-- Records of category_resources
-- ----------------------------
BEGIN;
INSERT INTO `category_resources` (`id`, `name`) VALUES (10, 'LOGO');
COMMIT;

-- ----------------------------
-- Table structure for data_h_comic
-- ----------------------------
DROP TABLE IF EXISTS `data_h_comic`;
CREATE TABLE `data_h_comic` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `filename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件名称',
  `pages` int unsigned NOT NULL COMMENT '总页数',
  `mosaic` int unsigned NOT NULL COMMENT '修正（1：无修正，2：有修正，）',
  `category` int unsigned NOT NULL COMMENT '类型（1：短篇，2：单本，）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=363 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='数据表\r\n 漫画的数据';

-- ----------------------------
-- Records of data_h_comic
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for data_h_comic_tags
-- ----------------------------
DROP TABLE IF EXISTS `data_h_comic_tags`;
CREATE TABLE `data_h_comic_tags` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'tag名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='数据表\r\n 漫画的标签数据';

-- ----------------------------
-- Records of data_h_comic_tags
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for data_h_picture
-- ----------------------------
DROP TABLE IF EXISTS `data_h_picture`;
CREATE TABLE `data_h_picture` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `filename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'webp' COMMENT '文件名称(后缀)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1263 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='数据表\r\n 图片的数据';

-- ----------------------------
-- Records of data_h_picture
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for data_h_video
-- ----------------------------
DROP TABLE IF EXISTS `data_h_video`;
CREATE TABLE `data_h_video` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `filename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件名称',
  `number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '番号',
  `mosaic` int unsigned NOT NULL COMMENT '修正（1：无修正，2：有修正，）',
  `category` int unsigned NOT NULL COMMENT '类型（1：REAL，2：3D MMD，3：2D，）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=299 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='数据表\r\n 视频的数据';

-- ----------------------------
-- Records of data_h_video
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for data_h_video_tags
-- ----------------------------
DROP TABLE IF EXISTS `data_h_video_tags`;
CREATE TABLE `data_h_video_tags` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'tag名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='数据表\r\n 视频的标签数据';

-- ----------------------------
-- Records of data_h_video_tags
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for data_library
-- ----------------------------
DROP TABLE IF EXISTS `data_library`;
CREATE TABLE `data_library` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `filename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件名称',
  `category` int unsigned NOT NULL COMMENT '类别（对应id）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='数据表\r\n 书库的数据';

-- ----------------------------
-- Records of data_library
-- ----------------------------
BEGIN;
INSERT INTO `data_library` (`id`, `title`, `filename`, `category`) VALUES (1, '豪赤的红烧肉', '1.md', 1);
INSERT INTO `data_library` (`id`, `title`, `filename`, `category`) VALUES (2, '番茄牛腩', '2.md', 1);
INSERT INTO `data_library` (`id`, `title`, `filename`, `category`) VALUES (4, 'SPRINGBOOT 与 MYBATIS 后端四层架构', '4.md', 4);
INSERT INTO `data_library` (`id`, `title`, `filename`, `category`) VALUES (5, 'vuecli对vue3项目的创建与依赖的引入', '5.md', 3);
INSERT INTO `data_library` (`id`, `title`, `filename`, `category`) VALUES (6, 'VUE2中组件间的传值与VUEX', '6.md', 3);
INSERT INTO `data_library` (`id`, `title`, `filename`, `category`) VALUES (7, 'VUE中的简单动画效果', '7.md', 3);
INSERT INTO `data_library` (`id`, `title`, `filename`, `category`) VALUES (8, 'swift data 基础数据操作样例', '8.md', 5);
COMMIT;

-- ----------------------------
-- Table structure for data_notes
-- ----------------------------
DROP TABLE IF EXISTS `data_notes`;
CREATE TABLE `data_notes` (
  `uuid` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '主键',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `text` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `user_uuid` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建用户uuid',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `is_deleted` int unsigned NOT NULL DEFAULT '0' COMMENT '是否删除（0：未删除，1：已删除）',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='数据表\r\n 笔记数据';

-- ----------------------------
-- Records of data_notes
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for data_project
-- ----------------------------
DROP TABLE IF EXISTS `data_project`;
CREATE TABLE `data_project` (
  `uuid` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
  `is_ended` int unsigned NOT NULL DEFAULT '0' COMMENT '是否结束项目（0：未结束，1：已结束）',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='数据表\r\n 项目数据';

-- ----------------------------
-- Records of data_project
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for data_project_item
-- ----------------------------
DROP TABLE IF EXISTS `data_project_item`;
CREATE TABLE `data_project_item` (
  `uuid` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '主键',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `project_uuid` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '关联项目uuid',
  `create_user_uuid` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建用户uuid',
  `assign_user_uuid` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '指派用户uuid',
  `is_ended` int unsigned NOT NULL DEFAULT '0' COMMENT '是否结束（0：未结束，1：已结束）',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='数据表\r\n 项目内容数据';

-- ----------------------------
-- Records of data_project_item
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for data_resources
-- ----------------------------
DROP TABLE IF EXISTS `data_resources`;
CREATE TABLE `data_resources` (
  `uuid` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `filename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件名称',
  `level` int unsigned NOT NULL COMMENT '权限等级',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `category` int unsigned NOT NULL COMMENT '类别（对应id）',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件类型',
  `size` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件大小',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='数据表\r\n 资源对应的数据';

-- ----------------------------
-- Records of data_resources
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for data_user
-- ----------------------------
DROP TABLE IF EXISTS `data_user`;
CREATE TABLE `data_user` (
  `uuid` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '主键',
  `UID` int unsigned NOT NULL AUTO_INCREMENT COMMENT '用户UID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `mail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱',
  `create_time` datetime NOT NULL COMMENT '注册时间',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'NULL.webp' COMMENT '头像文件名称',
  `mark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'NULL MARK' COMMENT '个人签名',
  `status` int unsigned NOT NULL DEFAULT '9' COMMENT '权限',
  PRIMARY KEY (`uuid`) USING BTREE,
  KEY `uidkey` (`UID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='数据表\r\n 用户数据';

-- ----------------------------
-- Records of data_user
-- ----------------------------
BEGIN;
INSERT INTO `data_user` (`uuid`, `UID`, `username`, `password`, `mail`, `create_time`, `avatar`, `mark`, `status`) VALUES ('5e1d0a6c-37ad-11f0-b1de-82c5565ed616', 5, 'SRIC-003', '21f45b25479c4f4d2cec012e0eb402abaf2a88cf27b2860b70976b9181cd9ca9', 'soraincloud@gmail.com', '2025-05-23 16:10:19', 'NULL.webp', '我是003 我为我自己代言 QwQ\n', 9);
INSERT INTO `data_user` (`uuid`, `UID`, `username`, `password`, `mail`, `create_time`, `avatar`, `mark`, `status`) VALUES ('ab1094e8-37ac-11f0-b1de-82c5565ed616', 4, 'SRIC-002', '3e91e35b9058e250119be418c17f8c551acb5b13e678add73e5660bc7dd19a1c', '3355905055@qq.com', '2025-05-23 16:05:19', 'NULL.webp', '这里是 002\n具体事宜请联系 3355905055@qq.com', 9);
INSERT INTO `data_user` (`uuid`, `UID`, `username`, `password`, `mail`, `create_time`, `avatar`, `mark`, `status`) VALUES ('e1b2a6fe-37a6-11f0-b1de-82c5565ed616', 3, 'SRIC-001', 'be15535d03c124dcb95add560f5632baee064fe2d2b73bc10e43fd872efc08ba', 'soraincloud@foxmail.com', '2025-05-23 15:23:53', 'NULL.webp', 'NULL MARK', 1);
COMMIT;

-- ----------------------------
-- Table structure for permission_main_menu
-- ----------------------------
DROP TABLE IF EXISTS `permission_main_menu`;
CREATE TABLE `permission_main_menu` (
  `uuid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 名称',
  `level` int unsigned NOT NULL COMMENT '权限等级',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='权限表\r\n 对主菜单的权限控制';

-- ----------------------------
-- Records of permission_main_menu
-- ----------------------------
BEGIN;
INSERT INTO `permission_main_menu` (`uuid`, `name`, `level`, `description`) VALUES ('478ccc77-992f-11ef-bf4d-7c10c9c0a516', 'Notes', 9, '笔记菜单');
INSERT INTO `permission_main_menu` (`uuid`, `name`, `level`, `description`) VALUES ('4a5ab763-bbd1-11ef-9d4c-7c10c9c0a516', 'Project', 9, '项目菜单');
INSERT INTO `permission_main_menu` (`uuid`, `name`, `level`, `description`) VALUES ('99e89acd-9305-11ef-8959-7c10c9c0a516', 'Library', 10, '知识库菜单');
INSERT INTO `permission_main_menu` (`uuid`, `name`, `level`, `description`) VALUES ('a401f45b-9305-11ef-8959-7c10c9c0a516', 'Hfiles', 9, '媒体菜单');
INSERT INTO `permission_main_menu` (`uuid`, `name`, `level`, `description`) VALUES ('ded885ab-b4d6-11ef-aa19-7c10c9c0a516', 'Resources', 10, '资源菜单');
COMMIT;

-- ----------------------------
-- Table structure for permission_request
-- ----------------------------
DROP TABLE IF EXISTS `permission_request`;
CREATE TABLE `permission_request` (
  `uuid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `request_mapping` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求uri模块',
  `level` int unsigned NOT NULL COMMENT '权限等级',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='权限表\r\n 对后端接口的权限控制';

-- ----------------------------
-- Records of permission_request
-- ----------------------------
BEGIN;
INSERT INTO `permission_request` (`uuid`, `request_mapping`, `level`, `description`) VALUES ('057857d4-b570-11ef-aa19-7c10c9c0a516', 'resources', 10, '资源模块');
INSERT INTO `permission_request` (`uuid`, `request_mapping`, `level`, `description`) VALUES ('112cb3e5-b570-11ef-aa19-7c10c9c0a516', 'resourcesManage', 3, '资源管理模块');
INSERT INTO `permission_request` (`uuid`, `request_mapping`, `level`, `description`) VALUES ('1c0e8139-9ea6-11ef-95ed-7c10c9c0a516', 'notes', 9, '笔记模块');
INSERT INTO `permission_request` (`uuid`, `request_mapping`, `level`, `description`) VALUES ('1cde2aa7-ba00-11ef-9d4c-7c10c9c0a516', 'projectManage', 3, '项目管理模块');
INSERT INTO `permission_request` (`uuid`, `request_mapping`, `level`, `description`) VALUES ('26378016-9ea6-11ef-95ed-7c10c9c0a516', 'notesManage', 3, '笔记管理模块');
INSERT INTO `permission_request` (`uuid`, `request_mapping`, `level`, `description`) VALUES ('2651bf6e-ba00-11ef-9d4c-7c10c9c0a516', 'project', 9, '项目模块');
INSERT INTO `permission_request` (`uuid`, `request_mapping`, `level`, `description`) VALUES ('34faf53d-8e56-11ef-8f3b-7c10c9c0a516', 'libraryManage', 3, '知识库管理模块');
INSERT INTO `permission_request` (`uuid`, `request_mapping`, `level`, `description`) VALUES ('5e551f18-8e56-11ef-8f3b-7c10c9c0a516', 'library', 10, '知识库模块');
INSERT INTO `permission_request` (`uuid`, `request_mapping`, `level`, `description`) VALUES ('75e86af4-8e33-11ef-8f3b-7c10c9c0a516', 'userManage', 1, '用户管理模块');
INSERT INTO `permission_request` (`uuid`, `request_mapping`, `level`, `description`) VALUES ('863dd17a-93d4-11ef-8894-7c10c9c0a516', 'permissionMainMenuManage', 1, '主菜单权限管理模块');
INSERT INTO `permission_request` (`uuid`, `request_mapping`, `level`, `description`) VALUES ('8966a9ee-8e56-11ef-8f3b-7c10c9c0a516', 'h', 9, '媒体浏览模块');
INSERT INTO `permission_request` (`uuid`, `request_mapping`, `level`, `description`) VALUES ('bd9bdc8a-9305-11ef-8959-7c10c9c0a516', 'permissionMainMenu', 10, '主菜单获取');
INSERT INTO `permission_request` (`uuid`, `request_mapping`, `level`, `description`) VALUES ('c4df9159-8e55-11ef-8f3b-7c10c9c0a516', 'permissionRequest', 1, '接口权限管理模块');
INSERT INTO `permission_request` (`uuid`, `request_mapping`, `level`, `description`) VALUES ('dbd09ed2-c095-11ef-9829-7c10c9c0a516', 'hManage', 6, ' 媒体管理模块');
INSERT INTO `permission_request` (`uuid`, `request_mapping`, `level`, `description`) VALUES ('e8f07304-8d87-11ef-8f3b-7c10c9c0a516', 'user', 10, '基础用户服务模块');
INSERT INTO `permission_request` (`uuid`, `request_mapping`, `level`, `description`) VALUES ('f524021f-c774-11ef-9a44-7c10c9c0a516', 'projectItem', 9, '项目内容模块');
COMMIT;

-- ----------------------------
-- Table structure for relation_h_comic_tag
-- ----------------------------
DROP TABLE IF EXISTS `relation_h_comic_tag`;
CREATE TABLE `relation_h_comic_tag` (
  `uuid` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '主键',
  `tagid` int unsigned NOT NULL COMMENT '对应tag的id',
  `comicid` int unsigned NOT NULL COMMENT '对应comic的id',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='关系表\r\n 漫画与对应标签的关系';

-- ----------------------------
-- Records of relation_h_comic_tag
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for relation_h_video_tag
-- ----------------------------
DROP TABLE IF EXISTS `relation_h_video_tag`;
CREATE TABLE `relation_h_video_tag` (
  `uuid` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '主键',
  `tagid` int unsigned NOT NULL COMMENT '对应tag的id',
  `videoid` int unsigned NOT NULL COMMENT '对应视频的id',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='关系表\r\n 视频与对应标签的关系';

-- ----------------------------
-- Records of relation_h_video_tag
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for relation_project_user
-- ----------------------------
DROP TABLE IF EXISTS `relation_project_user`;
CREATE TABLE `relation_project_user` (
  `uuid` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '主键',
  `user_uuid` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户uuid',
  `project_uuid` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '关联项目uuid',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='关系表\r\n 项目与加入其中用户的关系';

-- ----------------------------
-- Records of relation_project_user
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
