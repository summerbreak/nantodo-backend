package com.example.nantodo_backend.controller;

import com.example.nantodo_backend.data.GroupRepository;
import com.example.nantodo_backend.data.UserRepository;
import com.example.nantodo_backend.document.Group;
import com.example.nantodo_backend.document.User;
import com.example.nantodo_backend.pojo.Application;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @GetMapping
    public Group getGroup(@RequestParam String id) {
        return groupRepository.findById(id).orElse(null);
    }

    @GetMapping("/all")
    public List<Group> getAllGroupOfUser(@RequestParam String userId, HttpServletResponse response) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            response.setStatus(500);
            return null;
        }
        response.setStatus(200);
        return user.getGroups().stream().map(id -> groupRepository.findById(id).orElse(null)).collect(Collectors.toList());
    }

    @GetMapping("/allMember")
    public List<User> getAllUserOfGroup(@RequestParam String groupId, HttpServletResponse response) {
        Group group = groupRepository.findById(groupId).orElse(null);
        if (group == null) {
            response.setStatus(500);
            return null;
        }
        return group.getMembers().stream().map(id -> userRepository.findById(id).orElse(null)).collect(Collectors.toList());
    }

    @PostMapping
    public String addGroup(@RequestBody Group group, HttpServletResponse response) {
        // 添加到组长的小组列表
        User user = userRepository.findById(group.getLeaderId()).orElse(null);
        if (user == null) {
            response.setStatus(500);
            return null;
        }
        groupRepository.save(group);
        user.getGroups().add(group.getId());
        userRepository.save(user);
        return group.getId();
    }

    @PostMapping("/app")
    public void addApplication(@RequestParam String id, @RequestBody Application application, HttpServletResponse response) {
        Group group = groupRepository.findById(id).orElse(null);
        if (group == null) {
            response.setStatus(500);
            return;
        }
        group.getApplications().add(application);
        groupRepository.save(group);
    }

    @PutMapping
    public void updateGroup(@RequestParam String id, @RequestBody Group group) {
        group.setId(id);
        groupRepository.save(group);
    }

    @DeleteMapping
    public void deleteGroup(@RequestParam String id) {
        groupRepository.deleteById(id);
    }
}
