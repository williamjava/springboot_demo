DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名',
  `category` varchar(40) NOT NULL DEFAULT '' COMMENT '所属分类（使用枚举英文）',
  `style` varchar(20) NOT NULL DEFAULT '' COMMENT '风格标签',
  `delivery_address` varchar(50) DEFAULT NULL COMMENT '客户收货地址',
  `contact` varchar(10) DEFAULT NULL COMMENT '联系人',
  `contact_phone` varchar(15) DEFAULT NULL COMMENT '联系电话',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  `deleted` tinyint(4) DEFAULT '0' COMMENT '0 否 1 是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='客户表';

DROP TABLE IF EXISTS `pattern_detail`;
CREATE TABLE `pattern_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pattern_id` bigint(20) DEFAULT NULL COMMENT '款式ID',
  `pic_url` varchar(50) DEFAULT NULL COMMENT '款式图片名称',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  `deleted` tinyint(4) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='款式详情表';

DROP TABLE IF EXISTS `pattern`;
CREATE TABLE `pattern` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '款式名称',
  `desc` varchar(200) DEFAULT NULL COMMENT '款式描述',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  `deleted` tinyint(4) DEFAULT '0',
  `default_pic_url` varchar(50) DEFAULT NULL COMMENT '款式默认图片',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='款式表';

DROP TABLE IF EXISTS `requirement_pattern`;
CREATE TABLE `requirement_pattern` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `requirement_id` bigint(20) NOT NULL COMMENT '需求ID',
  `pattern_id` bigint(20) NOT NULL COMMENT '款式ID',
  `chosen` tinyint(4) DEFAULT '0' COMMENT '是否选中',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  `deleted` tinyint(4) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='需求款式关联表';

DROP TABLE IF EXISTS `requirement`;
CREATE TABLE `requirement` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `customer_id` bigint(11) NOT NULL COMMENT '客户表的id',
  `customer_name` varchar(20) NOT NULL DEFAULT '' COMMENT '客户名称',
  `pattern` varchar(100) DEFAULT '' COMMENT '款式要求',
  `style` varchar(100) DEFAULT NULL COMMENT '风格要求',
  `doc` varchar(30) DEFAULT NULL COMMENT '客户提供的文档',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `status` int(3) NOT NULL COMMENT '需求单状态',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='客户需求单表';

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `loginname` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(40) NOT NULL DEFAULT '' COMMENT '密码',
  `email` varchar(40) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(15) DEFAULT NULL COMMENT '联系电话',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '0 否 1 是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';


INSERT INTO `user` (`loginname`, `password`, `created_at`, `updated_at`, `deleted`) VALUES ('admin', '96e79218965eb72c92a549dd5a330112', NOW(), NOW(), 0);