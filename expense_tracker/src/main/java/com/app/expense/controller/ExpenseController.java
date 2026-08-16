package com.app.expense.controller;

import com.app.expense.request_dto.ExpenseRequestDTO;
import com.app.expense.response_dto.ExpenseResponseDTO;
import com.app.expense.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/add_expense")
    public ResponseEntity<ExpenseResponseDTO> addExpense(@RequestBody ExpenseRequestDTO expense){
        try {
            return ResponseEntity.ok(expenseService.addExpense(expense));
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update_expense/{expense_id}")
    public ResponseEntity<ExpenseResponseDTO> updateExpense(@PathVariable ("expense_id") Integer expenseId,
                                                            @RequestBody ExpenseRequestDTO expense){
        try{
            return ResponseEntity.ok(expenseService.updateExpense(expenseId, expense));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete_expense/{expense_id}")
    public ResponseEntity<ExpenseResponseDTO> deleteExpense(@PathVariable ("expense_id") Integer expenseId){
        try{
            return ResponseEntity.ok(expenseService.deleteExpense(expenseId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/get_expense_records")
    public ResponseEntity<List<ExpenseResponseDTO>> getExpenseRecords(){
        try{
            return ResponseEntity.ok(expenseService.getExpenseRecords());
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/get_expense_records/{user_id}")
    public ResponseEntity<List<ExpenseResponseDTO>> getExpenseRecordsByUserID(@PathVariable("user_id") int userId){
        try{
            return ResponseEntity.ok(expenseService.getExpenseRecordsByUserID(userId));
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/expense_records_by_month/{month}")
    public ResponseEntity<List<ExpenseResponseDTO>> getExpenseRecordsByMonth(@PathVariable int month){
        try{
            return ResponseEntity.ok(expenseService.getExpenseRecordsByMonth(month));
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }
}
