package com.app.expense.dao;

import com.app.expense.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseDao extends JpaRepository<Expense, Integer> {
    List<Expense> findByUserId(int userId);

    List<Expense> findBySpendingDateBetween(LocalDate startDate, LocalDate endDate);

    List<Expense> findByUserIdAndSpendingDateBetween(int userId, LocalDate startDate, LocalDate endDate);
}
