package com.exmaple.Dao;

import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Z.Xue
 * @apiNote Link database
 */

public class DBDao implements BaseDao{

    public static Connection connection;

    public void init() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/servlet";
            //数据库连接用户
            String user = "root";
            //用户密码
            String pwd = "saMandy99";

            connection = DriverManager.getConnection(url, user, pwd);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
