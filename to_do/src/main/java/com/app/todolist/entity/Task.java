package com.app.todolist.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Task {
    @Id
    private int id;

    private String goal;
    private String description;
    @Column(name="start_date")
    private LocalDateTime startDateTime;
    @Column(name="end_date")
    private LocalDateTime endDateTime;
}
