package com.app.expense.response_dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ExpenseResponseDTO {
    private Integer id;

    private String description;

    private LocalDate spendingDate;

    private LocalTime spendingTime;

    private BigDecimal amount;

    private String userName;
}
