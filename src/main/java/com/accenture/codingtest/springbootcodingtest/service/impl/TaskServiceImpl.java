package com.accenture.codingtest.springbootcodingtest.service.impl;

import com.accenture.codingtest.springbootcodingtest.ErrorDsc;
import com.accenture.codingtest.springbootcodingtest.dto.TaskDto;
import com.accenture.codingtest.springbootcodingtest.entity.Task;
import com.accenture.codingtest.springbootcodingtest.exception.TaskNotFoundException;
import com.accenture.codingtest.springbootcodingtest.repository.TaskRepository;
import com.accenture.codingtest.springbootcodingtest.service.TaskService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    private ModelMapper mapper;

    /**
     * Creating task
     *
     * @param taskDto
     * @return
     */
    @Override
    public Task createTask(TaskDto taskDto) {
        return taskRepository.save(mapper.map(taskDto, Task.class));
    }

    /**
     * Getting all tasks
     *
     * @return
     */
    @Override
    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    /**
     * Get a particular task
     *
     * @param taskId
     * @return
     * @throws TaskNotFoundException
     */
    @Override
    public Task getTask(UUID taskId) throws TaskNotFoundException {
        return taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(ErrorDsc.ERR_DSC_TASK_NOT_FOUND));
    }

    /**
     * Updating  whole task
     *
     * @param taskDto
     * @param taskId
     * @return
     * @throws TaskNotFoundException
     */
    @Override
    public Task updateTask(TaskDto taskDto, UUID taskId) throws TaskNotFoundException {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(ErrorDsc.ERR_DSC_TASK_NOT_FOUND));
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskDto.getStatus());
        task.setTitle(taskDto.getTitle());
        return taskRepository.save(task);
    }

    /**
     * Updating task partially
     *
     * @param updatedFields
     * @param taskId
     * @return
     * @throws TaskNotFoundException
     */
    @Override
    public Task updateTaskPartially(Map<String, Object> updatedFields, UUID taskId) throws TaskNotFoundException {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(ErrorDsc.ERR_DSC_TASK_NOT_FOUND));

        updatedFields.forEach((requestField, value) -> {
            Field field = ReflectionUtils.findField(Task.class, requestField);
            field.setAccessible(true);
            ReflectionUtils.setField(field, task, value);
        });

        return taskRepository.save(task);
    }

    /**
     * Deleting task
     *
     * @param taskId
     * @return
     * @throws TaskNotFoundException
     */
    @Override
    public String deleteTask(UUID taskId) throws TaskNotFoundException {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(ErrorDsc.ERR_DSC_TASK_NOT_FOUND));
        taskRepository.deleteById(task.getId());
        return "Task successfully deleted";
    }
}
