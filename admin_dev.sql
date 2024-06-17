/*
 Navicat Premium Data Transfer

 Source Server         : 本机mysql
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : admin_dev

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 17/06/2024 22:08:47
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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 174 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_func
-- ----------------------------
INSERT INTO `auth_func` VALUES (2, '权限系统', '/auth', 'dir', 'FacebookOutlined', 1, 'show', 0, '2022-03-19 15:57:28', '2024-06-16 20:51:25');
INSERT INTO `auth_func` VALUES (3, '用户管理', '/auth/user', 'dir', 'UserAddOutlined', 1, 'show', 2, '2022-03-19 16:10:26', '2023-10-05 14:18:01');
INSERT INTO `auth_func` VALUES (4, '角色管理', '/auth/role', 'dir', 'IdcardOutlined', 4, 'show', 2, '2022-03-19 16:11:29', '2023-09-15 21:40:09');
INSERT INTO `auth_func` VALUES (5, '功能管理', '/auth/func', 'dir', 'CloudServerOutlined', 2, 'show', 2, '2022-03-19 16:11:41', '2024-06-16 20:51:29');
INSERT INTO `auth_func` VALUES (6, '资源管理', '/auth/resc', 'dir', 'FundProjectionScreenOutlined', 5, 'show', 2, '2022-03-19 16:11:54', '2023-09-15 21:33:29');
INSERT INTO `auth_func` VALUES (8, '角色删除', 'auth:role:delete', 'func', '', 7, 'show', 4, '2022-03-19 18:23:22', '2023-09-03 18:58:48');
INSERT INTO `auth_func` VALUES (9, '角色更新', 'auth:role:update', 'func', '', 8, 'show', 4, '2022-03-19 18:23:38', '2022-03-19 18:23:40');
INSERT INTO `auth_func` VALUES (11, '角色查询', 'auth:role:page', 'func', '', 9, 'show', 4, '2022-03-19 18:24:02', '2022-03-19 18:24:03');
INSERT INTO `auth_func` VALUES (12, '功能查询', 'auth:func:page', 'func', '', 5, 'show', 5, '2022-03-19 18:24:30', '2023-09-03 18:58:58');
INSERT INTO `auth_func` VALUES (13, '列表查询', 'auth:resc:page', 'func', '', 4, 'show', 6, '2022-03-19 18:24:51', '2023-07-15 23:17:47');
INSERT INTO `auth_func` VALUES (14, '功能新增', 'auth:func:add', 'func', '', 6, 'show', 5, '2022-03-19 18:26:06', '2023-09-03 18:58:59');
INSERT INTO `auth_func` VALUES (26, '资源添加', 'auth:resc:add', 'func', '', 5, 'show', 6, '2022-03-20 21:43:55', '2023-07-15 23:18:31');
INSERT INTO `auth_func` VALUES (27, '资源删除', 'auth:resc:delete', 'func', '', 6, 'show', 6, '2022-03-20 21:44:03', '2023-09-03 18:58:47');
INSERT INTO `auth_func` VALUES (54, '功能删除', 'auth:func:delete', 'func', '', 7, 'show', 5, '2022-03-26 20:57:44', '2023-09-03 18:58:41');
INSERT INTO `auth_func` VALUES (55, '功能更新', 'auth:func:update', 'func', '', 8, 'show', 5, '2022-03-26 20:58:00', '2023-09-03 18:58:43');
INSERT INTO `auth_func` VALUES (56, '资源编辑', 'auth:resc:update', 'func', '', 7, 'show', 6, '2022-03-26 21:00:16', '2023-07-15 23:18:21');
INSERT INTO `auth_func` VALUES (57, '用户查询', 'auth:user:page', 'func', '', 2, 'show', 3, '2022-03-26 21:04:37', '2023-09-09 11:45:35');
INSERT INTO `auth_func` VALUES (60, '用户编辑', 'auth:user:update', 'func', '', 5, 'show', 3, '2022-03-26 21:05:33', '2023-07-15 23:18:48');
INSERT INTO `auth_func` VALUES (61, '用户删除', 'auth:user:delete', 'func', '', 6, 'show', 3, '2022-03-26 21:05:55', '2023-09-23 15:29:31');
INSERT INTO `auth_func` VALUES (62, '分配角色', 'auth:user:roleUpdate', 'func', '', 7, 'show', 3, '2022-03-26 21:06:48', '2023-09-03 18:58:45');
INSERT INTO `auth_func` VALUES (63, '分配用户', 'auth:role:userUpdate', 'func', '', 6, 'show', 4, '2022-03-26 21:08:39', '2023-07-15 15:28:20');
INSERT INTO `auth_func` VALUES (71, '角色授权', '/auth/role/authorized', 'dir', 'HomeOutlined', 5, 'hide', 4, '2022-04-04 17:45:36', '2023-09-15 21:37:22');
INSERT INTO `auth_func` VALUES (72, '角色新增', 'auth:role:add', 'func', '', 10, 'show', 4, '2022-04-16 11:13:19', '2023-07-01 11:12:50');
INSERT INTO `auth_func` VALUES (73, '解绑用户', 'auth:func:userRemove', 'func', '', 9, 'show', 5, '2022-04-16 21:55:19', '2023-07-15 15:27:43');
INSERT INTO `auth_func` VALUES (74, '解绑资源', 'auth:func:rescRemove', 'func', '', 10, 'show', 5, '2022-04-16 22:02:49', '2023-09-03 13:58:36');
INSERT INTO `auth_func` VALUES (75, '用户添加', 'auth:user:add', 'func', '', 8, 'show', 3, '2022-05-02 11:40:57', '2023-07-15 23:19:01');
INSERT INTO `auth_func` VALUES (84, '修改密码', 'auth:user:pwdUpdate', 'func', '', 9, 'show', 3, '2022-05-14 12:20:12', '2023-07-15 15:27:23');
INSERT INTO `auth_func` VALUES (93, '同步系统', '/sync', 'dir', 'HddOutlined', 4, 'show', 1, '2022-09-13 20:54:26', '2023-11-09 20:34:18');
INSERT INTO `auth_func` VALUES (94, '同步计划', '/sync/plan', 'dir', 'CarryOutOutlined', 0, 'show', 93, '2022-09-13 20:54:58', '2024-05-17 21:01:26');
INSERT INTO `auth_func` VALUES (95, '同步记录', '/sync/result', 'dir', 'CloudSyncOutlined', 3, 'show', 93, '2022-12-04 23:48:14', '2023-10-31 09:51:55');
INSERT INTO `auth_func` VALUES (96, '消息回调', '/sync/callback', 'dir', 'PartitionOutlined', 5, 'show', 93, '2022-12-04 23:48:39', '2023-10-14 22:24:08');
INSERT INTO `auth_func` VALUES (97, '本地消息', '/sync/msg', 'dir', 'LaptopOutlined', 4, 'show', 93, '2022-12-04 23:49:04', '2023-10-14 22:24:37');
INSERT INTO `auth_func` VALUES (102, '绑定资源', 'auth:func:rescUpdate', 'func', '', 4, 'show', 5, '2023-06-17 21:00:09', '2023-07-15 23:17:19');
INSERT INTO `auth_func` VALUES (105, '授权初始化', 'auth:role:funcInit', 'func', '', 0, 'show', 71, '2023-06-21 21:02:03', '2023-09-24 21:44:26');
INSERT INTO `auth_func` VALUES (106, '更新授权', 'auth:role:funcUpdate', 'func', '', 0, 'show', 71, '2023-06-21 21:02:29', '2023-10-05 14:19:56');
INSERT INTO `auth_func` VALUES (128, '切换登录', 'auth:user:switchLogin', 'func', '', 9, 'show', 3, '2023-07-15 15:35:51', '2023-07-16 18:19:27');
INSERT INTO `auth_func` VALUES (129, '状态更新', 'auth:user:statusUpdate', 'func', '', 4, 'show', 3, '2023-07-15 20:40:12', '2023-09-23 15:29:39');
INSERT INTO `auth_func` VALUES (136, '计划查询', 'sync:plan:page', 'func', '', 0, 'show', 94, '2023-07-18 20:53:06', '2023-09-03 18:58:32');
INSERT INTO `auth_func` VALUES (137, '新增计划', 'sync:plan:add', 'func', '', 1, 'show', 94, '2023-07-19 21:04:36', '2023-09-03 13:23:09');
INSERT INTO `auth_func` VALUES (138, '编辑计划', 'sync:plan:update', 'func', '', 3, 'show', 94, '2023-07-19 22:08:32', '2023-09-03 18:58:36');
INSERT INTO `auth_func` VALUES (139, '删除计划', 'sync:plan:delete', 'func', '', 4, 'show', 94, '2023-07-19 22:26:53', '2024-06-17 22:07:11');
INSERT INTO `auth_func` VALUES (140, '启用禁用', 'sync:plan:updateStatus', 'func', '', 7, 'show', 94, '2023-09-03 13:19:10', '2023-09-03 15:20:12');
INSERT INTO `auth_func` VALUES (141, '消息列表', 'sync:msg:page', 'func', '', 1, 'show', 97, '2023-09-03 19:10:00', '2023-09-03 19:10:00');
INSERT INTO `auth_func` VALUES (142, '记录列表', 'sync:result:page', 'func', '', 1, 'show', 95, '2023-09-03 19:26:31', '2023-09-03 19:26:31');
INSERT INTO `auth_func` VALUES (143, '标记成功', 'sync:result:signSuccess', 'func', '', 2, 'show', 95, '2023-09-03 19:32:55', '2023-09-03 19:32:55');
INSERT INTO `auth_func` VALUES (144, '重新同步', 'sync:result:repeatSync', 'func', '', 3, 'show', 95, '2023-09-03 19:33:22', '2023-09-03 19:33:22');
INSERT INTO `auth_func` VALUES (145, '校验结果', 'sync:result:validResultData', 'func', '', 4, 'show', 95, '2023-09-03 19:33:48', '2023-09-03 19:33:48');
INSERT INTO `auth_func` VALUES (156, '功能关联', 'auth:resc:funcSearch', 'func', '', 1, 'show', 6, '2023-09-17 13:18:24', '2023-09-17 13:18:24');
INSERT INTO `auth_func` VALUES (160, '操作日志', '/auth/log', 'dir', 'CloudSyncOutlined', 7, 'show', 2, '2023-09-28 11:05:24', '2023-09-28 11:05:24');
INSERT INTO `auth_func` VALUES (161, '列表查询', 'auth:log:page', 'func', '', 1, 'show', 160, '2023-09-28 11:06:21', '2023-09-28 11:06:21');
INSERT INTO `auth_func` VALUES (162, '资源刷新', 'auth:resc:refresh', 'func', '', 8, 'show', 6, '2023-09-28 11:51:58', '2023-09-28 11:51:58');
INSERT INTO `auth_func` VALUES (171, '回调列表', 'sync:callback:page', 'func', '', 1, 'show', 96, '2023-11-09 20:35:55', '2023-11-09 20:35:55');
INSERT INTO `auth_func` VALUES (172, '重新回调', 'sync:callback:repeat', 'func', '', 3, 'show', 96, '2023-11-09 20:36:32', '2023-11-11 21:23:42');
INSERT INTO `auth_func` VALUES (173, '计划刷新', 'sync:plan:refresh', 'func', '', 3, 'show', 94, '2023-11-09 20:42:43', '2023-11-11 21:48:14');

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
) ENGINE = InnoDB AUTO_INCREMENT = 541 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
  `user_id` int(11) NULL DEFAULT NULL COMMENT '操作人id',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人账号',
  `real_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人姓名',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`) USING BTREE,
  INDEX `idx_log_module`(`log_module`) USING BTREE,
  INDEX `idx_user_id_name`(`user_id`, `username`) USING BTREE,
  INDEX `idx_log_level`(`log_level`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 516 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_log
-- ----------------------------
INSERT INTO `auth_log` VALUES (1, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-09-28 11:03:36');
INSERT INTO `auth_log` VALUES (2, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-09-28 11:04:13');
INSERT INTO `auth_log` VALUES (3, '新增资源信息', 2, 1, 61, 'admin', '管理员', '2023-09-28 11:04:54');
INSERT INTO `auth_log` VALUES (4, '新增功能信息', 3, 1, 61, 'admin', '管理员', '2023-09-28 11:05:24');
INSERT INTO `auth_log` VALUES (5, '功能解绑资源', 3, 2, 61, 'admin', '管理员', '2023-09-28 11:05:48');
INSERT INTO `auth_log` VALUES (6, '新增功能信息', 3, 1, 61, 'admin', '管理员', '2023-09-28 11:06:21');
INSERT INTO `auth_log` VALUES (7, '角色分配功能', 1, 2, 61, 'admin', '管理员', '2023-09-28 11:07:08');
INSERT INTO `auth_log` VALUES (8, '角色分配功能', 1, 2, 61, 'admin', '管理员', '2023-09-28 11:07:52');
INSERT INTO `auth_log` VALUES (9, '更新功能信息', 3, 1, 81, 'teacher', '章老师', '2023-09-28 11:08:13');
INSERT INTO `auth_log` VALUES (10, '更新角色信息', 1, 1, 81, 'teacher', '章老师', '2023-09-28 11:08:20');
INSERT INTO `auth_log` VALUES (11, '角色分配功能', 1, 2, 81, 'teacher', '章老师', '2023-09-28 11:08:25');
INSERT INTO `auth_log` VALUES (12, '更新功能信息', 3, 1, 81, 'teacher', '章老师', '2023-09-28 11:08:54');
INSERT INTO `auth_log` VALUES (13, '更新资源信息', 2, 1, 81, 'teacher', '章老师', '2023-09-28 11:09:07');
INSERT INTO `auth_log` VALUES (14, '更新资源信息', 2, 1, 81, 'teacher', '章老师', '2023-09-28 11:10:16');
INSERT INTO `auth_log` VALUES (15, '用户分配角色', 0, 2, 81, 'teacher', '章老师', '2023-09-28 11:10:38');
INSERT INTO `auth_log` VALUES (16, '角色分配功能', 1, 2, 81, 'teacher', '章老师', '2023-09-28 11:10:53');
INSERT INTO `auth_log` VALUES (17, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-09-28 11:11:17');
INSERT INTO `auth_log` VALUES (18, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-09-28 11:11:40');
INSERT INTO `auth_log` VALUES (19, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-09-28 11:12:27');
INSERT INTO `auth_log` VALUES (20, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-09-28 11:12:47');
INSERT INTO `auth_log` VALUES (21, '用户注销登录', 0, 0, 61, 'admin', '管理员', '2023-09-28 11:51:01');
INSERT INTO `auth_log` VALUES (22, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-09-28 11:51:02');
INSERT INTO `auth_log` VALUES (23, '新增功能信息', 3, 1, 61, 'admin', '管理员', '2023-09-28 11:51:59');
INSERT INTO `auth_log` VALUES (24, '新增资源信息', 2, 1, 61, 'admin', '管理员', '2023-09-28 11:52:27');
INSERT INTO `auth_log` VALUES (25, '角色分配功能', 1, 2, 61, 'admin', '管理员', '2023-09-28 11:53:28');
INSERT INTO `auth_log` VALUES (26, '更新资源信息', 2, 1, 61, 'admin', '管理员', '2023-09-28 12:04:30');
INSERT INTO `auth_log` VALUES (27, '用户注销登录', 0, 0, 61, 'admin', '管理员', '2023-09-28 12:04:43');
INSERT INTO `auth_log` VALUES (28, '用户登录系统', 0, 0, 82, 'student', '学生', '2023-09-28 12:04:46');
INSERT INTO `auth_log` VALUES (29, '用户注销登录', 0, 0, 82, 'student', '学生', '2023-09-28 12:05:31');
INSERT INTO `auth_log` VALUES (30, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-09-28 12:05:32');
INSERT INTO `auth_log` VALUES (31, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-09-28 12:08:00');
INSERT INTO `auth_log` VALUES (32, '更新角色信息', 1, 1, 61, 'admin', '管理员', '2023-09-28 12:53:15');
INSERT INTO `auth_log` VALUES (33, '新增角色信息', 1, 1, 61, 'admin', '管理员', '2023-09-28 12:53:32');
INSERT INTO `auth_log` VALUES (34, '删除角色信息', 1, 2, 61, 'admin', '管理员', '2023-09-28 12:53:38');
INSERT INTO `auth_log` VALUES (35, '新增角色信息', 1, 1, 61, 'admin', '管理员', '2023-09-28 12:53:46');
INSERT INTO `auth_log` VALUES (36, '删除角色信息', 1, 2, 61, 'admin', '管理员', '2023-09-28 12:53:53');
INSERT INTO `auth_log` VALUES (37, '新增资源信息', 2, 1, 61, 'admin', '管理员', '2023-09-28 17:06:51');
INSERT INTO `auth_log` VALUES (38, '新增资源信息', 2, 1, 61, 'admin', '管理员', '2023-09-28 17:07:13');
INSERT INTO `auth_log` VALUES (39, '新增功能信息', 3, 1, 61, 'admin', '管理员', '2023-09-28 17:07:43');
INSERT INTO `auth_log` VALUES (40, '新增功能信息', 3, 1, 61, 'admin', '管理员', '2023-09-28 17:08:11');
INSERT INTO `auth_log` VALUES (41, '角色分配功能', 1, 2, 61, 'admin', '管理员', '2023-09-28 17:08:38');
INSERT INTO `auth_log` VALUES (42, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-09-28 17:32:32');
INSERT INTO `auth_log` VALUES (43, '禁用用户账号', 0, 0, 61, 'admin', '管理员', '2023-09-28 17:32:37');
INSERT INTO `auth_log` VALUES (44, '启用用户账号', 0, 0, 61, 'admin', '管理员', '2023-09-28 17:46:35');
INSERT INTO `auth_log` VALUES (45, '更新角色信息', 1, 1, 61, 'admin', '管理员', '2023-09-28 17:50:02');
INSERT INTO `auth_log` VALUES (46, '更新角色信息', 1, 1, 61, 'admin', '管理员', '2023-09-28 17:51:02');
INSERT INTO `auth_log` VALUES (47, '更新角色信息', 1, 1, 61, 'admin', '管理员', '2023-09-28 17:51:06');
INSERT INTO `auth_log` VALUES (48, '更新资源信息', 2, 1, 61, 'admin', '管理员', '2023-09-28 17:51:14');
INSERT INTO `auth_log` VALUES (49, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-09-28 20:20:40');
INSERT INTO `auth_log` VALUES (50, '更新资源信息', 2, 1, 61, 'admin', '管理员', '2023-09-28 20:20:54');
INSERT INTO `auth_log` VALUES (51, '禁用用户账号', 0, 0, 61, 'admin', '管理员', '2023-09-28 20:21:01');
INSERT INTO `auth_log` VALUES (52, '启用用户账号', 0, 0, 61, 'admin', '管理员', '2023-09-28 20:21:06');
INSERT INTO `auth_log` VALUES (53, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-09-28 23:35:35');
INSERT INTO `auth_log` VALUES (54, '角色分配功能', 1, 2, 61, 'admin', '管理员', '2023-09-28 23:38:21');
INSERT INTO `auth_log` VALUES (55, '角色分配功能', 1, 2, 61, 'admin', '管理员', '2023-09-28 23:38:35');
INSERT INTO `auth_log` VALUES (56, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-09-28 23:38:55');
INSERT INTO `auth_log` VALUES (57, '用户注销登录', 0, 0, 82, 'student', '学生', '2023-09-28 23:39:26');
INSERT INTO `auth_log` VALUES (58, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-09-28 23:39:28');
INSERT INTO `auth_log` VALUES (59, '更新资源信息', 2, 1, 61, 'admin', '管理员', '2023-09-28 23:41:06');
INSERT INTO `auth_log` VALUES (60, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-09-28 23:41:16');
INSERT INTO `auth_log` VALUES (61, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-09-28 23:51:24');
INSERT INTO `auth_log` VALUES (62, '更新资源信息', 2, 1, 61, 'admin', '管理员', '2023-09-28 23:53:33');
INSERT INTO `auth_log` VALUES (63, '禁用用户账号', 0, 0, 61, 'admin', '管理员', '2023-09-28 23:53:39');
INSERT INTO `auth_log` VALUES (64, '启用用户账号', 0, 0, 61, 'admin', '管理员', '2023-09-28 23:53:44');
INSERT INTO `auth_log` VALUES (65, '角色分配功能', 1, 2, 61, 'admin', '管理员', '2023-09-28 23:53:56');
INSERT INTO `auth_log` VALUES (66, '更新资源信息', 2, 1, 61, 'admin', '管理员', '2023-09-28 23:54:03');
INSERT INTO `auth_log` VALUES (67, '角色分配功能', 1, 2, 61, 'admin', '管理员', '2023-09-28 23:54:17');
INSERT INTO `auth_log` VALUES (68, '角色分配功能', 1, 2, 61, 'admin', '管理员', '2023-09-28 23:54:38');
INSERT INTO `auth_log` VALUES (69, '更新角色信息', 1, 1, 61, 'admin', '管理员', '2023-09-29 10:28:07');
INSERT INTO `auth_log` VALUES (70, '角色分配功能', 1, 2, 61, 'admin', '管理员', '2023-09-29 10:29:54');
INSERT INTO `auth_log` VALUES (71, '角色分配功能', 1, 2, 61, 'admin', '管理员', '2023-09-29 10:30:27');
INSERT INTO `auth_log` VALUES (72, '用户注销登录', 0, 0, 82, 'student', '学生', '2023-09-29 10:31:21');
INSERT INTO `auth_log` VALUES (73, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-09-29 10:31:21');
INSERT INTO `auth_log` VALUES (74, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-09-29 10:42:09');
INSERT INTO `auth_log` VALUES (75, '用户注销登录', 0, 0, 61, 'admin', '管理员', '2023-09-29 10:48:19');
INSERT INTO `auth_log` VALUES (76, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-09-29 10:48:20');
INSERT INTO `auth_log` VALUES (77, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-09-29 10:48:49');
INSERT INTO `auth_log` VALUES (78, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-09-29 10:48:51');
INSERT INTO `auth_log` VALUES (79, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-09-29 10:48:53');
INSERT INTO `auth_log` VALUES (80, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-09-29 10:48:55');
INSERT INTO `auth_log` VALUES (81, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-09-29 10:57:18');
INSERT INTO `auth_log` VALUES (82, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-09-29 10:58:55');
INSERT INTO `auth_log` VALUES (83, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-09-29 10:59:45');
INSERT INTO `auth_log` VALUES (84, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-09-29 11:00:36');
INSERT INTO `auth_log` VALUES (85, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-09-29 11:09:00');
INSERT INTO `auth_log` VALUES (86, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-09-29 11:09:20');
INSERT INTO `auth_log` VALUES (87, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-09-29 11:13:18');
INSERT INTO `auth_log` VALUES (88, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-09-29 11:13:20');
INSERT INTO `auth_log` VALUES (89, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-09-29 11:13:21');
INSERT INTO `auth_log` VALUES (90, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-09-29 11:14:16');
INSERT INTO `auth_log` VALUES (91, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-09-29 11:15:15');
INSERT INTO `auth_log` VALUES (92, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-09-29 11:33:41');
INSERT INTO `auth_log` VALUES (93, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-09-29 11:33:43');
INSERT INTO `auth_log` VALUES (94, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-09-29 11:33:46');
INSERT INTO `auth_log` VALUES (95, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-09-29 20:40:51');
INSERT INTO `auth_log` VALUES (96, '新增资源信息', 2, 1, 61, 'admin', '管理员', '2023-09-29 20:52:16');
INSERT INTO `auth_log` VALUES (97, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-09-29 22:35:48');
INSERT INTO `auth_log` VALUES (98, '角色分配功能', 1, 2, 61, 'admin', '管理员', '2023-09-29 22:36:55');
INSERT INTO `auth_log` VALUES (99, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-09-29 22:37:02');
INSERT INTO `auth_log` VALUES (100, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-09-29 22:37:05');
INSERT INTO `auth_log` VALUES (101, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-09-29 22:48:03');
INSERT INTO `auth_log` VALUES (102, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-09-29 22:48:24');
INSERT INTO `auth_log` VALUES (103, '用户注销登录', 0, 0, 82, 'student', '学生', '2023-09-29 22:52:12');
INSERT INTO `auth_log` VALUES (104, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-09-29 22:52:13');
INSERT INTO `auth_log` VALUES (105, '角色分配功能', 1, 2, 61, 'admin', '管理员', '2023-09-29 22:58:28');
INSERT INTO `auth_log` VALUES (106, '角色分配功能', 1, 2, 61, 'admin', '管理员', '2023-09-29 22:58:36');
INSERT INTO `auth_log` VALUES (107, '角色分配功能', 1, 2, 61, 'admin', '管理员', '2023-09-29 22:59:04');
INSERT INTO `auth_log` VALUES (108, '角色分配功能', 1, 2, 61, 'admin', '管理员', '2023-09-29 22:59:13');
INSERT INTO `auth_log` VALUES (109, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-10-01 10:28:02');
INSERT INTO `auth_log` VALUES (110, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-01 10:28:29');
INSERT INTO `auth_log` VALUES (111, '更新角色信息', 1, 1, 61, 'admin', '管理员', '2023-10-01 10:31:14');
INSERT INTO `auth_log` VALUES (112, '更新角色信息', 1, 1, 61, 'admin', '管理员', '2023-10-01 10:31:15');
INSERT INTO `auth_log` VALUES (113, '更新角色信息', 1, 1, 61, 'admin', '管理员', '2023-10-01 10:31:18');
INSERT INTO `auth_log` VALUES (114, '更新角色信息', 1, 1, 61, 'admin', '管理员', '2023-10-01 10:31:20');
INSERT INTO `auth_log` VALUES (115, '更新角色信息', 1, 1, 61, 'admin', '管理员', '2023-10-01 10:31:22');
INSERT INTO `auth_log` VALUES (116, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-01 10:31:26');
INSERT INTO `auth_log` VALUES (117, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-10-01 10:31:29');
INSERT INTO `auth_log` VALUES (118, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-10-01 10:33:35');
INSERT INTO `auth_log` VALUES (119, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-01 11:00:55');
INSERT INTO `auth_log` VALUES (120, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-01 11:00:57');
INSERT INTO `auth_log` VALUES (121, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-01 11:00:59');
INSERT INTO `auth_log` VALUES (122, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-10-01 11:01:04');
INSERT INTO `auth_log` VALUES (123, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-10-01 11:01:07');
INSERT INTO `auth_log` VALUES (124, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-10-01 11:01:09');
INSERT INTO `auth_log` VALUES (125, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-10-01 11:01:11');
INSERT INTO `auth_log` VALUES (126, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-10-01 11:01:14');
INSERT INTO `auth_log` VALUES (127, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-10-01 11:01:16');
INSERT INTO `auth_log` VALUES (128, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-10-01 11:01:20');
INSERT INTO `auth_log` VALUES (129, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-10-01 11:01:22');
INSERT INTO `auth_log` VALUES (130, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-10-01 11:01:24');
INSERT INTO `auth_log` VALUES (131, '更新角色信息', 1, 1, 61, 'admin', '管理员', '2023-10-01 11:01:37');
INSERT INTO `auth_log` VALUES (132, '更新角色信息', 1, 1, 61, 'admin', '管理员', '2023-10-01 11:01:39');
INSERT INTO `auth_log` VALUES (133, '更新角色信息', 1, 1, 61, 'admin', '管理员', '2023-10-01 11:01:40');
INSERT INTO `auth_log` VALUES (134, '更新资源信息', 2, 1, 61, 'admin', '管理员', '2023-10-01 11:01:43');
INSERT INTO `auth_log` VALUES (135, '更新资源信息', 2, 1, 61, 'admin', '管理员', '2023-10-01 11:01:45');
INSERT INTO `auth_log` VALUES (136, '更新资源信息', 2, 1, 61, 'admin', '管理员', '2023-10-01 11:01:47');
INSERT INTO `auth_log` VALUES (137, '更新资源信息', 2, 1, 61, 'admin', '管理员', '2023-10-01 11:01:49');
INSERT INTO `auth_log` VALUES (138, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-10-05 00:33:50');
INSERT INTO `auth_log` VALUES (139, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-05 00:34:27');
INSERT INTO `auth_log` VALUES (140, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-10-05 14:13:40');
INSERT INTO `auth_log` VALUES (141, '更新资源信息', 2, 1, 61, 'admin', '管理员', '2023-10-05 14:14:39');
INSERT INTO `auth_log` VALUES (142, '更新资源信息', 2, 1, 61, 'admin', '管理员', '2023-10-05 14:14:45');
INSERT INTO `auth_log` VALUES (143, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-10-05 14:17:53');
INSERT INTO `auth_log` VALUES (144, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-10-05 14:17:56');
INSERT INTO `auth_log` VALUES (145, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-10-05 14:18:01');
INSERT INTO `auth_log` VALUES (146, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-10-05 14:18:04');
INSERT INTO `auth_log` VALUES (147, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-10-05 14:18:07');
INSERT INTO `auth_log` VALUES (148, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-10-05 14:18:09');
INSERT INTO `auth_log` VALUES (149, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-10-05 14:18:16');
INSERT INTO `auth_log` VALUES (150, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-10-05 14:18:20');
INSERT INTO `auth_log` VALUES (151, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-10-05 14:18:23');
INSERT INTO `auth_log` VALUES (152, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-10-05 14:19:18');
INSERT INTO `auth_log` VALUES (153, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-10-05 14:19:50');
INSERT INTO `auth_log` VALUES (154, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-10-05 14:19:53');
INSERT INTO `auth_log` VALUES (155, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-10-05 14:19:56');
INSERT INTO `auth_log` VALUES (156, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-10-05 14:19:58');
INSERT INTO `auth_log` VALUES (157, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-10-05 14:20:20');
INSERT INTO `auth_log` VALUES (158, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-10-05 14:20:21');
INSERT INTO `auth_log` VALUES (159, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-10-05 14:21:19');
INSERT INTO `auth_log` VALUES (160, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-10-05 14:21:58');
INSERT INTO `auth_log` VALUES (161, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-10-05 14:27:16');
INSERT INTO `auth_log` VALUES (162, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-05 14:37:48');
INSERT INTO `auth_log` VALUES (163, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-05 14:37:51');
INSERT INTO `auth_log` VALUES (164, '更新角色信息', 1, 1, 61, 'admin', '管理员', '2023-10-05 15:32:46');
INSERT INTO `auth_log` VALUES (165, '更新角色信息', 1, 1, 61, 'admin', '管理员', '2023-10-05 15:32:48');
INSERT INTO `auth_log` VALUES (166, '更新角色信息', 1, 1, 61, 'admin', '管理员', '2023-10-05 15:32:50');
INSERT INTO `auth_log` VALUES (167, '更新资源信息', 2, 1, 61, 'admin', '管理员', '2023-10-05 15:39:47');
INSERT INTO `auth_log` VALUES (168, '删除资源信息', 2, 2, 61, 'admin', '管理员', '2023-10-05 15:40:02');
INSERT INTO `auth_log` VALUES (169, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-10-08 20:19:08');
INSERT INTO `auth_log` VALUES (170, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-10-08 20:19:18');
INSERT INTO `auth_log` VALUES (171, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-10-15 00:17:46');
INSERT INTO `auth_log` VALUES (172, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-10-21 21:37:25');
INSERT INTO `auth_log` VALUES (173, '新增资源信息', 2, 1, 61, 'admin', '管理员', '2023-10-21 21:47:46');
INSERT INTO `auth_log` VALUES (174, '新增功能信息', 3, 1, 61, 'admin', '管理员', '2023-10-21 21:48:40');
INSERT INTO `auth_log` VALUES (175, '角色分配功能', 1, 2, 61, 'admin', '管理员', '2023-10-21 21:48:52');
INSERT INTO `auth_log` VALUES (176, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-21 21:49:53');
INSERT INTO `auth_log` VALUES (178, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-21 21:53:03');
INSERT INTO `auth_log` VALUES (179, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-21 21:53:12');
INSERT INTO `auth_log` VALUES (180, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-21 21:53:16');
INSERT INTO `auth_log` VALUES (181, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-21 21:53:43');
INSERT INTO `auth_log` VALUES (182, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-21 21:53:46');
INSERT INTO `auth_log` VALUES (183, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-21 21:54:21');
INSERT INTO `auth_log` VALUES (184, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-21 21:54:25');
INSERT INTO `auth_log` VALUES (185, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-21 21:54:32');
INSERT INTO `auth_log` VALUES (186, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-21 21:54:35');
INSERT INTO `auth_log` VALUES (187, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-21 21:54:37');
INSERT INTO `auth_log` VALUES (188, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-21 21:54:39');
INSERT INTO `auth_log` VALUES (189, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-21 21:54:41');
INSERT INTO `auth_log` VALUES (190, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-21 21:54:53');
INSERT INTO `auth_log` VALUES (191, '角色分配功能', 1, 2, 61, 'admin', '管理员', '2023-10-21 21:56:39');
INSERT INTO `auth_log` VALUES (192, '角色分配功能', 1, 2, 61, 'admin', '管理员', '2023-10-21 21:56:44');
INSERT INTO `auth_log` VALUES (193, '更新用户信息', 0, 1, 81, 'teacher', '章老师', '2023-10-21 21:57:34');
INSERT INTO `auth_log` VALUES (194, '更新用户信息', 0, 1, 81, 'teacher', '章老师', '2023-10-21 21:57:36');
INSERT INTO `auth_log` VALUES (195, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-10-22 10:49:48');
INSERT INTO `auth_log` VALUES (196, '用户注销登录', 0, 0, 61, 'admin', '管理员', '2023-10-22 10:51:45');
INSERT INTO `auth_log` VALUES (197, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-10-22 10:51:53');
INSERT INTO `auth_log` VALUES (198, '用户注销登录', 0, 0, 61, 'admin', '管理员', '2023-10-22 10:57:01');
INSERT INTO `auth_log` VALUES (199, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-10-22 10:57:04');
INSERT INTO `auth_log` VALUES (200, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-10-22 10:58:23');
INSERT INTO `auth_log` VALUES (201, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-10-22 11:00:34');
INSERT INTO `auth_log` VALUES (202, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-22 11:00:47');
INSERT INTO `auth_log` VALUES (203, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-22 11:00:52');
INSERT INTO `auth_log` VALUES (204, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-22 11:00:54');
INSERT INTO `auth_log` VALUES (205, '更新用户信息', 0, 1, 82, 'student', '学生', '2023-10-22 11:03:22');
INSERT INTO `auth_log` VALUES (206, '更新用户信息', 0, 1, 82, 'student', '学生', '2023-10-22 11:03:24');
INSERT INTO `auth_log` VALUES (207, '更新用户信息', 0, 1, 82, 'student', '学生', '2023-10-22 11:03:27');
INSERT INTO `auth_log` VALUES (208, '更新用户信息', 0, 1, 82, 'student', '学生', '2023-10-22 11:03:29');
INSERT INTO `auth_log` VALUES (209, '更新用户信息', 0, 1, 82, 'student', '学生', '2023-10-22 11:03:31');
INSERT INTO `auth_log` VALUES (210, '更新用户信息', 0, 1, 82, 'student', '学生', '2023-10-22 11:03:33');
INSERT INTO `auth_log` VALUES (211, '更新用户信息', 0, 1, 82, 'student', '学生', '2023-10-22 11:03:41');
INSERT INTO `auth_log` VALUES (212, '更新用户信息', 0, 1, 82, 'student', '学生', '2023-10-22 11:03:43');
INSERT INTO `auth_log` VALUES (213, '更新用户信息', 0, 1, 82, 'student', '学生', '2023-10-22 11:03:45');
INSERT INTO `auth_log` VALUES (214, '更新用户信息', 0, 1, 82, 'student', '学生', '2023-10-22 11:03:47');
INSERT INTO `auth_log` VALUES (215, '更新用户信息', 0, 1, 82, 'student', '学生', '2023-10-22 11:03:59');
INSERT INTO `auth_log` VALUES (216, '更新用户信息', 0, 1, 82, 'student', '学生', '2023-10-22 11:04:06');
INSERT INTO `auth_log` VALUES (217, '更新用户信息', 0, 1, 82, 'student', '学生', '2023-10-22 11:04:09');
INSERT INTO `auth_log` VALUES (218, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-10-22 18:48:56');
INSERT INTO `auth_log` VALUES (219, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-10-22 18:51:57');
INSERT INTO `auth_log` VALUES (220, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-10-22 18:53:03');
INSERT INTO `auth_log` VALUES (221, '用户注销登录', 0, 0, 61, 'admin', '管理员', '2023-10-22 18:53:20');
INSERT INTO `auth_log` VALUES (222, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-10-22 18:53:21');
INSERT INTO `auth_log` VALUES (223, '功能解绑用户', 3, 2, 61, 'admin', '管理员', '2023-10-22 18:54:35');
INSERT INTO `auth_log` VALUES (224, '功能解绑资源', 3, 2, 61, 'admin', '管理员', '2023-10-22 18:54:37');
INSERT INTO `auth_log` VALUES (225, '删除功能信息', 3, 2, 61, 'admin', '管理员', '2023-10-22 18:54:48');
INSERT INTO `auth_log` VALUES (226, '用户注销登录', 0, 0, 61, 'admin', '管理员', '2023-10-22 18:54:56');
INSERT INTO `auth_log` VALUES (227, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-10-22 18:54:57');
INSERT INTO `auth_log` VALUES (228, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-22 19:14:26');
INSERT INTO `auth_log` VALUES (229, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-22 19:32:04');
INSERT INTO `auth_log` VALUES (230, '删除资源信息', 2, 2, 61, 'admin', '管理员', '2023-10-22 19:42:24');
INSERT INTO `auth_log` VALUES (231, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-22 19:42:32');
INSERT INTO `auth_log` VALUES (232, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-22 19:42:37');
INSERT INTO `auth_log` VALUES (233, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-10-22 23:25:12');
INSERT INTO `auth_log` VALUES (234, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-22 23:33:27');
INSERT INTO `auth_log` VALUES (235, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-22 23:48:22');
INSERT INTO `auth_log` VALUES (236, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-22 23:49:06');
INSERT INTO `auth_log` VALUES (237, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-22 23:49:12');
INSERT INTO `auth_log` VALUES (238, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-22 23:49:40');
INSERT INTO `auth_log` VALUES (239, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-22 23:49:42');
INSERT INTO `auth_log` VALUES (240, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-22 23:49:44');
INSERT INTO `auth_log` VALUES (241, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-22 23:50:12');
INSERT INTO `auth_log` VALUES (242, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-22 23:50:50');
INSERT INTO `auth_log` VALUES (243, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-22 23:51:08');
INSERT INTO `auth_log` VALUES (244, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-22 23:51:15');
INSERT INTO `auth_log` VALUES (245, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-22 23:51:17');
INSERT INTO `auth_log` VALUES (246, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-22 23:51:20');
INSERT INTO `auth_log` VALUES (247, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-22 23:53:39');
INSERT INTO `auth_log` VALUES (248, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-22 23:53:42');
INSERT INTO `auth_log` VALUES (249, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-22 23:53:44');
INSERT INTO `auth_log` VALUES (250, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-22 23:53:47');
INSERT INTO `auth_log` VALUES (251, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-22 23:54:10');
INSERT INTO `auth_log` VALUES (252, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-22 23:54:13');
INSERT INTO `auth_log` VALUES (253, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-22 23:54:15');
INSERT INTO `auth_log` VALUES (254, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-22 23:54:19');
INSERT INTO `auth_log` VALUES (255, '用户注销登录', 0, 0, 61, 'admin', '管理员', '2023-10-22 23:56:39');
INSERT INTO `auth_log` VALUES (256, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-10-22 23:58:08');
INSERT INTO `auth_log` VALUES (257, '用户登录系统', 0, 0, 82, 'student', '学生', '2023-10-22 23:58:38');
INSERT INTO `auth_log` VALUES (258, '更新用户信息', 0, 1, 82, 'student', '学生', '2023-10-22 23:58:47');
INSERT INTO `auth_log` VALUES (259, '更新用户信息', 0, 1, 82, 'student', '学生', '2023-10-22 23:58:50');
INSERT INTO `auth_log` VALUES (260, '更新用户信息', 0, 1, 82, 'student', '学生', '2023-10-22 23:58:53');
INSERT INTO `auth_log` VALUES (261, '更新用户信息', 0, 1, 82, 'student', '学生', '2023-10-22 23:58:56');
INSERT INTO `auth_log` VALUES (262, '更新用户信息', 0, 1, 82, 'student', '学生', '2023-10-22 23:58:58');
INSERT INTO `auth_log` VALUES (263, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-23 00:00:22');
INSERT INTO `auth_log` VALUES (264, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-23 00:00:24');
INSERT INTO `auth_log` VALUES (265, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-10-29 14:37:54');
INSERT INTO `auth_log` VALUES (266, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-10-29 14:38:20');
INSERT INTO `auth_log` VALUES (267, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-10-29 14:42:44');
INSERT INTO `auth_log` VALUES (268, '角色分配功能', 1, 2, 82, 'student', '学生', '2023-10-29 14:50:26');
INSERT INTO `auth_log` VALUES (269, '用户注销登录', 0, 0, 82, 'student', '学生', '2023-10-29 14:50:48');
INSERT INTO `auth_log` VALUES (270, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-10-29 14:50:48');
INSERT INTO `auth_log` VALUES (271, '删除资源信息', 2, 2, 61, 'admin', '管理员', '2023-10-29 14:53:48');
INSERT INTO `auth_log` VALUES (272, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-11-02 20:22:26');
INSERT INTO `auth_log` VALUES (273, '更新角色信息', 1, 1, 61, 'admin', '管理员', '2023-11-02 20:23:10');
INSERT INTO `auth_log` VALUES (274, '更新角色信息', 1, 1, 61, 'admin', '管理员', '2023-11-02 20:23:13');
INSERT INTO `auth_log` VALUES (275, '更新资源信息', 2, 1, 61, 'admin', '管理员', '2023-11-02 20:23:20');
INSERT INTO `auth_log` VALUES (276, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-11-09 20:17:07');
INSERT INTO `auth_log` VALUES (277, '新增功能信息', 3, 1, 61, 'admin', '管理员', '2023-11-09 20:17:40');
INSERT INTO `auth_log` VALUES (278, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-11-09 20:17:52');
INSERT INTO `auth_log` VALUES (279, '新增功能信息', 3, 1, 61, 'admin', '管理员', '2023-11-09 20:18:14');
INSERT INTO `auth_log` VALUES (280, '新增功能信息', 3, 1, 61, 'admin', '管理员', '2023-11-09 20:18:39');
INSERT INTO `auth_log` VALUES (281, '新增功能信息', 3, 1, 61, 'admin', '管理员', '2023-11-09 20:19:02');
INSERT INTO `auth_log` VALUES (282, '新增功能信息', 3, 1, 61, 'admin', '管理员', '2023-11-09 20:19:19');
INSERT INTO `auth_log` VALUES (283, '角色分配功能', 1, 2, 61, 'admin', '管理员', '2023-11-09 20:19:35');
INSERT INTO `auth_log` VALUES (284, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-11-09 20:19:57');
INSERT INTO `auth_log` VALUES (285, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-11-09 20:20:18');
INSERT INTO `auth_log` VALUES (286, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-11-09 20:20:26');
INSERT INTO `auth_log` VALUES (287, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-11-09 20:20:34');
INSERT INTO `auth_log` VALUES (288, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-09 20:20:46');
INSERT INTO `auth_log` VALUES (289, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-11-09 20:31:43');
INSERT INTO `auth_log` VALUES (290, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-11-09 20:32:45');
INSERT INTO `auth_log` VALUES (291, '角色分配功能', 1, 2, 61, 'admin', '管理员', '2023-11-09 20:33:35');
INSERT INTO `auth_log` VALUES (292, '删除功能信息', 3, 2, 61, 'admin', '管理员', '2023-11-09 20:33:40');
INSERT INTO `auth_log` VALUES (293, '删除功能信息', 3, 2, 61, 'admin', '管理员', '2023-11-09 20:33:42');
INSERT INTO `auth_log` VALUES (294, '删除功能信息', 3, 2, 61, 'admin', '管理员', '2023-11-09 20:33:43');
INSERT INTO `auth_log` VALUES (295, '删除功能信息', 3, 2, 61, 'admin', '管理员', '2023-11-09 20:33:44');
INSERT INTO `auth_log` VALUES (296, '删除功能信息', 3, 2, 61, 'admin', '管理员', '2023-11-09 20:33:46');
INSERT INTO `auth_log` VALUES (297, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-11-09 20:34:18');
INSERT INTO `auth_log` VALUES (298, '新增功能信息', 3, 1, 61, 'admin', '管理员', '2023-11-09 20:35:55');
INSERT INTO `auth_log` VALUES (299, '新增功能信息', 3, 1, 61, 'admin', '管理员', '2023-11-09 20:36:32');
INSERT INTO `auth_log` VALUES (300, '角色分配功能', 1, 2, 61, 'admin', '管理员', '2023-11-09 20:37:58');
INSERT INTO `auth_log` VALUES (301, '新增功能信息', 3, 1, 61, 'admin', '管理员', '2023-11-09 20:42:43');
INSERT INTO `auth_log` VALUES (302, '角色分配功能', 1, 2, 61, 'admin', '管理员', '2023-11-09 20:43:10');
INSERT INTO `auth_log` VALUES (303, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-09 20:47:26');
INSERT INTO `auth_log` VALUES (304, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-09 20:47:28');
INSERT INTO `auth_log` VALUES (305, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-09 20:47:30');
INSERT INTO `auth_log` VALUES (306, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-11-09 20:55:56');
INSERT INTO `auth_log` VALUES (307, '用户注销登录', 0, 0, 61, 'admin', '管理员', '2023-11-09 20:58:41');
INSERT INTO `auth_log` VALUES (308, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-11-09 21:02:37');
INSERT INTO `auth_log` VALUES (309, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-11-09 21:03:23');
INSERT INTO `auth_log` VALUES (310, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-11-09 21:04:07');
INSERT INTO `auth_log` VALUES (311, '角色分配功能', 1, 2, 61, 'admin', '管理员', '2023-11-09 21:24:45');
INSERT INTO `auth_log` VALUES (312, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-11-10 09:24:28');
INSERT INTO `auth_log` VALUES (313, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-11-10 13:47:49');
INSERT INTO `auth_log` VALUES (314, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-11-10 15:57:37');
INSERT INTO `auth_log` VALUES (315, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-10 16:12:16');
INSERT INTO `auth_log` VALUES (316, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-10 16:12:23');
INSERT INTO `auth_log` VALUES (317, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-11-11 10:22:43');
INSERT INTO `auth_log` VALUES (318, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-11-11 14:25:04');
INSERT INTO `auth_log` VALUES (319, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-11-11 20:02:57');
INSERT INTO `auth_log` VALUES (320, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:07:34');
INSERT INTO `auth_log` VALUES (321, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:12:11');
INSERT INTO `auth_log` VALUES (322, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:14:09');
INSERT INTO `auth_log` VALUES (323, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:14:11');
INSERT INTO `auth_log` VALUES (324, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:14:13');
INSERT INTO `auth_log` VALUES (325, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:16:07');
INSERT INTO `auth_log` VALUES (326, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:16:09');
INSERT INTO `auth_log` VALUES (327, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:16:11');
INSERT INTO `auth_log` VALUES (328, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-11-11 21:23:05');
INSERT INTO `auth_log` VALUES (329, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-11-11 21:23:42');
INSERT INTO `auth_log` VALUES (330, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:24:41');
INSERT INTO `auth_log` VALUES (331, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:24:43');
INSERT INTO `auth_log` VALUES (332, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:24:44');
INSERT INTO `auth_log` VALUES (333, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:25:57');
INSERT INTO `auth_log` VALUES (334, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:26:30');
INSERT INTO `auth_log` VALUES (335, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:28:48');
INSERT INTO `auth_log` VALUES (336, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:28:50');
INSERT INTO `auth_log` VALUES (337, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:30:18');
INSERT INTO `auth_log` VALUES (338, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:30:20');
INSERT INTO `auth_log` VALUES (339, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:30:22');
INSERT INTO `auth_log` VALUES (340, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:30:23');
INSERT INTO `auth_log` VALUES (341, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:30:25');
INSERT INTO `auth_log` VALUES (342, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:30:26');
INSERT INTO `auth_log` VALUES (343, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:30:28');
INSERT INTO `auth_log` VALUES (344, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:30:29');
INSERT INTO `auth_log` VALUES (345, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:30:32');
INSERT INTO `auth_log` VALUES (346, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:30:33');
INSERT INTO `auth_log` VALUES (347, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:30:35');
INSERT INTO `auth_log` VALUES (348, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:30:36');
INSERT INTO `auth_log` VALUES (349, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:30:38');
INSERT INTO `auth_log` VALUES (350, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:30:39');
INSERT INTO `auth_log` VALUES (351, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:34:35');
INSERT INTO `auth_log` VALUES (352, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:34:37');
INSERT INTO `auth_log` VALUES (353, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:34:39');
INSERT INTO `auth_log` VALUES (354, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:34:41');
INSERT INTO `auth_log` VALUES (355, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:34:43');
INSERT INTO `auth_log` VALUES (356, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:40:08');
INSERT INTO `auth_log` VALUES (357, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:41:59');
INSERT INTO `auth_log` VALUES (358, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:42:00');
INSERT INTO `auth_log` VALUES (359, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:42:28');
INSERT INTO `auth_log` VALUES (360, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:42:30');
INSERT INTO `auth_log` VALUES (361, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:42:32');
INSERT INTO `auth_log` VALUES (362, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:42:33');
INSERT INTO `auth_log` VALUES (363, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:42:37');
INSERT INTO `auth_log` VALUES (364, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:42:39');
INSERT INTO `auth_log` VALUES (365, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:42:41');
INSERT INTO `auth_log` VALUES (366, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:42:42');
INSERT INTO `auth_log` VALUES (367, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:42:44');
INSERT INTO `auth_log` VALUES (368, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:42:45');
INSERT INTO `auth_log` VALUES (369, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:42:47');
INSERT INTO `auth_log` VALUES (370, '更新角色信息', 1, 1, 61, 'admin', '管理员', '2023-11-11 21:42:55');
INSERT INTO `auth_log` VALUES (371, '更新角色信息', 1, 1, 61, 'admin', '管理员', '2023-11-11 21:42:56');
INSERT INTO `auth_log` VALUES (372, '更新角色信息', 1, 1, 61, 'admin', '管理员', '2023-11-11 21:42:58');
INSERT INTO `auth_log` VALUES (373, '更新角色信息', 1, 1, 61, 'admin', '管理员', '2023-11-11 21:42:59');
INSERT INTO `auth_log` VALUES (374, '更新角色信息', 1, 1, 61, 'admin', '管理员', '2023-11-11 21:43:01');
INSERT INTO `auth_log` VALUES (375, '更新角色信息', 1, 1, 61, 'admin', '管理员', '2023-11-11 21:43:02');
INSERT INTO `auth_log` VALUES (376, '更新角色信息', 1, 1, 61, 'admin', '管理员', '2023-11-11 21:43:04');
INSERT INTO `auth_log` VALUES (377, '更新角色信息', 1, 1, 61, 'admin', '管理员', '2023-11-11 21:43:05');
INSERT INTO `auth_log` VALUES (378, '更新角色信息', 1, 1, 61, 'admin', '管理员', '2023-11-11 21:43:07');
INSERT INTO `auth_log` VALUES (379, '更新角色信息', 1, 1, 61, 'admin', '管理员', '2023-11-11 21:43:09');
INSERT INTO `auth_log` VALUES (380, '更新资源信息', 2, 1, 61, 'admin', '管理员', '2023-11-11 21:44:14');
INSERT INTO `auth_log` VALUES (381, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2023-11-11 21:48:14');
INSERT INTO `auth_log` VALUES (382, '更新资源信息', 2, 1, 61, 'admin', '管理员', '2023-11-11 21:49:09');
INSERT INTO `auth_log` VALUES (383, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:50:05');
INSERT INTO `auth_log` VALUES (384, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:50:07');
INSERT INTO `auth_log` VALUES (385, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:50:09');
INSERT INTO `auth_log` VALUES (386, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:50:10');
INSERT INTO `auth_log` VALUES (387, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:50:12');
INSERT INTO `auth_log` VALUES (388, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:50:13');
INSERT INTO `auth_log` VALUES (389, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:50:15');
INSERT INTO `auth_log` VALUES (390, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:50:18');
INSERT INTO `auth_log` VALUES (391, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:50:20');
INSERT INTO `auth_log` VALUES (392, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:50:22');
INSERT INTO `auth_log` VALUES (393, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:50:23');
INSERT INTO `auth_log` VALUES (394, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:50:26');
INSERT INTO `auth_log` VALUES (395, '更新资源信息', 2, 1, 61, 'admin', '管理员', '2023-11-11 21:50:33');
INSERT INTO `auth_log` VALUES (396, '更新资源信息', 2, 1, 61, 'admin', '管理员', '2023-11-11 21:50:35');
INSERT INTO `auth_log` VALUES (397, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:53:08');
INSERT INTO `auth_log` VALUES (398, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:53:10');
INSERT INTO `auth_log` VALUES (399, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-11 21:53:12');
INSERT INTO `auth_log` VALUES (400, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-11-12 19:30:04');
INSERT INTO `auth_log` VALUES (401, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-12 19:30:09');
INSERT INTO `auth_log` VALUES (402, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-12 19:30:11');
INSERT INTO `auth_log` VALUES (403, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-12 19:30:14');
INSERT INTO `auth_log` VALUES (404, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-12 19:30:36');
INSERT INTO `auth_log` VALUES (405, '更新资源信息', 2, 1, 61, 'admin', '管理员', '2023-11-12 19:30:45');
INSERT INTO `auth_log` VALUES (406, '角色分配功能', 1, 2, 61, 'admin', '管理员', '2023-11-12 19:31:00');
INSERT INTO `auth_log` VALUES (407, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-12 19:32:32');
INSERT INTO `auth_log` VALUES (408, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-12 19:32:34');
INSERT INTO `auth_log` VALUES (409, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-12 19:32:36');
INSERT INTO `auth_log` VALUES (410, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-12 19:32:38');
INSERT INTO `auth_log` VALUES (411, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-12 19:32:39');
INSERT INTO `auth_log` VALUES (412, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-12 19:32:41');
INSERT INTO `auth_log` VALUES (413, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-12 19:49:14');
INSERT INTO `auth_log` VALUES (414, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-12 19:49:15');
INSERT INTO `auth_log` VALUES (415, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-12 19:49:18');
INSERT INTO `auth_log` VALUES (416, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-12 19:49:20');
INSERT INTO `auth_log` VALUES (417, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-12 20:10:03');
INSERT INTO `auth_log` VALUES (418, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-12 20:10:05');
INSERT INTO `auth_log` VALUES (419, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-12 20:10:07');
INSERT INTO `auth_log` VALUES (420, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-12 20:10:08');
INSERT INTO `auth_log` VALUES (421, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-12 20:10:12');
INSERT INTO `auth_log` VALUES (422, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-12 20:10:14');
INSERT INTO `auth_log` VALUES (423, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-12 20:10:15');
INSERT INTO `auth_log` VALUES (424, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-12 20:10:17');
INSERT INTO `auth_log` VALUES (425, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-12 20:10:18');
INSERT INTO `auth_log` VALUES (426, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-12 20:10:20');
INSERT INTO `auth_log` VALUES (427, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-11-18 09:57:34');
INSERT INTO `auth_log` VALUES (428, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-18 10:08:22');
INSERT INTO `auth_log` VALUES (429, '更新资源信息', 2, 1, 61, 'admin', '管理员', '2023-11-18 10:28:51');
INSERT INTO `auth_log` VALUES (430, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2023-11-18 14:11:55');
INSERT INTO `auth_log` VALUES (431, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-18 14:17:02');
INSERT INTO `auth_log` VALUES (432, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-18 14:17:05');
INSERT INTO `auth_log` VALUES (433, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-18 14:17:07');
INSERT INTO `auth_log` VALUES (434, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2023-11-18 14:17:09');
INSERT INTO `auth_log` VALUES (435, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2024-05-17 21:00:33');
INSERT INTO `auth_log` VALUES (436, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2024-05-17 21:00:41');
INSERT INTO `auth_log` VALUES (437, '更新资源信息', 2, 1, 61, 'admin', '管理员', '2024-05-17 21:00:48');
INSERT INTO `auth_log` VALUES (438, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2024-05-17 21:01:26');
INSERT INTO `auth_log` VALUES (439, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2024-06-16 20:51:14');
INSERT INTO `auth_log` VALUES (440, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2024-06-16 20:51:25');
INSERT INTO `auth_log` VALUES (441, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2024-06-16 20:51:29');
INSERT INTO `auth_log` VALUES (442, '更新资源信息', 2, 1, 61, 'admin', '管理员', '2024-06-16 20:51:38');
INSERT INTO `auth_log` VALUES (443, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2024-06-17 21:46:12');
INSERT INTO `auth_log` VALUES (444, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2024-06-17 21:46:18');
INSERT INTO `auth_log` VALUES (445, '功能解绑资源', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:48:25');
INSERT INTO `auth_log` VALUES (446, '功能解绑资源', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:48:27');
INSERT INTO `auth_log` VALUES (447, '功能解绑资源', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:48:32');
INSERT INTO `auth_log` VALUES (448, '功能解绑资源', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:48:34');
INSERT INTO `auth_log` VALUES (449, '功能解绑资源', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:48:38');
INSERT INTO `auth_log` VALUES (450, '功能解绑资源', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:48:41');
INSERT INTO `auth_log` VALUES (451, '功能解绑资源', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:48:48');
INSERT INTO `auth_log` VALUES (452, '功能解绑资源', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:48:52');
INSERT INTO `auth_log` VALUES (453, '功能解绑资源', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:48:57');
INSERT INTO `auth_log` VALUES (454, '功能解绑用户', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:48:59');
INSERT INTO `auth_log` VALUES (455, '功能解绑用户', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:49:12');
INSERT INTO `auth_log` VALUES (456, '删除功能信息', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:49:14');
INSERT INTO `auth_log` VALUES (457, '删除功能信息', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:49:16');
INSERT INTO `auth_log` VALUES (458, '功能解绑用户', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:49:20');
INSERT INTO `auth_log` VALUES (459, '删除功能信息', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:49:22');
INSERT INTO `auth_log` VALUES (460, '功能解绑用户', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:49:26');
INSERT INTO `auth_log` VALUES (461, '删除功能信息', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:49:27');
INSERT INTO `auth_log` VALUES (462, '功能解绑资源', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:49:43');
INSERT INTO `auth_log` VALUES (463, '功能解绑用户', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:49:45');
INSERT INTO `auth_log` VALUES (464, '删除功能信息', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:49:48');
INSERT INTO `auth_log` VALUES (465, '功能解绑资源', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:49:52');
INSERT INTO `auth_log` VALUES (466, '功能解绑用户', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:49:54');
INSERT INTO `auth_log` VALUES (467, '功能解绑资源', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:49:56');
INSERT INTO `auth_log` VALUES (468, '功能解绑用户', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:49:58');
INSERT INTO `auth_log` VALUES (469, '删除功能信息', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:50:01');
INSERT INTO `auth_log` VALUES (470, '删除功能信息', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:50:03');
INSERT INTO `auth_log` VALUES (471, '功能解绑用户', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:50:08');
INSERT INTO `auth_log` VALUES (472, '删除功能信息', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:50:10');
INSERT INTO `auth_log` VALUES (473, '功能解绑用户', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:50:13');
INSERT INTO `auth_log` VALUES (474, '删除功能信息', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:50:16');
INSERT INTO `auth_log` VALUES (475, '功能解绑用户', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:50:20');
INSERT INTO `auth_log` VALUES (476, '功能解绑资源', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:50:22');
INSERT INTO `auth_log` VALUES (477, '删除功能信息', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:50:29');
INSERT INTO `auth_log` VALUES (478, '删除功能信息', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:50:31');
INSERT INTO `auth_log` VALUES (479, '功能解绑用户', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:50:40');
INSERT INTO `auth_log` VALUES (480, '删除功能信息', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:50:47');
INSERT INTO `auth_log` VALUES (481, '功能解绑用户', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:50:52');
INSERT INTO `auth_log` VALUES (482, '删除功能信息', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:50:57');
INSERT INTO `auth_log` VALUES (483, '功能解绑资源', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:51:21');
INSERT INTO `auth_log` VALUES (484, '功能解绑用户', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:52:21');
INSERT INTO `auth_log` VALUES (485, '删除功能信息', 3, 2, 61, 'admin', '管理员', '2024-06-17 21:52:24');
INSERT INTO `auth_log` VALUES (486, '删除资源信息', 2, 2, 61, 'admin', '管理员', '2024-06-17 21:52:56');
INSERT INTO `auth_log` VALUES (487, '删除资源信息', 2, 2, 61, 'admin', '管理员', '2024-06-17 21:52:57');
INSERT INTO `auth_log` VALUES (488, '删除资源信息', 2, 2, 61, 'admin', '管理员', '2024-06-17 21:52:58');
INSERT INTO `auth_log` VALUES (489, '删除资源信息', 2, 2, 61, 'admin', '管理员', '2024-06-17 21:53:00');
INSERT INTO `auth_log` VALUES (490, '删除资源信息', 2, 2, 61, 'admin', '管理员', '2024-06-17 21:53:01');
INSERT INTO `auth_log` VALUES (491, '删除资源信息', 2, 2, 61, 'admin', '管理员', '2024-06-17 21:53:02');
INSERT INTO `auth_log` VALUES (492, '更新用户信息', 0, 1, 61, 'admin', '管理员', '2024-06-17 21:54:47');
INSERT INTO `auth_log` VALUES (493, '更新角色信息', 1, 1, 61, 'admin', '管理员', '2024-06-17 21:54:58');
INSERT INTO `auth_log` VALUES (494, '用户分配角色', 0, 2, 61, 'admin', '管理员', '2024-06-17 21:59:36');
INSERT INTO `auth_log` VALUES (495, '用户分配角色', 0, 2, 61, 'admin', '管理员', '2024-06-17 21:59:41');
INSERT INTO `auth_log` VALUES (496, '禁用用户账号', 0, 0, 61, 'admin', '管理员', '2024-06-17 21:59:48');
INSERT INTO `auth_log` VALUES (497, '禁用用户账号', 0, 0, 61, 'admin', '管理员', '2024-06-17 21:59:50');
INSERT INTO `auth_log` VALUES (498, '删除用户信息', 0, 2, 61, 'admin', '管理员', '2024-06-17 21:59:52');
INSERT INTO `auth_log` VALUES (499, '删除用户信息', 0, 2, 61, 'admin', '管理员', '2024-06-17 21:59:54');
INSERT INTO `auth_log` VALUES (500, '新增用户信息', 0, 1, 61, 'admin', '管理员', '2024-06-17 22:00:14');
INSERT INTO `auth_log` VALUES (501, '删除角色信息', 1, 2, 61, 'admin', '管理员', '2024-06-17 22:00:23');
INSERT INTO `auth_log` VALUES (502, '删除角色信息', 1, 2, 61, 'admin', '管理员', '2024-06-17 22:00:24');
INSERT INTO `auth_log` VALUES (503, '新增角色信息', 1, 1, 61, 'admin', '管理员', '2024-06-17 22:00:51');
INSERT INTO `auth_log` VALUES (504, '角色分配功能', 1, 2, 61, 'admin', '管理员', '2024-06-17 22:01:07');
INSERT INTO `auth_log` VALUES (505, '用户注销登录', 0, 0, 61, 'admin', '管理员', '2024-06-17 22:01:12');
INSERT INTO `auth_log` VALUES (506, '用户登录系统', 0, 0, 83, 'user', '普通用户', '2024-06-17 22:01:15');
INSERT INTO `auth_log` VALUES (507, '用户注销登录', 0, 0, 83, 'user', '普通用户', '2024-06-17 22:01:24');
INSERT INTO `auth_log` VALUES (508, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2024-06-17 22:01:25');
INSERT INTO `auth_log` VALUES (509, '用户分配角色', 0, 2, 61, 'admin', '管理员', '2024-06-17 22:01:46');
INSERT INTO `auth_log` VALUES (510, '用户注销登录', 0, 0, 61, 'admin', '管理员', '2024-06-17 22:01:55');
INSERT INTO `auth_log` VALUES (511, '用户登录系统', 0, 0, 83, 'user', '普通用户', '2024-06-17 22:01:58');
INSERT INTO `auth_log` VALUES (512, '用户注销登录', 0, 0, 83, 'user', '普通用户', '2024-06-17 22:03:40');
INSERT INTO `auth_log` VALUES (513, '用户登录系统', 0, 0, 61, 'admin', '管理员', '2024-06-17 22:03:41');
INSERT INTO `auth_log` VALUES (514, '更新资源信息', 2, 1, 61, 'admin', '管理员', '2024-06-17 22:04:07');
INSERT INTO `auth_log` VALUES (515, '更新功能信息', 3, 1, 61, 'admin', '管理员', '2024-06-17 22:07:11');

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
) ENGINE = InnoDB AUTO_INCREMENT = 80 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `auth_resc` VALUES (35, '角色分配用户初始化', '/auth/role/user/init', 'auth', 'get', '2022-03-26 21:07:44', '2023-07-11 23:50:51');
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
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 16820 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 84 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_user
-- ----------------------------
INSERT INTO `auth_user` VALUES (61, 'admin', '管理员', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'yfb', 1, '2022-05-01 23:27:25', '2023-11-18 14:17:07');
INSERT INTO `auth_user` VALUES (83, 'user', '普通用户', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'yfb', 1, '2024-06-17 22:00:14', '2024-06-17 22:00:14');

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
) ENGINE = InnoDB AUTO_INCREMENT = 961 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
  PRIMARY KEY (`callback_id`) USING BTREE,
  INDEX `ix_create_time`(`create_time`) USING BTREE,
  INDEX `ix_send_time`(`send_time`) USING BTREE,
  INDEX `ix_status`(`status`) USING BTREE,
  INDEX `msg_id`(`msg_id`) USING BTREE,
  INDEX `ix_uq_uuid`(`uuid`) USING BTREE,
  FULLTEXT INDEX `ix_msg_content`(`msg_content`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 97 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pub_mq_message
-- ----------------------------
INSERT INTO `pub_mq_message` VALUES (96, '98bbb06eff22405081eadb1e61d28f67', 'ID:DESKTOP-JEORO4F-58861-1715950805196-6:2:1:1:1', '{\"plan\":\"update_admin_club_user\",\"tables\":[{\"code\":\"update_admin_club_user\",\"unique\":[\"82\"]}],\"createTime\":\"2024-05-17 21:00:40\",\"src\":\"admin\",\"msgId\":\"98bbb06eff22405081eadb1e61d28f67\"}', 'admin', 'club', '2024-05-17 21:00:40', '2024-05-17 21:00:41', NULL, 'success', 0, 'wait');

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pub_queue
-- ----------------------------

-- ----------------------------
-- Table structure for sync_activemq_acks
-- ----------------------------
DROP TABLE IF EXISTS `sync_activemq_acks`;
CREATE TABLE `sync_activemq_acks`  (
  `CONTAINER` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `SUB_DEST` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `CLIENT_ID` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `SUB_NAME` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `SELECTOR` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `LAST_ACKED_ID` bigint(20) NULL DEFAULT NULL,
  `PRIORITY` bigint(20) NOT NULL DEFAULT 5,
  `XID` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`CONTAINER`, `CLIENT_ID`, `SUB_NAME`, `PRIORITY`) USING BTREE,
  INDEX `sync_ACTIVEMQ_ACKS_XIDX`(`XID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
  `BROKER_NAME` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sync_activemq_lock
-- ----------------------------
INSERT INTO `sync_activemq_lock` VALUES (1, NULL, NULL);

-- ----------------------------
-- Table structure for sync_activemq_msgs
-- ----------------------------
DROP TABLE IF EXISTS `sync_activemq_msgs`;
CREATE TABLE `sync_activemq_msgs`  (
  `ID` bigint(20) NOT NULL,
  `CONTAINER` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `MSGID_PROD` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `MSGID_SEQ` bigint(20) NULL DEFAULT NULL,
  `EXPIRATION` bigint(20) NULL DEFAULT NULL,
  `MSG` blob NULL,
  `PRIORITY` bigint(20) NULL DEFAULT NULL,
  `XID` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `sync_ACTIVEMQ_MSGS_MIDX`(`MSGID_PROD`, `MSGID_SEQ`) USING BTREE,
  INDEX `sync_ACTIVEMQ_MSGS_CIDX`(`CONTAINER`) USING BTREE,
  INDEX `sync_ACTIVEMQ_MSGS_EIDX`(`EXPIRATION`) USING BTREE,
  INDEX `sync_ACTIVEMQ_MSGS_PIDX`(`PRIORITY`) USING BTREE,
  INDEX `sync_ACTIVEMQ_MSGS_XIDX`(`XID`) USING BTREE,
  INDEX `sync_ACTIVEMQ_MSGS_IIDX`(`ID`, `XID`, `CONTAINER`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sync_activemq_msgs
-- ----------------------------

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sync_plan
-- ----------------------------
INSERT INTO `sync_plan` VALUES ('07b86051cacc46e78a2a6b60475fff8e', 'delete_admin_club_resc', '删除系统资源', 'admin_dev', 'club_dev', 'admin', 'club', '<sync_plan>\n    <code>delete_admin_club_resc</code>\n    <name>删除系统资源</name>\n    <database src=\"admin_dev\" dest=\"club_dev\" ></database>\n    <project src=\"admin\" dest=\"club\" ></project>\n    <table code=\"delete_admin_club_resc\" src=\"auth_resc\" dest=\"club_resc\" type=\"delete\">\n        <unique src=\"resc_id\" dest=\"resc_id\"  ></unique>\n        <relate src=\"resc_id\" dest=\"resc_id\" ></relate>\n    </table>\n</sync_plan>', 'normal', '2023-09-03 19:52:56', '2024-05-17 21:01:01');
INSERT INTO `sync_plan` VALUES ('0bde11510a5d4c8b87b8c1ff0ffed307', 'update_admin_club_user', '更新系统用户', 'admin_dev', 'club_dev', 'admin', 'club', '<sync_plan>\n      <callbacks>\n          <callback dest=\"club\" desc=\"更新用户回调\"></callback>\n    </callbacks>\n    <code>update_admin_club_user</code>\n    <name>更新系统用户</name>\n    <database src=\"admin_dev\" dest=\"club_dev\" ></database>\n    <project src=\"admin\" dest=\"club\" ></project>\n    <table code=\"update_admin_club_user\" src=\"auth_user\" dest=\"club_user\" type=\"update\">\n        <unique src=\"user_id\" dest=\"user_id\"  ></unique>\n        <relate src=\"user_id\" dest=\"user_id\" ></relate>\n        <column src=\"user_id\" dest=\"user_id\" />\n        <column src=\"username\" dest=\"username\" />\n        <column src=\"real_name\" dest=\"real_name\" />\n        <column src=\"password\" dest=\"password\" />\n        <column src=\"dept_code\" dest=\"dept_code\" />\n        <column src=\"user_status\" dest=\"user_status\" />\n        <column src=\"create_time\" dest=\"create_time\" />\n        <column src=\"update_time\" dest=\"update_time\" />\n    </table>\n    <table code=\"update_auth_demo_user_status\" src=\"auth_user\" dest=\"auth_user_sync\" type=\"update\">\n        <unique src=\"user_id\" dest=\"user_id\"  ></unique>\n        <relate src=\"user_id\" dest=\"user_id\" ></relate>\n        <column src=\"user_status\" dest=\"user_status\" />\n         <column src=\"update_time\" dest=\"update_time\" />\n    </table>\n    <table code=\"update_auth_demo_user_pwd\" src=\"auth_user\" dest=\"auth_user_sync\" type=\"update\">\n        <unique src=\"user_id\" dest=\"user_id\"  ></unique>\n        <relate src=\"user_id\" dest=\"user_id\" ></relate>\n        <column src=\"password\" dest=\"password\" />\n    </table>\n</sync_plan>', 'normal', '2023-07-22 15:59:29', '2023-11-11 21:42:13');
INSERT INTO `sync_plan` VALUES ('0cfc9f66e8d44ee3b5bac326b84a0d1a', 'delete_admin_club_role', '删除系统角色', 'admin_dev', 'club_dev', 'admin', 'club', '<sync_plan>\n    <code>delete_admin_club_role</code>\n    <name>删除系统角色</name>\n    <database src=\"admin_dev\" dest=\"club_dev\" ></database>\n    <project src=\"admin\" dest=\"club\" ></project>\n    <table code=\"delete_admin_club_role\" src=\"auth_role\" dest=\"club_role\" type=\"delete\">\n        <unique src=\"role_id\" dest=\"role_id\"  ></unique>\n        <relate src=\"role_id\" dest=\"role_id\" ></relate>\n    </table>\n</sync_plan>', 'normal', '2023-07-23 11:06:22', '2023-11-11 21:00:30');
INSERT INTO `sync_plan` VALUES ('1075e58f891d4030a4464269efb864d2', 'delete_admin_club_user', '删除系统用户', 'admin_dev', 'club_dev', 'admin', 'club', '<sync_plan>\n    <code>delete_admin_club_user</code>\n    <name>删除系统用户</name>\n    <database src=\"admin_dev\" dest=\"club_dev\" ></database>\n    <project src=\"admin\" dest=\"club\" ></project>\n    <table code=\"delete_admin_club_user\" src=\"auth_user\" dest=\"club_user\" type=\"delete\">\n        <unique src=\"user_id\" dest=\"user_id\"  ></unique>\n        <relate src=\"user_id\" dest=\"user_id\" ></relate>\n    </table>\n</sync_plan>', 'normal', '2023-07-22 16:20:10', '2023-11-11 21:01:39');
INSERT INTO `sync_plan` VALUES ('26ff4a2791664dcd9654f633f56f93e7', 'insert_admin_club_user', '新增系统用户', 'admin_dev', 'club_dev', 'admin', 'club', '<sync_plan>\n    <code>insert_admin_club_user</code>\n    <name>新增系统用户</name>\n    <database src=\"admin_dev\" dest=\"club_dev\" ></database>\n    <project src=\"admin\" dest=\"club\" ></project>\n    <table code=\"insert_admin_club_user\" src=\"auth_user\" dest=\"club_user\" type=\"insert\">\n        <unique src=\"user_id\" dest=\"user_id\"  ></unique>\n        <relate src=\"user_id\" dest=\"user_id\" ></relate>\n        <column src=\"user_id\" dest=\"user_id\" />\n        <column src=\"username\" dest=\"username\" />\n	<column src=\"real_name\" dest=\"real_name\" />\n        <column src=\"password\" dest=\"password\" />\n	<column src=\"dept_code\" dest=\"dept_code\" />\n	<column src=\"user_status\" dest=\"user_status\" />\n	<column src=\"create_time\" dest=\"create_time\" />\n        <column src=\"update_time\" dest=\"update_time\" />\n    </table>\n</sync_plan>', 'normal', '2023-07-22 15:50:24', '2023-11-11 21:02:51');
INSERT INTO `sync_plan` VALUES ('2aae46089860446685191dac3c33d488', 'update_admin_club_resc', '更新系统资源', 'admin_dev', 'club_dev', 'admin', 'club', '<sync_plan>\n    <code>update_admin_club_resc</code>\n    <name>更新系统资源</name>\n    <database src=\"admin_dev\" dest=\"club_dev\" ></database>\n    <project src=\"admin\" dest=\"club\" ></project>\n    <table code=\"update_admin_club_resc\" src=\"auth_resc\" dest=\"club_resc\" type=\"update\">\n        <unique src=\"resc_id\" dest=\"resc_id\"  ></unique>\n        <relate src=\"resc_id\" dest=\"resc_id\" ></relate>\n        <column src=\"resc_id\" dest=\"resc_id\" />\n        <column src=\"resc_name\" dest=\"resc_name\" />\n        <column src=\"resc_url\" dest=\"resc_url\" />\n	<column src=\"resc_domain\" dest=\"resc_domain\" />\n        <column src=\"method_type\" dest=\"method_type\" />\n	<column src=\"create_time\" dest=\"create_time\" />\n        <column src=\"update_time\" dest=\"update_time\" />\n    </table>\n</sync_plan>', 'normal', '2023-09-03 19:52:09', '2023-11-11 20:56:23');
INSERT INTO `sync_plan` VALUES ('59dccfa463c140ccb0f9a78c9015e2d7', 'insert_admin_club_role', '新增系统角色', 'admin_dev', 'club_dev', 'admin', 'club', '<sync_plan>\n    <code>insert_admin_club_role</code>\n    <name>新增系统角色</name>\n    <database src=\"admin_dev\" dest=\"club_dev\" ></database>\n    <project src=\"admin\" dest=\"club\" ></project>\n    <table code=\"insert_admin_club_role\" src=\"auth_role\" dest=\"club_role\" type=\"insert\">\n        <unique src=\"role_id\" dest=\"role_id\"  ></unique>\n        <relate src=\"role_id\" dest=\"role_id\" ></relate>\n        <column src=\"role_id\" dest=\"role_id\" />\n        <column src=\"role_name\" dest=\"role_name\" />\n        <column src=\"role_desc\" dest=\"role_desc\" />\n	<column src=\"create_time\" dest=\"create_time\" />\n        <column src=\"update_time\" dest=\"update_time\" />\n    </table>\n</sync_plan>', 'normal', '2023-07-23 11:04:58', '2023-11-11 21:01:09');
INSERT INTO `sync_plan` VALUES ('973b88e89f794c9194136dc6bc327190', 'insert_admin_club_resc', '新增系统资源', 'admin_dev', 'club_dev', 'admin', 'club', '<sync_plan>\n    <code>insert_admin_club_resc</code>\n    <name>新增系统资源</name>\n    <database src=\"admin_dev\" dest=\"club_dev\" ></database>\n    <project src=\"admin\" dest=\"club\" ></project>\n    <table code=\"insert_admin_club_resc\" src=\"auth_resc\" dest=\"club_resc\" type=\"insert\">\n        <unique src=\"resc_id\" dest=\"resc_id\"  ></unique>\n        <relate src=\"resc_id\" dest=\"resc_id\" ></relate>\n        <column src=\"resc_id\" dest=\"resc_id\" />\n        <column src=\"resc_name\" dest=\"resc_name\" />\n        <column src=\"resc_url\" dest=\"resc_url\" />\n	<column src=\"resc_domain\" dest=\"resc_domain\" />\n        <column src=\"method_type\" dest=\"method_type\" />\n	<column src=\"create_time\" dest=\"create_time\" />\n        <column src=\"update_time\" dest=\"update_time\" />\n    </table>\n</sync_plan>', 'normal', '2023-09-03 19:38:40', '2023-11-11 21:00:16');
INSERT INTO `sync_plan` VALUES ('e58818eb1fb5454b8a943bc6b6d9dc06', 'update_admin_club_role', '修改系统角色', 'admin_dev', 'club_dev', 'admin', 'club', '<sync_plan>\n    <code>update_admin_club_role</code>\n    <name>修改系统角色</name>\n    <database src=\"admin_dev\" dest=\"club_dev\" ></database>\n    <project src=\"admin\" dest=\"club\" ></project>\n    <table code=\"update_admin_club_role\" src=\"auth_role\" dest=\"club_role\" type=\"update\">\n        <unique src=\"role_id\" dest=\"role_id\"  ></unique>\n        <relate src=\"role_id\" dest=\"role_id\" ></relate>\n        <column src=\"role_name\" dest=\"role_name\" />\n        <column src=\"role_desc\" dest=\"role_desc\" />\n	<column src=\"create_time\" dest=\"create_time\" />\n        <column src=\"update_time\" dest=\"update_time\" />\n    </table>\n</sync_plan>\n', 'normal', '2023-07-23 11:05:38', '2023-11-11 21:00:08');

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sync_result
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
