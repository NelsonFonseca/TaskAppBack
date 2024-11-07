package com.infolaft.task;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.infolaft.task.model.Task;
import com.infolaft.task.repository.TaskRepository;
import com.infolaft.task.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    public void testFindAllTasks() {
        Task task1 = new Task(1L, "Task 1", "Description 1", false);
        Task task2 = new Task(2L, "Task 2", "Description 2", true);
        List<Task> tasks = Arrays.asList(task1, task2);

        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> result = taskService.findAllTasks();

        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getTitle());
        assertEquals("Task 2", result.get(1).getTitle());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void testFindTaskById_Found() {
        Task task = new Task(1L, "Task 1", "Description 1", false);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<Task> result = taskService.findTaskById(1L);

        assertTrue(result.isPresent());
        assertEquals("Task 1", result.get().getTitle());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindTaskById_NotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Task> result = taskService.findTaskById(1L);

        assertFalse(result.isPresent());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveTask() {
        Task task = new Task(null, "Task 1", "Description 1", false);
        Task savedTask = new Task(1L, "Task 1", "Description 1", false);

        when(taskRepository.save(task)).thenReturn(savedTask);

        Task result = taskService.saveTask(task);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Task 1", result.getTitle());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testDeleteTask() {
        Long taskId = 1L;

        doNothing().when(taskRepository).deleteById(taskId);

        taskService.deleteTask(taskId);

        verify(taskRepository, times(1)).deleteById(taskId);
    }
}
