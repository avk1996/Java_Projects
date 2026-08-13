package com.app.expense.controller;

import com.app.expense.entity.Expense;
import com.app.expense.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/add_expense/{user}")
    public ResponseEntity<String> addExpense(@PathVariable String user, @RequestBody Expense expense){
        try {
            expenseService.addExpense(user, expense);
            return ResponseEntity.ok("");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("");
        }
    }
}
