package com.expensetracker.database;

import java.sql.Connection;

public class TestConnection {

    public static void main(String[] args) {

        Connection connection =
                DatabaseConnection.getConnection();

        if(connection != null) {
            System.out.println("Connected Successfully!");
        } else {
            System.out.println("Connection Failed!");
        }
    }
}