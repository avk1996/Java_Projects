package com.app.expense.entity;

import lombok.Data;
import org.hibernate.annotations.Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
public class Expense {
    @Id
    private Integer id;

    private String description;

    @Column(name = "spending_date")
    private LocalDateTime spendingDate;

    @Column(name = "spending_time")
    private LocalTime spendingTime;

    private BigDecimal amount;

    @OneToOne
    private User user;
}
