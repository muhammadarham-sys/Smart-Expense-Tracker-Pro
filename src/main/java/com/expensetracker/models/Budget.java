package com.expensetracker.models;


public class Budget {

    private int id;
    private double amount;

    public Budget() {
    }

    public Budget(double amount) {
        this.amount = amount;
    }

    public Budget(int id, double amount) {
        this.id = id;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
