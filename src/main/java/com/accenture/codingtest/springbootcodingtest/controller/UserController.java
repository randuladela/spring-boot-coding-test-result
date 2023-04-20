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
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String createUser(@RequestBody UserDto userDto) throws TaskNotFoundException, ProjectNotFound, UnAuthorized {
        log.info("Creating user");
        return userService.createUser(userDto);
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAllUsers() throws UnAuthorized {
        log.info("Getting all user");
        return userService.getUsers();
    }

    @GetMapping(value = "/users/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public User getUser(@PathVariable("user_id") UUID userId) throws UserNotFoundException, UnAuthorized {
        log.info("Getting an user");
        return userService.getUser(userId);
    }

    @PutMapping(value = "/users/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public User updateUser(@RequestBody UserDto userDto, @PathVariable("user_id") UUID userId) throws UserNotFoundException, UnAuthorized {
        log.info("Updating an user {} ", userId);
        return userService.updateUser(userDto, userId);
    }

    @PatchMapping(value = "/users/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public User updateUserPartially(@RequestBody Map<String, Object> updatedValues, @PathVariable("user_id") UUID userId) throws UserNotFoundException, UnAuthorized {
        log.info("Updating an user {} ", userId);
        return userService.updateUserPartially(updatedValues, userId);
    }

    @DeleteMapping(value = "/users/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteUser(@PathVariable("user_id") UUID userId) throws UserNotFoundException, UnAuthorized {
        log.info("Deleting an user {} ", userId);
        return userService.deleteUser(userId);
    }


}
