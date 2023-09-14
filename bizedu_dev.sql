/*
 Navicat Premium Data Transfer

 Source Server         : 本地localhost
 Source Server Type    : MySQL
 Source Server Version : 50742
 Source Host           : localhost:3306
 Source Schema         : bizedu_dev

 Target Server Type    : MySQL
 Target Server Version : 50742
 File Encoding         : 65001

 Date: 14/09/2023 16:35:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for auth_dept
-- ----------------------------
DROP TABLE IF EXISTS `auth_dept`;
CREATE TABLE `auth_dept`  (
  `dept_id` int(11) NOT NULL COMMENT '部门id',
  `dept_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门编号',
  `dept_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of auth_dept
-- ----------------------------
INSERT INTO `auth_dept` VALUES (1, 'yfb', '研发部', '2022-03-12 11:51:12', '2022-03-12 11:51:13');
INSERT INTO `auth_dept` VALUES (2, 'csb', '测试部', '2022-03-12 11:51:49', '2022-03-12 11:51:51');
INSERT INTO `auth_dept` VALUES (3, 'khb1', '客户一部', '2022-03-12 16:29:08', '2022-03-12 16:29:10');
INSERT INTO `auth_dept` VALUES (4, 'khb2', '客户二部', '2022-03-12 16:29:27', '2022-03-12 16:29:29');
INSERT INTO `auth_dept` VALUES (5, 'yyb', '运营部', '2022-03-12 16:29:44', '2022-03-12 16:29:45');
INSERT INTO `auth_dept` VALUES (6, 'jyb', '交易部', '2022-03-12 16:30:04', '2022-03-12 16:30:06');
INSERT INTO `auth_dept` VALUES (7, 'yuwb', '运维部', '2022-03-20 15:42:39', '2022-03-20 15:42:40');

-- ----------------------------
-- Table structure for auth_func
-- ----------------------------
DROP TABLE IF EXISTS `auth_func`;
CREATE TABLE `auth_func`  (
  `func_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `func_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '功能名称',
  `func_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '功能key',
  `func_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '功能类型(目录/功能)',
  `func_icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '目录图标',
  `func_sort` int(11) NULL DEFAULT NULL COMMENT '功能排序',
  `func_dir_status` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否隐藏',
  `parent_id` int(11) NOT NULL COMMENT '父级功能id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`func_id`) USING BTREE,
  UNIQUE INDEX `idx_func_key`(`func_key`) USING BTREE,
  INDEX `idx_parent_id`(`parent_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 112 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of auth_func
-- ----------------------------
INSERT INTO `auth_func` VALUES (2, '权限系统', 'auth', 'dir', 'UnlockOutlined', 1, 'show', 1, '2022-03-19 15:57:28', '2022-03-19 15:57:31');
INSERT INTO `auth_func` VALUES (3, '用户管理', 'AuthUser', 'dir', 'CameraOutlined', 3, 'show', 2, '2022-03-19 16:10:26', '2023-07-15 11:09:23');
INSERT INTO `auth_func` VALUES (4, '角色管理', 'AuthRole', 'dir', 'SafetyCertificateOutlined', 4, 'show', 2, '2022-03-19 16:11:29', '2022-03-19 16:11:30');
INSERT INTO `auth_func` VALUES (5, '功能管理', 'AuthFunc', 'dir', 'ShareAltOutlined', 5, 'show', 2, '2022-03-19 16:11:41', '2022-03-19 16:11:42');
INSERT INTO `auth_func` VALUES (6, '资源管理', 'AuthResc', 'dir', 'SkinOutlined', 6, 'show', 2, '2022-03-19 16:11:54', '2022-03-19 16:11:55');
INSERT INTO `auth_func` VALUES (8, '角色删除', 'auth:role:delete', 'func', 'HomeOutlined', 7, NULL, 4, '2022-03-19 18:23:22', '2022-03-19 18:23:25');
INSERT INTO `auth_func` VALUES (9, '角色更新', 'auth:role:update', 'func', 'HomeOutlined', 8, NULL, 4, '2022-03-19 18:23:38', '2022-03-19 18:23:40');
INSERT INTO `auth_func` VALUES (11, '角色查询', 'auth:role:page', 'func', 'HomeOutlined', 9, NULL, 4, '2022-03-19 18:24:02', '2022-03-19 18:24:03');
INSERT INTO `auth_func` VALUES (12, '功能查询', 'auth:func:page', 'func', 'HomeOutlined', 10, NULL, 5, '2022-03-19 18:24:30', '2023-07-15 15:11:24');
INSERT INTO `auth_func` VALUES (13, '资源查询', 'auth:resc:page', 'func', 'HomeOutlined', 11, NULL, 6, '2022-03-19 18:24:51', '2022-03-19 18:24:52');
INSERT INTO `auth_func` VALUES (14, '功能新增', 'auth:func:add', 'func', 'HomeOutlined', 12, NULL, 5, '2022-03-19 18:26:06', '2022-03-19 18:26:08');
INSERT INTO `auth_func` VALUES (26, '资源新增', 'auth:resc:add', 'func', 'HomeOutlined', 13, NULL, 6, '2022-03-20 21:43:55', '2023-07-13 11:44:08');
INSERT INTO `auth_func` VALUES (27, '资源删除', 'auth:resc:delete', 'func', 'HomeOutlined', 14, NULL, 6, '2022-03-20 21:44:03', '2022-03-20 21:44:03');
INSERT INTO `auth_func` VALUES (54, '功能删除', 'auth:func:delete', 'func', 'HomeOutlined', 15, NULL, 5, '2022-03-26 20:57:44', '2022-03-26 20:57:44');
INSERT INTO `auth_func` VALUES (55, '功能更新', 'auth:func:update', 'func', 'HomeOutlined', 16, NULL, 5, '2022-03-26 20:58:00', '2022-03-26 20:58:00');
INSERT INTO `auth_func` VALUES (56, '资源更新', 'auth:resc:update', 'func', 'HomeOutlined', 17, NULL, 6, '2022-03-26 21:00:16', '2022-03-26 21:00:16');
INSERT INTO `auth_func` VALUES (57, '用户查询', 'auth:user:page', 'func', 'HomeOutlined', 18, NULL, 3, '2022-03-26 21:04:37', '2023-06-21 17:52:15');
INSERT INTO `auth_func` VALUES (58, '用户启用', 'auth:user:enable', 'func', 'HomeOutlined', 19, NULL, 3, '2022-03-26 21:05:02', '2022-03-26 21:05:02');
INSERT INTO `auth_func` VALUES (59, '用户禁用', 'auth:user:disable', 'func', 'HomeOutlined', 20, NULL, 3, '2022-03-26 21:05:19', '2022-03-26 21:05:19');
INSERT INTO `auth_func` VALUES (60, '用户更新', 'auth:user:update', 'func', 'HomeOutlined', 21, NULL, 3, '2022-03-26 21:05:33', '2022-03-26 21:05:33');
INSERT INTO `auth_func` VALUES (61, '用户删除', 'auth:user:delete', 'func', 'HomeOutlined', 22, NULL, 3, '2022-03-26 21:05:55', '2022-03-26 21:05:55');
INSERT INTO `auth_func` VALUES (62, '分配角色', 'auth:user:roleUpdate', 'func', 'HomeOutlined', 23, NULL, 3, '2022-03-26 21:06:48', '2023-07-15 15:01:03');
INSERT INTO `auth_func` VALUES (63, '分配用户', 'auth:role:assign', 'func', 'HomeOutlined', 24, NULL, 4, '2022-03-26 21:08:39', '2022-03-26 21:08:39');
INSERT INTO `auth_func` VALUES (72, '角色新增', 'auth:role:add', 'func', 'HomeOutlined', 26, NULL, 4, '2022-04-16 11:13:19', '2022-04-16 11:13:19');
INSERT INTO `auth_func` VALUES (73, '解绑用户', 'auth:func:userRemove', 'func', 'HomeOutlined', 27, NULL, 5, '2022-04-16 21:55:19', '2023-07-15 15:17:13');
INSERT INTO `auth_func` VALUES (74, '解绑资源', 'auth:func:rescRemove', 'func', 'HomeOutlined', 28, NULL, 5, '2022-04-16 22:02:49', '2023-07-15 15:16:59');
INSERT INTO `auth_func` VALUES (75, '用户新增', 'auth:user:add', 'func', 'HomeOutlined', 29, NULL, 3, '2022-05-02 11:40:57', '2022-05-02 11:40:57');
INSERT INTO `auth_func` VALUES (84, '修改密码', 'auth:user:pwdUpdate', 'func', 'HomeOutlined', 30, NULL, 3, '2022-05-14 12:20:12', '2023-07-15 15:02:08');
INSERT INTO `auth_func` VALUES (93, '同步系统', 'sync', 'dir', 'VerifiedOutlined', 2, 'show', 1, '2022-09-13 20:54:26', '2023-06-29 09:09:49');
INSERT INTO `auth_func` VALUES (94, '同步计划', 'SyncPlan', 'dir', 'WalletOutlined', 1, 'show', 93, '2022-09-13 20:54:58', '2023-07-18 12:07:50');
INSERT INTO `auth_func` VALUES (95, '同步记录', 'syncResult', 'dir', 'ShoppingCartOutlined', 1, 'show', 93, '2022-12-04 23:48:14', '2022-12-04 23:48:14');
INSERT INTO `auth_func` VALUES (96, '消息回调', 'syncCallback', 'dir', 'HistoryOutlined', 3, 'show', 93, '2022-12-04 23:48:39', '2023-06-21 12:27:41');
INSERT INTO `auth_func` VALUES (97, '同步消息', 'syncMsg', 'dir', 'ScheduleOutlined', 5, 'show', 93, '2022-12-04 23:49:04', '2023-06-21 17:19:42');
INSERT INTO `auth_func` VALUES (98, '角色授权', 'AuthRoleAuthorized', 'dir', '', 0, 'hide', 4, '2023-06-21 15:18:12', '2023-07-13 15:42:06');
INSERT INTO `auth_func` VALUES (100, '初始化', 'auth:role:initFunc', 'func', '', 2, 'show', 98, '2023-06-21 17:15:16', '2023-07-15 14:59:21');
INSERT INTO `auth_func` VALUES (101, '授权更新', 'auth:role:funcUpdate', 'func', '', 1, 'show', 98, '2023-06-21 17:17:14', '2023-07-15 14:59:57');
INSERT INTO `auth_func` VALUES (107, '绑定资源', 'auth:func:rescUpdate', 'func', '', 8, 'show', 5, '2023-07-12 17:21:52', '2023-07-15 15:16:46');
INSERT INTO `auth_func` VALUES (108, '新增计划', 'sync:plan:add', 'func', '', 0, 'show', 94, '2023-07-20 16:40:29', '2023-07-20 16:40:29');
INSERT INTO `auth_func` VALUES (109, '删除计划', 'sync:plan:delete', 'func', '', 0, 'show', 94, '2023-07-20 16:40:52', '2023-07-20 16:40:52');
INSERT INTO `auth_func` VALUES (110, '计划查询', 'sync:plan:page', 'func', '', 0, 'show', 94, '2023-07-20 16:41:08', '2023-07-20 16:41:08');
INSERT INTO `auth_func` VALUES (111, '更新计划', 'sync:plan:update', 'func', '', 0, 'show', 94, '2023-07-20 16:41:34', '2023-07-20 16:41:34');

-- ----------------------------
-- Table structure for auth_func_resc
-- ----------------------------
DROP TABLE IF EXISTS `auth_func_resc`;
CREATE TABLE `auth_func_resc`  (
  `func_resc_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `func_id` int(11) NOT NULL COMMENT '功能id',
  `resc_id` int(11) NOT NULL COMMENT '资源id',
  PRIMARY KEY (`func_resc_id`) USING BTREE,
  INDEX `idx_func_id`(`func_id`) USING BTREE,
  INDEX `idx_resc_id`(`resc_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 380 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of auth_func_resc
-- ----------------------------
INSERT INTO `auth_func_resc` VALUES (286, 75, 27);
INSERT INTO `auth_func_resc` VALUES (287, 62, 9);
INSERT INTO `auth_func_resc` VALUES (288, 62, 10);
INSERT INTO `auth_func_resc` VALUES (290, 60, 4);
INSERT INTO `auth_func_resc` VALUES (291, 60, 2);
INSERT INTO `auth_func_resc` VALUES (292, 60, 11);
INSERT INTO `auth_func_resc` VALUES (293, 59, 14);
INSERT INTO `auth_func_resc` VALUES (297, 58, 12);
INSERT INTO `auth_func_resc` VALUES (301, 56, 23);
INSERT INTO `auth_func_resc` VALUES (302, 56, 22);
INSERT INTO `auth_func_resc` VALUES (303, 56, 41);
INSERT INTO `auth_func_resc` VALUES (304, 55, 30);
INSERT INTO `auth_func_resc` VALUES (305, 55, 29);
INSERT INTO `auth_func_resc` VALUES (306, 54, 31);
INSERT INTO `auth_func_resc` VALUES (307, 27, 24);
INSERT INTO `auth_func_resc` VALUES (308, 26, 21);
INSERT INTO `auth_func_resc` VALUES (312, 73, 39);
INSERT INTO `auth_func_resc` VALUES (313, 72, 19);
INSERT INTO `auth_func_resc` VALUES (316, 63, 35);
INSERT INTO `auth_func_resc` VALUES (317, 63, 36);
INSERT INTO `auth_func_resc` VALUES (318, 63, 13);
INSERT INTO `auth_func_resc` VALUES (319, 8, 15);
INSERT INTO `auth_func_resc` VALUES (320, 9, 16);
INSERT INTO `auth_func_resc` VALUES (321, 9, 17);
INSERT INTO `auth_func_resc` VALUES (327, 57, 1);
INSERT INTO `auth_func_resc` VALUES (328, 57, 11);
INSERT INTO `auth_func_resc` VALUES (329, 13, 20);
INSERT INTO `auth_func_resc` VALUES (330, 13, 41);
INSERT INTO `auth_func_resc` VALUES (335, 84, 42);
INSERT INTO `auth_func_resc` VALUES (336, 74, 40);
INSERT INTO `auth_func_resc` VALUES (363, 11, 18);
INSERT INTO `auth_func_resc` VALUES (372, 12, 33);
INSERT INTO `auth_func_resc` VALUES (373, 12, 34);
INSERT INTO `auth_func_resc` VALUES (374, 14, 32);
INSERT INTO `auth_func_resc` VALUES (375, 107, 44);
INSERT INTO `auth_func_resc` VALUES (376, 100, 37);
INSERT INTO `auth_func_resc` VALUES (377, 101, 43);
INSERT INTO `auth_func_resc` VALUES (378, 101, 38);
INSERT INTO `auth_func_resc` VALUES (379, 61, 3);

-- ----------------------------
-- Table structure for auth_resc
-- ----------------------------
DROP TABLE IF EXISTS `auth_resc`;
CREATE TABLE `auth_resc`  (
  `resc_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '资源id',
  `resc_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源名称',
  `resc_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源url',
  `resc_domain` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源所属系统',
  `method_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求方式',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`resc_id`) USING BTREE,
  UNIQUE INDEX `idx_resc_url`(`resc_url`) USING BTREE,
  INDEX `idx_resc_name`(`resc_name`) USING BTREE,
  INDEX `idx_resc_domain`(`resc_domain`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of auth_resc
-- ----------------------------
INSERT INTO `auth_resc` VALUES (1, '用户列表', '/auth/user/page', 'auth', 'get', '2022-03-19 09:31:31', '2022-04-09 14:03:50');
INSERT INTO `auth_resc` VALUES (2, '用户获取', '/auth/user/detail', 'auth', 'get', '2022-03-19 09:34:24', '2022-05-10 22:41:35');
INSERT INTO `auth_resc` VALUES (3, '用户删除', '/auth/user/delete', 'auth', 'get', '2022-03-19 09:34:40', '2022-05-01 23:18:10');
INSERT INTO `auth_resc` VALUES (4, '用户更新', '/auth/user/update', 'auth', 'post', '2022-03-19 09:34:58', '2022-05-10 22:36:29');
INSERT INTO `auth_resc` VALUES (9, '用户分配角色初始化', '/auth/user/role/init', 'auth', 'get', '2022-03-19 10:29:15', '2022-08-30 23:07:46');
INSERT INTO `auth_resc` VALUES (10, '用户分配角色', '/auth/user/role/update', 'auth', 'post', '2022-03-19 10:29:41', '2022-09-04 14:33:38');
INSERT INTO `auth_resc` VALUES (11, '部门列表', '/auth/dept/list', 'auth', 'get', '2022-03-19 10:30:01', '2022-04-09 14:03:20');
INSERT INTO `auth_resc` VALUES (12, '用户启用', '/auth/user/enable', 'auth', 'get', '2022-03-19 10:30:16', '2022-09-03 15:45:31');
INSERT INTO `auth_resc` VALUES (13, '用户角色信息', '/auth/user/select/role/names', 'auth', 'get', '2022-03-19 10:30:34', '2022-04-09 14:03:07');
INSERT INTO `auth_resc` VALUES (14, '用户禁用', '/auth/user/disable', 'auth', 'get', '2022-03-19 10:30:48', '2022-05-14 11:52:30');
INSERT INTO `auth_resc` VALUES (15, '角色删除', '/auth/role/delete', 'auth', 'get', '2022-03-19 10:31:30', '2022-05-01 23:18:07');
INSERT INTO `auth_resc` VALUES (16, '角色更新', '/auth/role/update', 'auth', 'post', '2022-03-19 10:31:44', '2022-05-01 22:23:59');
INSERT INTO `auth_resc` VALUES (17, '角色获取', '/auth/role/detail', 'auth', 'get', '2022-03-19 10:32:06', '2022-04-09 14:02:34');
INSERT INTO `auth_resc` VALUES (18, '角色列表', '/auth/role/page', 'auth', 'get', '2022-03-19 10:32:28', '2022-04-09 14:02:25');
INSERT INTO `auth_resc` VALUES (19, '角色新增', '/auth/role/save', 'auth', 'post', '2022-03-19 10:32:48', '2022-04-09 14:02:29');
INSERT INTO `auth_resc` VALUES (20, '资源列表', '/auth/resc/page', 'auth', 'get', '2022-03-19 10:33:08', '2022-09-04 14:44:14');
INSERT INTO `auth_resc` VALUES (21, '资源新增', '/auth/resc/save', 'auth', 'post', '2022-03-19 10:33:26', '2023-06-21 12:27:06');
INSERT INTO `auth_resc` VALUES (22, '资源获取', '/auth/resc/detail', 'auth', 'get', '2022-03-19 10:33:42', '2022-04-09 14:02:06');
INSERT INTO `auth_resc` VALUES (23, '资源更新', '/auth/resc/update', 'auth', 'post', '2022-03-19 10:33:56', '2022-05-01 22:23:25');
INSERT INTO `auth_resc` VALUES (24, '资源删除', '/auth/resc/delete', 'auth', 'get', '2022-03-19 10:34:13', '2022-05-01 23:17:58');
INSERT INTO `auth_resc` VALUES (27, '用户新增', '/auth/user/save', 'auth', 'post', '2022-03-19 11:18:04', '2022-04-09 14:01:38');
INSERT INTO `auth_resc` VALUES (29, '功能更新', '/auth/func/update', 'auth', 'post', '2022-03-26 20:52:38', '2023-07-12 17:08:54');
INSERT INTO `auth_resc` VALUES (30, '功能获取', '/auth/func/detail', 'auth', 'get', '2022-03-26 20:52:51', '2022-04-09 14:01:25');
INSERT INTO `auth_resc` VALUES (31, '功能删除', '/auth/func/delete', 'auth', 'get', '2022-03-26 20:53:09', '2022-05-01 23:17:54');
INSERT INTO `auth_resc` VALUES (32, '功能新增', '/auth/func/save', 'auth', 'post', '2022-03-26 20:53:24', '2022-04-09 14:01:12');
INSERT INTO `auth_resc` VALUES (33, '功能查询', '/auth/func/page', 'auth', 'get', '2022-03-26 20:53:40', '2022-09-11 16:05:51');
INSERT INTO `auth_resc` VALUES (34, '功能树获取', '/auth/func/tree', 'auth', 'get', '2022-03-26 20:53:58', '2022-04-09 14:01:03');
INSERT INTO `auth_resc` VALUES (35, '角色分配用户初始化', '/auth/role/user/init', 'auth', 'get', '2022-03-26 21:07:44', '2022-09-03 16:02:34');
INSERT INTO `auth_resc` VALUES (36, '角色分配用户', '/auth/role/user/update', 'auth', 'post', '2022-03-26 21:07:59', '2022-09-03 16:02:22');
INSERT INTO `auth_resc` VALUES (37, '角色授权功能初始化', '/auth/role/func/init', 'auth', 'get', '2022-04-04 17:46:28', '2022-09-03 16:02:00');
INSERT INTO `auth_resc` VALUES (38, '角色分配功能', '/auth/role/func/update', 'auth', 'post', '2022-04-04 17:46:57', '2022-08-30 23:00:27');
INSERT INTO `auth_resc` VALUES (39, '功能解绑用户', '/auth/func/user/remove', 'auth', 'get', '2022-04-16 21:54:38', '2022-04-16 22:02:08');
INSERT INTO `auth_resc` VALUES (40, '功能解绑资源', '/auth/func/resc/remove', 'auth', 'get', '2022-04-16 22:02:24', '2022-04-23 21:28:09');
INSERT INTO `auth_resc` VALUES (41, '资源搜索', '/auth/resc/search', 'auth', 'get', '2022-05-01 19:17:13', '2022-09-01 00:02:42');
INSERT INTO `auth_resc` VALUES (42, '用户修改密码', '/auth/user/pwd/update', 'auth', 'post', '2022-05-14 12:19:03', '2023-07-12 16:53:55');
INSERT INTO `auth_resc` VALUES (43, '角色授权功能预检查', '/auth/role/func/check', 'auth', 'post', '2022-05-21 15:34:56', '2023-07-12 17:08:44');
INSERT INTO `auth_resc` VALUES (44, '功能更新资源', '/auth/func/resc/update', 'auth', 'put', '2023-07-12 17:15:25', '2023-07-18 12:06:42');

-- ----------------------------
-- Table structure for auth_role
-- ----------------------------
DROP TABLE IF EXISTS `auth_role`;
CREATE TABLE `auth_role`  (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `role_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`role_id`) USING BTREE,
  UNIQUE INDEX `idx_role_name`(`role_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 53 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of auth_role
-- ----------------------------
INSERT INTO `auth_role` VALUES (42, '管理员', '高级权限', '2022-04-16 11:38:56', '2023-07-13 11:54:28');
INSERT INTO `auth_role` VALUES (52, '普通用户', '普通权限', '2023-07-13 11:54:15', '2023-07-18 12:25:41');

-- ----------------------------
-- Table structure for auth_role_func
-- ----------------------------
DROP TABLE IF EXISTS `auth_role_func`;
CREATE TABLE `auth_role_func`  (
  `role_func_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `func_id` int(11) NOT NULL COMMENT '功能id',
  PRIMARY KEY (`role_func_id`) USING BTREE,
  INDEX `idx_role_id`(`role_id`) USING BTREE,
  INDEX `idx_func_id`(`func_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7988 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of auth_role_func
-- ----------------------------
INSERT INTO `auth_role_func` VALUES (7881, 52, 72);
INSERT INTO `auth_role_func` VALUES (7882, 52, 75);
INSERT INTO `auth_role_func` VALUES (7883, 52, 3);
INSERT INTO `auth_role_func` VALUES (7884, 52, 57);
INSERT INTO `auth_role_func` VALUES (7885, 52, 58);
INSERT INTO `auth_role_func` VALUES (7886, 52, 59);
INSERT INTO `auth_role_func` VALUES (7887, 52, 60);
INSERT INTO `auth_role_func` VALUES (7888, 52, 61);
INSERT INTO `auth_role_func` VALUES (7889, 52, 62);
INSERT INTO `auth_role_func` VALUES (7890, 52, 84);
INSERT INTO `auth_role_func` VALUES (7891, 52, 11);
INSERT INTO `auth_role_func` VALUES (7892, 52, 12);
INSERT INTO `auth_role_func` VALUES (7893, 52, 13);
INSERT INTO `auth_role_func` VALUES (7894, 52, 6);
INSERT INTO `auth_role_func` VALUES (7895, 52, 26);
INSERT INTO `auth_role_func` VALUES (7896, 52, 27);
INSERT INTO `auth_role_func` VALUES (7897, 52, 56);
INSERT INTO `auth_role_func` VALUES (7898, 52, 14);
INSERT INTO `auth_role_func` VALUES (7899, 52, 54);
INSERT INTO `auth_role_func` VALUES (7900, 52, 55);
INSERT INTO `auth_role_func` VALUES (7901, 52, 107);
INSERT INTO `auth_role_func` VALUES (7902, 52, 4);
INSERT INTO `auth_role_func` VALUES (7903, 52, 2);
INSERT INTO `auth_role_func` VALUES (7904, 52, 5);
INSERT INTO `auth_role_func` VALUES (7905, 52, 93);
INSERT INTO `auth_role_func` VALUES (7906, 52, 94);
INSERT INTO `auth_role_func` VALUES (7907, 52, 95);
INSERT INTO `auth_role_func` VALUES (7908, 52, 96);
INSERT INTO `auth_role_func` VALUES (7909, 52, 97);
INSERT INTO `auth_role_func` VALUES (7947, 42, 2);
INSERT INTO `auth_role_func` VALUES (7948, 42, 3);
INSERT INTO `auth_role_func` VALUES (7949, 42, 4);
INSERT INTO `auth_role_func` VALUES (7950, 42, 5);
INSERT INTO `auth_role_func` VALUES (7951, 42, 6);
INSERT INTO `auth_role_func` VALUES (7952, 42, 8);
INSERT INTO `auth_role_func` VALUES (7953, 42, 72);
INSERT INTO `auth_role_func` VALUES (7954, 42, 9);
INSERT INTO `auth_role_func` VALUES (7955, 42, 73);
INSERT INTO `auth_role_func` VALUES (7956, 42, 74);
INSERT INTO `auth_role_func` VALUES (7957, 42, 75);
INSERT INTO `auth_role_func` VALUES (7958, 42, 11);
INSERT INTO `auth_role_func` VALUES (7959, 42, 12);
INSERT INTO `auth_role_func` VALUES (7960, 42, 13);
INSERT INTO `auth_role_func` VALUES (7961, 42, 14);
INSERT INTO `auth_role_func` VALUES (7962, 42, 84);
INSERT INTO `auth_role_func` VALUES (7963, 42, 26);
INSERT INTO `auth_role_func` VALUES (7964, 42, 27);
INSERT INTO `auth_role_func` VALUES (7965, 42, 98);
INSERT INTO `auth_role_func` VALUES (7966, 42, 100);
INSERT INTO `auth_role_func` VALUES (7967, 42, 101);
INSERT INTO `auth_role_func` VALUES (7968, 42, 107);
INSERT INTO `auth_role_func` VALUES (7969, 42, 54);
INSERT INTO `auth_role_func` VALUES (7970, 42, 55);
INSERT INTO `auth_role_func` VALUES (7971, 42, 56);
INSERT INTO `auth_role_func` VALUES (7972, 42, 57);
INSERT INTO `auth_role_func` VALUES (7973, 42, 58);
INSERT INTO `auth_role_func` VALUES (7974, 42, 59);
INSERT INTO `auth_role_func` VALUES (7975, 42, 60);
INSERT INTO `auth_role_func` VALUES (7976, 42, 61);
INSERT INTO `auth_role_func` VALUES (7977, 42, 62);
INSERT INTO `auth_role_func` VALUES (7978, 42, 63);
INSERT INTO `auth_role_func` VALUES (7979, 42, 95);
INSERT INTO `auth_role_func` VALUES (7980, 42, 96);
INSERT INTO `auth_role_func` VALUES (7981, 42, 97);
INSERT INTO `auth_role_func` VALUES (7982, 42, 93);
INSERT INTO `auth_role_func` VALUES (7983, 42, 94);
INSERT INTO `auth_role_func` VALUES (7984, 42, 108);
INSERT INTO `auth_role_func` VALUES (7985, 42, 109);
INSERT INTO `auth_role_func` VALUES (7986, 42, 110);
INSERT INTO `auth_role_func` VALUES (7987, 42, 111);

-- ----------------------------
-- Table structure for auth_user
-- ----------------------------
DROP TABLE IF EXISTS `auth_user`;
CREATE TABLE `auth_user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户账号',
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '真实名称',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `dept_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门编号',
  `user_status` int(255) NOT NULL COMMENT '状态',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `ix_username`(`username`) USING BTREE,
  INDEX `ix_deptcode`(`dept_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 89 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of auth_user
-- ----------------------------
INSERT INTO `auth_user` VALUES (61, 'admin', '系统管理员', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'yfb', 1, '2022-05-01 23:27:25', '2023-07-13 11:53:13');
INSERT INTO `auth_user` VALUES (88, 'user', '普通用户', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'khb1', 1, '2023-07-13 11:53:01', '2023-07-15 15:03:11');

-- ----------------------------
-- Table structure for auth_user_role
-- ----------------------------
DROP TABLE IF EXISTS `auth_user_role`;
CREATE TABLE `auth_user_role`  (
  `user_role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '组件',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`user_role_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_role_id`(`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 909 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of auth_user_role
-- ----------------------------
INSERT INTO `auth_user_role` VALUES (907, 88, 52);
INSERT INTO `auth_user_role` VALUES (908, 61, 42);

-- ----------------------------
-- Table structure for edu_catalog
-- ----------------------------
DROP TABLE IF EXISTS `edu_catalog`;
CREATE TABLE `edu_catalog`  (
  `catalog_id` int(11) NOT NULL COMMENT '分类id',
  `catalog_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类名称',
  `parent_id` int(11) NOT NULL DEFAULT 0 COMMENT '父级分类',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`catalog_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of edu_catalog
-- ----------------------------

-- ----------------------------
-- Table structure for edu_chapter
-- ----------------------------
DROP TABLE IF EXISTS `edu_chapter`;
CREATE TABLE `edu_chapter`  (
  `chapter_id` int(11) NOT NULL COMMENT '课程章节id',
  `chapter_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '章节标题',
  `course_id` int(11) NOT NULL COMMENT '课程id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`chapter_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of edu_chapter
-- ----------------------------

-- ----------------------------
-- Table structure for edu_course
-- ----------------------------
DROP TABLE IF EXISTS `edu_course`;
CREATE TABLE `edu_course`  (
  `course_id` int(11) NOT NULL COMMENT '课程id',
  `course_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程名称',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '描述信息',
  `creator_id` int(11) NOT NULL COMMENT '创建人id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`course_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of edu_course
-- ----------------------------

-- ----------------------------
-- Table structure for edu_course_catalog
-- ----------------------------
DROP TABLE IF EXISTS `edu_course_catalog`;
CREATE TABLE `edu_course_catalog`  (
  `course_catalog_id` int(11) NOT NULL COMMENT '课程分类关联id',
  `course_id` int(11) NOT NULL COMMENT '课程id',
  `catalog_id` int(11) NOT NULL COMMENT '分类id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`course_catalog_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of edu_course_catalog
-- ----------------------------

-- ----------------------------
-- Table structure for edu_lesson
-- ----------------------------
DROP TABLE IF EXISTS `edu_lesson`;
CREATE TABLE `edu_lesson`  (
  `lesson_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '课时ID',
  `course_id` int(11) NOT NULL COMMENT '所属课程id',
  `chapter_id` int(11) NOT NULL COMMENT '所属章节ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课时标题',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '课时描述',
  `content_type` enum('Video','Document','Quiz','Assignment') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课时内容类型',
  `content_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课时内容URL',
  `duration_minutes` int(11) NULL DEFAULT NULL COMMENT '课时时长（分钟）',
  `sequence_order` int(11) NOT NULL COMMENT '在章节中的顺序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`lesson_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of edu_lesson
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
