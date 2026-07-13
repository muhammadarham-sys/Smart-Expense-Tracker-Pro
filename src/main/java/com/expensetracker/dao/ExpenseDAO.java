package com.expensetracker.dao;

import com.expensetracker.database.DatabaseConnection;
import com.expensetracker.models.Expense;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {

    public boolean addExpense(Expense expense) {

        String sql =
                "INSERT INTO expenses(title, amount, category, date, user_id) VALUES(?,?,?,?,?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, expense.getTitle());
            stmt.setDouble(2, expense.getAmount());
            stmt.setString(3, expense.getCategory());
            stmt.setDate(4, expense.getDate());
            stmt.setInt(5, expense.getUserId());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateExpense(Expense expense) {

        String sql =
                "UPDATE expenses SET title=?, amount=?, category=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, expense.getTitle());
            stmt.setDouble(2, expense.getAmount());
            stmt.setString(3, expense.getCategory());
            stmt.setInt(4, expense.getId());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Expense> getAllExpenses() {

        List<Expense> expenses = new ArrayList<>();

        String sql = "SELECT * FROM expenses";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                Expense expense = new Expense(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getDouble("amount"),
                        rs.getString("category"),
                        rs.getDate("date"),
                        rs.getInt("user_id")
                );

                expenses.add(expense);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return expenses;
    }

    public boolean deleteExpense(int id) {

        String sql = "DELETE FROM expenses WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}