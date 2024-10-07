/*
 Navicat Premium Data Transfer

 Source Server         : 本机mysql
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : admin_dev_sync

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 07/10/2024 22:12:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
) ENGINE = InnoDB AUTO_INCREMENT = 318 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pub_callback
-- ----------------------------
INSERT INTO `pub_callback` VALUES (247, 'f8698337a55a478fa6ec383d998039ab', '53fe8d80e9b8431286d5fb2604e551bf', 'c83ec0a2412a4596a8893133020d8cb5', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"83\"]}],\"createTime\":\"2024-09-21 11:32:16\",\"src\":\"admin\",\"msgId\":\"53fe8d80e9b8431286d5fb2604e551bf\"}', '2024-09-21 11:32:17', '2024-09-21 12:01:46', '2024-09-21 12:01:48', 16, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (248, '8febd1d751494351b85c4ecf08840402', '60bc6699b67045a88fddccdf22b8dd8a', '131c262129294605a0a9ff382c386a3c', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"61\"]}],\"createTime\":\"2024-09-21 11:32:19\",\"src\":\"admin\",\"msgId\":\"60bc6699b67045a88fddccdf22b8dd8a\"}', '2024-09-21 11:32:20', '2024-09-21 12:01:46', '2024-09-21 12:01:48', 16, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (249, '525d9f021e1e44d3941fc212870f8b40', '50e81928852a4c7991575e4227eb67ce', 'd2ed2757f7bf4c56ab5abe8e3db3884d', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"83\"]}],\"createTime\":\"2024-09-21 11:33:36\",\"src\":\"admin\",\"msgId\":\"50e81928852a4c7991575e4227eb67ce\"}', '2024-09-21 11:33:37', '2024-09-21 12:06:46', '2024-09-21 12:06:48', 16, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (250, '7c5bc4922dba4582a2c71d43177e30d0', 'c77cf9d5cedf40fb8f4c68f5719ba8da', '874fa350461a48fbace8b317ae7d689d', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"61\"]}],\"createTime\":\"2024-09-21 11:33:38\",\"src\":\"admin\",\"msgId\":\"c77cf9d5cedf40fb8f4c68f5719ba8da\"}', '2024-09-21 11:33:39', '2024-09-21 12:06:46', '2024-09-21 12:06:48', 16, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (251, '2c7e1931ee2e468b80f33a818b4f599e', '45bfc8a3857b416baaadc529c2e0483e', '5a426d8931aa4661bef1c04c1de27a39', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"83\"]}],\"createTime\":\"2024-09-21 11:54:07\",\"src\":\"admin\",\"msgId\":\"45bfc8a3857b416baaadc529c2e0483e\"}', '2024-09-21 11:54:08', '2024-09-21 12:26:46', '2024-09-21 12:26:48', 16, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (252, '432563dadffb4a36878b3baf22bc01b4', '0db741f157fc4e4385210b3ae6c10fbc', '45b09e4d39e64b2aa8d0f44fe499e2b3', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"83\"]}],\"createTime\":\"2024-09-24 20:27:11\",\"src\":\"admin\",\"msgId\":\"0db741f157fc4e4385210b3ae6c10fbc\"}', '2024-09-24 20:27:12', '2024-09-24 21:06:46', '2024-09-24 21:06:48', 19, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (253, '059f9ee7b1b241b1b9b7f0c94ea01e9f', '8bf7700a79d54da8814eeba75c45c20d', 'bce14c881ed248f58d27112b48d952c1', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"83\"]}],\"createTime\":\"2024-09-24 20:27:15\",\"src\":\"admin\",\"msgId\":\"8bf7700a79d54da8814eeba75c45c20d\"}', '2024-09-24 20:27:16', '2024-09-24 20:28:00', '2024-09-24 20:27:30', 2, 'ing', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (254, 'd145a4b8276b4d4e99fb658af32e16b1', '9e4cf04f7fb2440b88a3868f0d034df7', '9c8e19bf7faa4147babb393be0e0b86f', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"83\"]}],\"createTime\":\"2024-09-24 22:34:13\",\"src\":\"admin\",\"msgId\":\"9e4cf04f7fb2440b88a3868f0d034df7\"}', '2024-09-24 22:34:15', '2024-09-24 23:21:46', '2024-09-24 23:21:48', 19, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (255, 'e0674c25a2144f0c8e16a96fb7c3114f', '8be6fbac101741c7b0230ac581797576', '560ec0d87bc5472c979be2982a826cdb', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"83\"]}],\"createTime\":\"2024-09-24 22:34:38\",\"src\":\"admin\",\"msgId\":\"8be6fbac101741c7b0230ac581797576\"}', '2024-09-24 22:34:39', '2024-09-24 23:21:46', '2024-09-24 23:21:48', 19, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (256, '9303d2c7cb184b4898fca1408da14de9', '5593d9bf67bf4da598d2cc9ed9e6df85', 'dad924ad9d2a4450adcc8d6b24437642', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"256\"]}],\"createTime\":\"2024-10-07 16:35:30\",\"src\":\"admin\",\"msgId\":\"5593d9bf67bf4da598d2cc9ed9e6df85\"}', '2024-10-07 16:35:31', '2024-10-07 17:06:46', '2024-10-07 17:06:48', 16, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (257, 'a3959a270f2d4b4f8fdbb78a7f604bb2', '74ed087365e34549ae0ce351710d15bb', 'fcac8648077f457aaa6a8af8f3017f82', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"768\"]}],\"createTime\":\"2024-10-07 16:35:32\",\"src\":\"admin\",\"msgId\":\"74ed087365e34549ae0ce351710d15bb\"}', '2024-10-07 16:35:33', '2024-10-07 17:06:46', '2024-10-07 17:06:48', 16, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (258, 'e092f6591fe74675ba8101b8f90629ff', '4a2dfd27ca964ceb8c30034adc21cfd9', '0ca8cf8183af4ced8de9bc07ab5af3ce', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"513\"]}],\"createTime\":\"2024-10-07 16:35:34\",\"src\":\"admin\",\"msgId\":\"4a2dfd27ca964ceb8c30034adc21cfd9\"}', '2024-10-07 16:35:34', '2024-10-07 17:06:48', '2024-10-07 17:06:50', 16, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (259, '45787b4c64ad416b9e0f727bc71d6c07', 'a39ff65a82db43b29ca2f498818e861d', 'ad7d8b314181428185500e014c380e52', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"258\"]}],\"createTime\":\"2024-10-07 16:35:35\",\"src\":\"admin\",\"msgId\":\"a39ff65a82db43b29ca2f498818e861d\"}', '2024-10-07 16:35:36', '2024-10-07 17:06:48', '2024-10-07 17:06:50', 16, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (260, '600b9f3fadd641a6949f5b8cd80d9db6', '06556875e4fa4c88b8a0574f66fdc00e', '5b7bdf3eb4ea4f88b066a707f3686846', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"256\"]}],\"createTime\":\"2024-10-07 16:36:28\",\"src\":\"admin\",\"msgId\":\"06556875e4fa4c88b8a0574f66fdc00e\"}', '2024-10-07 16:36:29', '2024-10-07 17:06:50', '2024-10-07 17:06:52', 16, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (261, '46b95761c3ae49fbadb4c12db88a1387', 'ed56a5bb8bd846a0a28c8601f3d9ced5', '48508887caaa4530acb4f287fc4801d9', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"768\"]}],\"createTime\":\"2024-10-07 16:36:31\",\"src\":\"admin\",\"msgId\":\"ed56a5bb8bd846a0a28c8601f3d9ced5\"}', '2024-10-07 16:36:33', '2024-10-07 17:06:50', '2024-10-07 17:06:52', 16, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (262, '5da49827639a47a3b083c1ce42f12587', '2acec7fc6570405a8e206d1cf6fe387f', 'd66f50802bfc4a8da88072100fa1a4f9', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"768\"]}],\"createTime\":\"2024-10-07 16:32:47\",\"src\":\"admin\",\"msgId\":\"2acec7fc6570405a8e206d1cf6fe387f\"}', '2024-10-07 17:12:39', '2024-10-07 17:31:46', '2024-10-07 17:31:48', 11, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (263, 'e6a72553d5ed41a2aaa5e65ca2a92161', '169cb1f5905a4754b1ce53c1bce6cef2', 'd0bd3df370a24a85b878fe47a42ddcde', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"256\"]}],\"createTime\":\"2024-10-07 16:32:45\",\"src\":\"admin\",\"msgId\":\"169cb1f5905a4754b1ce53c1bce6cef2\"}', '2024-10-07 17:12:46', '2024-10-07 17:31:46', '2024-10-07 17:31:48', 11, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (264, '349268c517e24b05902877d49dec949e', '9e3f25c390774e22ae75ffdfac6b2896', '6adb56fba5624726ae6d15170c2372d2', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"512\"]}],\"createTime\":\"2024-10-07 16:31:25\",\"src\":\"admin\",\"msgId\":\"9e3f25c390774e22ae75ffdfac6b2896\"}', '2024-10-07 17:13:13', '2024-10-07 17:31:48', '2024-10-07 17:31:50', 11, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (265, '6e099d66e9394609baec3c37bc4185d1', '1f4685b381e148a2973e654cdf8fd095', 'fc5ddd6a17844e0f975804484000e655', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"256\"]}],\"createTime\":\"2024-10-07 16:31:23\",\"src\":\"admin\",\"msgId\":\"1f4685b381e148a2973e654cdf8fd095\"}', '2024-10-07 17:13:16', '2024-10-07 17:26:46', '2024-10-07 17:26:48', 8, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (266, '0a2e8d55046a42f78ce41ecc971d9aa5', '24037f814b0648c38dcfbae80190a569', 'cf0940f9667d4773a20b3c1765a8ccaa', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"256\"]}],\"createTime\":\"2024-10-07 16:31:20\",\"src\":\"admin\",\"msgId\":\"24037f814b0648c38dcfbae80190a569\"}', '2024-10-07 17:13:19', '2024-10-07 17:26:47', '2024-10-07 17:26:49', 8, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (267, 'b9a65b3eb4e14ca3a911d765524d37c1', '2129d90a67c74bf1a135f07ce736bb40', '3ca0c4d7eeb2465bb9ae2d1ce2cdefec', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"260\"]}],\"createTime\":\"2024-10-06 20:22:27\",\"src\":\"admin\",\"msgId\":\"2129d90a67c74bf1a135f07ce736bb40\"}', '2024-10-07 17:19:48', '2024-10-07 17:31:48', '2024-10-07 17:31:50', 8, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (268, 'af6cde9de667423f880d7d4eeab7651f', '402068f7c0164f79b46f6a25303e1474', '7535527f9c304b559d0cfe4f864733b9', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"256\"]}],\"createTime\":\"2024-10-07 17:22:16\",\"src\":\"admin\",\"msgId\":\"402068f7c0164f79b46f6a25303e1474\"}', '2024-10-07 17:22:18', '2024-10-07 17:31:50', '2024-10-07 17:31:52', 8, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (269, '7ea950a77b20459281e1ae43bc065eea', 'ac95ca022297465d9141e239dd7241cc', '960edde0bba04f378dec9eef677902d4', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"256\"]}],\"createTime\":\"2024-10-07 17:23:51\",\"src\":\"admin\",\"msgId\":\"ac95ca022297465d9141e239dd7241cc\"}', '2024-10-07 17:23:52', '2024-10-07 17:25:38', '2024-10-07 17:25:40', 4, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (270, 'e751eb7058164a7f85e9d3e80ba10e3e', '0eb1c7bbf9ec4490a90f8bbc2cdaa744', '2e1f51e868df443d8c0b718ee1134321', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"256\"]}],\"createTime\":\"2024-10-07 17:25:58\",\"src\":\"admin\",\"msgId\":\"0eb1c7bbf9ec4490a90f8bbc2cdaa744\"}', '2024-10-07 17:25:59', '2024-10-07 17:27:45', '2024-10-07 17:27:47', 4, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (271, '0592d02035624f61bd267b277dbfb576', 'c7cac2b9312b421a98eb8289af4483df', 'ae372142aec54a108fca030c1a4674c5', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"768\"]}],\"createTime\":\"2024-10-07 17:26:00\",\"src\":\"admin\",\"msgId\":\"c7cac2b9312b421a98eb8289af4483df\"}', '2024-10-07 17:26:01', '2024-10-07 17:27:47', '2024-10-07 17:27:49', 4, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (272, 'ba14b00a27e44436a502287536d94200', '3e10d449f3c94cf0b30af5d6b71a2cf0', '7830c658a68745009f09ceeb33e02af4', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"509\"]}],\"createTime\":\"2024-10-07 17:26:04\",\"src\":\"admin\",\"msgId\":\"3e10d449f3c94cf0b30af5d6b71a2cf0\"}', '2024-10-07 17:26:05', '2024-10-07 17:27:51', '2024-10-07 17:27:53', 4, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (273, 'ed7a67bf6f3b4e08ba8da19d22b8b0ff', 'e3721cb33de34cb1b4b1f7e6331314c1', '3c317402e66d462e96014c7dfa33ed8a', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"256\"]}],\"createTime\":\"2024-10-07 20:58:03\",\"src\":\"admin\",\"msgId\":\"e3721cb33de34cb1b4b1f7e6331314c1\"}', '2024-10-07 20:58:04', '2024-10-07 21:36:48', '2024-10-07 21:36:50', 17, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (274, '5a0e240ad252496e9739e9d185daf6a3', 'c34cb0375c7348ce8ec4bd83862dc5c6', '9ca6144b3c794aa092ae8f29a7191bc5', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"1\"]}],\"createTime\":\"2024-10-07 20:58:05\",\"src\":\"admin\",\"msgId\":\"c34cb0375c7348ce8ec4bd83862dc5c6\"}', '2024-10-07 20:58:06', '2024-10-07 21:36:50', '2024-10-07 21:36:52', 17, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (275, '1874418160ff443ba658ddd8f7b4927f', 'a5b4ad34793542b98f7bde28989379cd', 'b6bfa320393e4cea9f7ac95dcbb8f06d', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"256\"]}],\"createTime\":\"2024-10-07 21:13:14\",\"src\":\"admin\",\"msgId\":\"a5b4ad34793542b98f7bde28989379cd\"}', '2024-10-07 21:13:15', '2024-10-07 21:46:46', '2024-10-07 21:46:48', 16, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (276, '0449f9c7802644d9a347edab43134633', '2308d9525dae40f9af5719fa8310b4a5', '366994cb9062426b91a24d12771bd379', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"256\"]}],\"createTime\":\"2024-10-07 21:13:16\",\"src\":\"admin\",\"msgId\":\"2308d9525dae40f9af5719fa8310b4a5\"}', '2024-10-07 21:13:18', '2024-10-07 21:46:46', '2024-10-07 21:46:48', 16, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (277, '620a8690d7984c7f83141b28d62638ab', '821d3e50a10844ceb181783c3306ce71', 'fcbf833e340943f6a08612c650407547', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"1\"]}],\"createTime\":\"2024-10-07 21:13:18\",\"src\":\"admin\",\"msgId\":\"821d3e50a10844ceb181783c3306ce71\"}', '2024-10-07 21:13:20', '2024-10-07 21:46:48', '2024-10-07 21:46:50', 16, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (278, '7d23398be2a44a3f9078730a8eaf8f7f', '3a3fb6c7fcca42988983002c814ecb1e', 'fb1b47c9ba094664963ead7bece951da', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"256\"]}],\"createTime\":\"2024-10-07 21:26:20\",\"src\":\"admin\",\"msgId\":\"3a3fb6c7fcca42988983002c814ecb1e\"}', '2024-10-07 21:26:22', '2024-10-07 22:06:46', '2024-10-07 22:06:48', 19, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (279, '88635a271b4b475092589f6c0bafacd0', '06177e99323c43f5831ac4a62fb89bcf', 'db5611184a8e476aa4d0754db6aabe1b', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"512\"]}],\"createTime\":\"2024-10-07 21:26:23\",\"src\":\"admin\",\"msgId\":\"06177e99323c43f5831ac4a62fb89bcf\"}', '2024-10-07 21:26:24', '2024-10-07 22:06:48', '2024-10-07 22:06:50', 19, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (280, '7475a92b9a904b7d97db829d1488049c', '0d4c7c08bdd84ea2b7c71d3d4d97dc54', '721e91a048d047f48b6ed83ae7c56853', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"256\"]}],\"createTime\":\"2024-10-07 21:28:45\",\"src\":\"admin\",\"msgId\":\"0d4c7c08bdd84ea2b7c71d3d4d97dc54\"}', '2024-10-07 21:28:46', '2024-10-07 22:01:50', '2024-10-07 22:01:52', 16, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (281, '38e3218d524c46af939fe77b72f2d26f', '369ddd7b869e416b8c233ea8e4a7597b', '57590aeaf7d943918fccc6c46b9e2d5f', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"768\"]}],\"createTime\":\"2024-10-07 21:28:47\",\"src\":\"admin\",\"msgId\":\"369ddd7b869e416b8c233ea8e4a7597b\"}', '2024-10-07 21:28:48', '2024-10-07 22:01:52', '2024-10-07 22:01:54', 16, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (282, 'e269438ba28340de99344dfbe26143ef', '02bcfc7bee3f47d49bba82ce8d7051a1', '40a9017c153f4cc2a18e55df73a79c8a', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"1\"]}],\"createTime\":\"2024-10-07 21:28:49\",\"src\":\"admin\",\"msgId\":\"02bcfc7bee3f47d49bba82ce8d7051a1\"}', '2024-10-07 21:28:49', '2024-10-07 22:01:54', '2024-10-07 22:01:56', 16, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (283, '835884675fca41c18bfd9338be5382e9', '139a9a1d099d49438002fa3eb2474043', '8adef218083042b9a5740fef699b6dd9', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"769\"]}],\"createTime\":\"2024-10-07 21:28:50\",\"src\":\"admin\",\"msgId\":\"139a9a1d099d49438002fa3eb2474043\"}', '2024-10-07 21:28:51', '2024-10-07 22:01:53', '2024-10-07 22:01:55', 16, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (284, '9fb0aada14c24e8b982ceefa13b054be', '17ec48e5f23948e3b1edf6cddde3f413', '9bdbc2c3736f4ecf9d72e24ed6d7394e', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"259\"]}],\"createTime\":\"2024-10-07 21:28:52\",\"src\":\"admin\",\"msgId\":\"17ec48e5f23948e3b1edf6cddde3f413\"}', '2024-10-07 21:28:53', '2024-10-07 22:02:02', '2024-10-07 22:02:04', 16, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (285, 'd99c2f671e354fd08189086399e4390a', 'da76b65955904f248709507a6a2c2aed', '4016875b42d04c87b41cc19d421e6fb9', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"5\"]}],\"createTime\":\"2024-10-07 21:28:54\",\"src\":\"admin\",\"msgId\":\"da76b65955904f248709507a6a2c2aed\"}', '2024-10-07 21:28:55', '2024-10-07 22:02:00', '2024-10-07 22:02:02', 16, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (286, '7830a831678e404f902232873bd08550', 'f2b782c1645842e1905f3260cba8e5f9', 'f88610036f5b4856996bb2a5d73bd038', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"769\"]}],\"createTime\":\"2024-10-07 21:28:57\",\"src\":\"admin\",\"msgId\":\"f2b782c1645842e1905f3260cba8e5f9\"}', '2024-10-07 21:28:57', '2024-10-07 22:02:02', '2024-10-07 22:02:04', 16, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (287, 'b724cca6579b46f5bbcac1585ec8bcca', '7671c28e1e894849b9abcd1b01d2b322', '0866ebbff5414193b02964662331d2c1', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"251\"]}],\"createTime\":\"2024-10-07 21:44:54\",\"src\":\"admin\",\"msgId\":\"7671c28e1e894849b9abcd1b01d2b322\"}', '2024-10-07 21:44:55', '2024-10-07 22:06:50', '2024-10-07 22:06:52', 12, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (288, '60c1bf66d2584db4a7e51b7d44a599cd', 'a578a46b633049b48c6eb46a837c7ff8', '3d320892bb7d43abb0a70f2e5f0345bd', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"1\"]}],\"createTime\":\"2024-10-07 21:44:56\",\"src\":\"admin\",\"msgId\":\"a578a46b633049b48c6eb46a837c7ff8\"}', '2024-10-07 21:44:57', '2024-10-07 22:06:50', '2024-10-07 22:06:52', 12, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (289, 'c8448689c1d24a2799abf086a83563c9', 'f15311227833475cbae4a1a65da625f3', 'da44b476974e4763938d28a6e81bfc0a', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"768\"]}],\"createTime\":\"2024-10-07 21:46:10\",\"src\":\"admin\",\"msgId\":\"f15311227833475cbae4a1a65da625f3\"}', '2024-10-07 21:46:10', '2024-10-07 22:06:52', '2024-10-07 22:06:54', 12, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (290, '1caeefa00c994de691802be23bd7eded', 'dbd1ef6440194e938faad62a147c0e05', 'a19b325c6a524e399245ea526d7180df', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"768\"]}],\"createTime\":\"2024-10-07 21:46:13\",\"src\":\"admin\",\"msgId\":\"dbd1ef6440194e938faad62a147c0e05\"}', '2024-10-07 21:46:14', '2024-10-07 22:06:52', '2024-10-07 22:06:54', 12, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (291, 'ad8ae8dd294a4bba9ef09bfc721f7fea', '58fc805c2a7f41128a632b080eacc4af', '2596f9658568454ab91f152500e55bc4', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"258\"]}],\"createTime\":\"2024-10-07 21:46:16\",\"src\":\"admin\",\"msgId\":\"58fc805c2a7f41128a632b080eacc4af\"}', '2024-10-07 21:46:18', '2024-10-07 22:06:54', '2024-10-07 22:06:56', 12, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (292, '6728539f503443f889a535672393ed04', 'ccfdaa8d206f4863aaf5a1b68d56c3fa', '63c4baa313464aecb313bf838963524e', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"517\"]}],\"createTime\":\"2024-10-07 21:46:19\",\"src\":\"admin\",\"msgId\":\"ccfdaa8d206f4863aaf5a1b68d56c3fa\"}', '2024-10-07 21:46:20', '2024-10-07 22:06:54', '2024-10-07 22:06:56', 12, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (293, 'c357e2f72d7b4d7fae2ab7c8efe429f0', '099d03c994024dc18ca56d819ac6b767', 'da4e3dfe1c8640aab10d27598a6a4386', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"518\"]}],\"createTime\":\"2024-10-07 21:46:21\",\"src\":\"admin\",\"msgId\":\"099d03c994024dc18ca56d819ac6b767\"}', '2024-10-07 21:46:22', '2024-10-07 22:06:56', '2024-10-07 22:06:58', 12, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (294, '3bc80586afe342cf86aa2ba168d960c4', 'b667fdde5f3f47c89de9bae032766882', '5d8e083bab8c454898f67e8a32bc0eac', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"515\"]}],\"createTime\":\"2024-10-07 21:58:39\",\"src\":\"admin\",\"msgId\":\"b667fdde5f3f47c89de9bae032766882\"}', '2024-10-07 22:00:12', '2024-10-07 22:11:48', '2024-10-07 22:11:50', 8, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (295, '24938230875745829c67737b69ff9132', 'c653e0837f1546f589b0745c0a23c9bd', 'b2085b7d06184b67872a258ce2c1a15b', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"769\"]}],\"createTime\":\"2024-10-07 21:58:42\",\"src\":\"admin\",\"msgId\":\"c653e0837f1546f589b0745c0a23c9bd\"}', '2024-10-07 22:00:12', '2024-10-07 22:11:50', '2024-10-07 22:11:52', 8, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (296, '7beaec16a4324e689b3d0e08350085cd', '5230c7ae8f824952a3bab35d2bc9f132', '7aaa2edf78db408f824884681c2a1161', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"5\"]}],\"createTime\":\"2024-10-07 21:58:45\",\"src\":\"admin\",\"msgId\":\"5230c7ae8f824952a3bab35d2bc9f132\"}', '2024-10-07 22:00:12', '2024-10-07 22:11:50', '2024-10-07 22:11:52', 8, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (297, 'caa2b79641374e3b833cbc28fb7541cc', '2ecb2e4e26dc4380bf8f3fe82016d53b', 'd52316ecfed342af931f14afe52bcc3b', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"262\"]}],\"createTime\":\"2024-10-07 21:58:49\",\"src\":\"admin\",\"msgId\":\"2ecb2e4e26dc4380bf8f3fe82016d53b\"}', '2024-10-07 22:00:12', '2024-10-07 22:11:52', '2024-10-07 22:11:54', 8, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (298, '3be231905b0b469ca0eb6a6dbb88ada3', '8feb416803bd454cb783e332de176d0a', '5ea75f1c0ff547f6aa3ef5c4fd8b4610', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"256\"]}],\"createTime\":\"2024-10-07 21:58:53\",\"src\":\"admin\",\"msgId\":\"8feb416803bd454cb783e332de176d0a\"}', '2024-10-07 22:00:12', '2024-10-07 22:11:54', '2024-10-07 22:11:56', 8, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (299, '6403a067977046279da70efe24aa4edc', 'e7893d65a679452b89a27339093f5b40', '94954b4444e8402399fa8df4f8c096c0', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"518\"]}],\"createTime\":\"2024-10-07 21:58:38\",\"src\":\"admin\",\"msgId\":\"e7893d65a679452b89a27339093f5b40\"}', '2024-10-07 22:00:16', '2024-10-07 22:11:56', '2024-10-07 22:11:59', 8, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (300, '970b4277a518489eac1c312581e40356', '4388c18ec6e44bb1aa5354e196baa081', '0163193c35b94fdab9b5a53b4bc22cba', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"258\"]}],\"createTime\":\"2024-10-07 21:58:41\",\"src\":\"admin\",\"msgId\":\"4388c18ec6e44bb1aa5354e196baa081\"}', '2024-10-07 22:00:16', '2024-10-07 22:11:54', '2024-10-07 22:11:57', 8, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (301, '0c13db55ec9f4cb1a30b1bdf5aadc6e1', '02ef9ffeca4e4e5aa3be28901147ff92', 'ddf128debddb4f4d8f56527567783039', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"260\"]}],\"createTime\":\"2024-10-07 21:58:44\",\"src\":\"admin\",\"msgId\":\"02ef9ffeca4e4e5aa3be28901147ff92\"}', '2024-10-07 22:00:16', '2024-10-07 22:11:59', '2024-10-07 22:12:01', 8, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (302, '921d436e02dc429d9c017b6b7f5ea6fd', 'ec65cbdaa55745b98388b018bdb7c6a9', '5fc8e28bd7cf41ac91d2beaf56833ada', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"517\"]}],\"createTime\":\"2024-10-07 21:58:47\",\"src\":\"admin\",\"msgId\":\"ec65cbdaa55745b98388b018bdb7c6a9\"}', '2024-10-07 22:00:16', '2024-10-07 22:11:57', '2024-10-07 22:11:59', 8, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (303, '13fc0873b89f4a599585d931532c77ad', 'fb8b4d862fcf47199c2659ccd7027a04', '5bb6126a07144a62ad98f362a365a496', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"769\"]}],\"createTime\":\"2024-10-07 21:58:50\",\"src\":\"admin\",\"msgId\":\"fb8b4d862fcf47199c2659ccd7027a04\"}', '2024-10-07 22:00:16', '2024-10-07 22:11:59', '2024-10-07 22:12:01', 8, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (304, 'fb197fab0cb941c1bcfd3bfcf0d47e78', '85120cc712c9480e829792f00d1d8630', 'e947960c35fd42b380898c0f2123f65c', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"1\"]}],\"createTime\":\"2024-10-07 21:58:55\",\"src\":\"admin\",\"msgId\":\"85120cc712c9480e829792f00d1d8630\"}', '2024-10-07 22:00:16', '2024-10-07 22:12:01', '2024-10-07 22:12:03', 8, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (305, '3a4f00300fb2462bafa9165e6aa911b5', '9e82188ec22c4b7fb4110be3ba67119c', 'f726e5d153374333a3b6d5f827620206', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"261\"]}],\"createTime\":\"2024-10-07 21:58:36\",\"src\":\"admin\",\"msgId\":\"9e82188ec22c4b7fb4110be3ba67119c\"}', '2024-10-07 22:00:37', '2024-10-07 22:12:01', '2024-10-07 22:12:03', 8, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (306, '7708bada8801428996c8efe28c1281f2', 'e79f94eb928d4447bd6748307be6925a', 'afa76708fc954c3f9492f31731775349', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"258\"]}],\"createTime\":\"2024-10-07 21:58:33\",\"src\":\"admin\",\"msgId\":\"e79f94eb928d4447bd6748307be6925a\"}', '2024-10-07 22:00:42', '2024-10-07 22:12:03', '2024-10-07 22:12:05', 8, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (307, 'c82f95c6430f4b80b938323e09b9f3de', '4ecede4f130e460ba3529e890a8f4b9b', '5cfbbd1b87df40c39ae7650b43e98feb', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"768\"]}],\"createTime\":\"2024-10-07 21:58:32\",\"src\":\"admin\",\"msgId\":\"4ecede4f130e460ba3529e890a8f4b9b\"}', '2024-10-07 22:00:45', '2024-10-07 22:12:03', '2024-10-07 22:12:05', 8, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (308, '584700b6e2364997a0befe007afec9a7', '765085998cbe49c69c85b34e6b7f7453', 'a0fdd5d9df3840058a2954da74e981bc', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"256\"]}],\"createTime\":\"2024-10-07 21:58:30\",\"src\":\"admin\",\"msgId\":\"765085998cbe49c69c85b34e6b7f7453\"}', '2024-10-07 22:00:49', '2024-10-07 22:12:05', '2024-10-07 22:12:07', 8, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (309, '2c09176d6d2642239d7e4316fea676bf', 'bf1fc4a7784d4d48b795b6655fda7fb7', 'ac0799f4d5cd4721976a8fb48ec128d1', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"880\"]}],\"createTime\":\"2024-10-07 22:05:26\",\"src\":\"admin\",\"msgId\":\"bf1fc4a7784d4d48b795b6655fda7fb7\"}', '2024-10-07 22:05:27', '2024-10-07 22:07:13', '2024-10-07 22:07:15', 4, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (310, '7f2f502c2c7d453f98cde50878cd3d75', '2ec68f42eda940fe8b6e7576212b38b5', '9dbc1497458242c09f62479b5d0e8fd8', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"768\"]}],\"createTime\":\"2024-10-07 22:05:28\",\"src\":\"admin\",\"msgId\":\"2ec68f42eda940fe8b6e7576212b38b5\"}', '2024-10-07 22:05:29', '2024-10-07 22:07:15', '2024-10-07 22:07:17', 4, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (311, 'f849a02bbd9848f5b90947bee5eb8757', '6e332c6da23c4f38a86c5f2b6588f46b', 'ecf8a3c1d560430591d8d0e25f1887b4', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"512\"]}],\"createTime\":\"2024-10-07 22:05:31\",\"src\":\"admin\",\"msgId\":\"6e332c6da23c4f38a86c5f2b6588f46b\"}', '2024-10-07 22:05:31', '2024-10-07 22:07:18', '2024-10-07 22:07:20', 4, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (312, 'f950373d5dd04db99c6d3ab1c1a7d275', 'a5e5a10ae549401d9c81768ad2372a27', '0e197f68483649dba8f8b13c0e108100', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"5\"]}],\"createTime\":\"2024-10-07 22:05:34\",\"src\":\"admin\",\"msgId\":\"a5e5a10ae549401d9c81768ad2372a27\"}', '2024-10-07 22:05:34', '2024-10-07 22:07:20', '2024-10-07 22:07:22', 4, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (313, 'a036038118fc482a895406ecac8eb435', 'e8a780a4ba1e4886a69a44858f6797d5', '7224aa9f02b7459bb6f984dcdc09b06f', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"880\"]}],\"createTime\":\"2024-10-07 22:07:39\",\"src\":\"admin\",\"msgId\":\"e8a780a4ba1e4886a69a44858f6797d5\"}', '2024-10-07 22:07:40', '2024-10-07 22:09:27', '2024-10-07 22:09:29', 4, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (314, 'd1488891b8f94b5cbce51be910f750a8', '4d1347eeb96345dcbac3bcd643559ebf', '81da9d1c780b44bc963567e7d9de97e1', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"256\"]}],\"createTime\":\"2024-10-07 22:07:41\",\"src\":\"admin\",\"msgId\":\"4d1347eeb96345dcbac3bcd643559ebf\"}', '2024-10-07 22:07:41', '2024-10-07 22:09:28', '2024-10-07 22:09:30', 4, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (315, 'b313d4a1783243a691e59d3bdcc44d6a', '652c9abf8f9c4628b67bbeee982742b6', '3ec00dd9b24e448ba42e8ddd8fa48b8d', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"768\"]}],\"createTime\":\"2024-10-07 22:07:42\",\"src\":\"admin\",\"msgId\":\"652c9abf8f9c4628b67bbeee982742b6\"}', '2024-10-07 22:07:43', '2024-10-07 22:09:30', '2024-10-07 22:09:32', 4, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (316, '693e87ed6c944a6c800a63f4785e88a1', '1f62cc1cfc4b4cc395dc58e8a03614f5', '0beaa72a12114ac1a21899a91f8496b6', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"880\"]}],\"createTime\":\"2024-10-07 22:08:35\",\"src\":\"admin\",\"msgId\":\"1f62cc1cfc4b4cc395dc58e8a03614f5\"}', '2024-10-07 22:08:37', '2024-10-07 22:10:24', '2024-10-07 22:10:26', 4, 'fail', '更新用户回调', '无法连接主机');
INSERT INTO `pub_callback` VALUES (317, '27010b11f2b74a929f00b868a0e1067d', '5a9ac5fc9353458191363c73e8401d54', '4c7d0c87c0e541c1a06a94df3e59cb6f', 'admin', 'sync', 'admin_dev_sync', '{\"plan\":\"update_admin_sync_user\",\"tables\":[{\"code\":\"update_admin_sync_user\",\"unique\":[\"880\"]}],\"createTime\":\"2024-10-07 22:11:08\",\"src\":\"admin\",\"msgId\":\"5a9ac5fc9353458191363c73e8401d54\"}', '2024-10-07 22:11:08', '2024-10-07 22:11:52', '2024-10-07 22:11:54', 3, 'fail', '更新用户回调', '无法连接主机');

-- ----------------------------
-- Table structure for sync_auth_dept
-- ----------------------------
DROP TABLE IF EXISTS `sync_auth_dept`;
CREATE TABLE `sync_auth_dept`  (
  `dept_id` int(11) NOT NULL COMMENT '部门id',
  `dept_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门编号',
  `dept_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `sync_time` datetime NULL DEFAULT NULL COMMENT '同步时间',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sync_auth_dept
-- ----------------------------
INSERT INTO `sync_auth_dept` VALUES (1, 'yfb', '研发部', '2022-03-12 11:51:12', '2022-03-12 11:51:13', NULL);
INSERT INTO `sync_auth_dept` VALUES (2, 'csb', '测试部', '2022-03-12 11:51:49', '2022-03-12 11:51:51', NULL);
INSERT INTO `sync_auth_dept` VALUES (3, 'khb1', '客户一部', '2022-03-12 16:29:08', '2022-03-12 16:29:10', NULL);
INSERT INTO `sync_auth_dept` VALUES (4, 'khb2', '客户二部', '2022-03-12 16:29:27', '2022-03-12 16:29:29', NULL);
INSERT INTO `sync_auth_dept` VALUES (5, 'yyb', '运营部', '2022-03-12 16:29:44', '2022-03-12 16:29:45', NULL);
INSERT INTO `sync_auth_dept` VALUES (6, 'jyb', '交易部', '2022-03-12 16:30:04', '2022-03-12 16:30:06', NULL);
INSERT INTO `sync_auth_dept` VALUES (7, 'yuwb', '运维部', '2022-03-20 15:42:39', '2022-03-20 15:42:40', NULL);

-- ----------------------------
-- Table structure for sync_auth_func
-- ----------------------------
DROP TABLE IF EXISTS `sync_auth_func`;
CREATE TABLE `sync_auth_func`  (
  `func_id` int(11) NOT NULL COMMENT '主键',
  `func_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '功能名称',
  `func_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '功能key',
  `func_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '功能类型(目录/功能)',
  `func_sort` int(11) NULL DEFAULT NULL COMMENT '功能排序',
  `hide` tinyint(4) NOT NULL COMMENT '是否隐藏',
  `parent_id` int(11) NOT NULL COMMENT '父级功能id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `sync_time` datetime NULL DEFAULT NULL COMMENT '同步时间',
  PRIMARY KEY (`func_id`) USING BTREE,
  UNIQUE INDEX `idx_func_key`(`func_key`) USING BTREE,
  INDEX `idx_parent_id`(`parent_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sync_auth_func
-- ----------------------------
INSERT INTO `sync_auth_func` VALUES (2, '权限系统', '/auth', 'dir', 1, 0, 0, '2022-03-19 15:57:28', '2024-06-16 20:51:25', NULL);
INSERT INTO `sync_auth_func` VALUES (3, '用户管理', '/auth/user', 'dir', 1, 0, 2, '2022-03-19 16:10:26', '2023-10-05 14:18:01', NULL);
INSERT INTO `sync_auth_func` VALUES (4, '角色管理', '/auth/role', 'dir', 4, 0, 2, '2022-03-19 16:11:29', '2023-09-15 21:40:09', NULL);
INSERT INTO `sync_auth_func` VALUES (5, '功能管理', '/auth/func', 'dir', 2, 0, 2, '2022-03-19 16:11:41', '2024-06-16 20:51:29', NULL);
INSERT INTO `sync_auth_func` VALUES (6, '资源管理', '/auth/resc', 'dir', 5, 0, 2, '2022-03-19 16:11:54', '2023-09-15 21:33:29', NULL);
INSERT INTO `sync_auth_func` VALUES (8, '角色删除', 'auth:role:delete', 'func', 7, 0, 4, '2022-03-19 18:23:22', '2023-09-03 18:58:48', NULL);
INSERT INTO `sync_auth_func` VALUES (9, '角色更新', 'auth:role:update', 'func', 8, 0, 4, '2022-03-19 18:23:38', '2022-03-19 18:23:40', NULL);
INSERT INTO `sync_auth_func` VALUES (11, '角色查询', 'auth:role:page', 'func', 9, 0, 4, '2022-03-19 18:24:02', '2022-03-19 18:24:03', NULL);
INSERT INTO `sync_auth_func` VALUES (12, '功能查询', 'auth:func:page', 'func', 5, 0, 5, '2022-03-19 18:24:30', '2023-09-03 18:58:58', NULL);
INSERT INTO `sync_auth_func` VALUES (13, '列表查询', 'auth:resc:page', 'func', 4, 0, 6, '2022-03-19 18:24:51', '2023-07-15 23:17:47', NULL);
INSERT INTO `sync_auth_func` VALUES (14, '功能新增', 'auth:func:add', 'func', 6, 0, 5, '2022-03-19 18:26:06', '2023-09-03 18:58:59', NULL);
INSERT INTO `sync_auth_func` VALUES (26, '资源添加', 'auth:resc:add', 'func', 5, 0, 6, '2022-03-20 21:43:55', '2023-07-15 23:18:31', NULL);
INSERT INTO `sync_auth_func` VALUES (27, '资源删除', 'auth:resc:delete', 'func', 6, 0, 6, '2022-03-20 21:44:03', '2023-09-03 18:58:47', NULL);
INSERT INTO `sync_auth_func` VALUES (54, '功能删除', 'auth:func:delete', 'func', 7, 0, 5, '2022-03-26 20:57:44', '2023-09-03 18:58:41', NULL);
INSERT INTO `sync_auth_func` VALUES (55, '功能更新', 'auth:func:update', 'func', 8, 0, 5, '2022-03-26 20:58:00', '2023-09-03 18:58:43', NULL);
INSERT INTO `sync_auth_func` VALUES (56, '资源编辑', 'auth:resc:update', 'func', 7, 0, 6, '2022-03-26 21:00:16', '2023-07-15 23:18:21', NULL);
INSERT INTO `sync_auth_func` VALUES (57, '用户查询', 'auth:user:page', 'func', 2, 0, 3, '2022-03-26 21:04:37', '2023-09-09 11:45:35', NULL);
INSERT INTO `sync_auth_func` VALUES (60, '用户编辑', 'auth:user:update', 'func', 5, 0, 3, '2022-03-26 21:05:33', '2023-07-15 23:18:48', NULL);
INSERT INTO `sync_auth_func` VALUES (61, '用户删除', 'auth:user:delete', 'func', 6, 0, 3, '2022-03-26 21:05:55', '2023-09-23 15:29:31', NULL);
INSERT INTO `sync_auth_func` VALUES (62, '分配角色', 'auth:user:roleUpdate', 'func', 7, 0, 3, '2022-03-26 21:06:48', '2023-09-03 18:58:45', NULL);
INSERT INTO `sync_auth_func` VALUES (63, '分配用户', 'auth:role:userUpdate', 'func', 6, 0, 4, '2022-03-26 21:08:39', '2023-07-15 15:28:20', NULL);
INSERT INTO `sync_auth_func` VALUES (71, '分配功能', 'auth_role_func_view', 'dir', 5, 1, 4, '2022-04-04 17:45:36', '2024-10-07 22:06:11', '2024-10-07 22:06:12');
INSERT INTO `sync_auth_func` VALUES (72, '角色新增', 'auth:role:add', 'func', 10, 0, 4, '2022-04-16 11:13:19', '2023-07-01 11:12:50', NULL);
INSERT INTO `sync_auth_func` VALUES (73, '解绑用户', 'auth:func:userRemove', 'func', 9, 0, 5, '2022-04-16 21:55:19', '2023-07-15 15:27:43', NULL);
INSERT INTO `sync_auth_func` VALUES (74, '解绑资源', 'auth:func:rescRemove', 'func', 10, 0, 5, '2022-04-16 22:02:49', '2023-09-03 13:58:36', NULL);
INSERT INTO `sync_auth_func` VALUES (75, '用户添加', 'auth:user:add', 'func', 8, 0, 3, '2022-05-02 11:40:57', '2023-07-15 23:19:01', NULL);
INSERT INTO `sync_auth_func` VALUES (84, '修改密码', 'auth:user:pwdUpdate', 'func', 9, 0, 3, '2022-05-14 12:20:12', '2023-07-15 15:27:23', NULL);
INSERT INTO `sync_auth_func` VALUES (93, '同步系统', 'sync_view', 'dir', 2, 0, 0, '2022-09-13 20:54:26', '2024-10-07 17:27:50', '2024-10-07 17:27:51');
INSERT INTO `sync_auth_func` VALUES (94, '同步计划', '/sync/plan', 'dir', 0, 0, 93, '2022-09-13 20:54:58', '2024-05-17 21:01:26', NULL);
INSERT INTO `sync_auth_func` VALUES (95, '同步记录', '/sync/result', 'dir', 3, 0, 93, '2022-12-04 23:48:14', '2023-10-31 09:51:55', NULL);
INSERT INTO `sync_auth_func` VALUES (96, '消息回调', '/sync/callback', 'dir', 5, 0, 93, '2022-12-04 23:48:39', '2023-10-14 22:24:08', NULL);
INSERT INTO `sync_auth_func` VALUES (97, '本地消息', '/sync/msg', 'dir', 4, 0, 93, '2022-12-04 23:49:04', '2023-10-14 22:24:37', NULL);
INSERT INTO `sync_auth_func` VALUES (102, '绑定资源', 'auth:func:rescUpdate', 'func', 4, 0, 5, '2023-06-17 21:00:09', '2023-07-15 23:17:19', NULL);
INSERT INTO `sync_auth_func` VALUES (105, '授权初始化', 'auth:role:funcInit', 'func', 0, 0, 71, '2023-06-21 21:02:03', '2023-09-24 21:44:26', NULL);
INSERT INTO `sync_auth_func` VALUES (106, '更新授权', 'auth:role:funcUpdate', 'func', 0, 0, 71, '2023-06-21 21:02:29', '2023-10-05 14:19:56', NULL);
INSERT INTO `sync_auth_func` VALUES (128, '切换登录', 'auth:user:switchLogin', 'func', 9, 0, 3, '2023-07-15 15:35:51', '2023-07-16 18:19:27', NULL);
INSERT INTO `sync_auth_func` VALUES (129, '状态更新', 'auth:user:statusUpdate', 'func', 4, 0, 3, '2023-07-15 20:40:12', '2023-09-23 15:29:39', NULL);
INSERT INTO `sync_auth_func` VALUES (136, '计划查询', 'sync:plan:page', 'func', 0, 0, 94, '2023-07-18 20:53:06', '2023-09-03 18:58:32', NULL);
INSERT INTO `sync_auth_func` VALUES (137, '新增计划', 'sync:plan:add', 'func', 1, 0, 94, '2023-07-19 21:04:36', '2023-09-03 13:23:09', NULL);
INSERT INTO `sync_auth_func` VALUES (138, '编辑计划', 'sync:plan:update', 'func', 3, 0, 94, '2023-07-19 22:08:32', '2023-09-03 18:58:36', NULL);
INSERT INTO `sync_auth_func` VALUES (139, '删除计划', 'sync:plan:delete', 'func', 4, 0, 94, '2023-07-19 22:26:53', '2024-06-17 22:07:11', NULL);
INSERT INTO `sync_auth_func` VALUES (140, '启用禁用', 'sync:plan:updateStatus', 'func', 7, 0, 94, '2023-09-03 13:19:10', '2023-09-03 15:20:12', NULL);
INSERT INTO `sync_auth_func` VALUES (141, '消息列表', 'sync_message_page', 'btn', 1, 0, 97, '2023-09-03 19:10:00', '2024-10-07 16:31:37', '2024-10-07 17:12:54');
INSERT INTO `sync_auth_func` VALUES (142, '记录列表', 'sync:result:page', 'func', 1, 0, 95, '2023-09-03 19:26:31', '2023-09-03 19:26:31', NULL);
INSERT INTO `sync_auth_func` VALUES (143, '标记成功', 'sync_result_sign_success', 'btn', 2, 0, 95, '2023-09-03 19:32:55', '2024-10-07 21:45:54', '2024-10-07 21:45:54');
INSERT INTO `sync_auth_func` VALUES (144, '重新同步', 'sync:result:repeatSync', 'func', 3, 0, 95, '2023-09-03 19:33:22', '2023-09-03 19:33:22', NULL);
INSERT INTO `sync_auth_func` VALUES (145, '校验结果', 'sync_result_valid_result_data', 'btn', 4, 0, 95, '2023-09-03 19:33:48', '2024-10-07 16:36:46', '2024-10-07 16:36:47');
INSERT INTO `sync_auth_func` VALUES (156, '功能关联', 'auth:resc:funcSearch', 'func', 1, 0, 6, '2023-09-17 13:18:24', '2023-09-17 13:18:24', NULL);
INSERT INTO `sync_auth_func` VALUES (160, '操作日志', 'auth_log_view', 'dir', 7, 0, 2, '2023-09-28 11:05:24', '2024-10-07 22:06:34', '2024-10-07 22:06:35');
INSERT INTO `sync_auth_func` VALUES (161, '列表查询', 'auth_log_page', 'btn', 1, 0, 160, '2023-09-28 11:06:21', '2024-10-07 16:31:32', '2024-10-07 17:13:00');
INSERT INTO `sync_auth_func` VALUES (162, '资源刷新', 'auth:resc:refresh', 'func', 8, 0, 6, '2023-09-28 11:51:58', '2023-09-28 11:51:58', NULL);
INSERT INTO `sync_auth_func` VALUES (171, '回调列表', 'sync:callback:page', 'func', 1, 0, 96, '2023-11-09 20:35:55', '2023-11-09 20:35:55', NULL);
INSERT INTO `sync_auth_func` VALUES (172, '重新回调', 'sync_callback_repeat', 'btn', 3, 0, 96, '2023-11-09 20:36:32', '2024-10-07 17:22:29', '2024-10-07 17:22:29');
INSERT INTO `sync_auth_func` VALUES (173, '计划刷新', 'sync_plan_refresh', 'btn', 3, 0, 94, '2023-11-09 20:42:43', '2024-10-07 17:26:45', '2024-10-07 17:26:46');

-- ----------------------------
-- Table structure for sync_auth_func_resc
-- ----------------------------
DROP TABLE IF EXISTS `sync_auth_func_resc`;
CREATE TABLE `sync_auth_func_resc`  (
  `func_resc_id` int(11) NOT NULL COMMENT '主键',
  `func_id` int(11) NOT NULL COMMENT '功能id',
  `resc_id` int(11) NOT NULL COMMENT '资源id',
  `sync_time` datetime NULL DEFAULT NULL COMMENT '同步时间',
  PRIMARY KEY (`func_resc_id`) USING BTREE,
  INDEX `idx_func_id`(`func_id`) USING BTREE,
  INDEX `idx_resc_id`(`resc_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sync_auth_func_resc
-- ----------------------------
INSERT INTO `sync_auth_func_resc` VALUES (307, 27, 24, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (312, 73, 39, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (313, 72, 19, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (322, 11, 18, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (329, 13, 20, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (330, 13, 41, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (339, 8, 15, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (382, 62, 9, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (383, 62, 10, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (387, 26, 21, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (390, 55, 30, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (391, 55, 29, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (403, 14, 32, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (404, 74, 40, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (405, 9, 16, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (406, 9, 17, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (410, 12, 33, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (411, 12, 34, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (456, 102, 30, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (457, 102, 44, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (458, 84, 42, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (459, 63, 35, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (460, 63, 36, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (461, 57, 1, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (462, 57, 11, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (463, 129, 12, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (464, 129, 14, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (468, 128, 45, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (470, 56, 23, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (471, 56, 22, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (474, 106, 38, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (475, 106, 43, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (477, 105, 37, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (505, 61, 3, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (510, 161, 62, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (511, 162, 63, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (521, 60, 4, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (522, 60, 2, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (523, 60, 11, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (524, 75, 27, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (526, 142, 73, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (527, 143, 74, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (528, 144, 76, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (529, 145, 75, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (530, 141, 69, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (531, 171, 78, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (532, 172, 79, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (533, 136, 66, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (534, 137, 71, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (535, 138, 67, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (536, 138, 70, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (537, 139, 72, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (538, 140, 68, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (539, 173, 77, NULL);
INSERT INTO `sync_auth_func_resc` VALUES (540, 54, 31, NULL);

-- ----------------------------
-- Table structure for sync_auth_log
-- ----------------------------
DROP TABLE IF EXISTS `sync_auth_log`;
CREATE TABLE `sync_auth_log`  (
  `log_id` int(11) NOT NULL COMMENT '日志id',
  `log_info` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日志内容',
  `log_module` int(11) NOT NULL COMMENT '模块类型',
  `log_level` int(11) NOT NULL COMMENT '日志等级',
  `ip_address` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ip地址',
  `device_info` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备信息',
  `location` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '位置',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '操作人id',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人账号',
  `real_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人姓名',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `sync_time` datetime NULL DEFAULT NULL COMMENT '同步时间',
  PRIMARY KEY (`log_id`) USING BTREE,
  INDEX `idx_log_module`(`log_module`) USING BTREE,
  INDEX `idx_user_id_name`(`user_id`, `username`) USING BTREE,
  INDEX `idx_log_level`(`log_level`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sync_auth_log
-- ----------------------------
INSERT INTO `sync_auth_log` VALUES (585, '更新用户信息', 0, 1, '192.168.2.108', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-08-05 16:46:20', '2024-09-28 09:42:32');
INSERT INTO `sync_auth_log` VALUES (586, '用户登录系统', 0, 0, '192.168.2.98', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-19 14:45:39', '2024-09-19 15:01:24');
INSERT INTO `sync_auth_log` VALUES (587, '用户登录系统', 0, 0, '192.168.2.98', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-20 14:12:12', '2024-09-20 14:18:03');
INSERT INTO `sync_auth_log` VALUES (589, '用户登录系统', 0, 0, '192.168.2.98', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-20 14:20:08', '2024-09-20 14:20:08');
INSERT INTO `sync_auth_log` VALUES (590, '更新资源信息', 2, 1, '192.168.2.98', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-20 14:21:20', '2024-09-20 14:21:20');
INSERT INTO `sync_auth_log` VALUES (591, '用户登录系统', 0, 0, '192.168.2.98', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-20 16:53:34', '2024-09-20 16:53:36');
INSERT INTO `sync_auth_log` VALUES (592, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '章宇康', '2024-09-21 11:32:17', '2024-09-21 11:32:17');
INSERT INTO `sync_auth_log` VALUES (593, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '章宇康', '2024-09-21 11:32:19', '2024-09-21 11:32:19');
INSERT INTO `sync_auth_log` VALUES (594, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '章宇康', '2024-09-21 11:32:24', '2024-09-21 11:32:25');
INSERT INTO `sync_auth_log` VALUES (595, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '章宇康', '2024-09-21 11:32:27', '2024-09-21 11:32:28');
INSERT INTO `sync_auth_log` VALUES (596, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '章宇康', '2024-09-21 11:32:57', '2024-09-21 11:32:58');
INSERT INTO `sync_auth_log` VALUES (597, '更新资源信息', 2, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '章宇康', '2024-09-21 11:33:19', '2024-09-21 11:33:19');
INSERT INTO `sync_auth_log` VALUES (598, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '章宇康', '2024-09-21 11:33:37', '2024-09-21 11:33:37');
INSERT INTO `sync_auth_log` VALUES (599, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '章宇康', '2024-09-21 11:33:38', '2024-09-21 11:33:39');
INSERT INTO `sync_auth_log` VALUES (600, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '章宇康', '2024-09-21 11:33:42', '2024-09-21 11:33:42');
INSERT INTO `sync_auth_log` VALUES (601, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '章宇康', '2024-09-21 11:33:43', '2024-09-21 11:33:44');
INSERT INTO `sync_auth_log` VALUES (602, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '章宇康', '2024-09-21 11:34:21', '2024-09-21 11:34:22');
INSERT INTO `sync_auth_log` VALUES (603, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '章宇康', '2024-09-21 11:47:32', '2024-09-21 11:47:33');
INSERT INTO `sync_auth_log` VALUES (604, '更新角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '章宇康', '2024-09-21 11:47:35', '2024-09-21 11:47:35');
INSERT INTO `sync_auth_log` VALUES (605, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '章宇康', '2024-09-21 11:54:08', '2024-09-21 11:54:08');
INSERT INTO `sync_auth_log` VALUES (606, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 14:35:49', '2024-09-21 14:35:50');
INSERT INTO `sync_auth_log` VALUES (607, '更新资源信息', 2, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 14:36:29', '2024-09-21 14:36:30');
INSERT INTO `sync_auth_log` VALUES (608, '更新资源信息', 2, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 14:36:45', '2024-09-21 14:36:45');
INSERT INTO `sync_auth_log` VALUES (609, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 20:28:01', '2024-09-21 20:28:02');
INSERT INTO `sync_auth_log` VALUES (610, '新增功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:28:25', '2024-09-21 21:28:26');
INSERT INTO `sync_auth_log` VALUES (611, '新增功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:29:41', '2024-09-21 21:29:42');
INSERT INTO `sync_auth_log` VALUES (612, '新增功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:29:59', '2024-09-21 21:30:00');
INSERT INTO `sync_auth_log` VALUES (613, '新增功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:31:22', '2024-09-21 21:31:23');
INSERT INTO `sync_auth_log` VALUES (614, '删除功能信息', 3, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:40:09', '2024-09-21 21:40:10');
INSERT INTO `sync_auth_log` VALUES (615, '删除功能信息', 3, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:40:11', '2024-09-21 21:40:12');
INSERT INTO `sync_auth_log` VALUES (616, '删除功能信息', 3, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:40:13', '2024-09-21 21:40:14');
INSERT INTO `sync_auth_log` VALUES (617, '删除功能信息', 3, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:40:16', '2024-09-21 21:40:16');
INSERT INTO `sync_auth_log` VALUES (618, '新增功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:46:18', '2024-09-21 21:46:18');
INSERT INTO `sync_auth_log` VALUES (619, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:48:23', '2024-09-21 21:48:23');
INSERT INTO `sync_auth_log` VALUES (620, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:49:11', '2024-09-21 21:49:11');
INSERT INTO `sync_auth_log` VALUES (621, '删除功能信息', 3, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:49:14', '2024-09-21 21:49:15');
INSERT INTO `sync_auth_log` VALUES (622, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:49:45', '2024-09-21 21:49:46');
INSERT INTO `sync_auth_log` VALUES (623, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:50:37', '2024-09-21 21:50:37');
INSERT INTO `sync_auth_log` VALUES (624, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:50:45', '2024-09-21 21:50:45');
INSERT INTO `sync_auth_log` VALUES (625, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:50:54', '2024-09-21 21:50:55');
INSERT INTO `sync_auth_log` VALUES (626, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:51:03', '2024-09-21 21:51:03');
INSERT INTO `sync_auth_log` VALUES (627, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:51:11', '2024-09-21 21:51:11');
INSERT INTO `sync_auth_log` VALUES (628, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:51:16', '2024-09-21 21:51:17');
INSERT INTO `sync_auth_log` VALUES (629, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:51:28', '2024-09-21 21:51:29');
INSERT INTO `sync_auth_log` VALUES (630, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:51:39', '2024-09-21 21:51:40');
INSERT INTO `sync_auth_log` VALUES (631, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:51:46', '2024-09-21 21:51:47');
INSERT INTO `sync_auth_log` VALUES (632, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:51:55', '2024-09-21 21:51:56');
INSERT INTO `sync_auth_log` VALUES (633, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:52:17', '2024-09-21 21:52:17');
INSERT INTO `sync_auth_log` VALUES (634, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:52:21', '2024-09-21 21:52:22');
INSERT INTO `sync_auth_log` VALUES (635, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:54:02', '2024-09-21 21:54:02');
INSERT INTO `sync_auth_log` VALUES (636, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:54:07', '2024-09-21 21:54:08');
INSERT INTO `sync_auth_log` VALUES (637, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:54:56', '2024-09-21 21:54:56');
INSERT INTO `sync_auth_log` VALUES (638, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:55:07', '2024-09-21 21:55:08');
INSERT INTO `sync_auth_log` VALUES (639, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:55:18', '2024-09-21 21:55:19');
INSERT INTO `sync_auth_log` VALUES (640, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:55:27', '2024-09-21 21:55:28');
INSERT INTO `sync_auth_log` VALUES (641, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:55:35', '2024-09-21 21:55:36');
INSERT INTO `sync_auth_log` VALUES (642, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:57:14', '2024-09-21 21:57:15');
INSERT INTO `sync_auth_log` VALUES (643, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:57:22', '2024-09-21 21:57:23');
INSERT INTO `sync_auth_log` VALUES (644, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:57:30', '2024-09-21 21:57:31');
INSERT INTO `sync_auth_log` VALUES (645, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:57:42', '2024-09-21 21:57:42');
INSERT INTO `sync_auth_log` VALUES (646, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:57:51', '2024-09-21 21:57:51');
INSERT INTO `sync_auth_log` VALUES (647, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:58:05', '2024-09-21 21:58:05');
INSERT INTO `sync_auth_log` VALUES (648, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:58:16', '2024-09-21 21:58:16');
INSERT INTO `sync_auth_log` VALUES (649, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:58:28', '2024-09-21 21:58:29');
INSERT INTO `sync_auth_log` VALUES (650, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:58:40', '2024-09-21 21:58:41');
INSERT INTO `sync_auth_log` VALUES (651, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:58:49', '2024-09-21 21:58:50');
INSERT INTO `sync_auth_log` VALUES (652, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:59:01', '2024-09-21 21:59:02');
INSERT INTO `sync_auth_log` VALUES (653, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:59:12', '2024-09-21 21:59:12');
INSERT INTO `sync_auth_log` VALUES (654, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:59:26', '2024-09-21 21:59:27');
INSERT INTO `sync_auth_log` VALUES (655, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:59:40', '2024-09-21 21:59:40');
INSERT INTO `sync_auth_log` VALUES (656, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 21:59:51', '2024-09-21 21:59:51');
INSERT INTO `sync_auth_log` VALUES (657, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:00:14', '2024-09-21 22:00:15');
INSERT INTO `sync_auth_log` VALUES (658, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:00:32', '2024-09-21 22:00:33');
INSERT INTO `sync_auth_log` VALUES (659, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:00:42', '2024-09-21 22:00:42');
INSERT INTO `sync_auth_log` VALUES (660, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:00:55', '2024-09-21 22:00:55');
INSERT INTO `sync_auth_log` VALUES (661, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:01:10', '2024-09-21 22:01:11');
INSERT INTO `sync_auth_log` VALUES (662, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:01:22', '2024-09-21 22:01:23');
INSERT INTO `sync_auth_log` VALUES (663, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:01:32', '2024-09-21 22:01:32');
INSERT INTO `sync_auth_log` VALUES (664, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:01:40', '2024-09-21 22:01:40');
INSERT INTO `sync_auth_log` VALUES (665, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:01:50', '2024-09-21 22:01:51');
INSERT INTO `sync_auth_log` VALUES (666, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:02:07', '2024-09-21 22:02:08');
INSERT INTO `sync_auth_log` VALUES (667, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:02:14', '2024-09-21 22:02:14');
INSERT INTO `sync_auth_log` VALUES (668, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:02:21', '2024-09-21 22:02:21');
INSERT INTO `sync_auth_log` VALUES (669, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:02:27', '2024-09-21 22:02:27');
INSERT INTO `sync_auth_log` VALUES (670, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:02:34', '2024-09-21 22:02:35');
INSERT INTO `sync_auth_log` VALUES (671, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:02:42', '2024-09-21 22:02:43');
INSERT INTO `sync_auth_log` VALUES (672, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:02:48', '2024-09-21 22:02:49');
INSERT INTO `sync_auth_log` VALUES (673, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:02:55', '2024-09-21 22:02:56');
INSERT INTO `sync_auth_log` VALUES (674, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:03:02', '2024-09-21 22:03:02');
INSERT INTO `sync_auth_log` VALUES (675, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:03:08', '2024-09-21 22:03:09');
INSERT INTO `sync_auth_log` VALUES (676, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:03:14', '2024-09-21 22:03:15');
INSERT INTO `sync_auth_log` VALUES (677, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:03:21', '2024-09-21 22:03:21');
INSERT INTO `sync_auth_log` VALUES (678, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:03:27', '2024-09-21 22:03:28');
INSERT INTO `sync_auth_log` VALUES (679, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:03:34', '2024-09-21 22:03:34');
INSERT INTO `sync_auth_log` VALUES (680, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:04:00', '2024-09-21 22:04:01');
INSERT INTO `sync_auth_log` VALUES (681, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:15:36', '2024-09-21 22:15:37');
INSERT INTO `sync_auth_log` VALUES (682, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:36:56', '2024-09-21 22:36:56');
INSERT INTO `sync_auth_log` VALUES (683, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:50:46', '2024-09-21 22:50:47');
INSERT INTO `sync_auth_log` VALUES (684, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:51:23', '2024-09-21 22:51:23');
INSERT INTO `sync_auth_log` VALUES (685, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:51:25', '2024-09-21 22:51:25');
INSERT INTO `sync_auth_log` VALUES (686, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:51:37', '2024-09-21 22:51:37');
INSERT INTO `sync_auth_log` VALUES (687, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:51:40', '2024-09-21 22:51:41');
INSERT INTO `sync_auth_log` VALUES (688, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:51:48', '2024-09-21 22:51:49');
INSERT INTO `sync_auth_log` VALUES (689, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:51:51', '2024-09-21 22:51:52');
INSERT INTO `sync_auth_log` VALUES (690, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:52:13', '2024-09-21 22:52:14');
INSERT INTO `sync_auth_log` VALUES (691, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:52:24', '2024-09-21 22:52:24');
INSERT INTO `sync_auth_log` VALUES (692, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:52:49', '2024-09-21 22:52:49');
INSERT INTO `sync_auth_log` VALUES (693, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-21 22:53:12', '2024-09-21 22:53:12');
INSERT INTO `sync_auth_log` VALUES (694, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-22 10:11:46', '2024-09-22 10:11:47');
INSERT INTO `sync_auth_log` VALUES (695, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-22 10:12:32', '2024-09-22 10:12:32');
INSERT INTO `sync_auth_log` VALUES (696, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-22 11:21:25', '2024-09-22 11:21:26');
INSERT INTO `sync_auth_log` VALUES (697, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-22 11:22:06', '2024-09-22 11:22:06');
INSERT INTO `sync_auth_log` VALUES (698, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-22 11:23:56', '2024-09-22 11:23:57');
INSERT INTO `sync_auth_log` VALUES (699, '更新资源信息', 2, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-22 13:07:02', '2024-09-22 13:07:03');
INSERT INTO `sync_auth_log` VALUES (700, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-23 21:31:17', '2024-09-23 21:33:02');
INSERT INTO `sync_auth_log` VALUES (701, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-23 21:31:23', '2024-09-23 21:33:02');
INSERT INTO `sync_auth_log` VALUES (711, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-23 21:45:39', '2024-09-23 21:45:40');
INSERT INTO `sync_auth_log` VALUES (712, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-23 21:47:45', '2024-09-23 21:47:46');
INSERT INTO `sync_auth_log` VALUES (716, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-23 22:04:01', '2024-09-23 22:04:01');
INSERT INTO `sync_auth_log` VALUES (723, '更新资源信息', 2, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-23 22:04:35', '2024-09-23 22:04:36');
INSERT INTO `sync_auth_log` VALUES (724, '更新资源信息', 2, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-23 22:04:38', '2024-09-23 22:04:39');
INSERT INTO `sync_auth_log` VALUES (725, '更新资源信息', 2, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-23 22:04:40', '2024-09-23 22:04:41');
INSERT INTO `sync_auth_log` VALUES (726, '更新资源信息', 2, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-23 22:04:46', '2024-09-23 22:04:47');
INSERT INTO `sync_auth_log` VALUES (736, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-23 22:45:37', '2024-09-23 22:45:37');
INSERT INTO `sync_auth_log` VALUES (738, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 19:46:33', '2024-09-24 19:46:34');
INSERT INTO `sync_auth_log` VALUES (739, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 19:46:44', '2024-09-24 19:46:44');
INSERT INTO `sync_auth_log` VALUES (740, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 20:26:29', '2024-09-24 20:26:29');
INSERT INTO `sync_auth_log` VALUES (741, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 20:26:30', '2024-09-24 20:26:30');
INSERT INTO `sync_auth_log` VALUES (742, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 20:26:31', '2024-09-24 20:26:31');
INSERT INTO `sync_auth_log` VALUES (743, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 20:26:41', '2024-09-24 20:26:42');
INSERT INTO `sync_auth_log` VALUES (744, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 20:26:52', '2024-09-24 20:26:52');
INSERT INTO `sync_auth_log` VALUES (745, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 20:27:11', '2024-09-24 20:27:12');
INSERT INTO `sync_auth_log` VALUES (746, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 20:27:16', '2024-09-24 20:27:16');
INSERT INTO `sync_auth_log` VALUES (747, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 22:32:42', '2024-09-24 22:32:43');
INSERT INTO `sync_auth_log` VALUES (748, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 22:32:46', '2024-09-24 22:32:46');
INSERT INTO `sync_auth_log` VALUES (749, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 22:32:51', '2024-09-24 22:32:51');
INSERT INTO `sync_auth_log` VALUES (750, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 22:32:58', '2024-09-24 22:32:59');
INSERT INTO `sync_auth_log` VALUES (751, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 22:33:57', '2024-09-24 22:33:58');
INSERT INTO `sync_auth_log` VALUES (752, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 22:34:04', '2024-09-24 22:34:05');
INSERT INTO `sync_auth_log` VALUES (753, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 22:34:14', '2024-09-24 22:34:15');
INSERT INTO `sync_auth_log` VALUES (754, '更新资源信息', 2, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 22:34:22', '2024-09-24 22:34:22');
INSERT INTO `sync_auth_log` VALUES (755, '更新角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 22:34:30', '2024-09-24 22:34:30');
INSERT INTO `sync_auth_log` VALUES (756, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 22:34:39', '2024-09-24 22:34:39');
INSERT INTO `sync_auth_log` VALUES (757, '新增用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 22:53:25', '2024-09-24 22:53:26');
INSERT INTO `sync_auth_log` VALUES (758, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 22:53:35', '2024-09-24 22:53:36');
INSERT INTO `sync_auth_log` VALUES (759, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 22:53:43', '2024-09-24 22:53:43');
INSERT INTO `sync_auth_log` VALUES (760, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 22:53:50', '2024-09-24 22:53:50');
INSERT INTO `sync_auth_log` VALUES (761, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 22:54:30', '2024-09-24 22:54:31');
INSERT INTO `sync_auth_log` VALUES (762, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 22:54:37', '2024-09-24 22:54:38');
INSERT INTO `sync_auth_log` VALUES (763, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 22:55:32', '2024-09-24 22:55:32');
INSERT INTO `sync_auth_log` VALUES (764, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 22:55:45', '2024-09-24 22:55:45');
INSERT INTO `sync_auth_log` VALUES (765, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 22:56:15', '2024-09-24 22:56:15');
INSERT INTO `sync_auth_log` VALUES (766, '更新角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 22:58:15', '2024-09-24 22:58:15');
INSERT INTO `sync_auth_log` VALUES (767, '更新角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 22:58:21', '2024-09-24 22:58:22');
INSERT INTO `sync_auth_log` VALUES (768, '更新角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 22:58:31', '2024-09-24 22:58:32');
INSERT INTO `sync_auth_log` VALUES (769, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 23:05:53', '2024-09-24 23:05:54');
INSERT INTO `sync_auth_log` VALUES (770, '更新资源信息', 2, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 23:06:10', '2024-09-24 23:06:10');
INSERT INTO `sync_auth_log` VALUES (771, '更新资源信息', 2, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 23:06:15', '2024-09-24 23:06:15');
INSERT INTO `sync_auth_log` VALUES (772, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 23:09:50', '2024-09-24 23:09:50');
INSERT INTO `sync_auth_log` VALUES (773, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 23:10:25', '2024-09-24 23:10:25');
INSERT INTO `sync_auth_log` VALUES (774, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 23:10:30', '2024-09-24 23:10:30');
INSERT INTO `sync_auth_log` VALUES (775, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 23:22:57', '2024-09-24 23:22:57');
INSERT INTO `sync_auth_log` VALUES (776, '更新角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-24 23:23:05', '2024-09-24 23:23:05');
INSERT INTO `sync_auth_log` VALUES (777, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-25 00:16:30', '2024-09-25 00:16:30');
INSERT INTO `sync_auth_log` VALUES (778, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-25 20:25:22', '2024-09-25 20:25:22');
INSERT INTO `sync_auth_log` VALUES (779, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-25 21:01:04', '2024-09-25 21:01:05');
INSERT INTO `sync_auth_log` VALUES (780, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-26 21:56:21', '2024-09-26 21:56:22');
INSERT INTO `sync_auth_log` VALUES (781, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-26 21:56:48', '2024-09-26 21:56:49');
INSERT INTO `sync_auth_log` VALUES (782, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-26 21:56:55', '2024-09-26 21:56:56');
INSERT INTO `sync_auth_log` VALUES (783, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-26 21:58:53', '2024-09-26 21:58:54');
INSERT INTO `sync_auth_log` VALUES (789, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-26 22:01:52', '2024-09-26 22:01:53');
INSERT INTO `sync_auth_log` VALUES (790, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-26 22:03:28', '2024-09-26 22:03:29');
INSERT INTO `sync_auth_log` VALUES (791, '更新资源信息', 2, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-26 22:07:04', '2024-09-26 22:07:04');
INSERT INTO `sync_auth_log` VALUES (792, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-26 22:13:24', '2024-09-26 22:13:24');
INSERT INTO `sync_auth_log` VALUES (793, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-26 22:13:29', '2024-09-26 22:13:29');
INSERT INTO `sync_auth_log` VALUES (796, '更新角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 21:11:17', '2024-09-27 21:11:17');
INSERT INTO `sync_auth_log` VALUES (797, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 21:11:20', '2024-09-27 21:11:21');
INSERT INTO `sync_auth_log` VALUES (798, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 21:14:27', '2024-09-27 21:14:27');
INSERT INTO `sync_auth_log` VALUES (799, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 21:14:31', '2024-09-27 21:14:31');
INSERT INTO `sync_auth_log` VALUES (800, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 21:14:34', '2024-09-27 21:14:34');
INSERT INTO `sync_auth_log` VALUES (801, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 21:14:36', '2024-09-27 21:14:37');
INSERT INTO `sync_auth_log` VALUES (802, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 21:14:45', '2024-09-27 21:14:46');
INSERT INTO `sync_auth_log` VALUES (803, '更新资源信息', 2, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 21:25:16', '2024-09-27 21:25:17');
INSERT INTO `sync_auth_log` VALUES (804, '更新资源信息', 2, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 21:25:24', '2024-09-27 21:25:24');
INSERT INTO `sync_auth_log` VALUES (805, '更新资源信息', 2, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 21:40:03', '2024-09-27 21:40:04');
INSERT INTO `sync_auth_log` VALUES (806, '更新资源信息', 2, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 21:40:16', '2024-09-27 21:50:30');
INSERT INTO `sync_auth_log` VALUES (807, '用户分配角色', 0, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 21:40:29', '2024-09-27 21:50:36');
INSERT INTO `sync_auth_log` VALUES (808, '用户分配角色', 0, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 21:40:35', '2024-09-27 21:50:36');
INSERT INTO `sync_auth_log` VALUES (809, '用户分配角色', 0, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 21:40:42', '2024-09-27 21:50:30');
INSERT INTO `sync_auth_log` VALUES (810, '用户分配角色', 0, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 21:41:16', '2024-09-27 21:50:30');
INSERT INTO `sync_auth_log` VALUES (811, '用户分配角色', 0, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 21:41:20', '2024-09-27 21:50:30');
INSERT INTO `sync_auth_log` VALUES (812, '用户分配角色', 0, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 21:41:26', '2024-09-27 21:50:30');
INSERT INTO `sync_auth_log` VALUES (813, '用户分配角色', 0, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 21:53:21', '2024-09-27 22:00:30');
INSERT INTO `sync_auth_log` VALUES (814, '用户分配角色', 0, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 21:54:48', '2024-09-27 22:00:30');
INSERT INTO `sync_auth_log` VALUES (815, '禁用用户账号', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 21:54:59', '2024-09-27 22:00:30');
INSERT INTO `sync_auth_log` VALUES (816, '启用用户账号', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 21:55:04', '2024-09-27 22:05:31');
INSERT INTO `sync_auth_log` VALUES (817, '用户分配角色', 0, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 21:55:51', '2024-09-27 22:05:30');
INSERT INTO `sync_auth_log` VALUES (818, '用户分配角色', 0, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 21:55:55', '2024-09-27 22:05:30');
INSERT INTO `sync_auth_log` VALUES (819, '用户分配角色', 0, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 21:56:02', '2024-09-27 22:05:30');
INSERT INTO `sync_auth_log` VALUES (820, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 21:58:49', '2024-09-27 22:05:30');
INSERT INTO `sync_auth_log` VALUES (821, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 21:58:53', '2024-09-27 22:05:30');
INSERT INTO `sync_auth_log` VALUES (823, '禁用用户账号', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 22:06:14', '2024-09-27 22:10:01');
INSERT INTO `sync_auth_log` VALUES (825, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 22:06:54', '2024-09-27 22:09:37');
INSERT INTO `sync_auth_log` VALUES (827, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 22:57:52', '2024-09-27 22:57:53');
INSERT INTO `sync_auth_log` VALUES (828, '用户分配角色', 0, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 23:01:30', '2024-09-27 23:01:31');
INSERT INTO `sync_auth_log` VALUES (829, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 23:12:00', '2024-09-27 23:12:00');
INSERT INTO `sync_auth_log` VALUES (830, '用户分配角色', 0, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 23:22:24', '2024-09-27 23:22:25');
INSERT INTO `sync_auth_log` VALUES (831, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 23:24:12', '2024-09-27 23:24:13');
INSERT INTO `sync_auth_log` VALUES (832, '用户分配角色', 0, 2, '192.168.10.1', 'Windows', '内网IP', 256, 'baiheng', '柏衡', '2024-09-27 23:30:31', '2024-09-27 23:40:30');
INSERT INTO `sync_auth_log` VALUES (833, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 23:36:34', '2024-09-27 23:45:30');
INSERT INTO `sync_auth_log` VALUES (834, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 23:36:42', '2024-09-27 23:45:30');
INSERT INTO `sync_auth_log` VALUES (835, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 23:37:12', '2024-09-27 23:45:30');
INSERT INTO `sync_auth_log` VALUES (836, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 23:40:22', '2024-09-27 23:50:30');
INSERT INTO `sync_auth_log` VALUES (837, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 23:43:09', '2024-09-27 23:50:30');
INSERT INTO `sync_auth_log` VALUES (838, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 23:43:19', '2024-09-27 23:50:30');
INSERT INTO `sync_auth_log` VALUES (839, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 23:45:28', '2024-09-27 23:55:30');
INSERT INTO `sync_auth_log` VALUES (840, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 23:45:46', '2024-09-28 09:09:03');
INSERT INTO `sync_auth_log` VALUES (841, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 23:45:52', '2024-09-27 23:55:36');
INSERT INTO `sync_auth_log` VALUES (842, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 23:46:23', '2024-09-27 23:55:36');
INSERT INTO `sync_auth_log` VALUES (843, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 23:47:19', '2024-09-27 23:55:36');
INSERT INTO `sync_auth_log` VALUES (844, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 23:47:26', '2024-09-27 23:55:36');
INSERT INTO `sync_auth_log` VALUES (845, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 23:47:32', '2024-09-27 23:55:36');
INSERT INTO `sync_auth_log` VALUES (846, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 23:47:39', '2024-09-27 23:55:30');
INSERT INTO `sync_auth_log` VALUES (847, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 23:48:35', '2024-09-27 23:55:30');
INSERT INTO `sync_auth_log` VALUES (848, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 23:48:42', '2024-09-27 23:55:30');
INSERT INTO `sync_auth_log` VALUES (849, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-27 23:48:49', '2024-09-27 23:55:30');
INSERT INTO `sync_auth_log` VALUES (850, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 09:09:17', '2024-09-28 09:09:18');
INSERT INTO `sync_auth_log` VALUES (851, '用户分配角色', 0, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 09:09:51', '2024-09-28 09:09:52');
INSERT INTO `sync_auth_log` VALUES (857, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 8, 'wenhaimei', '温海媚', '2024-09-28 09:45:19', '2024-09-28 09:45:20');
INSERT INTO `sync_auth_log` VALUES (858, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 8, 'wenhaimei', '温海媚', '2024-09-28 09:49:56', '2024-09-28 09:49:57');
INSERT INTO `sync_auth_log` VALUES (859, '切换用户登录', 0, 0, '192.168.10.1', 'Windows', '内网IP', 770, 'Zeriseboom', 'Zeriseboom', '2024-09-28 09:55:10', '2024-09-28 09:55:11');
INSERT INTO `sync_auth_log` VALUES (860, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 09:55:21', '2024-09-28 09:55:21');
INSERT INTO `sync_auth_log` VALUES (861, '更新资源信息', 2, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 09:57:33', '2024-09-28 09:57:34');
INSERT INTO `sync_auth_log` VALUES (862, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 10:06:52', '2024-09-28 10:06:53');
INSERT INTO `sync_auth_log` VALUES (863, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 11:39:12', '2024-09-28 11:39:13');
INSERT INTO `sync_auth_log` VALUES (864, '切换用户登录', 0, 0, '192.168.10.1', 'Windows', '内网IP', 514, 'jiangminxiang', '江民响', '2024-09-28 11:41:45', '2024-09-28 11:41:45');
INSERT INTO `sync_auth_log` VALUES (865, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 11:42:29', '2024-09-28 11:42:29');
INSERT INTO `sync_auth_log` VALUES (866, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 12:05:46', '2024-09-28 12:05:47');
INSERT INTO `sync_auth_log` VALUES (867, '用户分配角色', 0, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 12:41:02', '2024-09-28 12:41:02');
INSERT INTO `sync_auth_log` VALUES (868, '切换用户登录', 0, 0, '192.168.10.1', 'Windows', '内网IP', 515, 'yutiansheng', '余天胜', '2024-09-28 12:41:09', '2024-09-28 12:41:09');
INSERT INTO `sync_auth_log` VALUES (869, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 12:41:21', '2024-09-28 12:41:21');
INSERT INTO `sync_auth_log` VALUES (870, '更新角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 13:23:12', '2024-09-28 13:23:12');
INSERT INTO `sync_auth_log` VALUES (871, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 13:24:01', '2024-09-28 13:24:02');
INSERT INTO `sync_auth_log` VALUES (872, '用户分配角色', 0, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 13:31:30', '2024-09-28 13:31:30');
INSERT INTO `sync_auth_log` VALUES (873, '用户分配角色', 0, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 13:31:34', '2024-09-28 13:31:34');
INSERT INTO `sync_auth_log` VALUES (874, '用户分配角色', 0, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 13:31:53', '2024-09-28 13:31:54');
INSERT INTO `sync_auth_log` VALUES (875, '用户分配角色', 0, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 13:32:07', '2024-09-28 13:32:07');
INSERT INTO `sync_auth_log` VALUES (876, '用户分配角色', 0, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 13:32:12', '2024-09-28 13:32:13');
INSERT INTO `sync_auth_log` VALUES (877, '切换用户登录', 0, 0, '192.168.10.1', 'Windows', '内网IP', 769, 'caichunzhen', '蔡纯真', '2024-09-28 13:32:18', '2024-09-28 13:32:19');
INSERT INTO `sync_auth_log` VALUES (878, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 769, 'caichunzhen', '蔡纯真', '2024-09-28 13:32:28', '2024-09-28 13:32:29');
INSERT INTO `sync_auth_log` VALUES (879, '新增角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 769, 'caichunzhen', '蔡纯真', '2024-09-28 13:38:23', '2024-09-28 13:38:24');
INSERT INTO `sync_auth_log` VALUES (880, '角色分配用户', 1, 2, '192.168.10.1', 'Windows', '内网IP', 769, 'caichunzhen', '蔡纯真', '2024-09-28 13:38:30', '2024-09-28 13:38:30');
INSERT INTO `sync_auth_log` VALUES (881, '更新角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 769, 'caichunzhen', '蔡纯真', '2024-09-28 13:38:48', '2024-09-28 13:38:48');
INSERT INTO `sync_auth_log` VALUES (882, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 769, 'caichunzhen', '蔡纯真', '2024-09-28 13:42:44', '2024-09-28 13:42:45');
INSERT INTO `sync_auth_log` VALUES (883, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 769, 'caichunzhen', '蔡纯真', '2024-09-28 13:42:53', '2024-09-28 13:42:53');
INSERT INTO `sync_auth_log` VALUES (884, '删除用户信息', 0, 2, '192.168.10.1', 'Windows', '内网IP', 769, 'caichunzhen', '蔡纯真', '2024-09-28 13:42:56', '2024-09-28 13:42:56');
INSERT INTO `sync_auth_log` VALUES (885, '新增功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 769, 'caichunzhen', '蔡纯真', '2024-09-28 13:48:17', '2024-09-28 13:48:17');
INSERT INTO `sync_auth_log` VALUES (886, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 769, 'caichunzhen', '蔡纯真', '2024-09-28 13:48:43', '2024-09-28 13:48:43');
INSERT INTO `sync_auth_log` VALUES (887, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 769, 'caichunzhen', '蔡纯真', '2024-09-28 13:48:53', '2024-09-28 13:48:54');
INSERT INTO `sync_auth_log` VALUES (888, '新增功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 769, 'caichunzhen', '蔡纯真', '2024-09-28 13:49:15', '2024-09-28 13:49:15');
INSERT INTO `sync_auth_log` VALUES (889, '新增功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 769, 'caichunzhen', '蔡纯真', '2024-09-28 13:49:36', '2024-09-28 13:49:36');
INSERT INTO `sync_auth_log` VALUES (890, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 769, 'caichunzhen', '蔡纯真', '2024-09-28 14:00:02', '2024-09-28 14:00:03');
INSERT INTO `sync_auth_log` VALUES (891, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 769, 'caichunzhen', '蔡纯真', '2024-09-28 14:06:34', '2024-09-28 14:06:35');
INSERT INTO `sync_auth_log` VALUES (892, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 14:23:33', '2024-09-28 14:23:33');
INSERT INTO `sync_auth_log` VALUES (893, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 14:25:32', '2024-09-28 14:25:32');
INSERT INTO `sync_auth_log` VALUES (894, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 14:25:43', '2024-09-28 14:25:43');
INSERT INTO `sync_auth_log` VALUES (895, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 14:27:14', '2024-09-28 14:27:15');
INSERT INTO `sync_auth_log` VALUES (896, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 14:30:27', '2024-09-28 14:30:28');
INSERT INTO `sync_auth_log` VALUES (897, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 14:32:17', '2024-09-28 14:32:18');
INSERT INTO `sync_auth_log` VALUES (898, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 14:32:21', '2024-09-28 14:32:22');
INSERT INTO `sync_auth_log` VALUES (899, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 14:33:58', '2024-09-28 14:33:58');
INSERT INTO `sync_auth_log` VALUES (900, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 14:35:30', '2024-09-28 14:35:31');
INSERT INTO `sync_auth_log` VALUES (901, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 14:39:25', '2024-09-28 14:39:26');
INSERT INTO `sync_auth_log` VALUES (902, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 14:42:12', '2024-09-28 14:42:13');
INSERT INTO `sync_auth_log` VALUES (903, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 14:42:49', '2024-09-28 14:42:49');
INSERT INTO `sync_auth_log` VALUES (904, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 14:43:10', '2024-09-28 14:43:10');
INSERT INTO `sync_auth_log` VALUES (905, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 14:44:14', '2024-09-28 14:44:15');
INSERT INTO `sync_auth_log` VALUES (906, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 14:50:43', '2024-09-28 14:50:43');
INSERT INTO `sync_auth_log` VALUES (907, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 14:50:55', '2024-09-28 14:50:56');
INSERT INTO `sync_auth_log` VALUES (908, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 14:55:57', '2024-09-28 14:55:58');
INSERT INTO `sync_auth_log` VALUES (909, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 14:55:59', '2024-09-28 14:55:59');
INSERT INTO `sync_auth_log` VALUES (910, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 14:56:05', '2024-09-28 14:56:06');
INSERT INTO `sync_auth_log` VALUES (911, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 14:56:11', '2024-09-28 14:56:11');
INSERT INTO `sync_auth_log` VALUES (912, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 14:57:22', '2024-09-28 14:57:23');
INSERT INTO `sync_auth_log` VALUES (913, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 14:57:35', '2024-09-28 14:57:35');
INSERT INTO `sync_auth_log` VALUES (914, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 15:37:25', '2024-09-28 15:37:26');
INSERT INTO `sync_auth_log` VALUES (915, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 15:47:41', '2024-09-28 15:47:42');
INSERT INTO `sync_auth_log` VALUES (916, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 15:47:42', '2024-09-28 15:47:43');
INSERT INTO `sync_auth_log` VALUES (917, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 15:59:47', '2024-09-28 15:59:48');
INSERT INTO `sync_auth_log` VALUES (918, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 15:59:52', '2024-09-28 15:59:53');
INSERT INTO `sync_auth_log` VALUES (919, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 16:01:58', '2024-09-28 16:01:59');
INSERT INTO `sync_auth_log` VALUES (920, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 16:02:10', '2024-09-28 16:02:11');
INSERT INTO `sync_auth_log` VALUES (921, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 17:04:36', '2024-09-28 17:04:37');
INSERT INTO `sync_auth_log` VALUES (922, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 17:09:25', '2024-09-28 17:09:25');
INSERT INTO `sync_auth_log` VALUES (923, '切换用户登录', 0, 0, '192.168.10.1', 'Windows', '内网IP', 512, 'zhanglongbiao', '张龙标', '2024-09-28 17:18:31', '2024-09-28 17:18:32');
INSERT INTO `sync_auth_log` VALUES (924, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 512, 'zhanglongbiao', '张龙标', '2024-09-28 20:07:33', '2024-09-28 20:07:33');
INSERT INTO `sync_auth_log` VALUES (925, '用户分配角色', 0, 2, '192.168.10.1', 'Windows', '内网IP', 512, 'zhanglongbiao', '张龙标', '2024-09-28 20:09:34', '2024-09-28 20:09:35');
INSERT INTO `sync_auth_log` VALUES (926, '切换用户登录', 0, 0, '192.168.10.1', 'Windows', '内网IP', 5, 'jasonhu', '胡仁华', '2024-09-28 20:09:41', '2024-09-28 20:09:42');
INSERT INTO `sync_auth_log` VALUES (927, '切换用户登录', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 20:17:58', '2024-09-28 20:17:59');
INSERT INTO `sync_auth_log` VALUES (928, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 23:48:31', '2024-09-28 23:48:32');
INSERT INTO `sync_auth_log` VALUES (929, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 23:49:04', '2024-09-28 23:49:04');
INSERT INTO `sync_auth_log` VALUES (930, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 23:49:14', '2024-09-28 23:49:14');
INSERT INTO `sync_auth_log` VALUES (931, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 23:49:31', '2024-09-28 23:49:32');
INSERT INTO `sync_auth_log` VALUES (932, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 23:49:47', '2024-09-28 23:49:47');
INSERT INTO `sync_auth_log` VALUES (933, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 23:50:29', '2024-09-28 23:50:30');
INSERT INTO `sync_auth_log` VALUES (934, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 23:50:36', '2024-09-28 23:50:37');
INSERT INTO `sync_auth_log` VALUES (935, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 23:50:46', '2024-09-28 23:50:47');
INSERT INTO `sync_auth_log` VALUES (936, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 23:50:55', '2024-09-28 23:50:56');
INSERT INTO `sync_auth_log` VALUES (937, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 23:51:05', '2024-09-28 23:51:06');
INSERT INTO `sync_auth_log` VALUES (938, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 23:51:15', '2024-09-28 23:51:16');
INSERT INTO `sync_auth_log` VALUES (939, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 23:52:01', '2024-09-28 23:52:01');
INSERT INTO `sync_auth_log` VALUES (940, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 23:52:39', '2024-09-28 23:52:40');
INSERT INTO `sync_auth_log` VALUES (941, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 23:53:37', '2024-09-28 23:53:38');
INSERT INTO `sync_auth_log` VALUES (942, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 23:53:48', '2024-09-28 23:53:48');
INSERT INTO `sync_auth_log` VALUES (943, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 23:54:05', '2024-09-28 23:54:06');
INSERT INTO `sync_auth_log` VALUES (944, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 23:54:39', '2024-09-28 23:54:39');
INSERT INTO `sync_auth_log` VALUES (945, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 23:54:59', '2024-09-28 23:55:00');
INSERT INTO `sync_auth_log` VALUES (946, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-28 23:55:30', '2024-09-28 23:55:31');
INSERT INTO `sync_auth_log` VALUES (947, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-29 00:03:15', '2024-09-29 00:03:16');
INSERT INTO `sync_auth_log` VALUES (948, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-29 00:04:59', '2024-09-29 00:04:59');
INSERT INTO `sync_auth_log` VALUES (949, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-29 00:05:17', '2024-09-29 00:05:17');
INSERT INTO `sync_auth_log` VALUES (950, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-29 19:38:59', '2024-09-29 19:39:00');
INSERT INTO `sync_auth_log` VALUES (951, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-29 19:39:22', '2024-09-29 19:39:22');
INSERT INTO `sync_auth_log` VALUES (952, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-29 19:39:31', '2024-09-29 19:39:32');
INSERT INTO `sync_auth_log` VALUES (953, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-29 19:39:42', '2024-09-29 19:39:42');
INSERT INTO `sync_auth_log` VALUES (954, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-29 19:40:30', '2024-09-29 19:40:31');
INSERT INTO `sync_auth_log` VALUES (955, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-29 19:40:40', '2024-09-29 19:40:41');
INSERT INTO `sync_auth_log` VALUES (956, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-29 19:41:04', '2024-09-29 19:41:05');
INSERT INTO `sync_auth_log` VALUES (957, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-29 19:46:43', '2024-09-29 19:46:44');
INSERT INTO `sync_auth_log` VALUES (958, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-29 19:47:01', '2024-09-29 19:47:01');
INSERT INTO `sync_auth_log` VALUES (959, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-29 19:47:14', '2024-09-29 19:47:15');
INSERT INTO `sync_auth_log` VALUES (960, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-29 19:47:50', '2024-09-29 19:47:50');
INSERT INTO `sync_auth_log` VALUES (961, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-29 20:16:04', '2024-09-29 20:16:05');
INSERT INTO `sync_auth_log` VALUES (962, '用户分配角色', 0, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-29 20:20:39', '2024-09-29 20:20:40');
INSERT INTO `sync_auth_log` VALUES (963, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-29 20:20:55', '2024-09-29 20:20:55');
INSERT INTO `sync_auth_log` VALUES (964, '用户分配角色', 0, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-09-29 20:21:06', '2024-09-29 20:21:07');
INSERT INTO `sync_auth_log` VALUES (965, '切换用户登录', 0, 0, '192.168.10.1', 'Windows', '内网IP', 256, 'baiheng', '柏衡', '2024-09-29 20:21:12', '2024-09-29 20:21:13');
INSERT INTO `sync_auth_log` VALUES (966, '用户分配角色', 0, 2, '192.168.10.1', 'Windows', '内网IP', 256, 'baiheng', '柏衡', '2024-09-29 20:21:23', '2024-09-29 20:21:23');
INSERT INTO `sync_auth_log` VALUES (967, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 256, 'baiheng', '柏衡', '2024-10-06 10:51:30', '2024-10-06 10:51:30');
INSERT INTO `sync_auth_log` VALUES (968, '用户分配角色', 0, 2, '192.168.10.1', 'Windows', '内网IP', 256, 'baiheng', '柏衡', '2024-10-06 10:52:10', '2024-10-06 10:52:11');
INSERT INTO `sync_auth_log` VALUES (969, '切换用户登录', 0, 0, '192.168.10.1', 'Windows', '内网IP', 256, 'baiheng', '柏衡', '2024-10-06 10:52:16', '2024-10-06 10:52:16');
INSERT INTO `sync_auth_log` VALUES (970, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 256, 'baiheng', '柏衡', '2024-10-06 10:52:29', '2024-10-06 10:52:29');
INSERT INTO `sync_auth_log` VALUES (971, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 256, 'baiheng', '柏衡', '2024-10-06 10:52:38', '2024-10-06 10:52:38');
INSERT INTO `sync_auth_log` VALUES (972, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 256, 'baiheng', '柏衡', '2024-10-06 10:54:20', '2024-10-06 10:54:20');
INSERT INTO `sync_auth_log` VALUES (973, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 256, 'baiheng', '柏衡', '2024-10-06 11:20:22', '2024-10-06 11:20:22');
INSERT INTO `sync_auth_log` VALUES (974, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 256, 'baiheng', '柏衡', '2024-10-06 11:22:13', '2024-10-06 11:22:13');
INSERT INTO `sync_auth_log` VALUES (975, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 256, 'baiheng', '柏衡', '2024-10-06 11:46:17', '2024-10-06 11:46:18');
INSERT INTO `sync_auth_log` VALUES (976, '删除用户信息', 0, 2, '192.168.10.1', 'Windows', '内网IP', 256, 'baiheng', '柏衡', '2024-10-06 11:46:56', '2024-10-06 11:46:57');
INSERT INTO `sync_auth_log` VALUES (977, '用户分配角色', 0, 2, '192.168.10.1', 'Windows', '内网IP', 256, 'baiheng', '柏衡', '2024-10-06 11:47:07', '2024-10-06 11:47:07');
INSERT INTO `sync_auth_log` VALUES (978, '启用用户账号', 0, 0, '192.168.10.1', 'Windows', '内网IP', 256, 'baiheng', '柏衡', '2024-10-06 11:47:12', '2024-10-06 11:47:12');
INSERT INTO `sync_auth_log` VALUES (979, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 256, 'baiheng', '柏衡', '2024-10-06 11:50:15', '2024-10-06 11:50:15');
INSERT INTO `sync_auth_log` VALUES (980, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 256, 'baiheng', '柏衡', '2024-10-06 11:50:18', '2024-10-06 11:50:18');
INSERT INTO `sync_auth_log` VALUES (981, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 256, 'baiheng', '柏衡', '2024-10-06 11:55:59', '2024-10-06 11:55:59');
INSERT INTO `sync_auth_log` VALUES (982, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 256, 'baiheng', '柏衡', '2024-10-06 11:56:40', '2024-10-06 11:56:40');
INSERT INTO `sync_auth_log` VALUES (983, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 256, 'baiheng', '柏衡', '2024-10-06 11:59:41', '2024-10-06 11:59:41');
INSERT INTO `sync_auth_log` VALUES (984, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 256, 'baiheng', '柏衡', '2024-10-06 11:59:46', '2024-10-06 11:59:46');
INSERT INTO `sync_auth_log` VALUES (985, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-06 13:36:42', '2024-10-06 13:36:43');
INSERT INTO `sync_auth_log` VALUES (986, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-06 13:37:41', '2024-10-06 13:37:41');
INSERT INTO `sync_auth_log` VALUES (987, '删除功能信息', 3, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-06 13:38:54', '2024-10-06 13:38:54');
INSERT INTO `sync_auth_log` VALUES (988, '删除功能信息', 3, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-06 13:38:56', '2024-10-06 13:38:56');
INSERT INTO `sync_auth_log` VALUES (989, '删除功能信息', 3, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-06 13:38:58', '2024-10-06 13:38:58');
INSERT INTO `sync_auth_log` VALUES (990, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-06 13:54:48', '2024-10-06 13:54:49');
INSERT INTO `sync_auth_log` VALUES (991, '更新资源信息', 2, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-06 14:00:57', '2024-10-06 14:00:58');
INSERT INTO `sync_auth_log` VALUES (992, '用户分配角色', 0, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-06 14:02:30', '2024-10-06 14:02:30');
INSERT INTO `sync_auth_log` VALUES (993, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-06 14:05:42', '2024-10-06 14:05:42');
INSERT INTO `sync_auth_log` VALUES (994, '更新资源信息', 2, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-06 14:10:55', '2024-10-06 14:10:56');
INSERT INTO `sync_auth_log` VALUES (995, '禁用用户账号', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-06 19:57:01', '2024-10-06 19:57:02');
INSERT INTO `sync_auth_log` VALUES (996, '启用用户账号', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-06 20:04:53', '2024-10-06 20:04:53');
INSERT INTO `sync_auth_log` VALUES (997, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-06 20:10:21', '2024-10-06 20:10:21');
INSERT INTO `sync_auth_log` VALUES (998, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-06 20:22:27', '2024-10-06 20:22:28');
INSERT INTO `sync_auth_log` VALUES (999, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-06 20:22:35', '2024-10-06 20:22:36');
INSERT INTO `sync_auth_log` VALUES (1000, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-06 20:22:41', '2024-10-06 20:22:42');
INSERT INTO `sync_auth_log` VALUES (1001, '用户分配角色', 0, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-06 20:22:55', '2024-10-06 20:22:56');
INSERT INTO `sync_auth_log` VALUES (1002, '切换用户登录', 0, 0, '192.168.10.1', 'Windows', '内网IP', 531, 'linzhiwei', '林智威', '2024-10-06 20:23:00', '2024-10-06 20:23:23');
INSERT INTO `sync_auth_log` VALUES (1003, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 531, 'linzhiwei', '林智威', '2024-10-06 20:23:10', '2024-10-06 20:23:11');
INSERT INTO `sync_auth_log` VALUES (1004, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 531, 'linzhiwei', '林智威', '2024-10-06 20:24:20', '2024-10-06 20:24:21');
INSERT INTO `sync_auth_log` VALUES (1005, '更新资源信息', 2, 1, '192.168.10.1', 'Windows', '内网IP', 531, 'linzhiwei', '林智威', '2024-10-06 20:24:50', '2024-10-06 20:24:51');
INSERT INTO `sync_auth_log` VALUES (1006, '用户分配角色', 0, 2, '192.168.10.1', 'Windows', '内网IP', 531, 'linzhiwei', '林智威', '2024-10-06 20:25:53', '2024-10-06 20:25:53');
INSERT INTO `sync_auth_log` VALUES (1007, '新增功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 531, 'linzhiwei', '林智威', '2024-10-06 20:26:25', '2024-10-06 20:26:26');
INSERT INTO `sync_auth_log` VALUES (1008, '新增功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 531, 'linzhiwei', '林智威', '2024-10-06 20:26:44', '2024-10-06 20:26:44');
INSERT INTO `sync_auth_log` VALUES (1009, '新增功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 531, 'linzhiwei', '林智威', '2024-10-06 20:27:19', '2024-10-06 20:27:19');
INSERT INTO `sync_auth_log` VALUES (1010, '新增功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 531, 'linzhiwei', '林智威', '2024-10-06 20:27:27', '2024-10-06 20:27:27');
INSERT INTO `sync_auth_log` VALUES (1011, '新增功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 531, 'linzhiwei', '林智威', '2024-10-06 20:27:35', '2024-10-06 20:27:36');
INSERT INTO `sync_auth_log` VALUES (1012, '新增功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 531, 'linzhiwei', '林智威', '2024-10-06 20:28:07', '2024-10-06 20:28:08');
INSERT INTO `sync_auth_log` VALUES (1013, '新增功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 531, 'linzhiwei', '林智威', '2024-10-06 20:28:15', '2024-10-06 20:28:16');
INSERT INTO `sync_auth_log` VALUES (1014, '删除功能信息', 3, 2, '192.168.10.1', 'Windows', '内网IP', 531, 'linzhiwei', '林智威', '2024-10-06 20:28:42', '2024-10-06 20:28:43');
INSERT INTO `sync_auth_log` VALUES (1015, '删除功能信息', 3, 2, '192.168.10.1', 'Windows', '内网IP', 531, 'linzhiwei', '林智威', '2024-10-06 20:28:44', '2024-10-06 20:28:44');
INSERT INTO `sync_auth_log` VALUES (1016, '删除功能信息', 3, 2, '192.168.10.1', 'Windows', '内网IP', 531, 'linzhiwei', '林智威', '2024-10-06 20:28:45', '2024-10-06 20:28:46');
INSERT INTO `sync_auth_log` VALUES (1017, '删除功能信息', 3, 2, '192.168.10.1', 'Windows', '内网IP', 531, 'linzhiwei', '林智威', '2024-10-06 20:28:47', '2024-10-06 20:28:47');
INSERT INTO `sync_auth_log` VALUES (1018, '删除功能信息', 3, 2, '192.168.10.1', 'Windows', '内网IP', 531, 'linzhiwei', '林智威', '2024-10-06 20:28:48', '2024-10-06 20:28:49');
INSERT INTO `sync_auth_log` VALUES (1019, '删除功能信息', 3, 2, '192.168.10.1', 'Windows', '内网IP', 531, 'linzhiwei', '林智威', '2024-10-06 20:28:50', '2024-10-06 20:28:51');
INSERT INTO `sync_auth_log` VALUES (1020, '删除功能信息', 3, 2, '192.168.10.1', 'Windows', '内网IP', 531, 'linzhiwei', '林智威', '2024-10-06 20:28:52', '2024-10-06 20:28:52');
INSERT INTO `sync_auth_log` VALUES (1021, '更新资源信息', 2, 1, '192.168.10.1', 'Windows', '内网IP', 531, 'linzhiwei', '林智威', '2024-10-07 08:54:07', '2024-10-07 08:54:08');
INSERT INTO `sync_auth_log` VALUES (1022, '更新角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 531, 'linzhiwei', '林智威', '2024-10-07 09:50:15', '2024-10-07 09:50:15');
INSERT INTO `sync_auth_log` VALUES (1023, '更新资源信息', 2, 1, '192.168.10.1', 'Windows', '内网IP', 531, 'linzhiwei', '林智威', '2024-10-07 09:59:44', '2024-10-07 09:59:45');
INSERT INTO `sync_auth_log` VALUES (1024, '功能解绑资源', 3, 2, '192.168.10.1', 'Windows', '内网IP', 531, 'linzhiwei', '林智威', '2024-10-07 10:08:45', '2024-10-07 10:08:46');
INSERT INTO `sync_auth_log` VALUES (1025, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 531, 'linzhiwei', '林智威', '2024-10-07 10:10:49', '2024-10-07 10:10:50');
INSERT INTO `sync_auth_log` VALUES (1026, '更新角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 531, 'linzhiwei', '林智威', '2024-10-07 10:11:30', '2024-10-07 10:11:31');
INSERT INTO `sync_auth_log` VALUES (1027, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 531, 'linzhiwei', '林智威', '2024-10-07 11:05:16', '2024-10-07 11:05:17');
INSERT INTO `sync_auth_log` VALUES (1028, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 531, 'linzhiwei', '林智威', '2024-10-07 11:05:21', '2024-10-07 11:05:22');
INSERT INTO `sync_auth_log` VALUES (1029, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 14:29:14', '2024-10-07 14:29:15');
INSERT INTO `sync_auth_log` VALUES (1030, '更新角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 16:27:12', '2024-10-07 16:27:13');
INSERT INTO `sync_auth_log` VALUES (1031, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 16:31:21', '2024-10-07 16:31:22');
INSERT INTO `sync_auth_log` VALUES (1032, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 16:31:23', '2024-10-07 16:31:24');
INSERT INTO `sync_auth_log` VALUES (1033, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 16:31:26', '2024-10-07 16:31:26');
INSERT INTO `sync_auth_log` VALUES (1034, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 16:31:29', '2024-10-07 16:31:30');
INSERT INTO `sync_auth_log` VALUES (1035, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 16:31:32', '2024-10-07 16:31:33');
INSERT INTO `sync_auth_log` VALUES (1036, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 16:31:37', '2024-10-07 16:31:37');
INSERT INTO `sync_auth_log` VALUES (1037, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 16:32:45', '2024-10-07 16:32:46');
INSERT INTO `sync_auth_log` VALUES (1038, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 16:32:47', '2024-10-07 16:32:48');
INSERT INTO `sync_auth_log` VALUES (1039, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 16:35:30', '2024-10-07 16:35:31');
INSERT INTO `sync_auth_log` VALUES (1040, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 16:35:32', '2024-10-07 16:35:33');
INSERT INTO `sync_auth_log` VALUES (1041, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 16:35:34', '2024-10-07 16:35:34');
INSERT INTO `sync_auth_log` VALUES (1042, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 16:35:36', '2024-10-07 16:35:36');
INSERT INTO `sync_auth_log` VALUES (1043, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 16:36:28', '2024-10-07 16:36:29');
INSERT INTO `sync_auth_log` VALUES (1044, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 16:36:32', '2024-10-07 16:36:33');
INSERT INTO `sync_auth_log` VALUES (1045, '更新角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 16:36:34', '2024-10-07 16:36:35');
INSERT INTO `sync_auth_log` VALUES (1046, '更新角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 16:36:36', '2024-10-07 16:36:37');
INSERT INTO `sync_auth_log` VALUES (1047, '更新资源信息', 2, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 16:36:38', '2024-10-07 16:36:39');
INSERT INTO `sync_auth_log` VALUES (1048, '更新资源信息', 2, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 16:36:40', '2024-10-07 16:36:41');
INSERT INTO `sync_auth_log` VALUES (1049, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 16:36:43', '2024-10-07 16:36:44');
INSERT INTO `sync_auth_log` VALUES (1050, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 16:36:46', '2024-10-07 16:36:47');
INSERT INTO `sync_auth_log` VALUES (1051, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 17:22:17', '2024-10-07 17:22:18');
INSERT INTO `sync_auth_log` VALUES (1052, '更新角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 17:22:20', '2024-10-07 17:22:20');
INSERT INTO `sync_auth_log` VALUES (1053, '更新角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 17:22:21', '2024-10-07 17:22:22');
INSERT INTO `sync_auth_log` VALUES (1054, '更新角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 17:22:23', '2024-10-07 17:22:24');
INSERT INTO `sync_auth_log` VALUES (1055, '更新资源信息', 2, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 17:22:26', '2024-10-07 17:22:26');
INSERT INTO `sync_auth_log` VALUES (1056, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 17:22:29', '2024-10-07 17:22:29');
INSERT INTO `sync_auth_log` VALUES (1057, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 17:23:51', '2024-10-07 17:24:32');
INSERT INTO `sync_auth_log` VALUES (1058, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 17:25:59', '2024-10-07 17:25:59');
INSERT INTO `sync_auth_log` VALUES (1059, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 17:26:01', '2024-10-07 17:26:01');
INSERT INTO `sync_auth_log` VALUES (1060, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 17:26:04', '2024-10-07 17:26:05');
INSERT INTO `sync_auth_log` VALUES (1061, '更新角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 17:26:39', '2024-10-07 17:26:39');
INSERT INTO `sync_auth_log` VALUES (1062, '更新角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 17:26:41', '2024-10-07 17:26:41');
INSERT INTO `sync_auth_log` VALUES (1063, '更新角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 17:26:43', '2024-10-07 17:26:44');
INSERT INTO `sync_auth_log` VALUES (1064, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 17:26:45', '2024-10-07 17:26:46');
INSERT INTO `sync_auth_log` VALUES (1065, '更新角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 17:26:49', '2024-10-07 17:26:50');
INSERT INTO `sync_auth_log` VALUES (1066, '更新资源信息', 2, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 17:26:51', '2024-10-07 17:26:52');
INSERT INTO `sync_auth_log` VALUES (1067, '更新资源信息', 2, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 17:26:52', '2024-10-07 17:26:53');
INSERT INTO `sync_auth_log` VALUES (1068, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 17:27:50', '2024-10-07 17:27:51');
INSERT INTO `sync_auth_log` VALUES (1069, '更新角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 17:32:54', '2024-10-07 17:32:54');
INSERT INTO `sync_auth_log` VALUES (1070, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 20:58:04', '2024-10-07 20:58:04');
INSERT INTO `sync_auth_log` VALUES (1071, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 20:58:05', '2024-10-07 20:58:06');
INSERT INTO `sync_auth_log` VALUES (1072, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:13:14', '2024-10-07 21:13:15');
INSERT INTO `sync_auth_log` VALUES (1073, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:13:17', '2024-10-07 21:13:18');
INSERT INTO `sync_auth_log` VALUES (1074, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:13:19', '2024-10-07 21:13:20');
INSERT INTO `sync_auth_log` VALUES (1075, '更新角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:21:52', '2024-10-07 21:21:53');
INSERT INTO `sync_auth_log` VALUES (1076, '更新角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:21:54', '2024-10-07 21:21:55');
INSERT INTO `sync_auth_log` VALUES (1077, '新增角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:21:58', '2024-10-07 21:21:58');
INSERT INTO `sync_auth_log` VALUES (1078, '删除角色信息', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:22:00', '2024-10-07 21:22:00');
INSERT INTO `sync_auth_log` VALUES (1079, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:26:21', '2024-10-07 21:26:22');
INSERT INTO `sync_auth_log` VALUES (1080, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:26:23', '2024-10-07 21:26:24');
INSERT INTO `sync_auth_log` VALUES (1081, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:28:45', '2024-10-07 21:28:46');
INSERT INTO `sync_auth_log` VALUES (1082, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:28:47', '2024-10-07 21:28:48');
INSERT INTO `sync_auth_log` VALUES (1083, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:28:49', '2024-10-07 21:28:49');
INSERT INTO `sync_auth_log` VALUES (1084, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:28:51', '2024-10-07 21:28:51');
INSERT INTO `sync_auth_log` VALUES (1085, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:28:53', '2024-10-07 21:28:53');
INSERT INTO `sync_auth_log` VALUES (1086, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:28:55', '2024-10-07 21:28:55');
INSERT INTO `sync_auth_log` VALUES (1087, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:28:57', '2024-10-07 21:28:57');
INSERT INTO `sync_auth_log` VALUES (1088, '更新角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:30:21', '2024-10-07 21:30:21');
INSERT INTO `sync_auth_log` VALUES (1089, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:32:29', '2024-10-07 21:32:29');
INSERT INTO `sync_auth_log` VALUES (1090, '角色分配功能', 1, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:32:34', '2024-10-07 21:32:34');
INSERT INTO `sync_auth_log` VALUES (1091, '用户分配角色', 0, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:33:22', '2024-10-07 21:33:23');
INSERT INTO `sync_auth_log` VALUES (1092, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:44:55', '2024-10-07 21:45:19');
INSERT INTO `sync_auth_log` VALUES (1093, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:44:56', '2024-10-07 21:45:12');
INSERT INTO `sync_auth_log` VALUES (1094, '更新角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:45:35', '2024-10-07 21:45:35');
INSERT INTO `sync_auth_log` VALUES (1095, '更新角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:45:36', '2024-10-07 21:45:37');
INSERT INTO `sync_auth_log` VALUES (1096, '更新角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:45:38', '2024-10-07 21:45:39');
INSERT INTO `sync_auth_log` VALUES (1097, '更新角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:45:40', '2024-10-07 21:45:40');
INSERT INTO `sync_auth_log` VALUES (1098, '更新角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:45:41', '2024-10-07 21:45:42');
INSERT INTO `sync_auth_log` VALUES (1099, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:45:54', '2024-10-07 21:45:54');
INSERT INTO `sync_auth_log` VALUES (1100, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:46:10', '2024-10-07 21:46:10');
INSERT INTO `sync_auth_log` VALUES (1101, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:46:14', '2024-10-07 21:46:14');
INSERT INTO `sync_auth_log` VALUES (1102, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:46:17', '2024-10-07 21:46:17');
INSERT INTO `sync_auth_log` VALUES (1103, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:46:19', '2024-10-07 21:46:20');
INSERT INTO `sync_auth_log` VALUES (1104, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:46:21', '2024-10-07 21:46:22');
INSERT INTO `sync_auth_log` VALUES (1105, '禁用用户账号', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:46:26', '2024-10-07 21:46:27');
INSERT INTO `sync_auth_log` VALUES (1106, '启用用户账号', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:46:30', '2024-10-07 21:46:31');
INSERT INTO `sync_auth_log` VALUES (1107, '禁用用户账号', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:58:02', '2024-10-07 21:58:03');
INSERT INTO `sync_auth_log` VALUES (1108, '启用用户账号', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 21:58:06', '2024-10-07 21:58:06');
INSERT INTO `sync_auth_log` VALUES (1109, '切换用户登录', 0, 0, '192.168.10.1', 'Windows', '内网IP', 512, 'zhanglongbiao', '张龙标', '2024-10-07 21:58:13', '2024-10-07 21:58:13');
INSERT INTO `sync_auth_log` VALUES (1110, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 512, 'zhanglongbiao', '张龙标', '2024-10-07 21:58:30', '2024-10-07 21:58:31');
INSERT INTO `sync_auth_log` VALUES (1111, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 512, 'zhanglongbiao', '张龙标', '2024-10-07 21:58:32', '2024-10-07 21:58:33');
INSERT INTO `sync_auth_log` VALUES (1112, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 512, 'zhanglongbiao', '张龙标', '2024-10-07 21:58:34', '2024-10-07 21:58:34');
INSERT INTO `sync_auth_log` VALUES (1113, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 512, 'zhanglongbiao', '张龙标', '2024-10-07 21:58:36', '2024-10-07 21:58:37');
INSERT INTO `sync_auth_log` VALUES (1114, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 512, 'zhanglongbiao', '张龙标', '2024-10-07 21:58:38', '2024-10-07 21:58:38');
INSERT INTO `sync_auth_log` VALUES (1115, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 512, 'zhanglongbiao', '张龙标', '2024-10-07 21:58:40', '2024-10-07 21:58:40');
INSERT INTO `sync_auth_log` VALUES (1116, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 512, 'zhanglongbiao', '张龙标', '2024-10-07 21:58:41', '2024-10-07 21:58:42');
INSERT INTO `sync_auth_log` VALUES (1117, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 512, 'zhanglongbiao', '张龙标', '2024-10-07 21:58:43', '2024-10-07 21:58:43');
INSERT INTO `sync_auth_log` VALUES (1118, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 512, 'zhanglongbiao', '张龙标', '2024-10-07 21:58:44', '2024-10-07 21:58:45');
INSERT INTO `sync_auth_log` VALUES (1119, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 512, 'zhanglongbiao', '张龙标', '2024-10-07 21:58:46', '2024-10-07 21:58:46');
INSERT INTO `sync_auth_log` VALUES (1120, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 512, 'zhanglongbiao', '张龙标', '2024-10-07 21:58:47', '2024-10-07 21:58:47');
INSERT INTO `sync_auth_log` VALUES (1121, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 512, 'zhanglongbiao', '张龙标', '2024-10-07 21:58:49', '2024-10-07 21:58:49');
INSERT INTO `sync_auth_log` VALUES (1122, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 512, 'zhanglongbiao', '张龙标', '2024-10-07 21:58:51', '2024-10-07 21:58:51');
INSERT INTO `sync_auth_log` VALUES (1123, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 512, 'zhanglongbiao', '张龙标', '2024-10-07 21:58:53', '2024-10-07 21:58:53');
INSERT INTO `sync_auth_log` VALUES (1124, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 512, 'zhanglongbiao', '张龙标', '2024-10-07 21:58:55', '2024-10-07 21:58:55');
INSERT INTO `sync_auth_log` VALUES (1125, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 22:01:20', '2024-10-07 22:01:21');
INSERT INTO `sync_auth_log` VALUES (1126, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 22:01:32', '2024-10-07 22:01:33');
INSERT INTO `sync_auth_log` VALUES (1127, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 22:01:44', '2024-10-07 22:01:45');
INSERT INTO `sync_auth_log` VALUES (1128, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 22:01:57', '2024-10-07 22:01:58');
INSERT INTO `sync_auth_log` VALUES (1129, '新增用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 22:04:33', '2024-10-07 22:04:34');
INSERT INTO `sync_auth_log` VALUES (1130, '用户分配角色', 0, 2, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 22:04:38', '2024-10-07 22:04:39');
INSERT INTO `sync_auth_log` VALUES (1131, '切换用户登录', 0, 0, '192.168.10.1', 'Windows', '内网IP', 880, 'zhangyukang', '章宇康', '2024-10-07 22:04:46', '2024-10-07 22:04:47');
INSERT INTO `sync_auth_log` VALUES (1132, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 880, 'zhangyukang', '章宇康', '2024-10-07 22:05:27', '2024-10-07 22:05:27');
INSERT INTO `sync_auth_log` VALUES (1133, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 880, 'zhangyukang', '章宇康', '2024-10-07 22:05:29', '2024-10-07 22:05:29');
INSERT INTO `sync_auth_log` VALUES (1134, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 880, 'zhangyukang', '章宇康', '2024-10-07 22:05:31', '2024-10-07 22:05:31');
INSERT INTO `sync_auth_log` VALUES (1135, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 880, 'zhangyukang', '章宇康', '2024-10-07 22:05:34', '2024-10-07 22:05:34');
INSERT INTO `sync_auth_log` VALUES (1136, '更新资源信息', 2, 1, '192.168.10.1', 'Windows', '内网IP', 880, 'zhangyukang', '章宇康', '2024-10-07 22:05:40', '2024-10-07 22:05:41');
INSERT INTO `sync_auth_log` VALUES (1137, '更新资源信息', 2, 1, '192.168.10.1', 'Windows', '内网IP', 880, 'zhangyukang', '章宇康', '2024-10-07 22:05:44', '2024-10-07 22:05:44');
INSERT INTO `sync_auth_log` VALUES (1138, '更新资源信息', 2, 1, '192.168.10.1', 'Windows', '内网IP', 880, 'zhangyukang', '章宇康', '2024-10-07 22:05:46', '2024-10-07 22:05:46');
INSERT INTO `sync_auth_log` VALUES (1139, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 880, 'zhangyukang', '章宇康', '2024-10-07 22:06:11', '2024-10-07 22:06:12');
INSERT INTO `sync_auth_log` VALUES (1140, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 880, 'zhangyukang', '章宇康', '2024-10-07 22:06:24', '2024-10-07 22:06:24');
INSERT INTO `sync_auth_log` VALUES (1141, '更新功能信息', 3, 1, '192.168.10.1', 'Windows', '内网IP', 880, 'zhangyukang', '章宇康', '2024-10-07 22:06:34', '2024-10-07 22:06:35');
INSERT INTO `sync_auth_log` VALUES (1142, '用户登录系统', 0, 0, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 22:07:20', '2024-10-07 22:07:21');
INSERT INTO `sync_auth_log` VALUES (1143, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 22:07:40', '2024-10-07 22:07:40');
INSERT INTO `sync_auth_log` VALUES (1144, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 22:07:41', '2024-10-07 22:07:41');
INSERT INTO `sync_auth_log` VALUES (1145, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 22:07:43', '2024-10-07 22:07:43');
INSERT INTO `sync_auth_log` VALUES (1146, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 22:08:36', '2024-10-07 22:08:37');
INSERT INTO `sync_auth_log` VALUES (1147, '更新角色信息', 1, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 22:08:39', '2024-10-07 22:08:40');
INSERT INTO `sync_auth_log` VALUES (1148, '更新用户信息', 0, 1, '192.168.10.1', 'Windows', '内网IP', 61, 'admin', '管理员', '2024-10-07 22:11:08', '2024-10-07 22:11:08');

-- ----------------------------
-- Table structure for sync_auth_resc
-- ----------------------------
DROP TABLE IF EXISTS `sync_auth_resc`;
CREATE TABLE `sync_auth_resc`  (
  `resc_id` int(11) NOT NULL COMMENT '资源id',
  `resc_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源名称',
  `resc_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源url',
  `resc_domain` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源所属系统',
  `method_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求方式',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `sync_time` datetime NULL DEFAULT NULL COMMENT '同步时间',
  PRIMARY KEY (`resc_id`) USING BTREE,
  UNIQUE INDEX `idx_resc_url`(`resc_url`) USING BTREE,
  INDEX `idx_resc_name`(`resc_name`) USING BTREE,
  INDEX `idx_resc_domain`(`resc_domain`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sync_auth_resc
-- ----------------------------
INSERT INTO `sync_auth_resc` VALUES (1, '用户列表', '/auth/user/page', 'auth', 'get', '2022-03-19 09:31:31', '2023-09-23 15:30:56', NULL);
INSERT INTO `sync_auth_resc` VALUES (2, '用户获取', '/auth/user/detail', 'auth', 'get', '2022-03-19 09:34:24', '2023-09-28 12:04:30', NULL);
INSERT INTO `sync_auth_resc` VALUES (3, '用户删除', '/auth/user/delete', 'auth', 'delete', '2022-03-19 09:34:40', '2023-07-15 00:35:06', NULL);
INSERT INTO `sync_auth_resc` VALUES (4, '用户更新', '/auth/user/update', 'auth', 'post', '2022-03-19 09:34:58', '2022-05-10 22:36:29', NULL);
INSERT INTO `sync_auth_resc` VALUES (9, '用户分配角色初始化', '/auth/user/role/update/init', 'auth', 'get', '2022-03-19 10:29:15', '2024-09-27 21:25:24', '2024-09-27 21:25:24');
INSERT INTO `sync_auth_resc` VALUES (10, '用户分配角色', '/auth/user/role/update', 'auth', 'put', '2022-03-19 10:29:41', '2024-09-27 21:40:03', '2024-09-27 21:40:04');
INSERT INTO `sync_auth_resc` VALUES (11, '部门列表', '/auth/dept/list', 'auth', 'get', '2022-03-19 10:30:01', '2022-04-09 14:03:20', NULL);
INSERT INTO `sync_auth_resc` VALUES (12, '用户启用', '/auth/user/enable', 'auth', 'get', '2022-03-19 10:30:16', '2023-07-11 23:43:03', NULL);
INSERT INTO `sync_auth_resc` VALUES (14, '用户禁用', '/auth/user/disable', 'auth', 'get', '2022-03-19 10:30:48', '2023-07-11 23:42:56', NULL);
INSERT INTO `sync_auth_resc` VALUES (15, '角色删除', '/auth/role/delete', 'auth', 'delete', '2022-03-19 10:31:30', '2023-06-16 21:33:38', NULL);
INSERT INTO `sync_auth_resc` VALUES (16, '角色更新', '/auth/role/update', 'auth', 'post', '2022-03-19 10:31:44', '2022-05-01 22:23:59', NULL);
INSERT INTO `sync_auth_resc` VALUES (17, '角色获取', '/auth/role/detail', 'auth', 'get', '2022-03-19 10:32:06', '2022-04-09 14:02:34', NULL);
INSERT INTO `sync_auth_resc` VALUES (18, '角色列表', '/auth/role/page', 'auth', 'get', '2022-03-19 10:32:28', '2022-04-09 14:02:25', NULL);
INSERT INTO `sync_auth_resc` VALUES (19, '角色新增', '/auth/role/save', 'auth', 'post', '2022-03-19 10:32:48', '2022-04-09 14:02:29', NULL);
INSERT INTO `sync_auth_resc` VALUES (20, '资源列表', '/auth/resc/page', 'auth', 'get', '2022-03-19 10:33:08', '2022-09-04 14:44:14', NULL);
INSERT INTO `sync_auth_resc` VALUES (21, '资源新增', '/auth/resc/save', 'auth', 'post', '2022-03-19 10:33:26', '2022-04-09 14:02:15', NULL);
INSERT INTO `sync_auth_resc` VALUES (22, '资源获取', '/auth/resc/detail', 'auth', 'get', '2022-03-19 10:33:42', '2022-04-09 14:02:06', NULL);
INSERT INTO `sync_auth_resc` VALUES (23, '资源更新', '/auth/resc/update', 'auth', 'post', '2022-03-19 10:33:56', '2023-07-15 10:29:20', NULL);
INSERT INTO `sync_auth_resc` VALUES (24, '资源删除', '/auth/resc/delete', 'auth', 'delete', '2022-03-19 10:34:13', '2023-06-16 21:33:34', NULL);
INSERT INTO `sync_auth_resc` VALUES (27, '用户新增', '/auth/user/save', 'auth', 'post', '2022-03-19 11:18:04', '2022-04-09 14:01:38', NULL);
INSERT INTO `sync_auth_resc` VALUES (29, '功能更新', '/auth/func/update', 'auth', 'post', '2022-03-26 20:52:38', '2022-05-01 21:57:53', NULL);
INSERT INTO `sync_auth_resc` VALUES (30, '功能获取', '/auth/func/detail', 'auth', 'get', '2022-03-26 20:52:51', '2022-04-09 14:01:25', NULL);
INSERT INTO `sync_auth_resc` VALUES (31, '功能删除', '/auth/func/delete', 'auth', 'delete', '2022-03-26 20:53:09', '2023-06-17 21:04:10', NULL);
INSERT INTO `sync_auth_resc` VALUES (32, '功能新增', '/auth/func/save', 'auth', 'post', '2022-03-26 20:53:24', '2023-06-17 21:04:07', NULL);
INSERT INTO `sync_auth_resc` VALUES (33, '功能查询', '/auth/func/page', 'auth', 'get', '2022-03-26 20:53:40', '2022-09-11 16:05:51', NULL);
INSERT INTO `sync_auth_resc` VALUES (34, '功能树获取', '/auth/func/tree', 'auth', 'get', '2022-03-26 20:53:58', '2023-07-11 23:40:28', NULL);
INSERT INTO `sync_auth_resc` VALUES (35, '角色分配用户初始化', '/auth/role/user/update/init', 'auth', 'get', '2022-03-26 21:07:44', '2024-09-20 14:21:20', '2024-09-20 14:21:20');
INSERT INTO `sync_auth_resc` VALUES (36, '角色分配用户', '/auth/role/user/update', 'auth', 'put', '2022-03-26 21:07:59', '2024-09-27 21:40:16', '2024-09-27 21:50:36');
INSERT INTO `sync_auth_resc` VALUES (37, '角色授权功能初始化', '/auth/role/func/update/init', 'auth', 'get', '2022-04-04 17:46:28', '2024-09-27 21:25:16', '2024-09-27 21:25:17');
INSERT INTO `sync_auth_resc` VALUES (38, '角色分配功能', '/auth/role/func/update', 'auth', 'post', '2022-04-04 17:46:57', '2023-07-08 10:09:07', NULL);
INSERT INTO `sync_auth_resc` VALUES (39, '功能解绑用户', '/auth/func/user/remove', 'auth', 'delete', '2022-04-16 21:54:38', '2023-07-11 23:45:41', NULL);
INSERT INTO `sync_auth_resc` VALUES (40, '功能解绑资源', '/auth/func/resc/remove', 'auth', 'delete', '2022-04-16 22:02:24', '2023-09-03 12:59:45', NULL);
INSERT INTO `sync_auth_resc` VALUES (41, '资源搜索', '/auth/resc/search', 'auth', 'get', '2022-05-01 19:17:13', '2023-09-03 14:02:02', NULL);
INSERT INTO `sync_auth_resc` VALUES (42, '用户修改密码', '/auth/user/pwd/update', 'auth', 'post', '2022-05-14 12:19:03', '2023-07-16 14:07:43', NULL);
INSERT INTO `sync_auth_resc` VALUES (43, '角色授权功能预检查', '/auth/role/func/update/check', 'auth', 'post', '2022-05-21 15:34:56', '2024-09-28 09:57:33', '2024-09-28 09:57:34');
INSERT INTO `sync_auth_resc` VALUES (44, '功能设置资源', '/auth/func/resc/update', 'auth', 'put', '2023-06-17 21:01:39', '2024-09-23 22:04:40', '2024-09-23 22:04:41');
INSERT INTO `sync_auth_resc` VALUES (45, '切换登录', '/auth/user/switch/login', 'auth', 'post', '2023-07-15 22:19:29', '2023-10-01 11:01:49', NULL);
INSERT INTO `sync_auth_resc` VALUES (62, '系统操作日志', '/auth/log/page', 'auth', 'post', '2023-09-28 11:04:54', '2024-09-21 14:36:45', '2024-09-21 14:36:45');
INSERT INTO `sync_auth_resc` VALUES (63, '资源刷新', '/auth/resc/refresh', 'auth', 'put', '2023-09-28 11:52:26', '2023-09-28 11:52:26', NULL);
INSERT INTO `sync_auth_resc` VALUES (66, '同步计划查询', '/sync/plan/page', 'sync', 'put', '2023-07-18 20:50:10', '2024-10-07 22:05:46', '2024-10-07 22:05:46');
INSERT INTO `sync_auth_resc` VALUES (67, '同步计划详情', '/sync/plan/detail', 'sync', 'get', '2023-07-18 20:51:07', '2023-10-26 22:55:58', NULL);
INSERT INTO `sync_auth_resc` VALUES (68, '同步计划状态', '/sync/plan/update/status', 'sync', 'put', '2023-09-03 13:20:02', '2024-10-07 22:05:44', '2024-10-07 22:05:44');
INSERT INTO `sync_auth_resc` VALUES (69, '同步消息列表', '/sync/message/page', 'sync', 'put', '2023-09-03 19:09:05', '2024-09-24 23:06:15', '2024-09-24 23:06:15');
INSERT INTO `sync_auth_resc` VALUES (70, '同步计划更新', '/sync/plan/update', 'sync', 'put', '2023-09-03 19:21:40', '2023-11-11 21:44:14', NULL);
INSERT INTO `sync_auth_resc` VALUES (71, '同步计划新增', '/sync/plan/save', 'sync', 'put', '2023-09-03 19:24:36', '2024-10-07 09:59:44', '2024-10-07 09:59:45');
INSERT INTO `sync_auth_resc` VALUES (72, '同步计划删除', '/sync/plan/delete', 'sync', 'delete', '2023-09-03 19:25:22', '2023-11-05 00:50:56', NULL);
INSERT INTO `sync_auth_resc` VALUES (73, '同步记录列表', '/sync/result/page', 'sync', 'post', '2023-09-03 19:27:25', '2023-11-04 19:36:16', NULL);
INSERT INTO `sync_auth_resc` VALUES (74, '标记成功', '/sync/result/sign/success', 'sync', 'put', '2023-09-03 19:29:38', '2024-10-06 14:10:55', '2024-10-06 14:10:56');
INSERT INTO `sync_auth_resc` VALUES (75, '校验同步结果', '/sync/result/valid/data', 'sync', 'put', '2023-09-03 19:30:19', '2024-10-07 17:26:52', '2024-10-07 17:26:53');
INSERT INTO `sync_auth_resc` VALUES (76, '重新同步', '/sync/result/repeat/sync', 'sync', 'put', '2023-09-03 19:31:53', '2023-11-04 19:36:18', NULL);
INSERT INTO `sync_auth_resc` VALUES (77, '同步计划刷新', '/sync/plan/refresh', 'sync', 'put', '2023-10-22 20:34:47', '2024-10-07 22:05:40', '2024-10-07 22:05:41');
INSERT INTO `sync_auth_resc` VALUES (78, '回调列表', '/sync/callback/page', 'sync', 'post', '2023-11-04 21:30:49', '2024-10-07 17:22:26', '2024-10-07 17:22:26');
INSERT INTO `sync_auth_resc` VALUES (79, '重新回调', '/sync/callback/repeat', 'sync', 'post', '2023-11-05 12:56:41', '2024-10-07 16:36:38', '2024-10-07 16:36:39');

-- ----------------------------
-- Table structure for sync_auth_role
-- ----------------------------
DROP TABLE IF EXISTS `sync_auth_role`;
CREATE TABLE `sync_auth_role`  (
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `role_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `sync_time` datetime NULL DEFAULT NULL COMMENT '同步时间',
  PRIMARY KEY (`role_id`) USING BTREE,
  UNIQUE INDEX `idx_role_name`(`role_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sync_auth_role
-- ----------------------------
INSERT INTO `sync_auth_role` VALUES (42, '管理员角色', '管理员角色', '2022-04-16 11:38:56', '2024-10-07 21:45:38', '2024-10-07 21:45:39');
INSERT INTO `sync_auth_role` VALUES (51, '普通用户角色', '基础权限', '2024-06-17 22:00:51', '2024-10-07 22:08:39', '2024-10-07 22:08:40');
INSERT INTO `sync_auth_role` VALUES (52, '主管角色', '拥有系统的基础管理', '2024-09-28 13:38:23', '2024-10-07 21:45:41', '2024-10-07 21:45:42');

-- ----------------------------
-- Table structure for sync_auth_role_func
-- ----------------------------
DROP TABLE IF EXISTS `sync_auth_role_func`;
CREATE TABLE `sync_auth_role_func`  (
  `role_func_id` int(11) NOT NULL COMMENT '主键',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `func_id` int(11) NOT NULL COMMENT '功能id',
  `sync_time` datetime NULL DEFAULT NULL COMMENT '同步时间',
  PRIMARY KEY (`role_func_id`) USING BTREE,
  INDEX `idx_role_id`(`role_id`) USING BTREE,
  INDEX `idx_func_id`(`func_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sync_auth_role_func
-- ----------------------------
INSERT INTO `sync_auth_role_func` VALUES (16734, 42, 128, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16735, 42, 129, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16736, 42, 2, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16737, 42, 3, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16738, 42, 4, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16739, 42, 5, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16740, 42, 6, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16741, 42, 8, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16742, 42, 9, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16743, 42, 11, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16744, 42, 12, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16745, 42, 13, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16746, 42, 14, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16747, 42, 26, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16748, 42, 27, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16749, 42, 156, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16750, 42, 160, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16751, 42, 161, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16752, 42, 162, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16753, 42, 54, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16754, 42, 55, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16755, 42, 56, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16756, 42, 57, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16757, 42, 60, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16758, 42, 61, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16759, 42, 62, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16760, 42, 63, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16761, 42, 71, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16762, 42, 72, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16763, 42, 73, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16764, 42, 74, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16765, 42, 75, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16766, 42, 84, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16767, 42, 102, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16768, 42, 105, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16769, 42, 106, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16784, 42, 136, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16785, 42, 137, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16786, 42, 138, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16787, 42, 139, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16788, 42, 140, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16789, 42, 141, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16790, 42, 142, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16791, 42, 143, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16792, 42, 144, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16793, 42, 145, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16794, 42, 171, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16795, 42, 172, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16796, 42, 173, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16797, 42, 93, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16798, 42, 94, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16799, 42, 95, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16800, 42, 96, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16801, 42, 97, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16802, 51, 93, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16803, 51, 94, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16804, 51, 136, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16805, 51, 137, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16806, 51, 138, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16807, 51, 173, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16808, 51, 139, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16809, 51, 140, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16810, 51, 95, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16811, 51, 142, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16812, 51, 143, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16813, 51, 144, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16814, 51, 145, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16815, 51, 97, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16816, 51, 141, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16817, 51, 96, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16818, 51, 171, NULL);
INSERT INTO `sync_auth_role_func` VALUES (16819, 51, 172, NULL);

-- ----------------------------
-- Table structure for sync_auth_user
-- ----------------------------
DROP TABLE IF EXISTS `sync_auth_user`;
CREATE TABLE `sync_auth_user`  (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户账号',
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '真实名称',
  `dept_id` int(11) NOT NULL COMMENT '部门id',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `user_status` int(255) NOT NULL COMMENT '状态',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `sync_time` datetime NULL DEFAULT NULL COMMENT '同步时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `ix_username`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sync_auth_user
-- ----------------------------
INSERT INTO `sync_auth_user` VALUES (61, 'admin', '管理员', 0, '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 1, '2022-05-01 23:27:25', '2024-09-21 11:33:38', '2024-09-21 11:33:39');
INSERT INTO `sync_auth_user` VALUES (83, 'user', '普通用户', 0, '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 1, '2024-06-17 22:00:14', '2024-09-24 22:34:39', '2024-09-24 22:34:39');
INSERT INTO `sync_auth_user` VALUES (84, 'test', '杨明', 0, '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 1, '2024-09-24 22:53:25', '2024-09-24 22:53:25', '2024-09-24 22:53:26');
INSERT INTO `sync_auth_user` VALUES (880, 'zhangyukang', '章宇康', 1, '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 1, '2024-10-07 22:04:33', '2024-10-07 22:11:08', '2024-10-07 22:11:08');

-- ----------------------------
-- Table structure for sync_auth_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sync_auth_user_role`;
CREATE TABLE `sync_auth_user_role`  (
  `user_role_id` int(11) NOT NULL COMMENT '组件',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `sync_time` datetime NULL DEFAULT NULL COMMENT '同步时间',
  PRIMARY KEY (`user_role_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_role_id`(`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sync_auth_user_role
-- ----------------------------
INSERT INTO `sync_auth_user_role` VALUES (955, 61, 42, NULL);
INSERT INTO `sync_auth_user_role` VALUES (960, 83, 51, NULL);

SET FOREIGN_KEY_CHECKS = 1;
