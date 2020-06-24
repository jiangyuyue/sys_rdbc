/*
Navicat MySQL Data Transfer

Source Server         : mmmm
Source Server Version : 50722
Source Host           : localhost:3306
Source Database       : test_sys

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2020-06-24 10:07:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `parented` tinyint(1) DEFAULT '0' COMMENT ' 是否为父节点',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '系统管理', null, null, '0', 'system', '0', '1');
INSERT INTO `sys_menu` VALUES ('2', '1', '管理员列表', 'sys/user', '', '1', 'admin', '1', '1');
INSERT INTO `sys_menu` VALUES ('3', '1', '角色管理', 'sys/role', null, '1', 'role', '2', '1');
INSERT INTO `sys_menu` VALUES ('4', '1', '菜单管理', 'sys/menu', null, '1', 'menu', '3', '1');
INSERT INTO `sys_menu` VALUES ('5', '1', 'SQL监控', 'http://localhost:8080/renren-fast/druid/sql.html', null, '1', 'sql', '4', '0');
INSERT INTO `sys_menu` VALUES ('6', '1', '定时任务', 'job/schedule', null, '1', 'job', '5', '1');
INSERT INTO `sys_menu` VALUES ('7', '6', '查看', null, 'sys:schedule:list,sys:schedule:info', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('8', '6', '新增', null, 'sys:schedule:save', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('9', '6', '修改', null, 'sys:schedule:update', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('10', '6', '删除', null, 'sys:schedule:delete', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('11', '6', '暂停', null, 'sys:schedule:pause', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('12', '6', '恢复', null, 'sys:schedule:resume', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('13', '6', '立即执行', null, 'sys:schedule:run', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('14', '6', '日志列表', null, 'sys:schedule:log', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('15', '2', '查看', null, 'sys:user:list,sys:user:info', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('16', '2', '新增', null, 'sys:user:save,sys:role:select', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('17', '2', '修改', null, 'sys:user:update,sys:role:select', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('18', '2', '删除', null, 'sys:user:delete', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('19', '3', '查看', null, 'sys:role:list,sys:role:info', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('20', '3', '新增', null, 'sys:role:save,sys:menu:list', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('21', '3', '修改', null, 'sys:role:update,sys:menu:list', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('22', '3', '删除', null, 'sys:role:delete', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('23', '4', '查看', null, 'sys:menu:list,sys:menu:info', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('24', '4', '新增', null, 'sys:menu:save,sys:menu:select', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('25', '4', '修改', null, 'sys:menu:update,sys:menu:select', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('26', '4', '删除', null, 'sys:menu:delete', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('27', '1', '参数管理', 'sys/config', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', '1', 'config', '6', '0');
INSERT INTO `sys_menu` VALUES ('29', '1', '系统日志', 'sys/log', 'sys:log:list', '1', 'log', '7', '0');
INSERT INTO `sys_menu` VALUES ('30', '1', '文件上传', 'oss/oss', 'sys:oss:all', '1', 'oss', '6', '0');

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '测试', 'aa', '1', '2020-06-24 09:59:50');

-- ----------------------------
-- Table structure for `sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  `create_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1', '1', '2', '1');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '123', 'root@fafafa', '13612345678', '1', '1', '2016-11-11 11:11:11');

-- ----------------------------
-- Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `create_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='用户与角色对应关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', '1', '1');

-- ----------------------------
-- Table structure for `sys_user_token`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token` (
  `user_id` bigint(20) NOT NULL,
  `token` varchar(100) NOT NULL COMMENT 'token',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户Token';

-- ----------------------------
-- Records of sys_user_token
-- ----------------------------
INSERT INTO `sys_user_token` VALUES ('1', '5211beeefb4147c6afb689fa7cb1169c', '2020-06-24 03:19:29', '2020-06-24 01:19:29');

-- ----------------------------
-- Table structure for `tb_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `mobile` varchar(20) NOT NULL COMMENT '手机号',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='用户';

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('1', 'mark', '13612345678', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '2017-03-23 22:37:41');
