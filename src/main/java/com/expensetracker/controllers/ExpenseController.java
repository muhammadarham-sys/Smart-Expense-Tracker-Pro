package com.expensetracker.controllers;

import com.expensetracker.dao.ExpenseDAO;
import com.expensetracker.models.Expense;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "*")
public class ExpenseController {

    @GetMapping
    public List<Expense> getAllExpenses() {

        ExpenseDAO dao = new ExpenseDAO();
        return dao.getAllExpenses();
    }

    @PostMapping
    public String addExpense(@RequestBody Expense expense) {

        ExpenseDAO dao = new ExpenseDAO();

        if (dao.addExpense(expense)) {
            return "Expense Added";
        }

        return "Failed";
    }

    @PutMapping("/{id}")
    public String updateExpense(
            @PathVariable int id,
            @RequestBody Expense expense
    ) {

        ExpenseDAO dao = new ExpenseDAO();

        expense.setId(id);

        if (dao.updateExpense(expense)) {
            return "Expense Updated";
        }

        return "Failed";
    }

    @DeleteMapping("/{id}")
    public String deleteExpense(@PathVariable int id) {

        ExpenseDAO dao = new ExpenseDAO();

        if (dao.deleteExpense(id)) {
            return "Deleted";
        }

        return "Failed";
    }
}