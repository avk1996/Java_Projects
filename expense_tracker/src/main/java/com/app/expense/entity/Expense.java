package com.app.expense.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String description;

    @Column(name = "spending_date")
    private LocalDate spendingDate;

    @Column(name = "spending_time")
    @JsonFormat(pattern="HH:mm")
    private LocalTime spendingTime;

    private BigDecimal amount;

    @ManyToOne
    private User user;
}
