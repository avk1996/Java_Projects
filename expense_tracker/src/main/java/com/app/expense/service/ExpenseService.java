package com.app.expense.service;

import com.app.expense.dao.ExpenseDao;
import com.app.expense.dao.UserDao;
import com.app.expense.entity.Expense;
import com.app.expense.entity.User;
import com.app.expense.request_dto.ExpenseRequestDTO;
import com.app.expense.response_dto.ExpenseResponseDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ExpenseService {

    private static final Logger logger = LoggerFactory.getLogger(ExpenseService.class);

    @Autowired
    ExpenseDao expenseDao;

    @Autowired
    UserDao userDao;

    @Autowired
    private ModelMapper modelMapper;

    public ExpenseResponseDTO addExpense(Authentication authentication, ExpenseRequestDTO expenseRequestDTO){
        try{
            String authenticatedUsername = authentication.getName();
            System.out.println(authenticatedUsername);
            User authenticateUser = userDao.findByName(authenticatedUsername).orElseThrow(() -> new RuntimeException("User not found"));

            Expense expense = new Expense();

            expense.setAmount(expenseRequestDTO.getAmount());
            expense.setDescription(expenseRequestDTO.getDescription());
            expense.setSpendingTime(expenseRequestDTO.getSpendingTime());
            expense.setSpendingDate(expenseRequestDTO.getSpendingDate());

            expense.setUser(authenticateUser);

            return modelMapper.map(expenseDao.save(expense), ExpenseResponseDTO.class);

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

    public List<ExpenseResponseDTO> getExpensesByMonthOfCurrentYear(int month) {
        try{
            if(month <= 0 || month > 12)
                return new ArrayList<>();

            int currentYear = LocalDate.now().getYear();
            LocalDate startDate = LocalDate.of(currentYear, month, 1);
            LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

            logger.info("Month: "+month+", start date: "+startDate+", end date: "+endDate);

            List<Expense> expenses = expenseDao.findBySpendingDateBetween(startDate, endDate);

            return expenses.stream().map(expense -> modelMapper.map(expense, ExpenseResponseDTO.class)).toList();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<ExpenseResponseDTO> getExpensesByUserAndMonth(int userId, int month) {
        try{
            if(!userDao.existsById(userId))
                throw new NoSuchElementException("User does not exist, therefore record doesnot contain");
            if(month <= 0 || month > 12)
                return new ArrayList<>();

            int currentYear = LocalDate.now().getYear();
            LocalDate startDate = LocalDate.of(currentYear, month, 1);
            LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

            List<Expense> expenses = expenseDao.findByUserIdAndSpendingDateBetween(userId, startDate, endDate);

            if(expenses.isEmpty())
                throw new RuntimeException("Record is empty of user");

            return expenses.stream().map(expense -> modelMapper.map(expense, ExpenseResponseDTO.class)).toList();

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<ExpenseResponseDTO> getExpenseRecordsByUsername(Authentication authentication) {
        try{
            String authenticatedUsername = authentication.getName();
            System.out.println(authenticatedUsername);
            User authenticateUser = userDao.findByName(authenticatedUsername).orElseThrow(() -> new RuntimeException("User not found"));
            List<Expense> expensesPerUsername = expenseDao.findByUser(authenticateUser);
            return expensesPerUsername.stream().map(expense -> modelMapper.map(expense, ExpenseResponseDTO.class)).toList();
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
