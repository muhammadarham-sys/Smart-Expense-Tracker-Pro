package com.expensetracker.controllers;

import com.expensetracker.dao.BudgetDAO;
import com.expensetracker.models.Budget;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/budget")
@CrossOrigin(origins = "*")
public class BudgetController {

    @PostMapping
    public String saveBudget(@RequestBody Budget budget) {

        BudgetDAO dao = new BudgetDAO();

        if (dao.saveBudget(budget)) {
            return "Budget Saved";
        }

        return "Failed";
    }

    @GetMapping("/remaining")
    public double getRemainingBudget() {

        BudgetDAO dao = new BudgetDAO();
        return dao.getRemainingBudget();
    }
}