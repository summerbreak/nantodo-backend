package com.example.nantodo_backend.controller;

import com.example.nantodo_backend.data.TaskRepository;
import com.example.nantodo_backend.data.UserRepository;
import com.example.nantodo_backend.document.Task;
import com.example.nantodo_backend.document.User;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @GetMapping
    public Task getTask(@RequestParam String id) {
        return taskRepository.findById(id).orElse(null);
    }

    @GetMapping("/all")
    public List<Task> getAllTaskOfUser(@RequestParam String userId, HttpServletResponse response) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            response.setStatus(500);
            return null;
        }
        response.setStatus(200);
        return user.getTasks().stream().map(id -> taskRepository.findById(id).orElse(null)).collect(Collectors.toList());
    }

    @PostMapping
    public void addTask(@RequestBody Task task) {
        taskRepository.save(task);
    }

    @PutMapping
    public void updateTask(@RequestParam String id, @RequestBody Task task) {
        task.setId(id);
        taskRepository.save(task);
    }

    @DeleteMapping
    public void deleteTask(@RequestParam String id) {
        taskRepository.deleteById(id);
    }
}
