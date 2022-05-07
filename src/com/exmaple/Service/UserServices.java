package com.exmaple.Service;

import com.exmaple.Dao.DBDao;
import com.exmaple.Entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserServices {
    public boolean validateUser(String userName, String password) {
        new DBDao().init();
        try {
            String sql = "SELECT * FROM user WHERE username=? AND password=?";
            PreparedStatement preparedStatement = DBDao.connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
//监听器过滤器处理错误session
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException ignored) {
        }
        return false;
    }
    public boolean Register(User user) {

        new DBDao().init();
        try {
            String sql="insert into user (username, password, gender, education) values(?, ?, ?, ?);";
            PreparedStatement statement = DBDao.connection.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getGender());
            statement.setString(4, user.getEducation());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        }
        return true;
    }

    public User getUserDetails (String username) {
        new DBDao().init();
        User user = new User();
        try {
            String sql="select * from user where username=?";
            PreparedStatement statement = DBDao.connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setGender(resultSet.getString("gender"));
                user.setEducation(resultSet.getString("education"));
            }
        } catch (SQLException e) {
            return null;
        }
        return user;
    }
}