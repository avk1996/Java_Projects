package com.app.todolist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String goal;

    private String description;

    @Column(name="start_date")
    private LocalDateTime startDateTime;

    @Column(name="end_date")
    private LocalDateTime endDateTime;

    @Column(name="complete")
    private boolean isComplete;

    public Task(String goal, String description, LocalDateTime startTime, LocalDateTime endTime) {
        this.goal = goal;
        this.description = description;
        this.startDateTime = startTime;
        this.endDateTime = endTime;
    }
}
