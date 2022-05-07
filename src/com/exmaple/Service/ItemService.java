package com.exmaple.Service;

import com.exmaple.Dao.DBDao;
import com.exmaple.Entity.ItemAttribute;
import com.exmaple.Entity.UserResources;
import com.mysql.cj.conf.ConnectionUrlParser;

import java.io.InterruptedIOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

/**
 * @author Z.Xue
 * @param
 *
 */

public class ItemService {
    public boolean userSubmitBuyRequest (String username, HashMap<String, Integer> item) {
        new DBDao().init();
        Date startTime = new Date();
        startTime.setTime(System.currentTimeMillis());

        item.forEach((k, v)-> {
            for (int i = 0; i < v; i ++) {
                Date dueTime = new Date();
                dueTime.setTime(System.currentTimeMillis() + (long) 1000 * 3600 * 24 * ItemAttribute.valueOf(k).getDays());
                try {
                    UserResources userResources = new UserResources(username, k, startTime, dueTime, ItemAttribute.valueOf(k).getMoney(), 0);
                    String sql = "Insert into userResources(username, adName, startTime, dueTime, money, uniqueVisitors) " +
                            "values(?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement = DBDao.connection.prepareStatement(sql);
                    statement.setString(1, userResources.getUsername());
                    statement.setString(2, userResources.getAdName());
                    statement.setObject(3, userResources.getStartTime());
                    statement.setObject(4, userResources.getDueTime());
                    statement.setInt(5, (int) userResources.getMoney());
                    statement.setInt(6, userResources.getUniqueVisitors());

                    statement.executeUpdate();
                }catch (SQLException e) {
                    return ;
                }
            }
        });
        return true;
    }

    public int getExpireADs(String username) {
        int result = 0;
        new DBDao().init();
        try {
            String sql = "Select * from userResources where username=?";
            PreparedStatement preparedStatement = DBDao.connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getDate("dueTime").getTime() <= (long) 1000 * 3600 * 24 * 3 + System.currentTimeMillis()) {
                    result ++;
                }
            }
        } catch (SQLException e) {
            return 0;
        }
        return result;
    }

    public int getAllMoney(String username) {
        int result = 0;
        new DBDao().init();
        try {
            String sql = "Select sum(money) allMoney from userResources group by username having username=?";
            PreparedStatement preparedStatement = DBDao.connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt("allMoney");
            }
        } catch (SQLException e) {
            return 0;
        }
        return result;
    }

    public int getADSum(String username) {
        int result = 0;
        new DBDao().init();
        try {
            String sql = "Select count(*) ADSum from userResources group by username having username=?";
            PreparedStatement preparedStatement = DBDao.connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt("ADSum");
            }
        } catch (SQLException e) {
            return 0;
        }
        return result;
    }

    public int getUniqueVisitors(String username) {
        int result = 0;
        new DBDao().init();
        try {
            String sql = "Select sum(uniqueVisitors) visitorSum from userResources group by username having username=?";
            PreparedStatement preparedStatement = DBDao.connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt("visitorSum");
            }
        } catch (SQLException e) {
            return 0;
        }
        return result;
    }

    public int getYearlyVisitors(String username) {
        int result = 0;
        new DBDao().init();
        try {
            String sql = "Select sum(uniqueVisitors) visitorSum from userResources where year(dueTime) = year(now())" +
                    " group by username having username=?;";
            PreparedStatement preparedStatement = DBDao.connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt("visitorSum");
            }
        } catch (SQLException e) {
            return 0;
        }
        return result;
    }

    public int getYearlyExpense(String username) {
        int result = 0;
        new DBDao().init();
        try {
            String sql = "Select sum(money) allMoney from userResources where year(dueTime) = year(now()) " +
                    "group by username having username=?";
            PreparedStatement preparedStatement = DBDao.connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt("allMoney");
            }
        } catch (SQLException e) {
            return 0;
        }
        return result;
    }
}
