package com.todo.demo.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @Column(nullable = false,unique = true)
    private String taskName;

    @ManyToOne
    @JoinColumn(name = "person_id",nullable = false)
    @JsonBackReference
    private Person person;

    @Column(nullable = false)
    private String taskStatus;
}
