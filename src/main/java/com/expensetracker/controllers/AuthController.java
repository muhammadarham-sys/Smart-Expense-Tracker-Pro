package com.expensetracker.controllers;

import com.expensetracker.dao.UserDAO;
import com.expensetracker.models.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @PostMapping("/register")
    public String register(@RequestBody User user) {

        UserDAO dao = new UserDAO();

        if (dao.registerUser(user)) {
            return "Registration Successful";
        }

        return "Registration Failed";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {

        UserDAO dao = new UserDAO();

        if (dao.loginUser(
                user.getUsername(),
                user.getPassword())) {

            return "Login Successful";
        }

        return "Invalid Username or Password";
    }
}