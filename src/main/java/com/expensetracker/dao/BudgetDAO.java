package com.expensetracker.dao;

import com.expensetracker.database.DatabaseConnection;
import com.expensetracker.models.Budget;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BudgetDAO {

    public boolean saveBudget(Budget budget) {

        String sql = "INSERT INTO budgets(amount) VALUES(?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, budget.getAmount());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public double getLatestBudget() {

        String sql =
                "SELECT amount FROM budgets ORDER BY id DESC LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("amount");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public double getTotalExpenses() {

        String sql = "SELECT SUM(amount) AS total FROM expenses";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public double getRemainingBudget() {

        return getLatestBudget() - getTotalExpenses();
    }
}