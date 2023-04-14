package com.accenture.codingtest.springbootcodingtest.service;

import com.accenture.codingtest.springbootcodingtest.dto.ProjectDto;
import com.accenture.codingtest.springbootcodingtest.entity.Project;
import com.accenture.codingtest.springbootcodingtest.exception.ProjectNotFound;
import com.accenture.codingtest.springbootcodingtest.exception.UnAuthorized;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    Project createProject(ProjectDto projectDto, String roleId) throws UnAuthorized;

    List<Project> getProjects(String q, Integer pageIndex, Integer pageSize, String sortBy, String sortDirection);

    Project getProject(UUID projectId) throws ProjectNotFound;

    Project updateProject(ProjectDto projectDto, UUID projectId) throws ProjectNotFound;

    String deleteProject(UUID projectId) throws ProjectNotFound;
}
