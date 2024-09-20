/*
 Navicat Premium Data Transfer

 Source Server         : 本地mysql
 Source Server Type    : MySQL
 Source Server Version : 50735
 Source Host           : localhost:3306
 Source Schema         : admin_dev

 Target Server Type    : MySQL
 Target Server Version : 50735
 File Encoding         : 65001

 Date: 20/09/2024 17:18:17
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
  `hide` int(11) NOT NULL COMMENT '是否隐藏',
  `func_sort` int(11) NOT NULL COMMENT '功能排序',
  `parent_id` int(11) NOT NULL COMMENT '父级功能id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`func_id`) USING BTREE,
  UNIQUE INDEX `idx_func_key`(`func_key`) USING BTREE,
  INDEX `idx_parent_id`(`parent_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 174 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of auth_func
-- ----------------------------
INSERT INTO `auth_func` VALUES (2, '权限系统', '/auth', 'dir', 0, 1, 0, '2022-03-19 15:57:28', '2024-06-16 20:51:25');
INSERT INTO `auth_func` VALUES (3, '用户管理', '/auth/user', 'dir', 0, 1, 2, '2022-03-19 16:10:26', '2023-10-05 14:18:01');
INSERT INTO `auth_func` VALUES (4, '角色管理', '/auth/role', 'dir', 0, 4, 2, '2022-03-19 16:11:29', '2023-09-15 21:40:09');
INSERT INTO `auth_func` VALUES (5, '功能管理', '/auth/func', 'dir', 0, 2, 2, '2022-03-19 16:11:41', '2024-06-16 20:51:29');
INSERT INTO `auth_func` VALUES (6, '资源管理', '/auth/resc', 'dir', 0, 5, 2, '2022-03-19 16:11:54', '2023-09-15 21:33:29');
INSERT INTO `auth_func` VALUES (8, '角色删除', 'auth:role:delete', 'btn', 0, 7, 4, '2022-03-19 18:23:22', '2023-09-03 18:58:48');
INSERT INTO `auth_func` VALUES (9, '角色更新', 'auth:role:update', 'btn', 0, 8, 4, '2022-03-19 18:23:38', '2022-03-19 18:23:40');
INSERT INTO `auth_func` VALUES (11, '角色查询', 'auth:role:page', 'btn', 0, 9, 4, '2022-03-19 18:24:02', '2022-03-19 18:24:03');
INSERT INTO `auth_func` VALUES (12, '功能查询', 'auth:func:page', 'btn', 0, 5, 5, '2022-03-19 18:24:30', '2023-09-03 18:58:58');
INSERT INTO `auth_func` VALUES (13, '列表查询', 'auth:resc:page', 'btn', 0, 4, 6, '2022-03-19 18:24:51', '2023-07-15 23:17:47');
INSERT INTO `auth_func` VALUES (14, '功能新增', 'auth:func:add', 'btn', 0, 6, 5, '2022-03-19 18:26:06', '2023-09-03 18:58:59');
INSERT INTO `auth_func` VALUES (26, '资源添加', 'auth:resc:add', 'btn', 0, 5, 6, '2022-03-20 21:43:55', '2023-07-15 23:18:31');
INSERT INTO `auth_func` VALUES (27, '资源删除', 'auth:resc:delete', 'btn', 0, 6, 6, '2022-03-20 21:44:03', '2023-09-03 18:58:47');
INSERT INTO `auth_func` VALUES (54, '功能删除', 'auth:func:delete', 'btn', 0, 7, 5, '2022-03-26 20:57:44', '2023-09-03 18:58:41');
INSERT INTO `auth_func` VALUES (55, '功能更新', 'auth:func:update', 'btn', 0, 8, 5, '2022-03-26 20:58:00', '2023-09-03 18:58:43');
INSERT INTO `auth_func` VALUES (56, '资源编辑', 'auth:resc:update', 'btn', 0, 7, 6, '2022-03-26 21:00:16', '2023-07-15 23:18:21');
INSERT INTO `auth_func` VALUES (57, '用户查询', 'auth:user:page', 'btn', 0, 2, 3, '2022-03-26 21:04:37', '2023-09-09 11:45:35');
INSERT INTO `auth_func` VALUES (60, '用户编辑', 'auth:user:update', 'btn', 0, 5, 3, '2022-03-26 21:05:33', '2023-07-15 23:18:48');
INSERT INTO `auth_func` VALUES (61, '用户删除', 'auth:user:delete', 'btn', 0, 6, 3, '2022-03-26 21:05:55', '2023-09-23 15:29:31');
INSERT INTO `auth_func` VALUES (62, '分配角色', 'auth:user:roleUpdate', 'btn', 0, 7, 3, '2022-03-26 21:06:48', '2023-09-03 18:58:45');
INSERT INTO `auth_func` VALUES (63, '分配用户', 'auth:role:userUpdate', 'btn', 0, 6, 4, '2022-03-26 21:08:39', '2023-07-15 15:28:20');
INSERT INTO `auth_func` VALUES (71, '角色授权', '/auth/role/authorized', 'dir', 0, 5, 4, '2022-04-04 17:45:36', '2023-09-15 21:37:22');
INSERT INTO `auth_func` VALUES (72, '角色新增', 'auth:role:add', 'btn', 0, 10, 4, '2022-04-16 11:13:19', '2023-07-01 11:12:50');
INSERT INTO `auth_func` VALUES (73, '解绑用户', 'auth:func:userRemove', 'btn', 0, 9, 5, '2022-04-16 21:55:19', '2023-07-15 15:27:43');
INSERT INTO `auth_func` VALUES (74, '解绑资源', 'auth:func:rescRemove', 'btn', 0, 10, 5, '2022-04-16 22:02:49', '2023-09-03 13:58:36');
INSERT INTO `auth_func` VALUES (75, '用户添加', 'auth:user:add', 'btn', 0, 8, 3, '2022-05-02 11:40:57', '2023-07-15 23:19:01');
INSERT INTO `auth_func` VALUES (84, '修改密码', 'auth:user:pwdUpdate', 'btn', 0, 9, 3, '2022-05-14 12:20:12', '2023-07-15 15:27:23');
INSERT INTO `auth_func` VALUES (93, '同步系统', '/sync', 'dir', 0, 4, 0, '2022-09-13 20:54:26', '2023-11-09 20:34:18');
INSERT INTO `auth_func` VALUES (94, '同步计划', '/sync/plan', 'dir', 0, 0, 93, '2022-09-13 20:54:58', '2024-05-17 21:01:26');
INSERT INTO `auth_func` VALUES (95, '同步记录', '/sync/result', 'dir', 0, 3, 93, '2022-12-04 23:48:14', '2023-10-31 09:51:55');
INSERT INTO `auth_func` VALUES (96, '消息回调', '/sync/callback', 'dir', 0, 5, 93, '2022-12-04 23:48:39', '2023-10-14 22:24:08');
INSERT INTO `auth_func` VALUES (97, '本地消息', '/sync/msg', 'dir', 0, 4, 93, '2022-12-04 23:49:04', '2023-10-14 22:24:37');
INSERT INTO `auth_func` VALUES (102, '绑定资源', 'auth:func:rescUpdate', 'btn', 0, 4, 5, '2023-06-17 21:00:09', '2023-07-15 23:17:19');
INSERT INTO `auth_func` VALUES (105, '授权初始化', 'auth:role:funcInit', 'btn', 0, 0, 71, '2023-06-21 21:02:03', '2023-09-24 21:44:26');
INSERT INTO `auth_func` VALUES (106, '更新授权', 'auth:role:funcUpdate', 'btn', 0, 0, 71, '2023-06-21 21:02:29', '2023-10-05 14:19:56');
INSERT INTO `auth_func` VALUES (128, '切换登录', 'auth:user:switchLogin', 'btn', 0, 9, 3, '2023-07-15 15:35:51', '2023-07-16 18:19:27');
INSERT INTO `auth_func` VALUES (129, '状态更新', 'auth:user:statusUpdate', 'btn', 0, 4, 3, '2023-07-15 20:40:12', '2023-09-23 15:29:39');
INSERT INTO `auth_func` VALUES (136, '计划查询', 'sync:plan:page', 'btn', 0, 0, 94, '2023-07-18 20:53:06', '2023-09-03 18:58:32');
INSERT INTO `auth_func` VALUES (137, '新增计划', 'sync:plan:add', 'btn', 0, 1, 94, '2023-07-19 21:04:36', '2023-09-03 13:23:09');
INSERT INTO `auth_func` VALUES (138, '编辑计划', 'sync:plan:update', 'btn', 0, 3, 94, '2023-07-19 22:08:32', '2023-09-03 18:58:36');
INSERT INTO `auth_func` VALUES (139, '删除计划', 'sync:plan:delete', 'btn', 0, 4, 94, '2023-07-19 22:26:53', '2024-06-17 22:07:11');
INSERT INTO `auth_func` VALUES (140, '启用禁用', 'sync:plan:updateStatus', 'btn', 0, 7, 94, '2023-09-03 13:19:10', '2023-09-03 15:20:12');
INSERT INTO `auth_func` VALUES (141, '消息列表', 'sync:msg:page', 'btn', 0, 1, 97, '2023-09-03 19:10:00', '2023-09-03 19:10:00');
INSERT INTO `auth_func` VALUES (142, '记录列表', 'sync:result:page', 'btn', 0, 1, 95, '2023-09-03 19:26:31', '2023-09-03 19:26:31');
INSERT INTO `auth_func` VALUES (143, '标记成功', 'sync:result:signSuccess', 'btn', 0, 2, 95, '2023-09-03 19:32:55', '2023-09-03 19:32:55');
INSERT INTO `auth_func` VALUES (144, '重新同步', 'sync:result:repeatSync', 'btn', 0, 3, 95, '2023-09-03 19:33:22', '2023-09-03 19:33:22');
INSERT INTO `auth_func` VALUES (145, '校验结果', 'sync:result:validResultData', 'btn', 0, 4, 95, '2023-09-03 19:33:48', '2023-09-03 19:33:48');
INSERT INTO `auth_func` VALUES (156, '功能关联', 'auth:resc:funcSearch', 'btn', 0, 1, 6, '2023-09-17 13:18:24', '2023-09-17 13:18:24');
INSERT INTO `auth_func` VALUES (160, '操作日志', '/auth/log', 'dir', 0, 7, 2, '2023-09-28 11:05:24', '2023-09-28 11:05:24');
INSERT INTO `auth_func` VALUES (161, '列表查询', 'auth:log:page', 'btn', 0, 1, 160, '2023-09-28 11:06:21', '2023-09-28 11:06:21');
INSERT INTO `auth_func` VALUES (162, '资源刷新', 'auth:resc:refresh', 'btn', 0, 8, 6, '2023-09-28 11:51:58', '2023-09-28 11:51:58');
INSERT INTO `auth_func` VALUES (171, '回调列表', 'sync:callback:page', 'btn', 0, 1, 96, '2023-11-09 20:35:55', '2023-11-09 20:35:55');
INSERT INTO `auth_func` VALUES (172, '重新回调', 'sync:callback:repeat', 'btn', 0, 3, 96, '2023-11-09 20:36:32', '2023-11-11 21:23:42');
INSERT INTO `auth_func` VALUES (173, '计划刷新', 'sync:plan:refresh', 'btn', 0, 3, 94, '2023-11-09 20:42:43', '2023-11-11 21:48:14');

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
) ENGINE = InnoDB AUTO_INCREMENT = 541 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of auth_func_resc
-- ----------------------------
INSERT INTO `auth_func_resc` VALUES (307, 27, 24);
INSERT INTO `auth_func_resc` VALUES (312, 73, 39);
INSERT INTO `auth_func_resc` VALUES (313, 72, 19);
INSERT INTO `auth_func_resc` VALUES (322, 11, 18);
INSERT INTO `auth_func_resc` VALUES (329, 13, 20);
INSERT INTO `auth_func_resc` VALUES (330, 13, 41);
INSERT INTO `auth_func_resc` VALUES (339, 8, 15);
INSERT INTO `auth_func_resc` VALUES (382, 62, 9);
INSERT INTO `auth_func_resc` VALUES (383, 62, 10);
INSERT INTO `auth_func_resc` VALUES (387, 26, 21);
INSERT INTO `auth_func_resc` VALUES (390, 55, 30);
INSERT INTO `auth_func_resc` VALUES (391, 55, 29);
INSERT INTO `auth_func_resc` VALUES (403, 14, 32);
INSERT INTO `auth_func_resc` VALUES (404, 74, 40);
INSERT INTO `auth_func_resc` VALUES (405, 9, 16);
INSERT INTO `auth_func_resc` VALUES (406, 9, 17);
INSERT INTO `auth_func_resc` VALUES (410, 12, 33);
INSERT INTO `auth_func_resc` VALUES (411, 12, 34);
INSERT INTO `auth_func_resc` VALUES (456, 102, 30);
INSERT INTO `auth_func_resc` VALUES (457, 102, 44);
INSERT INTO `auth_func_resc` VALUES (458, 84, 42);
INSERT INTO `auth_func_resc` VALUES (459, 63, 35);
INSERT INTO `auth_func_resc` VALUES (460, 63, 36);
INSERT INTO `auth_func_resc` VALUES (461, 57, 1);
INSERT INTO `auth_func_resc` VALUES (462, 57, 11);
INSERT INTO `auth_func_resc` VALUES (463, 129, 12);
INSERT INTO `auth_func_resc` VALUES (464, 129, 14);
INSERT INTO `auth_func_resc` VALUES (468, 128, 45);
INSERT INTO `auth_func_resc` VALUES (470, 56, 23);
INSERT INTO `auth_func_resc` VALUES (471, 56, 22);
INSERT INTO `auth_func_resc` VALUES (474, 106, 38);
INSERT INTO `auth_func_resc` VALUES (475, 106, 43);
INSERT INTO `auth_func_resc` VALUES (477, 105, 37);
INSERT INTO `auth_func_resc` VALUES (505, 61, 3);
INSERT INTO `auth_func_resc` VALUES (510, 161, 62);
INSERT INTO `auth_func_resc` VALUES (511, 162, 63);
INSERT INTO `auth_func_resc` VALUES (521, 60, 4);
INSERT INTO `auth_func_resc` VALUES (522, 60, 2);
INSERT INTO `auth_func_resc` VALUES (523, 60, 11);
INSERT INTO `auth_func_resc` VALUES (524, 75, 27);
INSERT INTO `auth_func_resc` VALUES (526, 142, 73);
INSERT INTO `auth_func_resc` VALUES (527, 143, 74);
INSERT INTO `auth_func_resc` VALUES (528, 144, 76);
INSERT INTO `auth_func_resc` VALUES (529, 145, 75);
INSERT INTO `auth_func_resc` VALUES (530, 141, 69);
INSERT INTO `auth_func_resc` VALUES (531, 171, 78);
INSERT INTO `auth_func_resc` VALUES (532, 172, 79);
INSERT INTO `auth_func_resc` VALUES (533, 136, 66);
INSERT INTO `auth_func_resc` VALUES (534, 137, 71);
INSERT INTO `auth_func_resc` VALUES (535, 138, 67);
INSERT INTO `auth_func_resc` VALUES (536, 138, 70);
INSERT INTO `auth_func_resc` VALUES (537, 139, 72);
INSERT INTO `auth_func_resc` VALUES (538, 140, 68);
INSERT INTO `auth_func_resc` VALUES (539, 173, 77);
INSERT INTO `auth_func_resc` VALUES (540, 54, 31);

-- ----------------------------
-- Table structure for auth_log
-- ----------------------------
DROP TABLE IF EXISTS `auth_log`;
CREATE TABLE `auth_log`  (
  `log_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `log_info` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日志内容',
  `log_module` int(11) NOT NULL COMMENT '模块类型',
  `log_level` int(11) NOT NULL COMMENT '日志等级',
  `ip_address` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ip',
  `device_info` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备信息',
  `location` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '位置',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '操作人id',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人账号',
  `real_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人姓名',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`) USING BTREE,
  INDEX `idx_log_module`(`log_module`) USING BTREE,
  INDEX `idx_user_id_name`(`user_id`, `username`) USING BTREE,
  INDEX `idx_log_level`(`log_level`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 592 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of auth_log
-- ----------------------------
INSERT INTO `auth_log` VALUES (585, '更新用户信息', 0, 1, '192.168.2.108', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-08-05 16:46:20');
INSERT INTO `auth_log` VALUES (586, '用户登录系统', 0, 0, '192.168.2.98', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-19 14:45:39');
INSERT INTO `auth_log` VALUES (587, '用户登录系统', 0, 0, '192.168.2.98', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-20 14:12:12');
INSERT INTO `auth_log` VALUES (588, '用户登录系统', 0, 0, '192.168.2.98', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-20 14:16:59');
INSERT INTO `auth_log` VALUES (589, '用户登录系统', 0, 0, '192.168.2.98', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-20 14:20:08');
INSERT INTO `auth_log` VALUES (590, '更新资源信息', 2, 1, '192.168.2.98', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-20 14:21:20');
INSERT INTO `auth_log` VALUES (591, '用户登录系统', 0, 0, '192.168.2.98', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-20 16:53:34');

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
) ENGINE = InnoDB AUTO_INCREMENT = 80 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of auth_resc
-- ----------------------------
INSERT INTO `auth_resc` VALUES (1, '用户列表', '/auth/user/page', 'auth', 'get', '2022-03-19 09:31:31', '2023-09-23 15:30:56');
INSERT INTO `auth_resc` VALUES (2, '用户获取', '/auth/user/detail', 'auth', 'get', '2022-03-19 09:34:24', '2023-09-28 12:04:30');
INSERT INTO `auth_resc` VALUES (3, '用户删除', '/auth/user/delete', 'auth', 'delete', '2022-03-19 09:34:40', '2023-07-15 00:35:06');
INSERT INTO `auth_resc` VALUES (4, '用户更新', '/auth/user/update', 'auth', 'post', '2022-03-19 09:34:58', '2022-05-10 22:36:29');
INSERT INTO `auth_resc` VALUES (9, '用户分配角色初始化', '/auth/user/role/init', 'auth', 'get', '2022-03-19 10:29:15', '2023-07-11 23:51:20');
INSERT INTO `auth_resc` VALUES (10, '用户分配角色', '/auth/user/role/update', 'auth', 'post', '2022-03-19 10:29:41', '2022-09-04 14:33:38');
INSERT INTO `auth_resc` VALUES (11, '部门列表', '/auth/dept/list', 'auth', 'get', '2022-03-19 10:30:01', '2022-04-09 14:03:20');
INSERT INTO `auth_resc` VALUES (12, '用户启用', '/auth/user/enable', 'auth', 'get', '2022-03-19 10:30:16', '2023-07-11 23:43:03');
INSERT INTO `auth_resc` VALUES (14, '用户禁用', '/auth/user/disable', 'auth', 'get', '2022-03-19 10:30:48', '2023-07-11 23:42:56');
INSERT INTO `auth_resc` VALUES (15, '角色删除', '/auth/role/delete', 'auth', 'delete', '2022-03-19 10:31:30', '2023-06-16 21:33:38');
INSERT INTO `auth_resc` VALUES (16, '角色更新', '/auth/role/update', 'auth', 'post', '2022-03-19 10:31:44', '2022-05-01 22:23:59');
INSERT INTO `auth_resc` VALUES (17, '角色获取', '/auth/role/detail', 'auth', 'get', '2022-03-19 10:32:06', '2022-04-09 14:02:34');
INSERT INTO `auth_resc` VALUES (18, '角色列表', '/auth/role/page', 'auth', 'get', '2022-03-19 10:32:28', '2022-04-09 14:02:25');
INSERT INTO `auth_resc` VALUES (19, '角色新增', '/auth/role/save', 'auth', 'post', '2022-03-19 10:32:48', '2022-04-09 14:02:29');
INSERT INTO `auth_resc` VALUES (20, '资源列表', '/auth/resc/page', 'auth', 'get', '2022-03-19 10:33:08', '2022-09-04 14:44:14');
INSERT INTO `auth_resc` VALUES (21, '资源新增', '/auth/resc/save', 'auth', 'post', '2022-03-19 10:33:26', '2022-04-09 14:02:15');
INSERT INTO `auth_resc` VALUES (22, '资源获取', '/auth/resc/detail', 'auth', 'get', '2022-03-19 10:33:42', '2022-04-09 14:02:06');
INSERT INTO `auth_resc` VALUES (23, '资源更新', '/auth/resc/update', 'auth', 'post', '2022-03-19 10:33:56', '2023-07-15 10:29:20');
INSERT INTO `auth_resc` VALUES (24, '资源删除', '/auth/resc/delete', 'auth', 'delete', '2022-03-19 10:34:13', '2023-06-16 21:33:34');
INSERT INTO `auth_resc` VALUES (27, '用户新增', '/auth/user/save', 'auth', 'post', '2022-03-19 11:18:04', '2022-04-09 14:01:38');
INSERT INTO `auth_resc` VALUES (29, '功能更新', '/auth/func/update', 'auth', 'post', '2022-03-26 20:52:38', '2022-05-01 21:57:53');
INSERT INTO `auth_resc` VALUES (30, '功能获取', '/auth/func/detail', 'auth', 'get', '2022-03-26 20:52:51', '2022-04-09 14:01:25');
INSERT INTO `auth_resc` VALUES (31, '功能删除', '/auth/func/delete', 'auth', 'delete', '2022-03-26 20:53:09', '2023-06-17 21:04:10');
INSERT INTO `auth_resc` VALUES (32, '功能新增', '/auth/func/save', 'auth', 'post', '2022-03-26 20:53:24', '2023-06-17 21:04:07');
INSERT INTO `auth_resc` VALUES (33, '功能查询', '/auth/func/page', 'auth', 'get', '2022-03-26 20:53:40', '2022-09-11 16:05:51');
INSERT INTO `auth_resc` VALUES (34, '功能树获取', '/auth/func/tree', 'auth', 'get', '2022-03-26 20:53:58', '2023-07-11 23:40:28');
INSERT INTO `auth_resc` VALUES (35, '角色分配用户初始化', '/auth/role/user/update/init', 'auth', 'get', '2022-03-26 21:07:44', '2024-09-20 14:21:20');
INSERT INTO `auth_resc` VALUES (36, '角色分配用户', '/auth/role/user/update', 'auth', 'post', '2022-03-26 21:07:59', '2022-09-03 16:02:22');
INSERT INTO `auth_resc` VALUES (37, '角色授权功能初始化', '/auth/role/func/init', 'auth', 'get', '2022-04-04 17:46:28', '2023-07-11 23:54:43');
INSERT INTO `auth_resc` VALUES (38, '角色分配功能', '/auth/role/func/update', 'auth', 'post', '2022-04-04 17:46:57', '2023-07-08 10:09:07');
INSERT INTO `auth_resc` VALUES (39, '功能解绑用户', '/auth/func/user/remove', 'auth', 'delete', '2022-04-16 21:54:38', '2023-07-11 23:45:41');
INSERT INTO `auth_resc` VALUES (40, '功能解绑资源', '/auth/func/resc/remove', 'auth', 'delete', '2022-04-16 22:02:24', '2023-09-03 12:59:45');
INSERT INTO `auth_resc` VALUES (41, '资源搜索', '/auth/resc/search', 'auth', 'get', '2022-05-01 19:17:13', '2023-09-03 14:02:02');
INSERT INTO `auth_resc` VALUES (42, '用户修改密码', '/auth/user/pwd/update', 'auth', 'post', '2022-05-14 12:19:03', '2023-07-16 14:07:43');
INSERT INTO `auth_resc` VALUES (43, '角色授权功能预检查', '/auth/role/func/check', 'auth', 'post', '2022-05-21 15:34:56', '2023-09-23 15:30:41');
INSERT INTO `auth_resc` VALUES (44, '功能设置资源', '/auth/func/resc/update', 'auth', 'put', '2023-06-17 21:01:39', '2023-09-03 13:43:05');
INSERT INTO `auth_resc` VALUES (45, '切换登录', '/auth/user/switch/login', 'auth', 'post', '2023-07-15 22:19:29', '2023-10-01 11:01:49');
INSERT INTO `auth_resc` VALUES (62, '系统操作日志', '/auth/log/page', 'auth', 'post', '2023-09-28 11:04:54', '2023-09-28 11:10:15');
INSERT INTO `auth_resc` VALUES (63, '资源刷新', '/auth/resc/refresh', 'auth', 'put', '2023-09-28 11:52:26', '2023-09-28 11:52:26');
INSERT INTO `auth_resc` VALUES (66, '同步计划查询', '/sync/plan/page', 'sync', 'put', '2023-07-18 20:50:10', '2023-10-18 19:52:28');
INSERT INTO `auth_resc` VALUES (67, '同步计划详情', '/sync/plan/detail', 'sync', 'get', '2023-07-18 20:51:07', '2023-10-26 22:55:58');
INSERT INTO `auth_resc` VALUES (68, '同步计划状态', '/sync/plan/update/status', 'sync', 'put', '2023-09-03 13:20:02', '2023-11-04 19:36:15');
INSERT INTO `auth_resc` VALUES (69, '同步消息列表', '/sync/message/page', 'sync', 'put', '2023-09-03 19:09:05', '2023-10-27 20:14:09');
INSERT INTO `auth_resc` VALUES (70, '同步计划更新', '/sync/plan/update', 'sync', 'put', '2023-09-03 19:21:40', '2023-11-11 21:44:14');
INSERT INTO `auth_resc` VALUES (71, '同步计划新增', '/sync/plan/save', 'sync', 'put', '2023-09-03 19:24:36', '2023-11-04 19:58:10');
INSERT INTO `auth_resc` VALUES (72, '同步计划删除', '/sync/plan/delete', 'sync', 'delete', '2023-09-03 19:25:22', '2023-11-05 00:50:56');
INSERT INTO `auth_resc` VALUES (73, '同步记录列表', '/sync/result/page', 'sync', 'post', '2023-09-03 19:27:25', '2023-11-04 19:36:16');
INSERT INTO `auth_resc` VALUES (74, '标记成功', '/sync/result/sign/success', 'sync', 'put', '2023-09-03 19:29:38', '2023-11-05 21:49:19');
INSERT INTO `auth_resc` VALUES (75, '校验同步结果', '/sync/result/valid/data', 'sync', 'put', '2023-09-03 19:30:19', '2023-10-29 19:13:43');
INSERT INTO `auth_resc` VALUES (76, '重新同步', '/sync/result/repeat/sync', 'sync', 'put', '2023-09-03 19:31:53', '2023-11-04 19:36:18');
INSERT INTO `auth_resc` VALUES (77, '同步计划刷新', '/sync/plan/refresh', 'sync', 'put', '2023-10-22 20:34:47', '2023-11-05 21:49:17');
INSERT INTO `auth_resc` VALUES (78, '回调列表', '/sync/callback/page', 'sync', 'post', '2023-11-04 21:30:49', '2023-11-12 19:30:45');
INSERT INTO `auth_resc` VALUES (79, '重新回调', '/sync/callback/repeat', 'sync', 'post', '2023-11-05 12:56:41', '2024-06-17 22:04:07');

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
INSERT INTO `auth_role` VALUES (42, '管理员角色', '管理员角色', '2022-04-16 11:38:56', '2023-11-11 21:43:09');
INSERT INTO `auth_role` VALUES (51, '普通用户角色', '基础权限', '2024-06-17 22:00:51', '2024-06-17 22:00:51');

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
) ENGINE = InnoDB AUTO_INCREMENT = 16820 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of auth_role_func
-- ----------------------------
INSERT INTO `auth_role_func` VALUES (16734, 42, 128);
INSERT INTO `auth_role_func` VALUES (16735, 42, 129);
INSERT INTO `auth_role_func` VALUES (16736, 42, 2);
INSERT INTO `auth_role_func` VALUES (16737, 42, 3);
INSERT INTO `auth_role_func` VALUES (16738, 42, 4);
INSERT INTO `auth_role_func` VALUES (16739, 42, 5);
INSERT INTO `auth_role_func` VALUES (16740, 42, 6);
INSERT INTO `auth_role_func` VALUES (16741, 42, 8);
INSERT INTO `auth_role_func` VALUES (16742, 42, 9);
INSERT INTO `auth_role_func` VALUES (16743, 42, 11);
INSERT INTO `auth_role_func` VALUES (16744, 42, 12);
INSERT INTO `auth_role_func` VALUES (16745, 42, 13);
INSERT INTO `auth_role_func` VALUES (16746, 42, 14);
INSERT INTO `auth_role_func` VALUES (16747, 42, 26);
INSERT INTO `auth_role_func` VALUES (16748, 42, 27);
INSERT INTO `auth_role_func` VALUES (16749, 42, 156);
INSERT INTO `auth_role_func` VALUES (16750, 42, 160);
INSERT INTO `auth_role_func` VALUES (16751, 42, 161);
INSERT INTO `auth_role_func` VALUES (16752, 42, 162);
INSERT INTO `auth_role_func` VALUES (16753, 42, 54);
INSERT INTO `auth_role_func` VALUES (16754, 42, 55);
INSERT INTO `auth_role_func` VALUES (16755, 42, 56);
INSERT INTO `auth_role_func` VALUES (16756, 42, 57);
INSERT INTO `auth_role_func` VALUES (16757, 42, 60);
INSERT INTO `auth_role_func` VALUES (16758, 42, 61);
INSERT INTO `auth_role_func` VALUES (16759, 42, 62);
INSERT INTO `auth_role_func` VALUES (16760, 42, 63);
INSERT INTO `auth_role_func` VALUES (16761, 42, 71);
INSERT INTO `auth_role_func` VALUES (16762, 42, 72);
INSERT INTO `auth_role_func` VALUES (16763, 42, 73);
INSERT INTO `auth_role_func` VALUES (16764, 42, 74);
INSERT INTO `auth_role_func` VALUES (16765, 42, 75);
INSERT INTO `auth_role_func` VALUES (16766, 42, 84);
INSERT INTO `auth_role_func` VALUES (16767, 42, 102);
INSERT INTO `auth_role_func` VALUES (16768, 42, 105);
INSERT INTO `auth_role_func` VALUES (16769, 42, 106);
INSERT INTO `auth_role_func` VALUES (16784, 42, 136);
INSERT INTO `auth_role_func` VALUES (16785, 42, 137);
INSERT INTO `auth_role_func` VALUES (16786, 42, 138);
INSERT INTO `auth_role_func` VALUES (16787, 42, 139);
INSERT INTO `auth_role_func` VALUES (16788, 42, 140);
INSERT INTO `auth_role_func` VALUES (16789, 42, 141);
INSERT INTO `auth_role_func` VALUES (16790, 42, 142);
INSERT INTO `auth_role_func` VALUES (16791, 42, 143);
INSERT INTO `auth_role_func` VALUES (16792, 42, 144);
INSERT INTO `auth_role_func` VALUES (16793, 42, 145);
INSERT INTO `auth_role_func` VALUES (16794, 42, 171);
INSERT INTO `auth_role_func` VALUES (16795, 42, 172);
INSERT INTO `auth_role_func` VALUES (16796, 42, 173);
INSERT INTO `auth_role_func` VALUES (16797, 42, 93);
INSERT INTO `auth_role_func` VALUES (16798, 42, 94);
INSERT INTO `auth_role_func` VALUES (16799, 42, 95);
INSERT INTO `auth_role_func` VALUES (16800, 42, 96);
INSERT INTO `auth_role_func` VALUES (16801, 42, 97);
INSERT INTO `auth_role_func` VALUES (16802, 51, 93);
INSERT INTO `auth_role_func` VALUES (16803, 51, 94);
INSERT INTO `auth_role_func` VALUES (16804, 51, 136);
INSERT INTO `auth_role_func` VALUES (16805, 51, 137);
INSERT INTO `auth_role_func` VALUES (16806, 51, 138);
INSERT INTO `auth_role_func` VALUES (16807, 51, 173);
INSERT INTO `auth_role_func` VALUES (16808, 51, 139);
INSERT INTO `auth_role_func` VALUES (16809, 51, 140);
INSERT INTO `auth_role_func` VALUES (16810, 51, 95);
INSERT INTO `auth_role_func` VALUES (16811, 51, 142);
INSERT INTO `auth_role_func` VALUES (16812, 51, 143);
INSERT INTO `auth_role_func` VALUES (16813, 51, 144);
INSERT INTO `auth_role_func` VALUES (16814, 51, 145);
INSERT INTO `auth_role_func` VALUES (16815, 51, 97);
INSERT INTO `auth_role_func` VALUES (16816, 51, 141);
INSERT INTO `auth_role_func` VALUES (16817, 51, 96);
INSERT INTO `auth_role_func` VALUES (16818, 51, 171);
INSERT INTO `auth_role_func` VALUES (16819, 51, 172);

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
) ENGINE = InnoDB AUTO_INCREMENT = 85 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of auth_user
-- ----------------------------
INSERT INTO `auth_user` VALUES (61, 'admin', '管理员', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'yfb', 1, '2022-05-01 23:27:25', '2023-11-18 14:17:07');
INSERT INTO `auth_user` VALUES (83, 'user', '普通用户', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'yfb', 1, '2024-06-17 22:00:14', '2024-08-05 16:46:20');

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
) ENGINE = InnoDB AUTO_INCREMENT = 961 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of auth_user_role
-- ----------------------------
INSERT INTO `auth_user_role` VALUES (955, 61, 42);
INSERT INTO `auth_user_role` VALUES (960, 83, 51);

-- ----------------------------
-- Table structure for pub_callback
-- ----------------------------
DROP TABLE IF EXISTS `pub_callback`;
CREATE TABLE `pub_callback`  (
  `callback_id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `msg_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `sync_uuid` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `src_project` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `dest_project` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `db_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `msg_content` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` datetime NOT NULL,
  `send_time` datetime NULL DEFAULT NULL,
  `ack_time` datetime NULL DEFAULT NULL,
  `repeat_count` int(11) NOT NULL,
  `status` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `error_msg` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`callback_id`) USING BTREE,
  INDEX `ix_create_time`(`create_time`) USING BTREE,
  INDEX `ix_send_time`(`send_time`) USING BTREE,
  INDEX `ix_status`(`status`) USING BTREE,
  INDEX `msg_id`(`msg_id`) USING BTREE,
  INDEX `ix_uq_uuid`(`uuid`) USING BTREE,
  FULLTEXT INDEX `ix_msg_content`(`msg_content`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pub_callback
-- ----------------------------

-- ----------------------------
-- Table structure for pub_mq_message
-- ----------------------------
DROP TABLE IF EXISTS `pub_mq_message`;
CREATE TABLE `pub_mq_message`  (
  `mq_message_id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `mid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `msg_content` varchar(8192) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `src_project` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `dest_project` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` datetime NOT NULL,
  `send_time` datetime NULL DEFAULT NULL,
  `ack_time` datetime NULL DEFAULT NULL,
  `send_status` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `deal_count` int(11) NOT NULL,
  `deal_status` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`mq_message_id`) USING BTREE,
  INDEX `ix_uq_uuid`(`uuid`) USING BTREE,
  INDEX `ix_create_time`(`create_time`) USING BTREE,
  INDEX `ix_deal_status`(`deal_status`) USING BTREE,
  INDEX `ix_src_project_dest_project_create_time`(`src_project`, `dest_project`, `create_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3839 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pub_mq_message
-- ----------------------------
INSERT INTO `pub_mq_message` VALUES (3830, '2a9b23374dfd4b65a61ee2ceacf42ceb', 'ID:CHINAMI-MC5KLHT-17080-1722847551131-6:2:1:1:1', '{\"plan\":\"insert_admin_sync_log\",\"tables\":[{\"code\":\"insert_admin_sync_log\",\"unique\":[\"585\"]}],\"createTime\":\"2024-08-05 16:46:19\",\"src\":\"admin\",\"msgId\":\"2a9b23374dfd4b65a61ee2ceacf42ceb\"}', 'admin', 'log', '2024-08-05 16:46:19', '2024-08-05 16:46:19', NULL, 'success', 0, 'wait');
INSERT INTO `pub_mq_message` VALUES (3831, 'da55e050c661432cad0e36376d3b1e26', 'ID:CHINAMI-MC5KLHT-17080-1722847551131-6:3:1:1:1', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"83\"]}],\"createTime\":\"2024-08-05 16:46:19\",\"src\":\"admin\",\"msgId\":\"da55e050c661432cad0e36376d3b1e26\"}', 'admin', 'sync', '2024-08-05 16:46:19', '2024-08-05 16:46:19', NULL, 'success', 0, 'wait');
INSERT INTO `pub_mq_message` VALUES (3832, 'b135e8eb882541359473e66cd598feb3', 'C0A80262852818B4AAC25FDD4FE10000', '{\"plan\":\"insert_admin_sync_log\",\"tables\":[{\"code\":\"insert_admin_sync_log\",\"unique\":[\"586\"]}],\"createTime\":\"2024-09-19 14:45:38\",\"src\":\"admin\",\"msgId\":\"b135e8eb882541359473e66cd598feb3\"}', 'admin', 'log', '2024-09-19 14:45:38', '2024-09-19 14:45:39', '2024-09-19 15:01:24', 'success', 1, 'success');
INSERT INTO `pub_mq_message` VALUES (3833, '69bff3bec3044c36bbb6503e89d00185', '', '{\"plan\":\"insert_admin_sync_log\",\"tables\":[{\"code\":\"insert_admin_sync_log\",\"unique\":[\"587\"]}],\"createTime\":\"2024-09-20 14:12:11\",\"src\":\"admin\",\"msgId\":\"69bff3bec3044c36bbb6503e89d00185\"}', 'admin', 'log', '2024-09-20 14:12:11', '2024-09-20 14:12:22', '2024-09-20 14:18:03', 'fail', 1, 'success');
INSERT INTO `pub_mq_message` VALUES (3834, 'a2178cb39f554e108ad488e4f9f5d613', '', '{\"plan\":\"insert_admin_sync_log\",\"tables\":[{\"code\":\"insert_admin_sync_log\",\"unique\":[\"588\"]}],\"createTime\":\"2024-09-20 14:16:59\",\"src\":\"admin\",\"msgId\":\"a2178cb39f554e108ad488e4f9f5d613\"}', 'admin', 'log', '2024-09-20 14:16:59', '2024-09-20 14:17:06', NULL, 'fail', 0, 'wait');
INSERT INTO `pub_mq_message` VALUES (3835, 'ef9defb1f43a454288a2cfee357b4f4c', 'C0A80262605418B4AAC264EC4F8D0002', '{\"plan\":\"insert_admin_sync_log\",\"tables\":[{\"code\":\"insert_admin_sync_log\",\"unique\":[\"589\"]}],\"createTime\":\"2024-09-20 14:20:07\",\"src\":\"admin\",\"msgId\":\"ef9defb1f43a454288a2cfee357b4f4c\"}', 'admin', 'log', '2024-09-20 14:20:07', '2024-09-20 14:20:08', '2024-09-20 14:20:09', 'success', 1, 'success');
INSERT INTO `pub_mq_message` VALUES (3836, '3a5bbb963807475c9878506610537119', 'C0A80262605418B4AAC264ED66E80003', '{\"plan\":\"insert_admin_sync_log\",\"tables\":[{\"code\":\"insert_admin_sync_log\",\"unique\":[\"590\"]}],\"createTime\":\"2024-09-20 14:21:19\",\"src\":\"admin\",\"msgId\":\"3a5bbb963807475c9878506610537119\"}', 'admin', 'log', '2024-09-20 14:21:19', '2024-09-20 14:21:19', '2024-09-20 14:21:20', 'success', 1, 'success');
INSERT INTO `pub_mq_message` VALUES (3837, '006de981aaf242028fafd6355bf42367', 'C0A80262605418B4AAC264ED66FC0004', '{\"plan\":\"update_admin_sync_resc\",\"tables\":[{\"code\":\"update_admin_sync_resc\",\"unique\":[\"35\"]}],\"createTime\":\"2024-09-20 14:21:19\",\"src\":\"admin\",\"msgId\":\"006de981aaf242028fafd6355bf42367\"}', 'admin', 'sync', '2024-09-20 14:21:19', '2024-09-20 14:21:20', '2024-09-20 14:21:20', 'success', 1, 'success');
INSERT INTO `pub_mq_message` VALUES (3838, '790752ea95874c47a5da634b0eda903e', 'C0A80262280C18B4AAC26578CC590000', '{\"plan\":\"insert_admin_sync_log\",\"tables\":[{\"code\":\"insert_admin_sync_log\",\"unique\":[\"591\"]}],\"createTime\":\"2024-09-20 16:53:34\",\"src\":\"admin\",\"msgId\":\"790752ea95874c47a5da634b0eda903e\"}', 'admin', 'log', '2024-09-20 16:53:34', '2024-09-20 16:53:35', '2024-09-20 16:53:36', 'success', 1, 'success');

-- ----------------------------
-- Table structure for pub_mq_msg
-- ----------------------------
DROP TABLE IF EXISTS `pub_mq_msg`;
CREATE TABLE `pub_mq_msg`  (
  `uuid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `mid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `tag` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `msg` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `send_status` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `retry` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`uuid`) USING BTREE,
  INDEX `ix_tag`(`tag`) USING BTREE,
  INDEX `ix_status_retry_ctime`(`send_status`, `retry`, `create_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pub_mq_msg
-- ----------------------------

-- ----------------------------
-- Table structure for pub_queue
-- ----------------------------
DROP TABLE IF EXISTS `pub_queue`;
CREATE TABLE `pub_queue`  (
  `node_id` int(11) NOT NULL AUTO_INCREMENT,
  `business_data` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `queue_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `is_consumed` bit(1) NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`node_id`) USING BTREE,
  INDEX `ix_queue_name`(`queue_name`, `is_consumed`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pub_queue
-- ----------------------------

-- ----------------------------
-- Table structure for sync_activemq_acks
-- ----------------------------
DROP TABLE IF EXISTS `sync_activemq_acks`;
CREATE TABLE `sync_activemq_acks`  (
  `CONTAINER` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `SUB_DEST` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CLIENT_ID` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `SUB_NAME` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `SELECTOR` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `LAST_ACKED_ID` bigint(20) NULL DEFAULT NULL,
  `PRIORITY` bigint(20) NOT NULL DEFAULT 5,
  `XID` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`CONTAINER`, `CLIENT_ID`, `SUB_NAME`, `PRIORITY`) USING BTREE,
  INDEX `sync_ACTIVEMQ_ACKS_XIDX`(`XID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sync_activemq_acks
-- ----------------------------

-- ----------------------------
-- Table structure for sync_activemq_lock
-- ----------------------------
DROP TABLE IF EXISTS `sync_activemq_lock`;
CREATE TABLE `sync_activemq_lock`  (
  `ID` bigint(20) NOT NULL,
  `TIME` bigint(20) NULL DEFAULT NULL,
  `BROKER_NAME` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sync_activemq_lock
-- ----------------------------

-- ----------------------------
-- Table structure for sync_activemq_msgs
-- ----------------------------
DROP TABLE IF EXISTS `sync_activemq_msgs`;
CREATE TABLE `sync_activemq_msgs`  (
  `ID` bigint(20) NOT NULL,
  `CONTAINER` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `MSGID_PROD` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `MSGID_SEQ` bigint(20) NULL DEFAULT NULL,
  `EXPIRATION` bigint(20) NULL DEFAULT NULL,
  `MSG` blob NULL,
  `PRIORITY` bigint(20) NULL DEFAULT NULL,
  `XID` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `sync_ACTIVEMQ_MSGS_MIDX`(`MSGID_PROD`, `MSGID_SEQ`) USING BTREE,
  INDEX `sync_ACTIVEMQ_MSGS_CIDX`(`CONTAINER`) USING BTREE,
  INDEX `sync_ACTIVEMQ_MSGS_EIDX`(`EXPIRATION`) USING BTREE,
  INDEX `sync_ACTIVEMQ_MSGS_PIDX`(`PRIORITY`) USING BTREE,
  INDEX `sync_ACTIVEMQ_MSGS_XIDX`(`XID`) USING BTREE,
  INDEX `sync_ACTIVEMQ_MSGS_IIDX`(`ID`, `XID`, `CONTAINER`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sync_activemq_msgs
-- ----------------------------
INSERT INTO `sync_activemq_msgs` VALUES (1, 'queue://SYNC_QUEUE_DEV', 'ID:CHINAMI-MC5KLHT-17080-1722847551131-6:2:1:1', 1, 0, 0x000001AF1C0000000501017B01002A49443A4348494E414D492D4D43354B4C48542D31373038302D313732323834373535313133312D363A3200000000000000010000000000000001016401000E53594E435F51554555455F4445560000016E00017B01002A49443A4348494E414D492D4D43354B4C48542D31373038302D313732323834373535313133312D363A3200000000000000010000000000000001000000000000000100000000000000090000000000000001000000000000000004000000019121B7CE3B0001000000BD000000B97B22706C616E223A22696E736572745F61646D696E5F73796E635F6C6F67222C227461626C6573223A5B7B22636F6465223A22696E736572745F61646D696E5F73796E635F6C6F67222C22756E69717565223A5B22353835225D7D5D2C2263726561746554696D65223A22323032342D30382D30352031363A34363A3139222C22737263223A2261646D696E222C226D73674964223A223261396232333337346466643462363561363165653263656163663432636562227D0000000000000000000000000000000000000000000000019121B7CE3C000000000000000000, 0, NULL);

-- ----------------------------
-- Table structure for sync_plan
-- ----------------------------
DROP TABLE IF EXISTS `sync_plan`;
CREATE TABLE `sync_plan`  (
  `uuid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `plan_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `src_db` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `dest_db` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `src_project` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `dest_project` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `plan_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sync_plan
-- ----------------------------
INSERT INTO `sync_plan` VALUES ('074e8e9a33b140109d4ff98d5b21b0c1', 'insert_admin_sync_log', '新增系统日志', 'admin_dev', 'admin_dev_sync', 'admin', 'log', '<sync_plan>\n    <code>insert_admin_sync_log</code>\n    <name>新增系统日志</name>\n    <database src=\"admin_dev\" dest=\"admin_dev_sync\" ></database>\n    <project src=\"admin\" dest=\"log\" ></project>\n    <table code=\"insert_admin_sync_log\" src=\"auth_log\" dest=\"sync_auth_log\" type=\"insert\">\n			<unique src=\"log_id\" dest=\"log_id\"  ></unique>\n			<relate src=\"log_id\" dest=\"log_id\" ></relate>\n			<column src=\"log_id\" dest=\"log_id\" />\n			<column src=\"log_info\" dest=\"log_info\" />\n			<column src=\"log_module\" dest=\"log_module\" />\n			<column src=\"log_level\" dest=\"log_level\" />\n			<column src=\"ip_address\" dest=\"ip_address\" />\n			<column src=\"device_info\" dest=\"device_info\" />\n			<column src=\"location\" dest=\"location\" />\n			<column src=\"user_id\" dest=\"user_id\" />\n			<column src=\"username\" dest=\"username\" />\n			<column src=\"real_name\" dest=\"real_name\" />\n			<column src=\"create_time\" dest=\"create_time\" />\n    </table>\n</sync_plan>\n', 'normal', '2024-08-05 11:43:21', '2024-08-05 15:48:56');
INSERT INTO `sync_plan` VALUES ('07b86051cacc46e78a2a6b60475fff8e', 'delete_admin_sync_resc', '删除系统资源', 'admin_dev', 'admin_dev_sync', 'admin', 'sync', '<sync_plan>\n    <code>delete_admin_sync_resc</code>\n    <name>删除系统资源</name>\n    <database src=\"admin_dev\" dest=\"admin_dev_sync\" ></database>\n    <project src=\"admin\" dest=\"sync\" ></project>\n    <table code=\"delete_admin_sync_resc\" src=\"auth_resc\" dest=\"sync_auth_resc\" type=\"delete\">\n        <unique src=\"resc_id\" dest=\"resc_id\"  ></unique>\n        <relate src=\"resc_id\" dest=\"resc_id\" ></relate>\n    </table>\n</sync_plan>', 'normal', '2023-09-03 19:52:56', '2024-08-04 21:20:32');
INSERT INTO `sync_plan` VALUES ('0bde11510a5d4c8b87b8c1ff0ffed307', 'update_admin_sync_user', '更新系统用户', 'admin_dev', 'admin_dev_sync', 'admin', 'sync', '<sync_plan>\n      <callbacks>\n          <callback dest=\"sync\" desc=\"更新用户回调\"></callback>\n    </callbacks>\n    <code>update_admin_sync_user</code>\n    <name>更新系统用户</name>\n    <database src=\"admin_dev\" dest=\"admin_dev_sync\" ></database>\n    <project src=\"admin\" dest=\"sync\" ></project>\n    <table code=\"update_admin_sync_user\" src=\"auth_user\" dest=\"sync_auth_user\" type=\"update\">\n        <unique src=\"user_id\" dest=\"user_id\"  ></unique>\n        <relate src=\"user_id\" dest=\"user_id\" ></relate>\n        <column src=\"user_id\" dest=\"user_id\" />\n        <column src=\"username\" dest=\"username\" />\n        <column src=\"real_name\" dest=\"real_name\" />\n        <column src=\"password\" dest=\"password\" />\n        <column src=\"dept_code\" dest=\"dept_code\" />\n        <column src=\"user_status\" dest=\"user_status\" />\n        <column src=\"create_time\" dest=\"create_time\" />\n        <column src=\"update_time\" dest=\"update_time\" />\n    </table>\n    <table code=\"update_admin_sync_user_status\" src=\"auth_user\" dest=\"sync_auth_user\" type=\"update\">\n        <unique src=\"user_id\" dest=\"user_id\"  ></unique>\n        <relate src=\"user_id\" dest=\"user_id\" ></relate>\n        <column src=\"user_status\" dest=\"user_status\" />\n         <column src=\"update_time\" dest=\"update_time\" />\n    </table>\n    <table code=\"update_admin_sync_user_pwd\" src=\"auth_user\" dest=\"sync_auth_user\" type=\"update\">\n        <unique src=\"user_id\" dest=\"user_id\"  ></unique>\n        <relate src=\"user_id\" dest=\"user_id\" ></relate>\n        <column src=\"password\" dest=\"password\" />\n    </table>\n</sync_plan>', 'normal', '2023-07-22 15:59:29', '2024-08-04 21:29:39');
INSERT INTO `sync_plan` VALUES ('0cfc9f66e8d44ee3b5bac326b84a0d1a', 'delete_admin_sync_role', '删除系统角色', 'admin_dev', 'admin_dev_sync', 'admin', 'sync', '<sync_plan>\n    <code>delete_admin_sync_role</code>\n    <name>删除系统角色</name>\n    <database src=\"admin_dev\" dest=\"admin_dev_sync\" ></database>\n    <project src=\"admin\" dest=\"sync\" ></project>\n    <table code=\"delete_admin_sync_role\" src=\"auth_role\" dest=\"sync_auth_role\" type=\"delete\">\n        <unique src=\"role_id\" dest=\"role_id\"  ></unique>\n        <relate src=\"role_id\" dest=\"role_id\" ></relate>\n    </table>\n</sync_plan>', 'normal', '2023-07-23 11:06:22', '2024-08-04 21:25:29');
INSERT INTO `sync_plan` VALUES ('1075e58f891d4030a4464269efb864d2', 'delete_admin_sync_user', '删除系统用户', 'admin_dev', 'admin_dev_sync', 'admin', 'sync', '<sync_plan>\n    <code>delete_admin_sync_user</code>\n    <name>删除系统用户</name>\n    <database src=\"admin_dev\" dest=\"admin_dev_sync\" ></database>\n    <project src=\"admin\" dest=\"sync\" ></project>\n    <table code=\"delete_admin_sync_user\" src=\"auth_user\" dest=\"sync_auth_user\" type=\"delete\">\n        <unique src=\"user_id\" dest=\"user_id\"  ></unique>\n        <relate src=\"user_id\" dest=\"user_id\" ></relate>\n    </table>\n</sync_plan>', 'normal', '2023-07-22 16:20:10', '2024-08-04 21:27:10');
INSERT INTO `sync_plan` VALUES ('26ff4a2791664dcd9654f633f56f93e7', 'insert_admin_sync_user', '新增系统用户', 'admin_dev', 'admin_dev_sync', 'admin', 'sync', '<sync_plan>\n    <code>insert_admin_sync_user</code>\n    <name>新增系统用户</name>\n    <database src=\"admin_dev\" dest=\"admin_dev_sync\" ></database>\n    <project src=\"admin\" dest=\"sync\" ></project>\n    <table code=\"insert_admin_sync_user\" src=\"auth_user\" dest=\"sync_auth_user\" type=\"insert\">\n        <unique src=\"user_id\" dest=\"user_id\"  ></unique>\n        <relate src=\"user_id\" dest=\"user_id\" ></relate>\n        <column src=\"user_id\" dest=\"user_id\" />\n        <column src=\"username\" dest=\"username\" />\n	<column src=\"real_name\" dest=\"real_name\" />\n        <column src=\"password\" dest=\"password\" />\n	<column src=\"dept_code\" dest=\"dept_code\" />\n	<column src=\"user_status\" dest=\"user_status\" />\n	<column src=\"create_time\" dest=\"create_time\" />\n        <column src=\"update_time\" dest=\"update_time\" />\n    </table>\n</sync_plan>', 'normal', '2023-07-22 15:50:24', '2024-08-04 21:30:14');
INSERT INTO `sync_plan` VALUES ('2aae46089860446685191dac3c33d488', 'update_admin_sync_resc', '更新系统资源', 'admin_dev', 'admin_dev_sync', 'admin', 'sync', '<sync_plan>\n    <code>update_admin_sync_resc</code>\n    <name>更新系统资源</name>\n    <database src=\"admin_dev\" dest=\"admin_dev_sync\" ></database>\n    <project src=\"admin\" dest=\"sync\" ></project>\n    <table code=\"update_admin_sync_resc\" src=\"auth_resc\" dest=\"sync_auth_resc\" type=\"update\">\n        <unique src=\"resc_id\" dest=\"resc_id\"  ></unique>\n        <relate src=\"resc_id\" dest=\"resc_id\" ></relate>\n        <column src=\"resc_id\" dest=\"resc_id\" />\n        <column src=\"resc_name\" dest=\"resc_name\" />\n        <column src=\"resc_url\" dest=\"resc_url\" />\n	<column src=\"resc_domain\" dest=\"resc_domain\" />\n        <column src=\"method_type\" dest=\"method_type\" />\n	<column src=\"create_time\" dest=\"create_time\" />\n        <column src=\"update_time\" dest=\"update_time\" />\n    </table>\n</sync_plan>', 'normal', '2023-09-03 19:52:09', '2024-08-04 21:24:27');
INSERT INTO `sync_plan` VALUES ('59dccfa463c140ccb0f9a78c9015e2d7', 'insert_admin_sync_role', '新增系统角色', 'admin_dev', 'admin_dev_sync', 'admin', 'sync', '<sync_plan>\n    <code>insert_admin_sync_role</code>\n    <name>新增系统角色</name>\n    <database src=\"admin_dev\" dest=\"admin_dev_sync\" ></database>\n    <project src=\"admin\" dest=\"sync\" ></project>\n    <table code=\"insert_admin_sync_role\" src=\"auth_role\" dest=\"sync_auth_role\" type=\"insert\">\n        <unique src=\"role_id\" dest=\"role_id\"  ></unique>\n        <relate src=\"role_id\" dest=\"role_id\" ></relate>\n        <column src=\"role_id\" dest=\"role_id\" />\n        <column src=\"role_name\" dest=\"role_name\" />\n        <column src=\"role_desc\" dest=\"role_desc\" />\n	<column src=\"create_time\" dest=\"create_time\" />\n        <column src=\"update_time\" dest=\"update_time\" />\n    </table>\n</sync_plan>', 'normal', '2023-07-23 11:04:58', '2024-08-04 21:26:40');
INSERT INTO `sync_plan` VALUES ('7275635a21654dc6b759cb5660493e3d', 'insert_admin_sync_func', '新增系统功能', 'admin_dev', 'admin_dev_sync', 'admin', 'sync', '<sync_plan>\n    <code>insert_admin_sync_func</code>\n    <name>新增系统功能</name>\n    <database src=\"admin_dev\" dest=\"admin_dev_sync\" ></database>\n    <project src=\"admin\" dest=\"sync\" ></project>\n    <table code=\"insert_admin_sync_func\" src=\"auth_func\" dest=\"sync_auth_func\" type=\"insert\">\n        <unique src=\"func_id\" dest=\"func_id\"  ></unique>\n        <relate src=\"func_id\" dest=\"func_id\" ></relate>\n	<column src=\"func_id\" dest=\"func_id\" />\n        <column src=\"func_name\" dest=\"func_name\" />\n        <column src=\"func_key\" dest=\"func_key\" />\n	<column src=\"func_type\" dest=\"func_type\" />\n        <column src=\"func_icon\" dest=\"func_icon\" />\n	<column src=\"func_sort\" dest=\"func_sort\" />\n	<column src=\"func_icon\" dest=\"func_icon\" />\n	<column src=\"func_dir_status\" dest=\"func_dir_status\" />\n	<column src=\"parent_id\" dest=\"parent_id\" />\n	<column src=\"create_time\" dest=\"create_time\" />\n	<column src=\"update_time\" dest=\"update_time\" />\n    </table>\n</sync_plan>', 'normal', '2024-08-05 09:54:58', '2024-08-05 09:54:58');
INSERT INTO `sync_plan` VALUES ('766d4f0dcbc0484b8a14be02dabee141', 'delete_admin_sync_func', '删除系统功能', 'admin_dev', 'admin_dev_sync', 'admin', 'sync', '<sync_plan>\n    <code>delete_admin_sync_func</code>\n    <name>删除系统功能</name>\n    <database src=\"admin_dev\" dest=\"admin_dev_sync\" ></database>\n    <project src=\"admin\" dest=\"sync\" ></project>\n    <table code=\"delete_admin_sync_func\" src=\"auth_func\" dest=\"sync_auth_func\" type=\"delete\">\n        <unique src=\"func_id\" dest=\"func_id\"  ></unique>\n        <relate src=\"func_id\" dest=\"func_id\" ></relate>\n    </table>\n</sync_plan>\n', 'normal', '2024-08-05 10:12:24', '2024-08-05 10:12:24');
INSERT INTO `sync_plan` VALUES ('973b88e89f794c9194136dc6bc327190', 'insert_admin_sync_resc', '新增系统资源', 'admin_dev', 'admin_dev_sync', 'admin', 'sync', '<sync_plan>\n    <code>insert_admin_sync_resc</code>\n    <name>新增系统资源</name>\n    <database src=\"admin_dev\" dest=\"admin_dev_sync\" ></database>\n    <project src=\"admin\" dest=\"sync\" ></project>\n    <table code=\"insert_admin_sync_resc\" src=\"auth_resc\" dest=\"sync_auth_resc\" type=\"insert\">\n        <unique src=\"resc_id\" dest=\"resc_id\"  ></unique>\n        <relate src=\"resc_id\" dest=\"resc_id\" ></relate>\n        <column src=\"resc_id\" dest=\"resc_id\" />\n        <column src=\"resc_name\" dest=\"resc_name\" />\n        <column src=\"resc_url\" dest=\"resc_url\" />\n	<column src=\"resc_domain\" dest=\"resc_domain\" />\n        <column src=\"method_type\" dest=\"method_type\" />\n	<column src=\"create_time\" dest=\"create_time\" />\n        <column src=\"update_time\" dest=\"update_time\" />\n    </table>\n</sync_plan>', 'normal', '2023-09-03 19:38:40', '2024-08-04 21:24:59');
INSERT INTO `sync_plan` VALUES ('a3c2bc8a16e04fa9bbb69830648cce78', 'update_admin_sync_func', '修改系统功能', 'admin_dev', 'admin_dev_sync', 'admin', 'sync', '<sync_plan>\n    <code>update_admin_sync_func</code>\n    <name>修改系统功能</name>\n    <database src=\"admin_dev\" dest=\"admin_dev_sync\" ></database>\n    <project src=\"admin\" dest=\"sync\" ></project>\n    <table code=\"update_admin_sync_func\" src=\"auth_func\" dest=\"sync_auth_func\" type=\"update\">\n        <unique src=\"func_id\" dest=\"func_id\"  ></unique>\n        <relate src=\"func_id\" dest=\"func_id\" ></relate>\n        <column src=\"func_name\" dest=\"func_name\" />\n        <column src=\"func_key\" dest=\"func_key\" />\n	<column src=\"func_type\" dest=\"func_type\" />\n        <column src=\"func_icon\" dest=\"func_icon\" />\n	<column src=\"func_sort\" dest=\"func_sort\" />\n	<column src=\"func_icon\" dest=\"func_icon\" />\n	<column src=\"func_dir_status\" dest=\"func_dir_status\" />\n	<column src=\"parent_id\" dest=\"parent_id\" />\n	<column src=\"create_time\" dest=\"create_time\" />\n	<column src=\"update_time\" dest=\"update_time\" />\n    </table>\n</sync_plan>', 'normal', '2024-08-04 21:53:51', '2024-08-04 21:53:51');
INSERT INTO `sync_plan` VALUES ('e58818eb1fb5454b8a943bc6b6d9dc06', 'update_admin_sync_role', '修改系统角色', 'admin_dev', 'admin_dev_sync', 'admin', 'sync', '<sync_plan>\n    <code>update_admin_sync_role</code>\n    <name>修改系统角色</name>\n    <database src=\"admin_dev\" dest=\"admin_dev_sync\" ></database>\n    <project src=\"admin\" dest=\"sync\" ></project>\n    <table code=\"update_admin_sync_role\" src=\"auth_role\" dest=\"sync_auth_role\" type=\"update\">\n        <unique src=\"role_id\" dest=\"role_id\"  ></unique>\n        <relate src=\"role_id\" dest=\"role_id\" ></relate>\n        <column src=\"role_name\" dest=\"role_name\" />\n        <column src=\"role_desc\" dest=\"role_desc\" />\n	<column src=\"create_time\" dest=\"create_time\" />\n        <column src=\"update_time\" dest=\"update_time\" />\n    </table>\n</sync_plan>\n', 'normal', '2023-07-23 11:05:38', '2024-08-04 21:26:08');

-- ----------------------------
-- Table structure for sync_result
-- ----------------------------
DROP TABLE IF EXISTS `sync_result`;
CREATE TABLE `sync_result`  (
  `uuid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `plan_uuid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `plan_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `plan_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `msg_src` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `mq_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `msg_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `msg_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `src_project` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `dest_project` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sync_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `msg_create_time` datetime NULL DEFAULT NULL,
  `sync_time` datetime NULL DEFAULT NULL,
  `status` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `error_msg` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `repeat_count` int(11) NULL DEFAULT NULL,
  `remark` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sync_to_es` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`uuid`) USING BTREE,
  INDEX `ix_msg_id`(`msg_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sync_result
-- ----------------------------
INSERT INTO `sync_result` VALUES ('027398e0cdb84745b3b9da128b1c9c31', '074e8e9a33b140109d4ff98d5b21b0c1', 'insert_admin_sync_log', '新增系统日志', 'rocket_mq', 'C0A80262280C18B4AAC26578CC590000', '790752ea95874c47a5da634b0eda903e', '{\"plan\":\"insert_admin_sync_log\",\"tables\":[{\"code\":\"insert_admin_sync_log\",\"unique\":[\"591\"]}],\"createTime\":\"2024-09-20 16:53:34\",\"src\":\"admin\",\"msgId\":\"790752ea95874c47a5da634b0eda903e\"}', 'admin', 'log', '{\"insert into sync_auth_log(log_id,log_info,log_module,log_level,ip_address,device_info,location,user_id,username,real_name,create_time,sync_time) values (?,?,?,?,?,?,?,?,?,?,?,?);\":[[591,\"用户登录系统\",0,0,\"192.168.2.98\",\"Windows\",\"内网IP\",61,\"admin\",\"管理员\",\"2024-09-20 16:53:34\",\"2024-09-20 16:53:35\"]],\"影响行数\":[1]}', '2024-09-20 16:53:34', '2024-09-20 16:53:36', 'success', '', 0, NULL, b'1');
INSERT INTO `sync_result` VALUES ('15048d93cde04646b6b05bedd8d3b15c', '074e8e9a33b140109d4ff98d5b21b0c1', 'insert_admin_sync_log', '新增系统日志', 'rocket_mq', 'C0A80262605418B4AAC264E50B310000', '69bff3bec3044c36bbb6503e89d00185', '{\"plan\":\"insert_admin_sync_log\",\"tables\":[{\"code\":\"insert_admin_sync_log\",\"unique\":[\"587\"]}],\"createTime\":\"2024-09-20 14:12:11\",\"src\":\"admin\",\"msgId\":\"69bff3bec3044c36bbb6503e89d00185\"}', 'admin', 'log', '{\"insert into sync_auth_log(log_id,log_info,log_module,log_level,ip_address,device_info,location,user_id,username,real_name,create_time,sync_time) values (?,?,?,?,?,?,?,?,?,?,?,?);\":[[587,\"用户登录系统\",0,0,\"192.168.2.98\",\"Windows\",\"内网IP\",61,\"admin\",\"管理员\",\"2024-09-20 14:12:12\",\"2024-09-20 14:18:02\"]],\"影响行数\":[1]}', '2024-09-20 14:12:11', '2024-09-20 14:18:03', 'success', '', 0, NULL, b'0');
INSERT INTO `sync_result` VALUES ('1f2c5686085642c18e45d936eb10120b', '074e8e9a33b140109d4ff98d5b21b0c1', 'insert_admin_sync_log', '新增系统日志', 'rocket_mq', 'C0A80262852818B4AAC25FDD4FE10000', 'b135e8eb882541359473e66cd598feb3', '{\"plan\":\"insert_admin_sync_log\",\"tables\":[{\"code\":\"insert_admin_sync_log\",\"unique\":[\"586\"]}],\"createTime\":\"2024-09-19 14:45:38\",\"src\":\"admin\",\"msgId\":\"b135e8eb882541359473e66cd598feb3\"}', 'admin', 'log', NULL, '2024-09-19 14:45:38', '2024-09-19 14:49:20', 'success', '未知异常,tableCode:insert_admin_sync_log,msg:null,目标库:null,error:org.springframework.jdbc.BadSqlGrammarException: PreparedStatementCallback; bad SQL grammar [insert into sync_auth_log(log_id,log_info,log_module,log_level,ip_address,device_info,location,user_id,username,real_name,create_time,sync_time) values (?,?,?,?,?,?,?,?,?,?,?,?);]; nested exception is java.sql.BatchUpdateException: Unknown column \'ip_address\' in \'field list\'', 4, '系统标记成功', b'1');
INSERT INTO `sync_result` VALUES ('20ecba1ab311450fa7ca5c0753047a4c', '074e8e9a33b140109d4ff98d5b21b0c1', 'insert_admin_sync_log', '新增系统日志', 'rocket_mq', 'C0A80262605418B4AAC264EC4F8D0002', 'ef9defb1f43a454288a2cfee357b4f4c', '{\"plan\":\"insert_admin_sync_log\",\"tables\":[{\"code\":\"insert_admin_sync_log\",\"unique\":[\"589\"]}],\"createTime\":\"2024-09-20 14:20:07\",\"src\":\"admin\",\"msgId\":\"ef9defb1f43a454288a2cfee357b4f4c\"}', 'admin', 'log', '{\"insert into sync_auth_log(log_id,log_info,log_module,log_level,ip_address,device_info,location,user_id,username,real_name,create_time,sync_time) values (?,?,?,?,?,?,?,?,?,?,?,?);\":[[589,\"用户登录系统\",0,0,\"192.168.2.98\",\"Windows\",\"内网IP\",61,\"admin\",\"管理员\",\"2024-09-20 14:20:08\",\"2024-09-20 14:20:08\"]],\"影响行数\":[1]}', '2024-09-20 14:20:07', '2024-09-20 14:20:08', 'success', '', 0, NULL, b'0');
INSERT INTO `sync_result` VALUES ('29146955bf104e2bbb318b9aa42d5c34', '074e8e9a33b140109d4ff98d5b21b0c1', 'insert_admin_sync_log', '新增系统日志', 'rocket_mq', 'C0A80262852818B4AAC25FDD4FE10000', 'b135e8eb882541359473e66cd598feb3', '{\"plan\":\"insert_admin_sync_log\",\"tables\":[{\"code\":\"insert_admin_sync_log\",\"unique\":[\"586\"]}],\"createTime\":\"2024-09-19 14:45:38\",\"src\":\"admin\",\"msgId\":\"b135e8eb882541359473e66cd598feb3\"}', 'admin', 'log', NULL, '2024-09-19 14:45:38', '2024-09-19 14:45:39', 'success', '未知异常,tableCode:insert_admin_sync_log,msg:null,目标库:null,error:org.springframework.jdbc.BadSqlGrammarException: PreparedStatementCallback; bad SQL grammar [insert into sync_auth_log(log_id,log_info,log_module,log_level,ip_address,device_info,location,user_id,username,real_name,create_time,sync_time) values (?,?,?,?,?,?,?,?,?,?,?,?);]; nested exception is java.sql.BatchUpdateException: Unknown column \'ip_address\' in \'field list\'', 0, '系统标记成功', b'1');
INSERT INTO `sync_result` VALUES ('4c7c9b97d2da49359d7efcb5df761cbc', '074e8e9a33b140109d4ff98d5b21b0c1', 'insert_admin_sync_log', '新增系统日志', 'rocket_mq', 'C0A80262852818B4AAC25FDD4FE10000', 'b135e8eb882541359473e66cd598feb3', '{\"plan\":\"insert_admin_sync_log\",\"tables\":[{\"code\":\"insert_admin_sync_log\",\"unique\":[\"586\"]}],\"createTime\":\"2024-09-19 14:45:38\",\"src\":\"admin\",\"msgId\":\"b135e8eb882541359473e66cd598feb3\"}', 'admin', 'log', NULL, '2024-09-19 14:45:38', '2024-09-19 14:47:20', 'success', '未知异常,tableCode:insert_admin_sync_log,msg:null,目标库:null,error:org.springframework.jdbc.BadSqlGrammarException: PreparedStatementCallback; bad SQL grammar [insert into sync_auth_log(log_id,log_info,log_module,log_level,ip_address,device_info,location,user_id,username,real_name,create_time,sync_time) values (?,?,?,?,?,?,?,?,?,?,?,?);]; nested exception is java.sql.BatchUpdateException: Unknown column \'ip_address\' in \'field list\'', 3, '系统标记成功', b'1');
INSERT INTO `sync_result` VALUES ('5b9102c5d64846e28ffad67008de58a3', '074e8e9a33b140109d4ff98d5b21b0c1', 'insert_admin_sync_log', '新增系统日志', 'active_mq', 'ID:CHINAMI-MC5KLHT-17080-1722847551131-6:2:1:1:1', '2a9b23374dfd4b65a61ee2ceacf42ceb', '{\"plan\":\"insert_admin_sync_log\",\"tables\":[{\"code\":\"insert_admin_sync_log\",\"unique\":[\"585\"]}],\"createTime\":\"2024-08-05 16:46:19\",\"src\":\"admin\",\"msgId\":\"2a9b23374dfd4b65a61ee2ceacf42ceb\"}', 'admin', 'log', NULL, '2024-08-05 16:46:19', '2024-08-05 16:46:21', 'fail', 'insert_admin_sync_log中源表数据不存在', 2, NULL, b'0');
INSERT INTO `sync_result` VALUES ('6cded2a6ebcf464fb76b9886a260a7ee', '074e8e9a33b140109d4ff98d5b21b0c1', 'insert_admin_sync_log', '新增系统日志', 'rocket_mq', 'C0A80262852818B4AAC25FDD4FE10000', 'b135e8eb882541359473e66cd598feb3', '{\"plan\":\"insert_admin_sync_log\",\"tables\":[{\"code\":\"insert_admin_sync_log\",\"unique\":[\"586\"]}],\"createTime\":\"2024-09-19 14:45:38\",\"src\":\"admin\",\"msgId\":\"b135e8eb882541359473e66cd598feb3\"}', 'admin', 'log', '{\"insert into sync_auth_log(log_id,log_info,log_module,log_level,ip_address,device_info,location,user_id,username,real_name,create_time,sync_time) values (?,?,?,?,?,?,?,?,?,?,?,?);\":[[586,\"用户登录系统\",0,0,\"192.168.2.98\",\"Windows\",\"内网IP\",61,\"admin\",\"管理员\",\"2024-09-19 14:45:39\",\"2024-09-19 15:01:24\"]],\"影响行数\":[1]}', '2024-09-19 14:45:38', '2024-09-19 15:01:24', 'success', '', 7, NULL, b'1');
INSERT INTO `sync_result` VALUES ('8c962c2b74984b46b6b88f011d6245f3', '074e8e9a33b140109d4ff98d5b21b0c1', 'insert_admin_sync_log', '新增系统日志', 'rocket_mq', 'C0A80262852818B4AAC25FDD4FE10000', 'b135e8eb882541359473e66cd598feb3', '{\"plan\":\"insert_admin_sync_log\",\"tables\":[{\"code\":\"insert_admin_sync_log\",\"unique\":[\"586\"]}],\"createTime\":\"2024-09-19 14:45:38\",\"src\":\"admin\",\"msgId\":\"b135e8eb882541359473e66cd598feb3\"}', 'admin', 'log', NULL, '2024-09-19 14:45:38', '2024-09-19 14:45:50', 'success', '未知异常,tableCode:insert_admin_sync_log,msg:null,目标库:null,error:org.springframework.jdbc.BadSqlGrammarException: PreparedStatementCallback; bad SQL grammar [insert into sync_auth_log(log_id,log_info,log_module,log_level,ip_address,device_info,location,user_id,username,real_name,create_time,sync_time) values (?,?,?,?,?,?,?,?,?,?,?,?);]; nested exception is java.sql.BatchUpdateException: Unknown column \'ip_address\' in \'field list\'', 1, '系统标记成功', b'1');
INSERT INTO `sync_result` VALUES ('964b907058cd4c839fdbcc7216a2e87a', '074e8e9a33b140109d4ff98d5b21b0c1', 'insert_admin_sync_log', '新增系统日志', 'active_mq', 'ID:CHINAMI-MC5KLHT-17080-1722847551131-6:2:1:1:1', '2a9b23374dfd4b65a61ee2ceacf42ceb', '{\"plan\":\"insert_admin_sync_log\",\"tables\":[{\"code\":\"insert_admin_sync_log\",\"unique\":[\"585\"]}],\"createTime\":\"2024-08-05 16:46:19\",\"src\":\"admin\",\"msgId\":\"2a9b23374dfd4b65a61ee2ceacf42ceb\"}', 'admin', 'log', NULL, '2024-08-05 16:46:19', '2024-08-05 16:46:20', 'fail', 'insert_admin_sync_log中源表数据不存在', 1, NULL, b'1');
INSERT INTO `sync_result` VALUES ('985ad2859c5e4e49836dac183c11ab6f', '2aae46089860446685191dac3c33d488', 'update_admin_sync_resc', '更新系统资源', 'rocket_mq', 'C0A80262605418B4AAC264ED66FC0004', '006de981aaf242028fafd6355bf42367', '{\"plan\":\"update_admin_sync_resc\",\"tables\":[{\"code\":\"update_admin_sync_resc\",\"unique\":[\"35\"]}],\"createTime\":\"2024-09-20 14:21:19\",\"src\":\"admin\",\"msgId\":\"006de981aaf242028fafd6355bf42367\"}', 'admin', 'sync', '{\"update sync_auth_resc set resc_id = ?, resc_name = ?, resc_url = ?, resc_domain = ?, method_type = ?, create_time = ?, update_time = ?, sync_time = ? where resc_id = ?;\":[[35,\"角色分配用户初始化\",\"/auth/role/user/update/init\",\"auth\",\"get\",\"2022-03-26 21:07:44\",\"2024-09-20 14:21:20\",\"2024-09-20 14:21:20\",35]],\"影响行数\":[1]}', '2024-09-20 14:21:19', '2024-09-20 14:21:20', 'success', '', 0, NULL, b'0');
INSERT INTO `sync_result` VALUES ('9e9c21f3dab34fe6abe450157d47d065', '074e8e9a33b140109d4ff98d5b21b0c1', 'insert_admin_sync_log', '新增系统日志', 'rocket_mq', 'C0A80262852818B4AAC25FDD4FE10000', 'b135e8eb882541359473e66cd598feb3', '{\"plan\":\"insert_admin_sync_log\",\"tables\":[{\"code\":\"insert_admin_sync_log\",\"unique\":[\"586\"]}],\"createTime\":\"2024-09-19 14:45:38\",\"src\":\"admin\",\"msgId\":\"b135e8eb882541359473e66cd598feb3\"}', 'admin', 'log', NULL, '2024-09-19 14:45:38', '2024-09-19 14:46:20', 'success', '未知异常,tableCode:insert_admin_sync_log,msg:null,目标库:null,error:org.springframework.jdbc.BadSqlGrammarException: PreparedStatementCallback; bad SQL grammar [insert into sync_auth_log(log_id,log_info,log_module,log_level,ip_address,device_info,location,user_id,username,real_name,create_time,sync_time) values (?,?,?,?,?,?,?,?,?,?,?,?);]; nested exception is java.sql.BatchUpdateException: Unknown column \'ip_address\' in \'field list\'', 2, '系统标记成功', b'1');
INSERT INTO `sync_result` VALUES ('df7a3c2d2a784dde838dbeba360773ae', '074e8e9a33b140109d4ff98d5b21b0c1', 'insert_admin_sync_log', '新增系统日志', 'rocket_mq', 'C0A80262605418B4AAC264ED66E80003', '3a5bbb963807475c9878506610537119', '{\"plan\":\"insert_admin_sync_log\",\"tables\":[{\"code\":\"insert_admin_sync_log\",\"unique\":[\"590\"]}],\"createTime\":\"2024-09-20 14:21:19\",\"src\":\"admin\",\"msgId\":\"3a5bbb963807475c9878506610537119\"}', 'admin', 'log', '{\"insert into sync_auth_log(log_id,log_info,log_module,log_level,ip_address,device_info,location,user_id,username,real_name,create_time,sync_time) values (?,?,?,?,?,?,?,?,?,?,?,?);\":[[590,\"更新资源信息\",2,1,\"192.168.2.98\",\"Windows\",\"内网IP\",61,\"admin\",\"管理员\",\"2024-09-20 14:21:20\",\"2024-09-20 14:21:19\"]],\"影响行数\":[1]}', '2024-09-20 14:21:19', '2024-09-20 14:21:20', 'success', '', 0, NULL, b'0');
INSERT INTO `sync_result` VALUES ('e8b816bb0b1d438b99efafeb26d312f2', '074e8e9a33b140109d4ff98d5b21b0c1', 'insert_admin_sync_log', '新增系统日志', 'rocket_mq', 'C0A80262852818B4AAC25FDD4FE10000', 'b135e8eb882541359473e66cd598feb3', '{\"plan\":\"insert_admin_sync_log\",\"tables\":[{\"code\":\"insert_admin_sync_log\",\"unique\":[\"586\"]}],\"createTime\":\"2024-09-19 14:45:38\",\"src\":\"admin\",\"msgId\":\"b135e8eb882541359473e66cd598feb3\"}', 'admin', 'log', NULL, '2024-09-19 14:45:38', '2024-09-19 14:52:24', 'success', '未知异常,tableCode:insert_admin_sync_log,msg:null,目标库:null,error:org.springframework.jdbc.BadSqlGrammarException: PreparedStatementCallback; bad SQL grammar [insert into sync_auth_log(log_id,log_info,log_module,log_level,ip_address,device_info,location,user_id,username,real_name,create_time,sync_time) values (?,?,?,?,?,?,?,?,?,?,?,?);]; nested exception is java.sql.BatchUpdateException: Unknown column \'ip_address\' in \'field list\'', 5, '系统标记成功', b'1');
INSERT INTO `sync_result` VALUES ('f9eb60fda1ed490d9f8478ca52fa2cb3', '074e8e9a33b140109d4ff98d5b21b0c1', 'insert_admin_sync_log', '新增系统日志', 'rocket_mq', 'C0A80262852818B4AAC25FDD4FE10000', 'b135e8eb882541359473e66cd598feb3', '{\"plan\":\"insert_admin_sync_log\",\"tables\":[{\"code\":\"insert_admin_sync_log\",\"unique\":[\"586\"]}],\"createTime\":\"2024-09-19 14:45:38\",\"src\":\"admin\",\"msgId\":\"b135e8eb882541359473e66cd598feb3\"}', 'admin', 'log', NULL, '2024-09-19 14:45:38', '2024-09-19 14:56:24', 'success', '未知异常,tableCode:insert_admin_sync_log,msg:null,目标库:null,error:org.springframework.jdbc.BadSqlGrammarException: PreparedStatementCallback; bad SQL grammar [insert into sync_auth_log(log_id,log_info,log_module,log_level,ip_address,device_info,location,user_id,username,real_name,create_time,sync_time) values (?,?,?,?,?,?,?,?,?,?,?,?);]; nested exception is java.sql.BatchUpdateException: Unknown column \'ip_address\' in \'field list\'', 6, '系统标记成功', b'1');

SET FOREIGN_KEY_CHECKS = 1;
