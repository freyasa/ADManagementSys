package com.exmaple.Entity;

import java.util.Date;

/**
 * @author Z.Xue
 * create table userResources
 * (
 *     adId int primary key unique not null auto_increment,
 *     username char(20) not null,
 *     adName char(20) not null,
 *     startTime date not null,
 *     dueTime date not null,
 *     money float not null,
 *     uniqueVisitors int default 0,
 *     foreign key (username) references user(username)
 * );
 */

public class UserResources implements BaseEntity{
    private int adId;
    private String username, adName;
    private Date startTime, dueTime;
    float money;
    int uniqueVisitors;

    public UserResources() {
    }

    public UserResources(String username, String adName, Date startTime, Date dueTime, float money, int uniqueVisitors) {
        this.username = username;
        this.adName = adName;
        this.startTime = startTime;
        this.dueTime = dueTime;
        this.money = money;
        this.uniqueVisitors = uniqueVisitors;
    }

    public int getAdId() {
        return adId;
    }

    public void setAdId(int adId) {
        this.adId = adId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getDueTime() {
        return dueTime;
    }

    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public int getUniqueVisitors() {
        return uniqueVisitors;
    }

    public void setUniqueVisitors(int uniqueVisitors) {
        this.uniqueVisitors = uniqueVisitors;
    }
}
