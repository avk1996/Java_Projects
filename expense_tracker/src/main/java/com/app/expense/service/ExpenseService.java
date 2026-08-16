package com.app.expense.service;

import com.app.expense.dao.ExpenseDao;
import com.app.expense.dao.UserDao;
import com.app.expense.entity.Expense;
import com.app.expense.request_dto.ExpenseRequestDTO;
import com.app.expense.response_dto.ExpenseResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ExpenseService {

    @Autowired
    ExpenseDao expenseDao;

    @Autowired
    UserDao userDao;

    @Autowired
    private ModelMapper modelMapper;

    public ExpenseResponseDTO addExpense(ExpenseRequestDTO expenseRequestDTO){
        try{
            boolean isUserExists = userDao.existsById(expenseRequestDTO.getUserId());
            System.out.println("is user exists: "+isUserExists);
            if(!isUserExists)
                throw new RuntimeException("User does not exists");
            Expense expense = modelMapper.map(expenseRequestDTO, Expense.class);
            Expense expenseAdded = expenseDao.save(expense);
            return modelMapper.map(expenseAdded, ExpenseResponseDTO.class);
        }catch (RuntimeException exception){
            throw new NoSuchElementException(exception.getLocalizedMessage());
        }
        catch (Exception e){
            return new ExpenseResponseDTO();
        }
    }

    public ExpenseResponseDTO updateExpense(Integer userId, ExpenseRequestDTO expenseRequestDTO) {
        try {
            Expense expenseForUpdate = modelMapper.map(expenseRequestDTO, Expense.class);

            Expense existingExpense = expenseDao.findById(userId).orElseThrow(()-> new RuntimeException("Expense Not Found"));
            existingExpense.setDescription(expenseForUpdate.getDescription());
            existingExpense.setAmount(expenseForUpdate.getAmount());
            existingExpense.setSpendingDate(expenseForUpdate.getSpendingDate());
            existingExpense.setSpendingTime(expenseForUpdate.getSpendingTime());

            return modelMapper.map(existingExpense, ExpenseResponseDTO.class);
        } catch (Exception e) {
            return new ExpenseResponseDTO();
        }
    }

    public ExpenseResponseDTO deleteExpense(Integer expenseId) {
        try{
            Expense existingExpense = expenseDao.findById(expenseId).orElseThrow(()->new RuntimeException("Expense not found"));
            expenseDao.deleteById(expenseId);
            return modelMapper.map(existingExpense, ExpenseResponseDTO.class);
        } catch (Exception e) {
            return new ExpenseResponseDTO();
        }
    }

    public List<ExpenseResponseDTO> getExpenseRecords() {
        try{
            List<Expense> expenseList = expenseDao.findAll();
            return expenseList.stream().map(expense -> modelMapper.map(expense, ExpenseResponseDTO.class)).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<ExpenseResponseDTO> getExpenseRecordsByUserID(int userId) {
        try{
            List<Expense> expenseListById = expenseDao.findByUserId(userId);
            return  expenseListById.stream().map(expense -> modelMapper.map(expense, ExpenseResponseDTO.class)).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<ExpenseResponseDTO> getExpenseRecordsByMonth(int month) {
        try{
            if(month >=1 && month <=12)
                return new ArrayList<>();

            int currentYear = LocalDate.now().getYear();
            LocalDate startDate = LocalDate.of(currentYear, month, 1);
            LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

            List<Expense> expenses = expenseDao.findBySpendingDateBetween(startDate, endDate);

            return expenses.stream().map(expense -> modelMapper.map(expense, ExpenseResponseDTO.class)).toList();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
