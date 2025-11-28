package com.example.teacherApp.dao;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class DataBase {

    public DataBase(){}

    public Connection getConnect() {
        Connection connection = null;
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:school.db");
            connection.createStatement().execute("PRAGMA foreign_keys = ON;");
        }
        catch (Exception e){
            System.out.println("Could not connect.");
        }
        return connection;
    }
}
