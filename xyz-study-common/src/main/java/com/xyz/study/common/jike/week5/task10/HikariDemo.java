package com.xyz.study.common.jike.week5.task10;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author Zhu WeiJie
 * @date 2021/7/25
 **/
public class HikariDemo {

    private HikariDataSource ds;

    {
        HikariConfig configuration = new HikariConfig();
        configuration.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/test?characterEncoding=utf8&useSSL=false&serverTimezone=GMT");
        configuration.setUsername("root");
        configuration.setPassword("123456");
        configuration.setDriverClassName("com.mysql.cj.jdbc.Driver");
        configuration.setMaximumPoolSize(5);
        ds = new HikariDataSource(configuration);
    }

    /**
     * 改为由HikariDataSource拿连接 用的地方对比PreJdbcDemo无需改动
     */
    private Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public void insert(User user) {
        try (Connection con = getConnection()) {
            PreparedStatement ps = con.prepareStatement("insert into t_user(nickname,avatar,sex) values (?,?,?)");
            ps.setString(1, user.getNickname());
            ps.setString(2, user.getAvatar());
            ps.setString(3, user.getSex());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 配合事务的多条插入
     */
    public void insert(User... users) {
        Connection con = null;
        try {
            con = getConnection();
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement("insert into t_user(nickname,avatar,sex) values (?,?,?)");
            for (User user : users) {
                ps.setString(1, user.getNickname());
                ps.setString(2, user.getAvatar());
                ps.setString(3, user.getSex());
                ps.executeUpdate();
            }
            con.commit();
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    /**
     * 批处理直接批插入多条
     *
     * @param users
     */
    public void batchInsert(List<User> users) {
        try (Connection con = getConnection()) {
            PreparedStatement ps = con.prepareStatement("insert into t_user(nickname,avatar,sex) values (?,?,?)");
            for (User user : users) {
                ps.setString(1, user.getNickname());
                ps.setString(2, user.getAvatar());
                ps.setString(3, user.getSex());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void delete(long id) {
        try (Connection con = getConnection()) {
            PreparedStatement ps = con.prepareStatement("delete from t_user where id = ?");
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateNickname(long id, String nickname) {
        try (Connection con = getConnection()) {
            PreparedStatement ps = con.prepareStatement("update t_user set nickname = ? where id = ?");
            ps.setString(1, nickname);
            ps.setLong(2, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public User selectById(long id) {
        try (Connection con = getConnection()) {
            PreparedStatement ps = con.prepareStatement("select * from t_user where id = ?");
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setNickname(resultSet.getString("nickname"));
                user.setAvatar(resultSet.getString("avatar"));
                user.setSex(resultSet.getString("sex"));
                return user;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        HikariDemo demo = new HikariDemo();
//        User user = demo.selectById(1);
//        System.out.println(user);

        User user1 = new User();
        user1.setNickname("小胖");
        user1.setAvatar("default");
        user1.setSex("M");
        User user2 = new User();
        user2.setNickname("小秀");
        user2.setAvatar("default");
        user2.setSex("W");
        demo.batchInsert(Arrays.asList(user1, user2));


    }

}
