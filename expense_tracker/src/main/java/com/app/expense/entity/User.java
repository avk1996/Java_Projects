package com.app.expense.entity;

import lombok.Data;
import org.hibernate.annotations.Entity;

import javax.persistence.Id;

@Data
@Entity
public class User {
    @Id
    private Integer id;

    private String name;
}
