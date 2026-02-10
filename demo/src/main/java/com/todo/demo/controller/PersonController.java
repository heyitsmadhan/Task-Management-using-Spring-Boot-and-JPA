package com.todo.demo.controller;

import com.todo.demo.dtos.MyTasksDTO;
import com.todo.demo.dtos.PersonDTO;
import com.todo.demo.dtos.PersonTaskDTO;
import com.todo.demo.repository.PersonRepository;
import com.todo.demo.service.PersonService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mytodo")
public class PersonController {

    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;


    @GetMapping("/myprofile")
    public ResponseEntity<String>myProfile()
    {
        return ResponseEntity.status(HttpStatus.OK).body("this is your profile!");
    }


    @PostMapping("/createaccount")
    public ResponseEntity<PersonDTO>createAccount(@Valid @RequestBody PersonDTO personDTO)
    {
        logger.info("create account method invoked---");
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.createPerson(personDTO));
    }


    @GetMapping("/addtask/{personId}/{taskName}")
    public ResponseEntity<PersonTaskDTO>addTask(@PathVariable long personId,@PathVariable String taskName)
    {
        logger.info("add task method invoked---");
        return ResponseEntity.status(HttpStatus.OK).body(personService.addTask(personId,taskName));
    }

    @GetMapping("/showmytasks/{personId}")
    public ResponseEntity<MyTasksDTO>showMyTasks(@PathVariable long personId)
    {
        logger.info("showMyTasks method invoked---");
        return ResponseEntity.status(HttpStatus.OK).body(personService.getAllMyTasks(personId));
    }

    @GetMapping("/showpendingtasks/{personId}")
    public ResponseEntity<MyTasksDTO>showPendingTasks(@PathVariable long personId)
    {
        logger.info("show pending tasks invoked---");
        return ResponseEntity.status(HttpStatus.OK).body(personService.showPendingTasks(personId));
    }


    @GetMapping("/updatetaskstatus/{personId}/{taskId}/{msg}")
    public ResponseEntity<String>updateTaskStatus(@PathVariable long personId,
                                                  @PathVariable long taskId,
                                                  @PathVariable String msg)
    {
        logger.info("update tasks method invoked---");
        return ResponseEntity.status(HttpStatus.OK).body(personService.updateMyTask(personId,taskId,msg));
    }

    @PutMapping("/updateprofile/{personId}/{name}")
    public ResponseEntity<PersonDTO>updateProfile(@PathVariable long personId,@PathVariable String name)
    {
        logger.info("update profile method invoked---");
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.updateProfile(personId,name));
    }

    @DeleteMapping("/deleteprofile/{personId}")
    public ResponseEntity<String >deleteMyProfile(@PathVariable long personId)
    {
        logger.info("delete profile method invoked---");
        return ResponseEntity.status(HttpStatus.OK).body(personService.deleteMyProfile(personId));
    }
}
