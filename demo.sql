CREATE TABLE `pattern` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) NOT NULL COMMENT '款式名称',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='款式表';

CREATE TABLE `pattern_detail` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `pattern_id` bigint(11) NOT NULL COMMENT '款式ID',
  'pic_url' varchar(100) DEFAULT NULL COMMENT '款式图地址',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='款式详情表';