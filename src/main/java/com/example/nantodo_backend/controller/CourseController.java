package com.example.nantodo_backend.controller;

import com.example.nantodo_backend.data.CourseRepository;
import com.example.nantodo_backend.data.UserRepository;
import com.example.nantodo_backend.document.Course;
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

    @PostMapping
    public void addCourse(@RequestBody Course course) {
        courseRepository.save(course);
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
