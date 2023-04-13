package com.accenture.codingtest.springbootcodingtest.controller;

import com.accenture.codingtest.springbootcodingtest.dto.ProjectDto;
import com.accenture.codingtest.springbootcodingtest.dto.TaskDto;
import com.accenture.codingtest.springbootcodingtest.dto.UserDto;
import com.accenture.codingtest.springbootcodingtest.entity.Project;
import com.accenture.codingtest.springbootcodingtest.entity.Task;
import com.accenture.codingtest.springbootcodingtest.enums.TaskStatus;
import com.accenture.codingtest.springbootcodingtest.repository.ProjectRepository;
import com.accenture.codingtest.springbootcodingtest.repository.TaskRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTest {
    private static TestRestTemplate restTemplate;
    private static ObjectMapper om;
    @LocalServerPort
    private int port;
    private String baseUrl = "http://localhost";
    @MockBean
    private ProjectRepository projectRepository;

    @MockBean
    private TaskRepository taskRepository;

    @BeforeAll
    public static void init() {
        restTemplate = new TestRestTemplate();
        om = new ObjectMapper();
        om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/v1");
    }

    @Test
    public void testProjectCreate() throws JsonProcessingException {
        ProjectDto projectRequestDto = new ProjectDto();
        projectRequestDto.setName("rest-api");

        HttpEntity<ProjectDto> request = new HttpEntity<>(projectRequestDto);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(baseUrl + "/projects/roles/product_owner", request, String.class);
        ProjectDto response = om.readValue(stringResponseEntity.getBody(), ProjectDto.class);

        assertEquals(projectRequestDto.getName(), response.getName());

    }

    @Test
    public void testTaskCreate() throws JsonProcessingException {
        TaskDto taskRequestDto = new TaskDto();
        taskRequestDto.setDescription("Deploying microcks env");
        taskRequestDto.setStatus(TaskStatus.NOT_STARTED.toString());
        taskRequestDto.setTitle("Mocking APIs");

        HttpEntity<TaskDto> request = new HttpEntity<>(taskRequestDto);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(baseUrl + "/tasks/roles/product_owner", request, String.class);
        TaskDto response = om.readValue(stringResponseEntity.getBody(), TaskDto.class);

        assertEquals(taskRequestDto.getDescription(), response.getDescription());
        assertEquals(taskRequestDto.getStatus(), response.getStatus());
        assertEquals(taskRequestDto.getTitle(), response.getTitle());

    }

    @Test
    public void testUserCreate() {
        UserDto userDto = new UserDto();
        userDto.setUserName("Randula");
        userDto.setPassword("Password@123");

        UUID projectId = UUID.randomUUID();
        Project project = new Project();
        project.setId(projectId);
        project.setName("rest-api");

        when(projectRepository.findById(UUID.randomUUID())).thenReturn(Optional.of(project));

        Task task = new Task();
        task.setStatus(TaskStatus.NOT_STARTED.toString());
        task.setDescription("Deploying microcks env");
        task.setTitle("Mocking APIs");

        when(taskRepository.findById(UUID.randomUUID())).thenReturn(Optional.of(task));

        HttpEntity<UserDto> request = new HttpEntity<>(userDto);
        ResponseEntity<String> createUserResponseEntity = restTemplate.postForEntity(baseUrl + "/users/roles/admin", request, String.class);

        assertEquals(HttpStatus.OK, createUserResponseEntity.getStatusCode());
        assertEquals("User created successfully", createUserResponseEntity.getBody());

    }
}
