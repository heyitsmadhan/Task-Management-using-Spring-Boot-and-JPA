package com.todo.demo.dtos;

import com.todo.demo.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyTasksDTO {

    private long personId;

    private String personName;

    private String personEmail;

    private List<Task> allMyTasks;

}
