package com.todo.demo.service;

import com.todo.demo.dtos.MyTasksDTO;
import com.todo.demo.dtos.PersonDTO;
import com.todo.demo.dtos.PersonTaskDTO;
import com.todo.demo.entity.Person;
import com.todo.demo.entity.Task;
import com.todo.demo.exception.PersonExistsException;
import com.todo.demo.exception.TaskAlreadyExistsException;
import com.todo.demo.exception.TaskIdNotExistException;
import com.todo.demo.repository.PersonRepository;
import com.todo.demo.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PersonService {

    private static final Logger logger = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TaskRepository taskRepository;

    public PersonDTO createPerson(PersonDTO person)
    {
        if(personRepository.existsByPersonName(person.getPersonName()))
        {
            logger.error("person with the provided name already exists: ",person.getPersonName());
            throw new PersonExistsException("person with the given name: "+person.getPersonName()+" already exists!");
        }

        Person person1 = modelMapper.map(person,Person.class);
        Person savedPerson = personRepository.save(person1);
        logger.info("account created successfully");
        return modelMapper.map(savedPerson,PersonDTO.class);
    }

    public PersonTaskDTO addTask(long personId, String task)
    {
        Person person1 = personRepository.findById(personId)
                .orElseThrow(()-> new PersonExistsException("person with given id: "+personId+" not exists!"));

        List<Task>allMyTasks = taskRepository.findAllTasksByPersonId(person1.getPersonId());
        for(Task eachTask:allMyTasks)
        {
            if(eachTask.getTaskName().toLowerCase().equals(task.toLowerCase()))
            {
                logger.info("cannot add a task 2 times: duplicate!",eachTask.getTaskId());
                throw new TaskAlreadyExistsException("task already existed so first complete the existing task! "+eachTask.getTaskId());
            }
        }
        Task task1 = new Task();
        task1.setTaskName(task);
        task1.setPerson(person1);
        logger.info("new task added!");
        task1.setTaskStatus("yet to complete");
        taskRepository.save(task1);

        PersonTaskDTO personTaskDTO = new PersonTaskDTO();
        personTaskDTO.setPersonId(personId);
        personTaskDTO.setPersonName(person1.getPersonName());
        personTaskDTO.setTaskName(task);
        personTaskDTO.setTaskId(task1.getTaskId());

        return personTaskDTO;
    }

    public MyTasksDTO getAllMyTasks(long personId)
    {
        Person person1 = personRepository.findById(personId)
                .orElseThrow(()-> new PersonExistsException("person with given id: "+personId+" not exists!"));

        List<Task> allTasks = taskRepository.findAllTasksByPersonId(personId);
        MyTasksDTO myTasksDTO = new MyTasksDTO();
        myTasksDTO.setPersonId(personId);
        myTasksDTO.setPersonName(person1.getPersonName());
        myTasksDTO.setPersonEmail(person1.getPersonEmail());
        myTasksDTO.setAllMyTasks(allTasks);
        logger.info("retrieved all the tasks!!");
        return myTasksDTO;
    }

    public String updateMyTask(long personId,long taskId,String msg)
    {
        Person person1 = personRepository.findById(personId)
                .orElseThrow(()-> new PersonExistsException("person with given id: "+personId+" not exists!"));

        Task task1 = taskRepository.findById(taskId)
                .orElseThrow(
                        ()-> new TaskIdNotExistException("there is no task available with the given task id: "+taskId));

        logger.info("successfully updated the task!!");
        task1.setTaskStatus(msg);
        taskRepository.save(task1);

        for (Task eachTask:taskRepository.findAllTasksByPersonId(personId))
        {
            if(eachTask.getTaskStatus().toLowerCase().equals("completed"))
            {
                taskRepository.delete(eachTask);
            }
        }

        return "Person Name: "+person1.getPersonName()+"\n"+
                "Task name :  "+task1.getTaskName()+"\n"+
                "status changed to:  "+msg;
    }

    public PersonDTO updateProfile(long personId,String name)
    {
        Person person1 = personRepository.findById(personId)
                .orElseThrow(
                        ()-> new PersonExistsException("person with the id: "+personId+" not exists!")
                );

        for(Person eachPerson:personRepository.findAll())
        {
            if(eachPerson.getPersonName().toLowerCase().equals(name.toLowerCase()))
            {
                throw new PersonExistsException("do not enter existing person name again!");
            }
        }
        logger.info("profile updated successfully!!");
        person1.setPersonName(name);
        personRepository.save(person1);
        return modelMapper.map(person1,PersonDTO.class);
    }

    public String deleteMyProfile(long personId)
    {
        Person person1 = personRepository.findById(personId)
                .orElseThrow(
                        ()-> new PersonExistsException("person with the id: "+personId+" not exists!")
                );
        logger.info("profile deleted successfully!!");
        taskRepository.deletePersonWithId(personId);
        personRepository.delete(person1);
        return "person with Id: "+person1.getPersonId()+"\n"
                +"person name: "+person1.getPersonName();

    }

    public MyTasksDTO showPendingTasks(long personId)
    {
        Person person1 = personRepository.findById(personId)
                .orElseThrow(
                        ()-> new PersonExistsException("person with the id: "+personId+" not exists!")
                );
        List<Task>pendingTasks=taskRepository.showPendingTask(personId);
        MyTasksDTO myTasksDTO = new MyTasksDTO();
        myTasksDTO.setAllMyTasks(pendingTasks);
        myTasksDTO.setPersonId(personId);
        myTasksDTO.setPersonName(person1.getPersonName());
        myTasksDTO.setPersonEmail(person1.getPersonEmail());

        return myTasksDTO;
    }
}
