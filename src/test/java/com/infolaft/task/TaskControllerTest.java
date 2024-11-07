package com.infolaft.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infolaft.task.controller.TaskController;
import com.infolaft.task.model.Task;
import com.infolaft.task.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    public void testGetAllTasks() throws Exception {
        Task task1 = new Task(1L, "Task 1", "Description 1", false);
        Task task2 = new Task(2L, "Task 2", "Description 2", true);
        List<Task> tasks = Arrays.asList(task1, task2);

        when(taskService.findAllTasks()).thenReturn(tasks);

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(tasks)))
                .andDo(print());

        verify(taskService, times(1)).findAllTasks();
    }

    @Test
    public void testGetTaskById_Found() throws Exception {
        Task task = new Task(1L, "Task 1", "Description 1", false);

        when(taskService.findTaskById(1L)).thenReturn(Optional.of(task));

        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(task)))
                .andDo(print());

        verify(taskService, times(1)).findTaskById(1L);
    }

    @Test
    public void testGetTaskById_NotFound() throws Exception {
        when(taskService.findTaskById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(taskService, times(1)).findTaskById(1L);
    }

    @Test
    public void testCreateTask() throws Exception {
        Task task = new Task(null, "Task 1", "Description 1", false);
        Task savedTask = new Task(1L, "Task 1", "Description 1", false);

        when(taskService.saveTask(any(Task.class))).thenReturn(savedTask);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(savedTask)))
                .andDo(print());

        verify(taskService, times(1)).saveTask(any(Task.class));
    }

    @Test
    public void testUpdateTask_Found() throws Exception {
        Task existingTask = new Task(1L, "Task 1", "Description 1", false);
        Task updatedTask = new Task(1L, "Updated Task", "Updated Description", true);

        when(taskService.findTaskById(1L)).thenReturn(Optional.of(existingTask));
        when(taskService.saveTask(any(Task.class))).thenReturn(updatedTask);

        mockMvc.perform(put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTask)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedTask)))
                .andDo(print());

        verify(taskService, times(1)).findTaskById(1L);
        verify(taskService, times(1)).saveTask(any(Task.class));
    }

    @Test
    public void testUpdateTask_NotFound() throws Exception {
        Task updatedTask = new Task(1L, "Updated Task", "Updated Description", true);

        when(taskService.findTaskById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTask)))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(taskService, times(1)).findTaskById(1L);
        verify(taskService, never()).saveTask(any(Task.class));
    }

    @Test
    public void testDeleteTask_Found() throws Exception {
        when(taskService.findTaskById(1L)).thenReturn(Optional.of(new Task()));

        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(taskService, times(1)).findTaskById(1L);
        verify(taskService, times(1)).deleteTask(1L);
    }

    @Test
    public void testDeleteTask_NotFound() throws Exception {
        when(taskService.findTaskById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(taskService, times(1)).findTaskById(1L);
        verify(taskService, never()).deleteTask(1L);
    }
}