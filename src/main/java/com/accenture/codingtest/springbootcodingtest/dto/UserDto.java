package com.accenture.codingtest.springbootcodingtest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String userName;
    private String password;
    private UUID taskId;
    private UUID projectId;
}
