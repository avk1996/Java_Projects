package com.app.expense.request_dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ExpenseRequestDTO {

    private Integer userId;

    private String description;

    private LocalDate spendingDate;

    private LocalTime spendingTime;

    private BigDecimal amount;
}
