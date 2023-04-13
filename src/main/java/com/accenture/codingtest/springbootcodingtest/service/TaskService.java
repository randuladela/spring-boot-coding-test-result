package com.accenture.codingtest.springbootcodingtest.service;

import com.accenture.codingtest.springbootcodingtest.dto.TaskDto;
import com.accenture.codingtest.springbootcodingtest.entity.Task;
import com.accenture.codingtest.springbootcodingtest.exception.TaskNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface TaskService {
    Task createTask(TaskDto taskDto);

    List<Task> getTasks();

    Task getTask(UUID taskId) throws TaskNotFoundException;

    Task updateTask(TaskDto taskDto, UUID taskId) throws TaskNotFoundException;

    Task updateTaskPartially(Map<String, Object> updatedFields, UUID taskId) throws TaskNotFoundException;

    String deleteTask(UUID taskId) throws TaskNotFoundException;
}
