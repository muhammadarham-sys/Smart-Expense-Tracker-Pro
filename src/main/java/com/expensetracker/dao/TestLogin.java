package com.expensetracker.dao;

public class TestLogin {

    public static void main(String[] args) {

        UserDAO dao = new UserDAO();

        boolean success = dao.loginUser("arham", "12345");

        if (success) {
            System.out.println("Login Successful!");
        } else {
            System.out.println("Invalid Username or Password!");
        }
    }
}