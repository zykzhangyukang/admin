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
-- Records of auth_role
-- ----------------------------
INSERT INTO `auth_role` VALUES (42, '管理员角色', '管理员角色', '2022-04-16 11:38:56', '2023-11-11 21:43:09');
INSERT INTO `auth_role` VALUES (51, '普通用户角色', '基础权限', '2024-06-17 22:00:51', '2024-06-17 22:00:51');

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
-- Records of auth_user
-- ----------------------------
INSERT INTO `auth_user` VALUES (61, 'admin', '管理员', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'yfb', 1, '2022-05-01 23:27:25', '2023-11-18 14:17:07');
INSERT INTO `auth_user` VALUES (83, 'user', '普通用户', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'yfb', 1, '2024-06-17 22:00:14', '2024-06-17 22:00:14');



-- ----------------------------
-- Records of auth_user_role
-- ----------------------------
INSERT INTO `auth_user_role` VALUES (955, 61, 42);
INSERT INTO `auth_user_role` VALUES (960, 83, 51);


-- ----------------------------
-- Records of sync_plan
-- ----------------------------
INSERT INTO `sync_plan` (`uuid`, `plan_code`, `description`, `src_db`, `dest_db`, `src_project`, `dest_project`, `plan_content`, `status`, `create_time`, `update_time`) VALUES ('074e8e9a33b140109d4ff98d5b21b0c1', 'insert_admin_sync_log', '新增系统日志', 'admin_dev', 'admin_dev_sync', 'admin', 'log', '<sync_plan>\n    <code>insert_admin_sync_log</code>\n    <name>新增系统日志</name>\n    <database src=\"admin_dev\" dest=\"admin_dev_sync\" ></database>\n    <project src=\"admin\" dest=\"log\" ></project>\n    <table code=\"insert_admin_sync_log\" src=\"auth_log\" dest=\"sync_auth_log\" type=\"insert\">\n			<unique src=\"log_id\" dest=\"log_id\"  ></unique>\n			<relate src=\"log_id\" dest=\"log_id\" ></relate>\n			<column src=\"log_id\" dest=\"log_id\" />\n			<column src=\"log_info\" dest=\"log_info\" />\n			<column src=\"log_module\" dest=\"log_module\" />\n			<column src=\"log_level\" dest=\"log_level\" />\n			<column src=\"ip_address\" dest=\"ip_address\" />\n			<column src=\"device_info\" dest=\"device_info\" />\n			<column src=\"location\" dest=\"location\" />\n			<column src=\"user_id\" dest=\"user_id\" />\n			<column src=\"username\" dest=\"username\" />\n			<column src=\"real_name\" dest=\"real_name\" />\n			<column src=\"create_time\" dest=\"create_time\" />\n    </table>\n</sync_plan>\n', 'normal', '2024-08-05 11:43:21', '2024-08-05 15:48:56');
INSERT INTO `sync_plan` (`uuid`, `plan_code`, `description`, `src_db`, `dest_db`, `src_project`, `dest_project`, `plan_content`, `status`, `create_time`, `update_time`) VALUES ('07b86051cacc46e78a2a6b60475fff8e', 'delete_admin_sync_resc', '删除系统资源', 'admin_dev', 'admin_dev_sync', 'admin', 'sync', '<sync_plan>\n    <code>delete_admin_sync_resc</code>\n    <name>删除系统资源</name>\n    <database src=\"admin_dev\" dest=\"admin_dev_sync\" ></database>\n    <project src=\"admin\" dest=\"sync\" ></project>\n    <table code=\"delete_admin_sync_resc\" src=\"auth_resc\" dest=\"sync_auth_resc\" type=\"delete\">\n        <unique src=\"resc_id\" dest=\"resc_id\"  ></unique>\n        <relate src=\"resc_id\" dest=\"resc_id\" ></relate>\n    </table>\n</sync_plan>', 'normal', '2023-09-03 19:52:56', '2024-08-04 21:20:32');
INSERT INTO `sync_plan` (`uuid`, `plan_code`, `description`, `src_db`, `dest_db`, `src_project`, `dest_project`, `plan_content`, `status`, `create_time`, `update_time`) VALUES ('0bde11510a5d4c8b87b8c1ff0ffed307', 'update_admin_sync_user', '更新系统用户', 'admin_dev', 'admin_dev_sync', 'admin', 'sync', '<sync_plan>\n      <callbacks>\n          <callback dest=\"sync\" desc=\"更新用户回调\"></callback>\n    </callbacks>\n    <code>update_admin_sync_user</code>\n    <name>更新系统用户</name>\n    <database src=\"admin_dev\" dest=\"admin_dev_sync\" ></database>\n    <project src=\"admin\" dest=\"sync\" ></project>\n    <table code=\"update_admin_sync_user\" src=\"auth_user\" dest=\"sync_auth_user\" type=\"update\">\n        <unique src=\"user_id\" dest=\"user_id\"  ></unique>\n        <relate src=\"user_id\" dest=\"user_id\" ></relate>\n        <column src=\"user_id\" dest=\"user_id\" />\n        <column src=\"username\" dest=\"username\" />\n        <column src=\"real_name\" dest=\"real_name\" />\n        <column src=\"password\" dest=\"password\" />\n        <column src=\"dept_code\" dest=\"dept_code\" />\n        <column src=\"user_status\" dest=\"user_status\" />\n        <column src=\"create_time\" dest=\"create_time\" />\n        <column src=\"update_time\" dest=\"update_time\" />\n    </table>\n    <table code=\"update_admin_sync_user_status\" src=\"auth_user\" dest=\"sync_auth_user\" type=\"update\">\n        <unique src=\"user_id\" dest=\"user_id\"  ></unique>\n        <relate src=\"user_id\" dest=\"user_id\" ></relate>\n        <column src=\"user_status\" dest=\"user_status\" />\n         <column src=\"update_time\" dest=\"update_time\" />\n    </table>\n    <table code=\"update_admin_sync_user_pwd\" src=\"auth_user\" dest=\"sync_auth_user\" type=\"update\">\n        <unique src=\"user_id\" dest=\"user_id\"  ></unique>\n        <relate src=\"user_id\" dest=\"user_id\" ></relate>\n        <column src=\"password\" dest=\"password\" />\n    </table>\n</sync_plan>', 'normal', '2023-07-22 15:59:29', '2024-08-04 21:29:39');
INSERT INTO `sync_plan` (`uuid`, `plan_code`, `description`, `src_db`, `dest_db`, `src_project`, `dest_project`, `plan_content`, `status`, `create_time`, `update_time`) VALUES ('0cfc9f66e8d44ee3b5bac326b84a0d1a', 'delete_admin_sync_role', '删除系统角色', 'admin_dev', 'admin_dev_sync', 'admin', 'sync', '<sync_plan>\n    <code>delete_admin_sync_role</code>\n    <name>删除系统角色</name>\n    <database src=\"admin_dev\" dest=\"admin_dev_sync\" ></database>\n    <project src=\"admin\" dest=\"sync\" ></project>\n    <table code=\"delete_admin_sync_role\" src=\"auth_role\" dest=\"sync_auth_role\" type=\"delete\">\n        <unique src=\"role_id\" dest=\"role_id\"  ></unique>\n        <relate src=\"role_id\" dest=\"role_id\" ></relate>\n    </table>\n</sync_plan>', 'normal', '2023-07-23 11:06:22', '2024-08-04 21:25:29');
INSERT INTO `sync_plan` (`uuid`, `plan_code`, `description`, `src_db`, `dest_db`, `src_project`, `dest_project`, `plan_content`, `status`, `create_time`, `update_time`) VALUES ('1075e58f891d4030a4464269efb864d2', 'delete_admin_sync_user', '删除系统用户', 'admin_dev', 'admin_dev_sync', 'admin', 'sync', '<sync_plan>\n    <code>delete_admin_sync_user</code>\n    <name>删除系统用户</name>\n    <database src=\"admin_dev\" dest=\"admin_dev_sync\" ></database>\n    <project src=\"admin\" dest=\"sync\" ></project>\n    <table code=\"delete_admin_sync_user\" src=\"auth_user\" dest=\"sync_auth_user\" type=\"delete\">\n        <unique src=\"user_id\" dest=\"user_id\"  ></unique>\n        <relate src=\"user_id\" dest=\"user_id\" ></relate>\n    </table>\n</sync_plan>', 'normal', '2023-07-22 16:20:10', '2024-08-04 21:27:10');
INSERT INTO `sync_plan` (`uuid`, `plan_code`, `description`, `src_db`, `dest_db`, `src_project`, `dest_project`, `plan_content`, `status`, `create_time`, `update_time`) VALUES ('26ff4a2791664dcd9654f633f56f93e7', 'insert_admin_sync_user', '新增系统用户', 'admin_dev', 'admin_dev_sync', 'admin', 'sync', '<sync_plan>\n    <code>insert_admin_sync_user</code>\n    <name>新增系统用户</name>\n    <database src=\"admin_dev\" dest=\"admin_dev_sync\" ></database>\n    <project src=\"admin\" dest=\"sync\" ></project>\n    <table code=\"insert_admin_sync_user\" src=\"auth_user\" dest=\"sync_auth_user\" type=\"insert\">\n        <unique src=\"user_id\" dest=\"user_id\"  ></unique>\n        <relate src=\"user_id\" dest=\"user_id\" ></relate>\n        <column src=\"user_id\" dest=\"user_id\" />\n        <column src=\"username\" dest=\"username\" />\n	<column src=\"real_name\" dest=\"real_name\" />\n        <column src=\"password\" dest=\"password\" />\n	<column src=\"dept_code\" dest=\"dept_code\" />\n	<column src=\"user_status\" dest=\"user_status\" />\n	<column src=\"create_time\" dest=\"create_time\" />\n        <column src=\"update_time\" dest=\"update_time\" />\n    </table>\n</sync_plan>', 'normal', '2023-07-22 15:50:24', '2024-08-04 21:30:14');
INSERT INTO `sync_plan` (`uuid`, `plan_code`, `description`, `src_db`, `dest_db`, `src_project`, `dest_project`, `plan_content`, `status`, `create_time`, `update_time`) VALUES ('2aae46089860446685191dac3c33d488', 'update_admin_sync_resc', '更新系统资源', 'admin_dev', 'admin_dev_sync', 'admin', 'sync', '<sync_plan>\n    <code>update_admin_sync_resc</code>\n    <name>更新系统资源</name>\n    <database src=\"admin_dev\" dest=\"admin_dev_sync\" ></database>\n    <project src=\"admin\" dest=\"sync\" ></project>\n    <table code=\"update_admin_sync_resc\" src=\"auth_resc\" dest=\"sync_auth_resc\" type=\"update\">\n        <unique src=\"resc_id\" dest=\"resc_id\"  ></unique>\n        <relate src=\"resc_id\" dest=\"resc_id\" ></relate>\n        <column src=\"resc_id\" dest=\"resc_id\" />\n        <column src=\"resc_name\" dest=\"resc_name\" />\n        <column src=\"resc_url\" dest=\"resc_url\" />\n	<column src=\"resc_domain\" dest=\"resc_domain\" />\n        <column src=\"method_type\" dest=\"method_type\" />\n	<column src=\"create_time\" dest=\"create_time\" />\n        <column src=\"update_time\" dest=\"update_time\" />\n    </table>\n</sync_plan>', 'normal', '2023-09-03 19:52:09', '2024-08-04 21:24:27');
INSERT INTO `sync_plan` (`uuid`, `plan_code`, `description`, `src_db`, `dest_db`, `src_project`, `dest_project`, `plan_content`, `status`, `create_time`, `update_time`) VALUES ('59dccfa463c140ccb0f9a78c9015e2d7', 'insert_admin_sync_role', '新增系统角色', 'admin_dev', 'admin_dev_sync', 'admin', 'sync', '<sync_plan>\n    <code>insert_admin_sync_role</code>\n    <name>新增系统角色</name>\n    <database src=\"admin_dev\" dest=\"admin_dev_sync\" ></database>\n    <project src=\"admin\" dest=\"sync\" ></project>\n    <table code=\"insert_admin_sync_role\" src=\"auth_role\" dest=\"sync_auth_role\" type=\"insert\">\n        <unique src=\"role_id\" dest=\"role_id\"  ></unique>\n        <relate src=\"role_id\" dest=\"role_id\" ></relate>\n        <column src=\"role_id\" dest=\"role_id\" />\n        <column src=\"role_name\" dest=\"role_name\" />\n        <column src=\"role_desc\" dest=\"role_desc\" />\n	<column src=\"create_time\" dest=\"create_time\" />\n        <column src=\"update_time\" dest=\"update_time\" />\n    </table>\n</sync_plan>', 'normal', '2023-07-23 11:04:58', '2024-08-04 21:26:40');
INSERT INTO `sync_plan` (`uuid`, `plan_code`, `description`, `src_db`, `dest_db`, `src_project`, `dest_project`, `plan_content`, `status`, `create_time`, `update_time`) VALUES ('7275635a21654dc6b759cb5660493e3d', 'insert_admin_sync_func', '新增系统功能', 'admin_dev', 'admin_dev_sync', 'admin', 'sync', '<sync_plan>\n    <code>insert_admin_sync_func</code>\n    <name>新增系统功能</name>\n    <database src=\"admin_dev\" dest=\"admin_dev_sync\" ></database>\n    <project src=\"admin\" dest=\"sync\" ></project>\n    <table code=\"insert_admin_sync_func\" src=\"auth_func\" dest=\"sync_auth_func\" type=\"insert\">\n        <unique src=\"func_id\" dest=\"func_id\"  ></unique>\n        <relate src=\"func_id\" dest=\"func_id\" ></relate>\n	<column src=\"func_id\" dest=\"func_id\" />\n        <column src=\"func_name\" dest=\"func_name\" />\n        <column src=\"func_key\" dest=\"func_key\" />\n	<column src=\"func_type\" dest=\"func_type\" />\n        <column src=\"func_icon\" dest=\"func_icon\" />\n	<column src=\"func_sort\" dest=\"func_sort\" />\n	<column src=\"func_icon\" dest=\"func_icon\" />\n	<column src=\"func_dir_status\" dest=\"func_dir_status\" />\n	<column src=\"parent_id\" dest=\"parent_id\" />\n	<column src=\"create_time\" dest=\"create_time\" />\n	<column src=\"update_time\" dest=\"update_time\" />\n    </table>\n</sync_plan>', 'normal', '2024-08-05 09:54:58', '2024-08-05 09:54:58');
INSERT INTO `sync_plan` (`uuid`, `plan_code`, `description`, `src_db`, `dest_db`, `src_project`, `dest_project`, `plan_content`, `status`, `create_time`, `update_time`) VALUES ('766d4f0dcbc0484b8a14be02dabee141', 'delete_admin_sync_func', '删除系统功能', 'admin_dev', 'admin_dev_sync', 'admin', 'sync', '<sync_plan>\n    <code>delete_admin_sync_func</code>\n    <name>删除系统功能</name>\n    <database src=\"admin_dev\" dest=\"admin_dev_sync\" ></database>\n    <project src=\"admin\" dest=\"sync\" ></project>\n    <table code=\"delete_admin_sync_func\" src=\"auth_func\" dest=\"sync_auth_func\" type=\"delete\">\n        <unique src=\"func_id\" dest=\"func_id\"  ></unique>\n        <relate src=\"func_id\" dest=\"func_id\" ></relate>\n    </table>\n</sync_plan>\n', 'normal', '2024-08-05 10:12:24', '2024-08-05 10:12:24');
INSERT INTO `sync_plan` (`uuid`, `plan_code`, `description`, `src_db`, `dest_db`, `src_project`, `dest_project`, `plan_content`, `status`, `create_time`, `update_time`) VALUES ('973b88e89f794c9194136dc6bc327190', 'insert_admin_sync_resc', '新增系统资源', 'admin_dev', 'admin_dev_sync', 'admin', 'sync', '<sync_plan>\n    <code>insert_admin_sync_resc</code>\n    <name>新增系统资源</name>\n    <database src=\"admin_dev\" dest=\"admin_dev_sync\" ></database>\n    <project src=\"admin\" dest=\"sync\" ></project>\n    <table code=\"insert_admin_sync_resc\" src=\"auth_resc\" dest=\"sync_auth_resc\" type=\"insert\">\n        <unique src=\"resc_id\" dest=\"resc_id\"  ></unique>\n        <relate src=\"resc_id\" dest=\"resc_id\" ></relate>\n        <column src=\"resc_id\" dest=\"resc_id\" />\n        <column src=\"resc_name\" dest=\"resc_name\" />\n        <column src=\"resc_url\" dest=\"resc_url\" />\n	<column src=\"resc_domain\" dest=\"resc_domain\" />\n        <column src=\"method_type\" dest=\"method_type\" />\n	<column src=\"create_time\" dest=\"create_time\" />\n        <column src=\"update_time\" dest=\"update_time\" />\n    </table>\n</sync_plan>', 'normal', '2023-09-03 19:38:40', '2024-08-04 21:24:59');
INSERT INTO `sync_plan` (`uuid`, `plan_code`, `description`, `src_db`, `dest_db`, `src_project`, `dest_project`, `plan_content`, `status`, `create_time`, `update_time`) VALUES ('a3c2bc8a16e04fa9bbb69830648cce78', 'update_admin_sync_func', '修改系统功能', 'admin_dev', 'admin_dev_sync', 'admin', 'sync', '<sync_plan>\n    <code>update_admin_sync_func</code>\n    <name>修改系统功能</name>\n    <database src=\"admin_dev\" dest=\"admin_dev_sync\" ></database>\n    <project src=\"admin\" dest=\"sync\" ></project>\n    <table code=\"update_admin_sync_func\" src=\"auth_func\" dest=\"sync_auth_func\" type=\"update\">\n        <unique src=\"func_id\" dest=\"func_id\"  ></unique>\n        <relate src=\"func_id\" dest=\"func_id\" ></relate>\n        <column src=\"func_name\" dest=\"func_name\" />\n        <column src=\"func_key\" dest=\"func_key\" />\n	<column src=\"func_type\" dest=\"func_type\" />\n        <column src=\"func_icon\" dest=\"func_icon\" />\n	<column src=\"func_sort\" dest=\"func_sort\" />\n	<column src=\"func_icon\" dest=\"func_icon\" />\n	<column src=\"func_dir_status\" dest=\"func_dir_status\" />\n	<column src=\"parent_id\" dest=\"parent_id\" />\n	<column src=\"create_time\" dest=\"create_time\" />\n	<column src=\"update_time\" dest=\"update_time\" />\n    </table>\n</sync_plan>', 'normal', '2024-08-04 21:53:51', '2024-08-04 21:53:51');
INSERT INTO `sync_plan` (`uuid`, `plan_code`, `description`, `src_db`, `dest_db`, `src_project`, `dest_project`, `plan_content`, `status`, `create_time`, `update_time`) VALUES ('e58818eb1fb5454b8a943bc6b6d9dc06', 'update_admin_sync_role', '修改系统角色', 'admin_dev', 'admin_dev_sync', 'admin', 'sync', '<sync_plan>\n    <code>update_admin_sync_role</code>\n    <name>修改系统角色</name>\n    <database src=\"admin_dev\" dest=\"admin_dev_sync\" ></database>\n    <project src=\"admin\" dest=\"sync\" ></project>\n    <table code=\"update_admin_sync_role\" src=\"auth_role\" dest=\"sync_auth_role\" type=\"update\">\n        <unique src=\"role_id\" dest=\"role_id\"  ></unique>\n        <relate src=\"role_id\" dest=\"role_id\" ></relate>\n        <column src=\"role_name\" dest=\"role_name\" />\n        <column src=\"role_desc\" dest=\"role_desc\" />\n	<column src=\"create_time\" dest=\"create_time\" />\n        <column src=\"update_time\" dest=\"update_time\" />\n    </table>\n</sync_plan>\n', 'normal', '2023-07-23 11:05:38', '2024-08-04 21:26:08');


