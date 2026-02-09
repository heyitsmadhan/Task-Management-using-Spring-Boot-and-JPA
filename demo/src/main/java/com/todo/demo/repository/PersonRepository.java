package com.todo.demo.repository;

import com.todo.demo.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {

    boolean existsByPersonName(String name);
}

