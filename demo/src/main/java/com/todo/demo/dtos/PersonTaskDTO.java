package com.todo.demo.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonTaskDTO {

    private long personId;
    private long taskId;
    private String personName;
    private String taskName;
}
