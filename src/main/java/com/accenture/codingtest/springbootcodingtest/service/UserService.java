package com.accenture.codingtest.springbootcodingtest.service;


import com.accenture.codingtest.springbootcodingtest.dto.UserDto;
import com.accenture.codingtest.springbootcodingtest.entity.User;
import com.accenture.codingtest.springbootcodingtest.exception.ProjectNotFound;
import com.accenture.codingtest.springbootcodingtest.exception.TaskNotFoundException;
import com.accenture.codingtest.springbootcodingtest.exception.UnAuthorized;
import com.accenture.codingtest.springbootcodingtest.exception.UserNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UserService {

    String createUser(UserDto userDto) throws TaskNotFoundException, ProjectNotFound, UnAuthorized;

    List<User> getUsers() throws UnAuthorized;

    User getUser(UUID userId) throws UserNotFoundException, UnAuthorized;

    User updateUser(UserDto user, UUID userId) throws UserNotFoundException, UnAuthorized;

    User updateUserPartially(Map<String, Object> map, UUID userId) throws UserNotFoundException, UnAuthorized;

    String deleteUser(UUID userId) throws UserNotFoundException, UnAuthorized;
}
