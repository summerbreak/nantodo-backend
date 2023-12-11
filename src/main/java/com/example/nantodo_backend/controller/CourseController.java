package com.example.nantodo_backend.controller;

import com.example.nantodo_backend.data.CourseRepository;
import com.example.nantodo_backend.data.GroupRepository;
import com.example.nantodo_backend.data.UserRepository;
import com.example.nantodo_backend.document.Course;
import com.example.nantodo_backend.document.Group;
import com.example.nantodo_backend.document.User;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    @GetMapping
    public Course getCourse(@RequestParam String id) {
        return courseRepository.findById(id).orElse(null);
    }

    @GetMapping("/all")
    public List<Course> getAllCourseOfUser(@RequestParam String userId, HttpServletResponse response) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            response.setStatus(500);
            return null;
        }
        response.setStatus(200);
        return user.getCourses().stream().map(id -> courseRepository.findById(id).orElse(null)).collect(Collectors.toList());
    }

    @GetMapping("/allCourse")
    public List<Course> getAllCourse() {
        return courseRepository.findAll();
    }

    @GetMapping("/allGroup")
    public List<Group> getAllGroupOfCourse(@RequestParam String courseId, HttpServletResponse response) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            response.setStatus(500);
            return null;
        }
        return course.getGroups().stream().map(id -> groupRepository.findById(id).orElse(null)).collect(Collectors.toList());
    }

    @PostMapping
    public String addCourse(@RequestBody Course course) {
        courseRepository.save(course);
        return course.getId();
    }

    @PutMapping
    public void updateCourse(@RequestParam String id, @RequestBody Course course) {
        course.setId(id);
        courseRepository.save(course);
    }

    @DeleteMapping
    public void deleteCourse(@RequestParam String id) {
        courseRepository.deleteById(id);
    }
}
