package com.todo.demo.repository;

import com.todo.demo.entity.Person;
import com.todo.demo.entity.Task;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    @Query("SELECT t FROM Task t WHERE t.person.personId = :personId")
    List<Task> findAllTasksByPersonId(@Param("personId") long personId);


    @Transactional
    @Modifying
    @Query("DELETE FROM Task t WHERE t.person.personId= :personId")
    void deletePersonWithId(@Param("personId") long personId);

    @Query("SELECT t FROM Task t WHERE t.person.personId =:personId AND t.taskStatus<>'Completed'")
    List<Task>showPendingTask(@Param("personId") long personId);
}
