package com.example.nantodo_backend.controller;

import com.example.nantodo_backend.data.*;
import com.example.nantodo_backend.document.*;
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
    private final GroupRepository groupRepository;

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

    @GetMapping("/ofGroup")
    public List<Task> getAllTaskOfGroup(@RequestParam String groupId, HttpServletResponse response) {
        Group group = groupRepository.findById(groupId).orElse(null);
        if (group == null) {
            response.setStatus(500);
            return null;
        }
        response.setStatus(200);
        return group.getTasks().stream().map(id -> taskRepository.findById(id).orElse(null)).collect(Collectors.toList());
    }

    @PostMapping
    public String addTask(@RequestBody Task task, HttpServletResponse response) {
        taskRepository.save(task);
        // 添加到用户的任务列表
        User user = userRepository.findById(task.getUserId()).orElse(null);
        if (task.getUserId().isBlank()) {
            response.setStatus(200);
        } else if (user == null) {
            response.setStatus(500);
        } else {
            user.getTasks().add(task.getId());
            userRepository.save(user);
        }
        // 添加到小组的任务列表
        Group group = groupRepository.findById(task.getGroupId()).orElse(null);
        if (group == null) {
            response.setStatus(500);
        } else {
            group.getTasks().add(task.getId());
            groupRepository.save(group);
        }
        return task.getId();
    }

    @PutMapping
    public void updateTask(@RequestParam String id, @RequestBody Task task, HttpServletResponse response) {
        Task originalTask = taskRepository.findById(id).orElse(null);
        if (originalTask == null) {
            response.setStatus(500);
            return;
        }
        if (!originalTask.getUserId().equals(task.getUserId())) {
            // 从原用户的任务列表中删除
            User originalUser = userRepository.findById(originalTask.getUserId()).orElse(null);
            if (originalUser != null) {
                originalUser.getTasks().remove(id);
                userRepository.save(originalUser);
            }
            // 添加到新用户的任务列表
            User user = userRepository.findById(task.getUserId()).orElse(null);
            if (user == null) {
                response.setStatus(500);
                return;
            }
            user.getTasks().add(id);
            userRepository.save(user);
        }
        task.setId(id);
        taskRepository.save(task);
    }

    @DeleteMapping
    public void deleteTask(@RequestParam String id, HttpServletResponse response) {
        Task task = taskRepository.findById(id).orElse(null);
        taskRepository.deleteById(id);
        if (task == null) {
            response.setStatus(500);
            return;
        }
        if (!task.getUserId().isBlank()) {
            // 从用户的任务列表中删除
            User user = userRepository.findById(task.getUserId()).orElse(null);
            if (user != null) {
                user.getTasks().remove(id);
                userRepository.save(user);
            }
        }
        // 从小组的任务列表中删除
        Group group = groupRepository.findById(task.getGroupId()).orElse(null);
        if (group != null) {
            group.getTasks().remove(id);
            groupRepository.save(group);
        }
    }

    @DeleteMapping("/ofGroup")
    public void deleteTaskOfGroup(@RequestParam String groupId, HttpServletResponse response) {
        Group group = groupRepository.findById(groupId).orElse(null);
        if (group == null) {
            response.setStatus(500);
            return;
        }
        deleteTaskInUser(group.getTasks(), taskRepository, userRepository);
        group.getTasks().clear();
        groupRepository.save(group);
    }

    public static void deleteTaskInUser(List<String> taskIdList, TaskRepository taskRepository, UserRepository userRepository) {
        for (String taskId : taskIdList) {
            Task task = taskRepository.findById(taskId).orElse(null);
            taskRepository.deleteById(taskId);
            if (task != null && !task.getUserId().isBlank()) {
                User user = userRepository.findById(task.getUserId()).orElse(null);
                if (user != null) {
                    user.getTasks().remove(taskId);
                    userRepository.save(user);
                }
            }
        }
    }
}
