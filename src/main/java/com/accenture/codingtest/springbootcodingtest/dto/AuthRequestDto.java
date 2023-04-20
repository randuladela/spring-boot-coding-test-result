package com.accenture.codingtest.springbootcodingtest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestDto {
    private String userName;
    private String password;

}
