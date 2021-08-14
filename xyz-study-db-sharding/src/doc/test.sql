CREATE TABLE `t_order`
(
    `id`               bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `uid`              bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
    `product_id`       bigint(20) NOT NULL DEFAULT '0' COMMENT '商品id',
    `product_amount`   int(11) NOT NULL DEFAULT '0' COMMENT '商品数量',
    `order_status`     tinyint(4) NOT NULL DEFAULT '0' COMMENT '订单状态 0:待支付 1:待发货 2:待收货 3:已完成 4:已取消',
    `order_price`      bigint(20) NOT NULL DEFAULT '0' COMMENT '订单金额 单位:分',
    `create_time`      bigint(20) NOT NULL DEFAULT '0' COMMENT '添加时间',
    `update_time`      bigint(20) NOT NULL DEFAULT '0' COMMENT '修改时间',
    `del_flag`         tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识 0:未删除 1:删除',
    PRIMARY KEY (`id`),
    KEY                `idx_uid` (`uid`),
    KEY                `idx_product_id` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='订单表';

CREATE TABLE `t_order_0` like t_order;
CREATE TABLE `t_order_1` like t_order;
CREATE TABLE `t_order_2` like t_order;
CREATE TABLE `t_order_3` like t_order;
CREATE TABLE `t_order_4` like t_order;
CREATE TABLE `t_order_5` like t_order;
CREATE TABLE `t_order_6` like t_order;
CREATE TABLE `t_order_7` like t_order;
CREATE TABLE `t_order_8` like t_order;
CREATE TABLE `t_order_9` like t_order;
CREATE TABLE `t_order_10` like t_order;
CREATE TABLE `t_order_11` like t_order;
CREATE TABLE `t_order_12` like t_order;
CREATE TABLE `t_order_13` like t_order;
CREATE TABLE `t_order_14` like t_order;
CREATE TABLE `t_order_15` like t_order;