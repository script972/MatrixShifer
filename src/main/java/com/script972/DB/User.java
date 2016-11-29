package com.script972.DB;

/**
 * Created by script972 on 15.11.2016.
 */
public class User {
    private int id;
    private String name;
    private String password;
    private String secretInfo;

    public User(int id, String name, String password, String secretInfo) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.secretInfo = secretInfo;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", secretInfo='" + secretInfo + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecretInfo() {
        return secretInfo;
    }

    public void setSecretInfo(String secretInfo) {
        this.secretInfo = secretInfo;
    }
}

