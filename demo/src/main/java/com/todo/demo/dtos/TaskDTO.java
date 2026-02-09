package com.todo.demo.dtos;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.todo.demo.entity.Person;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private Long taskId;

    @Column(nullable = false,unique = true)
    private String taskName;

    private Person person;
}
