package com.app.expense.controller;

import com.app.expense.request_dto.ExpenseRequestDTO;
import com.app.expense.response_dto.ExpenseResponseDTO;
import com.app.expense.service.ExpenseService;
import org.springframework.security.core.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    private static final Logger logger = LoggerFactory.getLogger(ExpenseController.class);

    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/add_expense")
    public ResponseEntity<?> addExpense(@RequestBody ExpenseRequestDTO expense, Authentication authentication){
        try {
            return ResponseEntity.ok(expenseService.addExpense(authentication, expense));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update_expense/{expense_id}")
    public ResponseEntity<ExpenseResponseDTO> updateExpense(@PathVariable ("expense_id") Integer expenseId,
                                                            @RequestBody ExpenseRequestDTO expense){
        try{
            System.out.println("expense id: "+expenseId);
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

    @GetMapping("/get_expense_records_user")
    public ResponseEntity<List<ExpenseResponseDTO>> getExpenseRecordsByAuthenticatedUser(Authentication authentication){
        try{
            return ResponseEntity.ok(expenseService.getExpenseRecordsByUsername(authentication));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/expense_records_by_month/{month}")
    public ResponseEntity<List<ExpenseResponseDTO>> getExpenseRecordsByMonth(@PathVariable int month){
        try{
            logger.info("Show records as per months: {}", month);
            return ResponseEntity.ok(expenseService.getExpensesByMonthOfCurrentYear(month));
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/expense_records/{user_id}/{month}")
    public ResponseEntity<List<ExpenseResponseDTO>> getExpensesByUserAndMonth(@PathVariable("user_id") int userId, @PathVariable int month){
        try{
            logger.info("User of {}: Show records as per months: {}", userId, month);
            return ResponseEntity.ok(expenseService.getExpensesByUserAndMonth(userId, month));
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }
}
