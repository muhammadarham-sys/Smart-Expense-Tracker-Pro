package com.expensetracker.dao;

import com.expensetracker.models.User;

public class TestRegister {

    public static void main(String[] args) {

        User user = new User("arham", "12345");

        UserDAO userDAO = new UserDAO();

        if (userDAO.registerUser(user)) {
            System.out.println("User Registered Successfully!");
        } else {
            System.out.println("Registration Failed!");
        }
    }
}