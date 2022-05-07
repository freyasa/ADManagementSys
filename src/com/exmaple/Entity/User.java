package com.exmaple.Entity;

public class User implements BaseEntity{
    private int id;
    private String username, password, education;
    private String gender;

    public User() {
    }

    public User(String username, String password, String education, String gender) {
        this.username = username;
        this.password = password;
        this.education = education;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
