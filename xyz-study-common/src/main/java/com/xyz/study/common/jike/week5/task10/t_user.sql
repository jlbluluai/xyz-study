CREATE TABLE `t_user`
(
    `id`       bigint(20) NOT NULL AUTO_INCREMENT,
    `nickname` varchar(128) NOT NULL DEFAULT '' COMMENT '昵称',
    `avatar`   varchar(256) NOT NULL DEFAULT '' COMMENT '头像',
    `sex`      char(1)      NOT NULL DEFAULT '' COMMENT '性别 M/W',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB COMMENT='用户表';