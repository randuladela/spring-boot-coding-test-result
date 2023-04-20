package com.accenture.codingtest.springbootcodingtest.controller;

import com.accenture.codingtest.springbootcodingtest.dto.AuthRequestDto;
import com.accenture.codingtest.springbootcodingtest.service.JWTService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Slf4j
public class TokenController {

    private JWTService jwtService;

    private AuthenticationManager authenticationManager;

    @PostMapping(value = "/token", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getToken(@RequestBody AuthRequestDto authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));

        if (authenticate.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUserName());
        } else {
            throw new UsernameNotFoundException("User " + authRequest.getUserName() + " not found");
        }

    }
}
