package com.example.nantodo_backend.controller;

import com.example.nantodo_backend.data.*;
import com.example.nantodo_backend.document.*;
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
    private final CourseRepository courseRepository;
    private final TaskRepository taskRepository;

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

    @GetMapping("/free")
    public List<Group> getAllFreeGroup() {
        List<Group> groups = groupRepository.findAll();
        List<Group> freeGroups = new ArrayList<>();
        for (Group group : groups) {
            if (!group.getType().equals("course")) {
                freeGroups.add(group);
            }
        }
        return freeGroups;
    }

    @PostMapping
    public String addGroup(@RequestBody Group group, HttpServletResponse response) {
        // 将leaderId添加到members中
        group.getMembers().add(group.getLeaderId());
        groupRepository.save(group);
        // 添加到组长的小组列表
        User user = userRepository.findById(group.getLeaderId()).orElse(null);
        if (user == null) {
            response.setStatus(500);
        } else {
            user.getGroups().add(group.getId());
            userRepository.save(user);
        }
        if (group.getType().equals("course")) {
            // 添加到课程的小组列表
            Course course = courseRepository.findById(group.getCourseId()).orElse(null);
            if (course == null) {
                response.setStatus(500);
            } else {
                course.getGroups().add(group.getId());
                courseRepository.save(course);
            }
        }
        return group.getId();
    }

    @PostMapping("/app")
    public void addApplication(@RequestParam String id, @RequestBody Application application, HttpServletResponse response) {
        Group group = groupRepository.findById(id).orElse(null);
        if (group == null) {
            response.setStatus(500);
            return;
        }
        group.getApplications().add(0, application);
        groupRepository.save(group);
    }

    @PutMapping
    public void updateGroup(@RequestParam String id, @RequestBody Group group) {
        group.setId(id);
        groupRepository.save(group);
    }

    @PutMapping("/app")
    public void updateApplication(@RequestParam String id, @RequestBody Application application, HttpServletResponse response) {
        Group group = groupRepository.findById(id).orElse(null);
        if (group == null) {
            response.setStatus(500);
            return;
        }
        for (int i = 0; i < group.getApplications().size(); i++) {
            if (group.getApplications().get(i).getTimestamp().equals(application.getTimestamp())) {
                group.getApplications().set(i, application);
                break;
            }
        }
        if (application.getStatus().equals("accepted")) {
            group.getMembers().add(application.getUserId());
        }
        groupRepository.save(group);
    }

    // 通过邀请码加入小组
    @PutMapping("/invite")
    public void joinByInviting(@RequestParam String code, @RequestParam String userId, HttpServletResponse response) {
        List<Group> groups = groupRepository.findAll();
        for (Group group : groups) {
            String invitingCode = group.getId().substring(group.getId().length() - 6).toUpperCase();
            if (invitingCode.equals(code)) {
                // 若已满员则返回400状态码
                if (group.getMembers().size() == group.getCapacity()) {
                    response.setStatus(400);
                    return;
                }
                group.getMembers().add(userId);
                groupRepository.save(group);
                return;
            }
        }
        // 若未找到对应的小组则返回500状态码
        response.setStatus(500);
    }

    @DeleteMapping
    public void deleteGroup(@RequestParam String id, HttpServletResponse response) {
        Group group = groupRepository.findById(id).orElse(null);
        groupRepository.deleteById(id);
        if (group == null) {
            response.setStatus(500);
            return;
        }
        for (String userId : group.getMembers()) {
            // 从成员的小组列表中删除
            User user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                user.getGroups().remove(id);
                userRepository.save(user);
            }
        }
        if (group.getType().equals("course")) {
            // 从课程的小组列表中删除
            Course course = courseRepository.findById(group.getCourseId()).orElse(null);
            if (course != null) {
                course.getGroups().remove(id);
                courseRepository.save(course);
            }
        }
        // 删除其所有任务
        TaskController.deleteTaskInUser(group.getTasks(), taskRepository, userRepository);
    }

    @DeleteMapping("/member")
    public void deleteMemberOfGroup(@RequestParam String userId, @RequestParam String groupId, HttpServletResponse response) {
        Group group = groupRepository.findById(groupId).orElse(null);
        if (group == null) {
            response.setStatus(500);
            return;
        }
        group.getMembers().remove(userId);
        groupRepository.save(group);
        // 从成员的小组列表中删除
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.getGroups().remove(groupId);
            userRepository.save(user);
        }
    }
}
