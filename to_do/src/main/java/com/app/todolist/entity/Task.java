package com.app.todolist.entity;

import com.app.todolist.utility.TaskStatus;
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

    @Column(name="created")
    private LocalDateTime created;

    @Column(name="updated")
    private LocalDateTime updated;

    @Column(name="complete")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    public Task(String goal, String description) {
        this.goal = goal;
        this.description = description;
        this.status = TaskStatus.IN_PROGRESS;
    }
}
