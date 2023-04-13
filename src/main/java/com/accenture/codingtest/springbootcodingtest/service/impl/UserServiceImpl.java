package com.accenture.codingtest.springbootcodingtest.service.impl;

import com.accenture.codingtest.springbootcodingtest.dto.UserDto;
import com.accenture.codingtest.springbootcodingtest.entity.Project;
import com.accenture.codingtest.springbootcodingtest.entity.Task;
import com.accenture.codingtest.springbootcodingtest.entity.User;
import com.accenture.codingtest.springbootcodingtest.enums.Role;
import com.accenture.codingtest.springbootcodingtest.exception.ProjectNotFound;
import com.accenture.codingtest.springbootcodingtest.exception.TaskNotFoundException;
import com.accenture.codingtest.springbootcodingtest.exception.UnAuthorized;
import com.accenture.codingtest.springbootcodingtest.exception.UserNotFoundException;
import com.accenture.codingtest.springbootcodingtest.repository.ProjectRepository;
import com.accenture.codingtest.springbootcodingtest.repository.TaskRepository;
import com.accenture.codingtest.springbootcodingtest.repository.UserRepository;
import com.accenture.codingtest.springbootcodingtest.service.UserService;
import com.accenture.codingtest.springbootcodingtest.util.ErrorDsc;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private ProjectRepository projectRepository;

    private TaskRepository taskRepository;

    private UserRepository userRepository;

    /**
     * Creating user
     *
     * @param userDto
     * @return
     * @throws TaskNotFoundException
     * @throws ProjectNotFound
     */
    @Override
    public String createUser(UserDto userDto, String roleId) throws TaskNotFoundException, ProjectNotFound, UnAuthorized {

        if (roleId.equalsIgnoreCase(Role.ADMIN.toString())) {
            User user = new User();
            user.setUserName(userDto.getUserName());
            user.setPassword(userDto.getPassword());
            Project project = projectRepository.findById(userDto.getProjectId()).orElseThrow(() -> new ProjectNotFound(ErrorDsc.ERR_DSC_PROJECT_NOT_FOUND));

            List<User> users = new ArrayList<>();
            users.add(user);
            Task task = taskRepository.findById(userDto.getTaskId()).orElseThrow(() -> new TaskNotFoundException(ErrorDsc.ERR_DSC_TASK_NOT_FOUND));
            user.setTask(task);
            project.setUsers(users);

            userRepository.save(user);
            return "User created successfully";

        } else {
            throw new UnAuthorized(ErrorDsc.ERR_DSC_UNAUTHORIZED);
        }

    }

    /**
     * Getting all users
     *
     * @return
     */
    @Override
    public List<User> getUsers(String roleId) throws UnAuthorized {
        if (roleId.equalsIgnoreCase(Role.ADMIN.toString())) {
            return userRepository.findAll();
        } else {
            throw new UnAuthorized(ErrorDsc.ERR_DSC_UNAUTHORIZED);
        }

    }

    /**
     * Getting a particular user
     *
     * @param userId
     * @return
     * @throws UserNotFoundException
     */
    @Override
    public User getUser(UUID userId, String roleId) throws UserNotFoundException, UnAuthorized {
        if (roleId.equalsIgnoreCase(Role.ADMIN.toString())) {
            return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(ErrorDsc.ERR_DSC_USER_NOT_FOUND));
        } else {
            throw new UnAuthorized(ErrorDsc.ERR_DSC_UNAUTHORIZED);
        }

    }

    /**
     * Updating whole user
     *
     * @param userDto
     * @param userId
     * @return
     * @throws UserNotFoundException
     */
    @Override
    public User updateUser(UserDto userDto, UUID userId, String roleId) throws UserNotFoundException, UnAuthorized {
        if (roleId.equalsIgnoreCase(Role.ADMIN.toString())) {
            User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(ErrorDsc.ERR_DSC_USER_NOT_FOUND));
            user.setUserName(userDto.getUserName());
            user.setPassword(userDto.getPassword());
            return userRepository.save(user);
        } else {
            throw new UnAuthorized(ErrorDsc.ERR_DSC_UNAUTHORIZED);
        }
    }

    /**
     * Updating user partially
     *
     * @param fields
     * @param userId
     * @return
     * @throws UserNotFoundException
     */
    @Override
    public User updateUserPartially(Map<String, Object> fields, UUID userId, String roleId) throws UserNotFoundException, UnAuthorized {
        if (roleId.equalsIgnoreCase(Role.ADMIN.toString())) {
            User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(ErrorDsc.ERR_DSC_USER_NOT_FOUND));

            fields.forEach((requestField, value) -> {
                Field field = ReflectionUtils.findField(User.class, requestField);
                field.setAccessible(true);
                ReflectionUtils.setField(field, user, value);
            });

            return userRepository.save(user);
        } else {
            throw new UnAuthorized(ErrorDsc.ERR_DSC_UNAUTHORIZED);
        }
    }

    /**
     * Deleting user
     *
     * @param userId
     * @return
     * @throws UserNotFoundException
     */
    @Override
    public String deleteUser(UUID userId, String roleId) throws UserNotFoundException, UnAuthorized {
        if (roleId.equalsIgnoreCase(Role.ADMIN.toString())) {
            User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(ErrorDsc.ERR_DSC_USER_NOT_FOUND));
            userRepository.deleteById(user.getId());
            return "User successfully deleted";
        } else {
            throw new UnAuthorized(ErrorDsc.ERR_DSC_UNAUTHORIZED);
        }
    }
}
