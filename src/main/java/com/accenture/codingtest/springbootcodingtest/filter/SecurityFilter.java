package com.accenture.codingtest.springbootcodingtest.filter;

import com.accenture.codingtest.springbootcodingtest.service.JWTService;
import com.accenture.codingtest.springbootcodingtest.service.UserInfoService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    JWTService jwtService;

    @Autowired
    UserInfoService userInfoService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        String token = null;
        String userName = null;
        if (Objects.nonNull(authorization) && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);
            userName = jwtService.getUserName(token);
        }

        if (Objects.nonNull(userName) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            UserDetails userDetails = userInfoService.loadUserByUsername(userName);
            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken unPwAuthToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                unPwAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(unPwAuthToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
