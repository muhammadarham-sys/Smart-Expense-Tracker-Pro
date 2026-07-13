package com.expensetracker.models;


import java.sql.Date;

public class Expense {

    private int id;
    private String title;
    private double amount;
    private String category;
    private Date date;
    private int userId;

    public Expense() {
    }

    public Expense(String title, double amount,
                   String category, Date date, int userId) {
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.userId = userId;
    }

    public Expense(int id, String title, double amount,
                   String category, Date date, int userId) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}