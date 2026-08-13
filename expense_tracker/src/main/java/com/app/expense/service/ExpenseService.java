package com.app.expense.service;

import com.app.expense.entity.Expense;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExpenseService {
    public String addExpense(String user, Expense expense){
        try{
            return "";
        }catch (Exception e){
            return "";
        }
    }
}
