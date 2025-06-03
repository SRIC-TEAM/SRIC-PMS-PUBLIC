/*
 Navicat Premium Data Transfer

 Source Server         : SRIC
 Source Server Type    : MySQL
 Source Server Version : 80037
 Source Host           : localhost:3306
 Source Schema         : sric_pms_public

 Target Server Type    : MySQL
 Target Server Version : 80037
 File Encoding         : 65001

 Date: 23/05/2025 22:46:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category_library
-- ----------------------------
DROP TABLE IF EXISTS `category_library`;
CREATE TABLE `category_library`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 类别名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '类别表\r\n 书库的类别' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of category_library
-- ----------------------------
INSERT INTO `category_library` VALUES (1, '吃的');
INSERT INTO `category_library` VALUES (3, 'vue');
INSERT INTO `category_library` VALUES (4, 'springboot');
INSERT INTO `category_library` VALUES (5, 'swift');

-- ----------------------------
-- Table structure for category_resources
-- ----------------------------
DROP TABLE IF EXISTS `category_resources`;
CREATE TABLE `category_resources`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 类别名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '类别表\r\n 资源的类别' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of category_resources
-- ----------------------------
INSERT INTO `category_resources` VALUES (1, 'LOGO');
INSERT INTO `category_resources` VALUES (2, '视频');

-- ----------------------------
-- Table structure for data_h_comic
-- ----------------------------
DROP TABLE IF EXISTS `data_h_comic`;
CREATE TABLE `data_h_comic`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `filename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件名称',
  `pages` int UNSIGNED NOT NULL COMMENT '总页数',
  `mosaic` int UNSIGNED NOT NULL COMMENT '修正（1：无修正，2：有修正，）',
  `category` int UNSIGNED NOT NULL COMMENT '类型（1：短篇，2：单本，）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 363 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据表\r\n 漫画的数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of data_h_comic
-- ----------------------------

-- ----------------------------
-- Table structure for data_h_comic_tags
-- ----------------------------
DROP TABLE IF EXISTS `data_h_comic_tags`;
CREATE TABLE `data_h_comic_tags`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'tag名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据表\r\n 漫画的标签数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of data_h_comic_tags
-- ----------------------------

-- ----------------------------
-- Table structure for data_h_picture
-- ----------------------------
DROP TABLE IF EXISTS `data_h_picture`;
CREATE TABLE `data_h_picture`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `filename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'webp' COMMENT '文件名称(后缀)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1263 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据表\r\n 图片的数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of data_h_picture
-- ----------------------------
INSERT INTO `data_h_picture` VALUES (1, 'webp');
INSERT INTO `data_h_picture` VALUES (2, 'webp');
INSERT INTO `data_h_picture` VALUES (3, 'webp');
INSERT INTO `data_h_picture` VALUES (4, 'webp');
INSERT INTO `data_h_picture` VALUES (5, 'webp');
INSERT INTO `data_h_picture` VALUES (6, 'webp');
INSERT INTO `data_h_picture` VALUES (7, 'webp');

-- ----------------------------
-- Table structure for data_h_video
-- ----------------------------
DROP TABLE IF EXISTS `data_h_video`;
CREATE TABLE `data_h_video`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `filename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件名称',
  `number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '番号',
  `mosaic` int UNSIGNED NOT NULL COMMENT '修正（1：无修正，2：有修正，）',
  `category` int UNSIGNED NOT NULL COMMENT '类型（1：REAL，2：3D MMD，3：2D，）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 299 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据表\r\n 视频的数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of data_h_video
-- ----------------------------
INSERT INTO `data_h_video` VALUES (299, 'SRIC', 'SRIC-LOGO-animation.mp4', '1', 1, 1);

-- ----------------------------
-- Table structure for data_h_video_tags
-- ----------------------------
DROP TABLE IF EXISTS `data_h_video_tags`;
CREATE TABLE `data_h_video_tags`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'tag名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据表\r\n 视频的标签数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of data_h_video_tags
-- ----------------------------
INSERT INTO `data_h_video_tags` VALUES (1, '日常');
INSERT INTO `data_h_video_tags` VALUES (2, '版权');
INSERT INTO `data_h_video_tags` VALUES (3, '信息');
INSERT INTO `data_h_video_tags` VALUES (4, '水印');
INSERT INTO `data_h_video_tags` VALUES (5, '标签');
INSERT INTO `data_h_video_tags` VALUES (6, '动态');
INSERT INTO `data_h_video_tags` VALUES (7, 'AE');
INSERT INTO `data_h_video_tags` VALUES (8, 'PR');
INSERT INTO `data_h_video_tags` VALUES (9, '视频制作');

-- ----------------------------
-- Table structure for data_library
-- ----------------------------
DROP TABLE IF EXISTS `data_library`;
CREATE TABLE `data_library`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `filename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `category` int UNSIGNED NOT NULL COMMENT '类别（对应id）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据表\r\n 书库的数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of data_library
-- ----------------------------
INSERT INTO `data_library` VALUES (1, '豪赤的红烧肉', '1.md', 1);
INSERT INTO `data_library` VALUES (2, '番茄牛腩', '2.md', 1);
INSERT INTO `data_library` VALUES (4, 'SPRINGBOOT 与 MYBATIS 后端四层架构', '4.md', 4);
INSERT INTO `data_library` VALUES (5, 'vuecli对vue3项目的创建与依赖的引入', '5.md', 3);
INSERT INTO `data_library` VALUES (6, 'VUE2中组件间的传值与VUEX', '6.md', 3);
INSERT INTO `data_library` VALUES (7, 'VUE中的简单动画效果', '7.md', 3);
INSERT INTO `data_library` VALUES (8, 'swift data 基础数据操作样例', '8.md', 5);

-- ----------------------------
-- Table structure for data_notes
-- ----------------------------
DROP TABLE IF EXISTS `data_notes`;
CREATE TABLE `data_notes`  (
  `uuid` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '主键',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `text` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `user_uuid` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建用户uuid',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `is_deleted` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除（0：未删除，1：已删除）',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据表\r\n 笔记数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of data_notes
-- ----------------------------
INSERT INTO `data_notes` VALUES ('39b974e0-37e0-11f0-9478-00ff3469808f', '     * 上传新的头像      * 直接根据 token 进行修改', '    @PostMapping(\"/uploadAvatar\")\n    public ResponseCode uploadAvatar(@RequestParam(\"file\") MultipartFile file,@RequestHeader(\"Authorization\") String tokenValue)\n    {\n        Object loginId = StpUtil.getLoginIdByToken(tokenValue); //通过 token 获取当前登录的用户 uuid\n        ResponseCode responseCode = new ResponseCode();\n        responseCode.setCode(userService.uploadAvatar(file,loginId.toString()));\n        return responseCode;\n    }', 'e1b2a6fe-37a6-11f0-b1de-82c5565ed616', '2025-05-23 22:14:22', 0);
INSERT INTO `data_notes` VALUES ('43ab244a-37e0-11f0-9478-00ff3469808f', '根据 keyWord 获取用户列表', '    @GetMapping(\"/getUserListByKeyWord\")\n    public List<User> getUserListByKeyWord(@RequestParam String keyWord)\n    {\n        return userService.getUserListByKeyWord(keyWord);\n    }', 'e1b2a6fe-37a6-11f0-b1de-82c5565ed616', '2025-05-23 22:14:39', 0);
INSERT INTO `data_notes` VALUES ('4c3e60ca-37e0-11f0-9478-00ff3469808f', '修改用户信息', '    @PostMapping(\"/editUserProfile\")\n    public ResponseCode editUserProfile(@RequestBody User user,@RequestHeader(\"Authorization\") String tokenValue)\n    {\n        Object loginId = StpUtil.getLoginIdByToken(tokenValue); //通过 token 获取当前登录的用户 uuid\n        ResponseCode responseCode = new ResponseCode();\n        if(!user.getUuid().equals(loginId.toString())) //若当前用户登录 uuid 与 需修改的用户信息 uuid 不同 返回权限不足\n        {\n            responseCode.setCode(403);\n            return responseCode;\n        }\n        responseCode.setCode(userService.editUserProfile(user));\n        return responseCode;\n    }', 'e1b2a6fe-37a6-11f0-b1de-82c5565ed616', '2025-05-23 22:14:53', 0);
INSERT INTO `data_notes` VALUES ('560446d3-37e0-11f0-9478-00ff3469808f', '注册', '    @PostMapping(\"/signUp\")\n    public ResponseCode signUp(@RequestBody SignUpRequestPojo signUpRequest)\n    {\n        ResponseCode responseCode = new ResponseCode();\n        responseCode.setCode(userService.signUp(signUpRequest));\n        return responseCode;\n    }', 'e1b2a6fe-37a6-11f0-b1de-82c5565ed616', '2025-05-23 22:15:10', 0);
INSERT INTO `data_notes` VALUES ('5eaeb2fc-37e0-11f0-9478-00ff3469808f', '通过传入的电子邮箱 发送动态验证码', '    @GetMapping(\"/getCodeByMail\")\n    public ResponseCode getCodeByMail(@RequestParam String mail)\n    {\n        ResponseCode responseCode = new ResponseCode();\n        responseCode.setCode(userService.getCodeByMail(mail));\n        return responseCode;\n    }', 'e1b2a6fe-37a6-11f0-b1de-82c5565ed616', '2025-05-23 22:15:24', 0);

-- ----------------------------
-- Table structure for data_project
-- ----------------------------
DROP TABLE IF EXISTS `data_project`;
CREATE TABLE `data_project`  (
  `uuid` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `is_ended` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否结束项目（0：未结束，1：已结束）',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据表\r\n 项目数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of data_project
-- ----------------------------
INSERT INTO `data_project` VALUES ('7c054199-37e0-11f0-9478-00ff3469808f', 'OA系统', '2025-05-23 22:16:14', '公司内部OA系统', 0);
INSERT INTO `data_project` VALUES ('80c2a0d3-37de-11f0-9478-00ff3469808f', '飞鸡工', '2025-05-23 22:02:03', '工人的机工记账好帮手', 0);
INSERT INTO `data_project` VALUES ('828fb5c7-37e0-11f0-9478-00ff3469808f', '管理系统', '2025-05-23 22:16:25', '外包管理系统', 0);

-- ----------------------------
-- Table structure for data_project_item
-- ----------------------------
DROP TABLE IF EXISTS `data_project_item`;
CREATE TABLE `data_project_item`  (
  `uuid` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '主键',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `project_uuid` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '关联项目uuid',
  `create_user_uuid` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建用户uuid',
  `assign_user_uuid` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '指派用户uuid',
  `is_ended` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否结束（0：未结束，1：已结束）',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据表\r\n 项目内容数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of data_project_item
-- ----------------------------
INSERT INTO `data_project_item` VALUES ('92f97866-37e0-11f0-9478-00ff3469808f', '增删改查', '页面增删改查', '2025-05-23 22:16:52', '828fb5c7-37e0-11f0-9478-00ff3469808f', 'e1b2a6fe-37a6-11f0-b1de-82c5565ed616', NULL, 0);
INSERT INTO `data_project_item` VALUES ('9ce2e57b-37e0-11f0-9478-00ff3469808f', 'OA报表', '报表插件配置', '2025-05-23 22:17:09', '7c054199-37e0-11f0-9478-00ff3469808f', 'e1b2a6fe-37a6-11f0-b1de-82c5565ed616', NULL, 0);
INSERT INTO `data_project_item` VALUES ('a2aafd01-37e0-11f0-9478-00ff3469808f', 'OA流程', '工作审批流程', '2025-05-23 22:17:18', '7c054199-37e0-11f0-9478-00ff3469808f', 'e1b2a6fe-37a6-11f0-b1de-82c5565ed616', NULL, 0);
INSERT INTO `data_project_item` VALUES ('ae4a7c0d-37e0-11f0-9478-00ff3469808f', 'OA消息推送', '消息推送需要实时提醒', '2025-05-23 22:17:38', '7c054199-37e0-11f0-9478-00ff3469808f', 'e1b2a6fe-37a6-11f0-b1de-82c5565ed616', NULL, 0);
INSERT INTO `data_project_item` VALUES ('b9907770-37e0-11f0-9478-00ff3469808f', 'OA数据问题', '2025/05/22 数据有问题', '2025-05-23 22:17:57', '7c054199-37e0-11f0-9478-00ff3469808f', 'e1b2a6fe-37a6-11f0-b1de-82c5565ed616', 'e1b2a6fe-37a6-11f0-b1de-82c5565ed616', 1);
INSERT INTO `data_project_item` VALUES ('f31a8aa9-37de-11f0-9478-00ff3469808f', '记工', '记工页面', '2025-05-23 22:05:14', '80c2a0d3-37de-11f0-9478-00ff3469808f', 'ab1094e8-37ac-11f0-b1de-82c5565ed616', NULL, 0);

-- ----------------------------
-- Table structure for data_resources
-- ----------------------------
DROP TABLE IF EXISTS `data_resources`;
CREATE TABLE `data_resources`  (
  `uuid` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `filename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件名称',
  `level` int UNSIGNED NOT NULL COMMENT '权限等级',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `category` int UNSIGNED NOT NULL COMMENT '类别（对应id）',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件类型',
  `size` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件大小',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据表\r\n 资源对应的数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of data_resources
-- ----------------------------
INSERT INTO `data_resources` VALUES ('25711ef4-37e1-11f0-9478-00ff3469808f', 'red', '1748010058378-pen-#d81e06.png', 9, '2025-05-23 22:20:58', 1, '.png', '0.087');
INSERT INTO `data_resources` VALUES ('2c941f3a-37e1-11f0-9478-00ff3469808f', 'BLUE', '1748010070353-pen-#23ade5.png', 9, '2025-05-23 22:21:10', 1, '.png', '0.083');
INSERT INTO `data_resources` VALUES ('3448d60c-37e1-11f0-9478-00ff3469808f', 'YELLO', '1748010083281-pen-#fdf66d.png', 8, '2025-05-23 22:21:23', 1, '.png', '0.067');
INSERT INTO `data_resources` VALUES ('3aab06af-37e1-11f0-9478-00ff3469808f', 'PINK', '1748010093987-pen-#fb7299.png', 2, '2025-05-23 22:21:33', 1, '.png', '0.077');
INSERT INTO `data_resources` VALUES ('42925895-37e1-11f0-9478-00ff3469808f', 'GREEN', '1748010107247-pen-#6dc781.png', 10, '2025-05-23 22:21:47', 1, '.png', '0.084');
INSERT INTO `data_resources` VALUES ('8aabeb54-37e1-11f0-9478-00ff3469808f', 'LOGO-动画', '1748010228215-SRIC-LOGO-animation-pr-project.prproj', 10, '2025-05-23 22:23:48', 2, '.prproj', '0.023');

-- ----------------------------
-- Table structure for data_user
-- ----------------------------
DROP TABLE IF EXISTS `data_user`;
CREATE TABLE `data_user`  (
  `uuid` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '主键',
  `UID` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户UID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `mail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱',
  `create_time` datetime NOT NULL COMMENT '注册时间',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'NULL.webp' COMMENT '头像文件名称',
  `mark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'NULL MARK' COMMENT '个人签名',
  `status` int UNSIGNED NOT NULL DEFAULT 9 COMMENT '权限',
  PRIMARY KEY (`uuid`) USING BTREE,
  INDEX `uidkey`(`UID` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据表\r\n 用户数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of data_user
-- ----------------------------
INSERT INTO `data_user` VALUES ('5e1d0a6c-37ad-11f0-b1de-82c5565ed616', 5, 'SRIC-003', '21f45b25479c4f4d2cec012e0eb402abaf2a88cf27b2860b70976b9181cd9ca9', 'soraincloud@gmail.com', '2025-05-23 16:10:19', '5e1d0a6c-37ad-11f0-b1de-82c5565ed616.webp', '我是003 我为我自己代言 QwQ\n', 9);
INSERT INTO `data_user` VALUES ('ab1094e8-37ac-11f0-b1de-82c5565ed616', 4, 'SRIC-002', '3e91e35b9058e250119be418c17f8c551acb5b13e678add73e5660bc7dd19a1c', '3355905055@qq.com', '2025-05-23 16:05:19', 'ab1094e8-37ac-11f0-b1de-82c5565ed616.webp', '这里是 002\n具体事宜请联系 3355905055@qq.com', 9);
INSERT INTO `data_user` VALUES ('e1b2a6fe-37a6-11f0-b1de-82c5565ed616', 3, 'SRIC-001', 'be15535d03c124dcb95add560f5632baee064fe2d2b73bc10e43fd872efc08ba', 'soraincloud@foxmail.com', '2025-05-23 15:23:53', 'e1b2a6fe-37a6-11f0-b1de-82c5565ed616.webp', 'NULL MARK', 1);

-- ----------------------------
-- Table structure for permission_main_menu
-- ----------------------------
DROP TABLE IF EXISTS `permission_main_menu`;
CREATE TABLE `permission_main_menu`  (
  `uuid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 名称',
  `level` int UNSIGNED NOT NULL COMMENT '权限等级',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限表\r\n 对主菜单的权限控制' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of permission_main_menu
-- ----------------------------
INSERT INTO `permission_main_menu` VALUES ('478ccc77-992f-11ef-bf4d-7c10c9c0a516', 'Notes', 9, '笔记菜单');
INSERT INTO `permission_main_menu` VALUES ('4a5ab763-bbd1-11ef-9d4c-7c10c9c0a516', 'Project', 9, '项目菜单');
INSERT INTO `permission_main_menu` VALUES ('99e89acd-9305-11ef-8959-7c10c9c0a516', 'Library', 10, '知识库菜单');
INSERT INTO `permission_main_menu` VALUES ('a401f45b-9305-11ef-8959-7c10c9c0a516', 'Hfiles', 9, '媒体菜单');
INSERT INTO `permission_main_menu` VALUES ('ded885ab-b4d6-11ef-aa19-7c10c9c0a516', 'Resources', 10, '资源菜单');

-- ----------------------------
-- Table structure for permission_request
-- ----------------------------
DROP TABLE IF EXISTS `permission_request`;
CREATE TABLE `permission_request`  (
  `uuid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `request_mapping` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求uri模块',
  `level` int UNSIGNED NOT NULL COMMENT '权限等级',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限表\r\n 对后端接口的权限控制' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of permission_request
-- ----------------------------
INSERT INTO `permission_request` VALUES ('057857d4-b570-11ef-aa19-7c10c9c0a516', 'resources', 10, '资源模块');
INSERT INTO `permission_request` VALUES ('112cb3e5-b570-11ef-aa19-7c10c9c0a516', 'resourcesManage', 3, '资源管理模块');
INSERT INTO `permission_request` VALUES ('1c0e8139-9ea6-11ef-95ed-7c10c9c0a516', 'notes', 9, '笔记模块');
INSERT INTO `permission_request` VALUES ('1cde2aa7-ba00-11ef-9d4c-7c10c9c0a516', 'projectManage', 3, '项目管理模块');
INSERT INTO `permission_request` VALUES ('26378016-9ea6-11ef-95ed-7c10c9c0a516', 'notesManage', 3, '笔记管理模块');
INSERT INTO `permission_request` VALUES ('2651bf6e-ba00-11ef-9d4c-7c10c9c0a516', 'project', 9, '项目模块');
INSERT INTO `permission_request` VALUES ('34faf53d-8e56-11ef-8f3b-7c10c9c0a516', 'libraryManage', 3, '知识库管理模块');
INSERT INTO `permission_request` VALUES ('5e551f18-8e56-11ef-8f3b-7c10c9c0a516', 'library', 10, '知识库模块');
INSERT INTO `permission_request` VALUES ('75e86af4-8e33-11ef-8f3b-7c10c9c0a516', 'userManage', 1, '用户管理模块');
INSERT INTO `permission_request` VALUES ('863dd17a-93d4-11ef-8894-7c10c9c0a516', 'permissionMainMenuManage', 1, '主菜单权限管理模块');
INSERT INTO `permission_request` VALUES ('8966a9ee-8e56-11ef-8f3b-7c10c9c0a516', 'h', 9, '媒体浏览模块');
INSERT INTO `permission_request` VALUES ('bd9bdc8a-9305-11ef-8959-7c10c9c0a516', 'permissionMainMenu', 10, '主菜单获取');
INSERT INTO `permission_request` VALUES ('c4df9159-8e55-11ef-8f3b-7c10c9c0a516', 'permissionRequest', 1, '接口权限管理模块');
INSERT INTO `permission_request` VALUES ('dbd09ed2-c095-11ef-9829-7c10c9c0a516', 'hManage', 6, ' 媒体管理模块');
INSERT INTO `permission_request` VALUES ('e8f07304-8d87-11ef-8f3b-7c10c9c0a516', 'user', 10, '基础用户服务模块');
INSERT INTO `permission_request` VALUES ('f524021f-c774-11ef-9a44-7c10c9c0a516', 'projectItem', 9, '项目内容模块');

-- ----------------------------
-- Table structure for relation_h_comic_tag
-- ----------------------------
DROP TABLE IF EXISTS `relation_h_comic_tag`;
CREATE TABLE `relation_h_comic_tag`  (
  `uuid` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '主键',
  `tagid` int UNSIGNED NOT NULL COMMENT '对应tag的id',
  `comicid` int UNSIGNED NOT NULL COMMENT '对应comic的id',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '关系表\r\n 漫画与对应标签的关系' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of relation_h_comic_tag
-- ----------------------------

-- ----------------------------
-- Table structure for relation_h_video_tag
-- ----------------------------
DROP TABLE IF EXISTS `relation_h_video_tag`;
CREATE TABLE `relation_h_video_tag`  (
  `uuid` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '主键',
  `tagid` int UNSIGNED NOT NULL COMMENT '对应tag的id',
  `videoid` int UNSIGNED NOT NULL COMMENT '对应视频的id',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '关系表\r\n 视频与对应标签的关系' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of relation_h_video_tag
-- ----------------------------
INSERT INTO `relation_h_video_tag` VALUES ('69ea56d7-37e1-11f0-9478-00ff3469808f', 2, 299);
INSERT INTO `relation_h_video_tag` VALUES ('6b7ba4cb-37e1-11f0-9478-00ff3469808f', 5, 299);
INSERT INTO `relation_h_video_tag` VALUES ('6d168d99-37e1-11f0-9478-00ff3469808f', 4, 299);
INSERT INTO `relation_h_video_tag` VALUES ('f9c223fb-37e0-11f0-9478-00ff3469808f', 1, 299);

-- ----------------------------
-- Table structure for relation_project_user
-- ----------------------------
DROP TABLE IF EXISTS `relation_project_user`;
CREATE TABLE `relation_project_user`  (
  `uuid` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '主键',
  `user_uuid` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户uuid',
  `project_uuid` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '关联项目uuid',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '关系表\r\n 项目与加入其中用户的关系' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of relation_project_user
-- ----------------------------
INSERT INTO `relation_project_user` VALUES ('6945b88d-37e0-11f0-9478-00ff3469808f', 'e1b2a6fe-37a6-11f0-b1de-82c5565ed616', '80c2a0d3-37de-11f0-9478-00ff3469808f');
INSERT INTO `relation_project_user` VALUES ('6bf59d41-37e0-11f0-9478-00ff3469808f', '5e1d0a6c-37ad-11f0-b1de-82c5565ed616', '80c2a0d3-37de-11f0-9478-00ff3469808f');
INSERT INTO `relation_project_user` VALUES ('862f3f78-37e0-11f0-9478-00ff3469808f', 'e1b2a6fe-37a6-11f0-b1de-82c5565ed616', '7c054199-37e0-11f0-9478-00ff3469808f');
INSERT INTO `relation_project_user` VALUES ('878ee537-37e0-11f0-9478-00ff3469808f', 'ab1094e8-37ac-11f0-b1de-82c5565ed616', '828fb5c7-37e0-11f0-9478-00ff3469808f');
INSERT INTO `relation_project_user` VALUES ('88301fee-37de-11f0-9478-00ff3469808f', 'ab1094e8-37ac-11f0-b1de-82c5565ed616', '80c2a0d3-37de-11f0-9478-00ff3469808f');

SET FOREIGN_KEY_CHECKS = 1;
