package com.xyz.study.common.jike.week7.task2;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试大量数据插入-方式2
 * 拼装好sql一次性执行
 *
 * @author Zhu WeiJie
 * @date 2021/8/5
 **/
@Slf4j
public class TestLargeDataInsert_2 {

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/xyz_shop?characterEncoding=utf8&useSSL=false&serverTimezone=GMT";
        String user = "root";
        String password = "123456";
        return DriverManager.getConnection(url, user, password);
    }


    private static final String INSERT_SQL = "INSERT INTO `t_order`(`uid`, `product_id`, `product_snapshot`, `product_amount`, `order_status`, `order_price`, `create_time`, `update_time`) " +
            "VALUES";
    private static final String INSERT_SQL_VALUE = "(%s, %s, '%s', %s, %s, %s, %s, %s)";

    public void insertMulti(List<Order> orders) {
        try (Connection con = getConnection()) {
            Statement statement = con.createStatement();
            StringBuilder builder = new StringBuilder();
            orders.forEach(order -> {
                String sql = String.format(INSERT_SQL_VALUE,
                        order.getUid(),
                        order.getProductId(),
                        order.getProductSnapshot(),
                        order.getProductAmount(),
                        order.getOrderStatus(),
                        order.getOrderPrice(),
                        order.getCreateTime(),
                        order.getUpdateTime());
                builder.append("\n").append(sql).append(",");
            });
            builder.deleteCharAt(builder.length() - 1).append(";");
            statement.executeUpdate(INSERT_SQL + builder.toString());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        TestLargeDataInsert_2 o = new TestLargeDataInsert_2();
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            Order testOrder = new Order();
            testOrder.setUid(i);
            testOrder.setProductId(i);
            testOrder.setProductSnapshot("");
            testOrder.setProductAmount(1);
            testOrder.setOrderStatus(1);
            testOrder.setOrderPrice(1);
            testOrder.setCreateTime(1);
            testOrder.setUpdateTime(1);
            orders.add(testOrder);

            if (orders.size() == 1000) {
                o.insertMulti(orders);
                orders.clear();
            }
        }

        if (orders.size() != 0) {
            o.insertMulti(orders);
            orders.clear();
        }

        log.info("cost={}ms", System.currentTimeMillis() - start);
    }

}
