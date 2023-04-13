package com.accenture.codingtest.springbootcodingtest.service.impl;

import com.accenture.codingtest.springbootcodingtest.dto.ProjectDto;
import com.accenture.codingtest.springbootcodingtest.entity.Project;
import com.accenture.codingtest.springbootcodingtest.enums.Role;
import com.accenture.codingtest.springbootcodingtest.exception.ProjectNotFound;
import com.accenture.codingtest.springbootcodingtest.exception.UnAuthorized;
import com.accenture.codingtest.springbootcodingtest.repository.ProjectRepository;
import com.accenture.codingtest.springbootcodingtest.service.ProjectService;
import com.accenture.codingtest.springbootcodingtest.util.ErrorDsc;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;

    private ModelMapper mapper;

    /**
     * Creating project
     *
     * @param projectDto
     * @return
     */
    @Override
    public Project createProject(ProjectDto projectDto, String roleId) throws UnAuthorized {
        if (roleId.equalsIgnoreCase(Role.PRODUCT_OWNER.toString())) {
            return projectRepository.save(mapper.map(projectDto, Project.class));
        } else {
            throw new UnAuthorized(ErrorDsc.ERR_DSC_UNAUTHORIZED);
        }

    }

    /**
     * Getting all projects
     *
     * @return
     */
    @Override
    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    /**
     * Getting particular project
     *
     * @param projectId
     * @return
     * @throws ProjectNotFound
     */
    @Override
    public Project getProject(UUID projectId) throws ProjectNotFound {
        return projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFound(ErrorDsc.ERR_DSC_PROJECT_NOT_FOUND));
    }

    /**
     * Updating project
     *
     * @param projectDto
     * @param projectId
     * @return
     * @throws ProjectNotFound
     */
    @Override
    public Project updateProject(ProjectDto projectDto, UUID projectId) throws ProjectNotFound {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFound(ErrorDsc.ERR_DSC_PROJECT_NOT_FOUND));
        project.setName(projectDto.getName());

        return projectRepository.save(project);
    }

    /**
     * Deleting project
     *
     * @param projectId
     * @return
     * @throws ProjectNotFound
     */
    @Override
    public String deleteProject(UUID projectId) throws ProjectNotFound {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFound(ErrorDsc.ERR_DSC_PROJECT_NOT_FOUND));
        projectRepository.deleteById(project.getId());

        return "Project successfully deleted";
    }
}
