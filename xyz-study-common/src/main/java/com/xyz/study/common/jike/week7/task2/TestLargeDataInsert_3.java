package com.xyz.study.common.jike.week7.task2;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试大量数据插入-方式3
 * PreparedStatement的batch方式
 *
 * @author Zhu WeiJie
 * @date 2021/8/5
 **/
@Slf4j
public class TestLargeDataInsert_3 {

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/xyz_shop?characterEncoding=utf8&useSSL=false&serverTimezone=GMT";
        String user = "root";
        String password = "123456";
        return DriverManager.getConnection(url, user, password);
    }

    private static final String INSERT_SQL = "INSERT INTO `t_order`(`uid`, `product_id`, `product_snapshot`, `product_amount`, `order_status`, `order_price`, `create_time`, `update_time`) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

    public void insertBatch(List<Order> orders) {
        try (Connection con = getConnection()) {
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement(INSERT_SQL);

            for (Order order : orders) {
                ps.setLong(1,order.getUid());
                ps.setLong(2,order.getProductId());
                ps.setString(3,order.getProductSnapshot());
                ps.setInt(4,order.getProductAmount());
                ps.setInt(5,order.getOrderStatus());
                ps.setLong(6,order.getOrderPrice());
                ps.setLong(7,order.getCreateTime());
                ps.setLong(8,order.getUpdateTime());
                ps.addBatch();
            }
            ps.executeBatch();
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        TestLargeDataInsert_3 o = new TestLargeDataInsert_3();
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
                o.insertBatch(orders);
                orders.clear();
            }
        }

        if (orders.size() != 0) {
            o.insertBatch(orders);
            orders.clear();
        }

        log.info("cost={}ms", System.currentTimeMillis() - start);
    }

}
