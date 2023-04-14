package com.accenture.codingtest.springbootcodingtest.controller;

import com.accenture.codingtest.springbootcodingtest.dto.UserDto;
import com.accenture.codingtest.springbootcodingtest.entity.User;
import com.accenture.codingtest.springbootcodingtest.exception.ProjectNotFound;
import com.accenture.codingtest.springbootcodingtest.exception.TaskNotFoundException;
import com.accenture.codingtest.springbootcodingtest.exception.UnAuthorized;
import com.accenture.codingtest.springbootcodingtest.exception.UserNotFoundException;
import com.accenture.codingtest.springbootcodingtest.service.UserService;
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
public class UserController {

    private UserService userService;

    @PostMapping(value = "/users/roles/{role_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String createUser(@RequestBody UserDto userDto, @PathVariable("role_id") String roleId) throws TaskNotFoundException, ProjectNotFound, UnAuthorized {
        log.info("Creating user");
        return userService.createUser(userDto, roleId);
    }

    @GetMapping(value = "/users/roles/{role_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers(@PathVariable("role_id") String roleId) throws UnAuthorized {
        log.info("Getting all user");
        return userService.getUsers(roleId);
    }

    @GetMapping(value = "/users/{user_id}/roles/{role_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable("user_id") UUID userId, @PathVariable("role_id") String roleId) throws UserNotFoundException, UnAuthorized {
        log.info("Getting an user");
        return userService.getUser(userId, roleId);
    }

    @PutMapping(value = "/users/{user_id}/roles/{role_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User updateUser(@RequestBody UserDto userDto, @PathVariable("user_id") UUID userId, @PathVariable("role_id") String roleId) throws UserNotFoundException, UnAuthorized {
        log.info("Updating an user {} ", userId);
        return userService.updateUser(userDto, userId, roleId);
    }

    @PatchMapping(value = "/users/{user_id}/roles/{role_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User updateUserPartially(@RequestBody Map<String, Object> updatedValues, @PathVariable("user_id") UUID userId, @PathVariable("role_id") String roleId) throws UserNotFoundException, UnAuthorized {
        log.info("Updating an user {} ", userId);
        return userService.updateUserPartially(updatedValues, userId, roleId);
    }

    @DeleteMapping(value = "/users/{user_id}/roles/{role_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteUser(@PathVariable("user_id") UUID userId, @PathVariable("role_id") String roleId) throws UserNotFoundException, UnAuthorized {
        log.info("Deleting an user {} ", userId);
        return userService.deleteUser(userId, roleId);
    }

}
