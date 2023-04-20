package com.accenture.codingtest.springbootcodingtest.controller;

import com.accenture.codingtest.springbootcodingtest.dto.ProjectDto;
import com.accenture.codingtest.springbootcodingtest.entity.Project;
import com.accenture.codingtest.springbootcodingtest.exception.ProjectNotFound;
import com.accenture.codingtest.springbootcodingtest.exception.UnAuthorized;
import com.accenture.codingtest.springbootcodingtest.service.ProjectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Slf4j
public class ProjectController {

    private ProjectService projectService;

    @PostMapping(value = "/projects", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PRODUCT_OWNER')")
    public Project createProject(@RequestBody ProjectDto projectDto) throws UnAuthorized {
        log.info("Creating project");
        return projectService.createProject(projectDto);
    }


    @GetMapping(value = "/projects", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PRODUCT_OWNER')")
    public List<Project> getAllProjects(@RequestParam(value = "q") String q, @RequestParam(required = false) Integer pageIndex,
                                        @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String sortBy, @RequestParam(required = false) String sortDirection) {
        log.info("Getting all projects");
        return projectService.getProjects(q, pageIndex, pageSize, sortBy, sortDirection);
    }

    @GetMapping(value = "/projects/{project_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PRODUCT_OWNER')")
    public Project getProject(@PathVariable("project_id") UUID projectId) throws ProjectNotFound {
        log.info("Getting a project {}", projectId.toString());
        return projectService.getProject(projectId);
    }

    @PutMapping(value = "/projects/{project_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PRODUCT_OWNER')")
    public Project updateProject(@RequestBody ProjectDto projectDto, @PathVariable("project_id") UUID projectId) throws ProjectNotFound {
        log.info("Updating a project {}", projectId.toString());
        return projectService.updateProject(projectDto, projectId);
    }


    @DeleteMapping(value = "/projects/{project_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PRODUCT_OWNER')")
    public String deleteProject(@PathVariable("project_id") UUID projectId) throws ProjectNotFound {
        log.info("Deleting a project {}", projectId.toString());
        return projectService.deleteProject(projectId);
    }

}
