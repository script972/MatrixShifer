package com.script972.DB;

import com.script972.controller.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * Created by script972 on 13.11.2016.
 */
public class DBMani {
    private static final String URL="jdbc:mysql://localhost:3306/buisnes";
    private static final String USERNAME="root";
    private static final String PASSWORD="root";
    private ResultSet resultSet;

    public ResultSet getResultSet() {
        return resultSet;
    }

    public ObservableList<User> TakePersonLog(String loginF) throws SQLException {
        ObservableList<User> userObservableList= FXCollections.observableArrayList();
        DBProcessor db=new DBProcessor();
        Connection conn=db.getConnection(URL, USERNAME, PASSWORD);
        String query="select * from buisnes.user where name='"+loginF+"'";
        Statement statement=conn.createStatement();
        ResultSet resSet=statement.executeQuery(query);
        while (resSet.next()){
            int user_id;
            String name;
            String password;
            String secretInfo;
            user_id=resSet.getInt("user_id");
            name=resSet.getString("name");
            password=resSet.getString("password");
            secretInfo=resSet.getString("secretInfo");
            User user=new User(user_id, name, password, secretInfo);
            userObservableList.add(user);
        }
        statement.close();
        conn.close();
        return userObservableList;
    }







}
