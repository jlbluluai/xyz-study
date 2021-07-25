package com.xyz.study.common.jike.week5.task10;

import java.sql.*;

/**
 * @author Zhu WeiJie
 * @date 2021/7/25
 **/
public class JdbcDemo {

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/test?characterEncoding=utf8&useSSL=false&serverTimezone=GMT";
        String user = "root";
        String password = "123456";
        return DriverManager.getConnection(url, user, password);
    }

    public void insert(User user) {
        try (Connection con = getConnection()) {
            Statement statement = con.createStatement();
            statement.execute("insert into t_user(nickname,avatar,sex) values ('" + user.getNickname() + "','" + user.getAvatar() + "','" + user.getSex() + "')");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void delete(long id) {
        try (Connection con = getConnection()) {
            Statement statement = con.createStatement();
            statement.execute("delete from t_user where id =" + id);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateNickname(long id, String nickname) {
        try (Connection con = getConnection()) {
            Statement statement = con.createStatement();
            statement.execute("update t_user set nickname = '" + nickname + "' where id = " + id);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public User selectById(long id) {
        try (Connection con = getConnection()) {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from t_user where id = " + id);
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
        JdbcDemo demo = new JdbcDemo();
        User user = demo.selectById(1);
        System.out.println(user);

//        User user = new User();
//        user.setNickname("小美");
//        user.setAvatar("default");
//        user.setSex("W");
//        demo.insert(user);

//        demo.updateNickname(3,"小美2");

//        demo.delete(3);
    }
}
