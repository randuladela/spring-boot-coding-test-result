package com.accenture.codingtest.springbootcodingtest.controller;

import com.accenture.codingtest.springbootcodingtest.dto.TaskDto;
import com.accenture.codingtest.springbootcodingtest.entity.Task;
import com.accenture.codingtest.springbootcodingtest.exception.TaskNotFoundException;
import com.accenture.codingtest.springbootcodingtest.service.TaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Slf4j
public class TaskController {

    private TaskService taskService;

    @PostMapping(value = "/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public Task createTask(@RequestBody TaskDto taskDto) {
        log.info("Creating task");
        return taskService.createTask(taskDto);
    }

    @GetMapping(value = "/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Task> getAllTasks() {
        log.info("Getting all task");
        return taskService.getTasks();
    }

    @GetMapping(value = "/tasks/{task_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Task getTask(@PathVariable("task_id") UUID taskId) throws TaskNotFoundException {
        log.info("Getting a task {} ", taskId.toString());
        return taskService.getTask(taskId);
    }

    @PutMapping(value = "/tasks/{task_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Task updateTask(@RequestBody TaskDto taskDto, @PathVariable("task_id") UUID taskId) throws TaskNotFoundException {
        log.info("Updating a task {} ", taskId.toString());
        return taskService.updateTask(taskDto, taskId);
    }

    @PatchMapping(value = "/tasks/{task_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Task updateTaskPartially(@RequestBody Map<String, Object> updatedValues, @PathVariable("task_id") UUID taskId) throws TaskNotFoundException {
        log.info("Updating a task {} ", taskId.toString());
        return taskService.updateTaskPartially(updatedValues, taskId);
    }

    @DeleteMapping(value = "/tasks/{task_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteTask(@PathVariable("task_id") UUID taskId) throws TaskNotFoundException {
        log.info("Deleting a task {} ", taskId.toString());
        return taskService.deleteTask(taskId);
    }

}
