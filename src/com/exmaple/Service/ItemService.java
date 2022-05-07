package com.exmaple.Service;

import com.exmaple.Dao.DBDao;
import com.exmaple.Dao.UtilsDao;
import com.exmaple.Entity.ItemAttribute;
import com.exmaple.Entity.User;
import com.exmaple.Entity.UserResources;
import com.mysql.cj.conf.ConnectionUrlParser;

import java.io.InterruptedIOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

/**
 * @param
 * @author Z.Xue
 */

public class ItemService {
    public boolean userSubmitBuyRequest(String username, HashMap<String, Integer> item) {
        new DBDao().init();
        Date startTime = new Date();
        startTime.setTime(System.currentTimeMillis());

        item.forEach((k, v) -> {
            for (int i = 0; i < v; i++) {
                Date dueTime = new Date();
                dueTime.setTime(System.currentTimeMillis() + (long) 1000 * 3600 * 24 * ItemAttribute.valueOf(k).getDays());
                String webPageMd5 = new UtilsDao().stringToMD5(username.concat(String.valueOf(System.currentTimeMillis())).concat(k).concat(String.valueOf(new Random().nextInt())));
                try {
                    UserResources userResources = new UserResources(username, k, startTime, dueTime, ItemAttribute.valueOf(k).getMoney(), 0);
                    String sql = "Insert into userResources(username, adName, startTime, dueTime, money, uniqueVisitors, webpage) " +
                            "values(?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement = DBDao.connection.prepareStatement(sql);
                    statement.setString(1, userResources.getUsername());
                    statement.setString(2, userResources.getAdName());
                    statement.setObject(3, userResources.getStartTime());
                    statement.setObject(4, userResources.getDueTime());
                    statement.setInt(5, (int) userResources.getMoney());
                    statement.setInt(6, userResources.getUniqueVisitors());
                    statement.setString(7, webPageMd5);

                    statement.executeUpdate();
                } catch (SQLException e) {
                    return;
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
                    result++;
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

    public User getAdOwner(String url) {
        new DBDao().init();
        User user = new User();
        try {
            String sql = "Select * from user where username in (Select username from userResources where webpage=?)";
            PreparedStatement preparedStatement = DBDao.connection.prepareStatement(sql);
            preparedStatement.setString(1, url);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user.setUsername(resultSet.getString("username"));
                user.setGender(resultSet.getString("gender"));
                user.setEducation(resultSet.getString("education"));
            }
        } catch (SQLException e) {
            return null;
        }
        return user;
    }

    public UserResources getAdDetails(String url) {
        new DBDao().init();
        UserResources user = new UserResources();
        try {
            String sql = "Select * from userResources where webpage=?";
            PreparedStatement preparedStatement = DBDao.connection.prepareStatement(sql);
            preparedStatement.setString(1, url);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user.setUsername(resultSet.getString("username"));
                user.setAdName(resultSet.getString("adName"));
                user.setStartTime(resultSet.getDate("startTime"));
                user.setDueTime(resultSet.getDate("dueTime"));
                user.setUniqueVisitors(resultSet.getInt("uniqueVisitors"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public void upDateVisitor(String url) {
        new DBDao().init();
        try {
            String sql = "Update userResources set uniqueVisitors=uniqueVisitors+1 where webpage=?";
            PreparedStatement preparedStatement = DBDao.connection.prepareStatement(sql);
            preparedStatement.setString(1, url);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            String sql = "Select * from dailyVisitor where webpage=? and TO_DAYS(date)=TO_DAYS(now())";
            PreparedStatement preparedStatement = DBDao.connection.prepareStatement(sql);
            preparedStatement.setString(1, url);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                Date date = new Date();
                date.setTime(System.currentTimeMillis());
                sql = "Insert into dailyVisitor(webpage, date, visitor) values (?, ?, 0)";
                preparedStatement = DBDao.connection.prepareStatement(sql);
                preparedStatement.setString(1, url);
                preparedStatement.setObject(2, date);
                preparedStatement.executeUpdate();
            } else {
                sql = "Update dailyVisitor set visitor=visitor+1 where webpage=? and TO_DAYS(date)=TO_DAYS(now())";
                preparedStatement = DBDao.connection.prepareStatement(sql);
                preparedStatement.setString(1, url);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> getLast10DaysVisit(String username) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        try {
            for (int i = 0; i <= 10; i++) {
                Date date = new Date();
                date.setTime(System.currentTimeMillis() - 1000 * 3600 * 24 * i);
                String sql = "Select sum(visitor) nums from dailyVisitor where webpage in (select webpage from userResources where username=?) and TO_DAYS(now())-TO_DAYS(date)=?";
                PreparedStatement preparedStatement = DBDao.connection.prepareStatement(sql);
                preparedStatement.setString(1, username);
                preparedStatement.setInt(2, i);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    arrayList.add(resultSet.getInt("nums"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public ArrayList<Integer> getAllUsersLast10DaysVisit(String username) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        try {
            for (int i = 0; i <= 10; i++) {
                Date date = new Date();
                date.setTime(System.currentTimeMillis() - 1000 * 3600 * 24 * i);
                String sql = "Select sum(visitor) nums from dailyVisitor where TO_DAYS(now())-TO_DAYS(date)=?";
                PreparedStatement preparedStatement = DBDao.connection.prepareStatement(sql);
                preparedStatement.setInt(1, i);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    arrayList.add(resultSet.getInt("nums"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static void main(String[] args) throws SQLException {
        new DBDao().init();
        ArrayList<Integer> arrayList = new ItemService().getLast10DaysVisit("kototo");
        for (Integer i : arrayList) {
            System.out.println(i);
        }
        new ItemService().getAdDetails("f74a4f76c552b0234306b4fcd66d3e63");
    }
}
