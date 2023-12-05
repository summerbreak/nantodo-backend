package com.example.nantodo_backend.controller;

import com.example.nantodo_backend.data.CourseRepository;
import com.example.nantodo_backend.data.HomeworkRepository;
import com.example.nantodo_backend.document.Course;
import com.example.nantodo_backend.document.Homework;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/homework")
@RequiredArgsConstructor
public class HomeworkController {
    private final HomeworkRepository homeworkRepository;
    private final CourseRepository courseRepository;

    @GetMapping
    public Homework getHomework(@RequestParam String id) {
        return homeworkRepository.findById(id).orElse(null);
    }

    @GetMapping("/all")
    public List<Homework> getAllHomeworkOfCourse(@RequestParam String courseId, HttpServletResponse response) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            response.setStatus(500);
            return null;
        }
        response.setStatus(200);
        return course.getHomeworks().stream().map(id -> homeworkRepository.findById(id).orElse(null)).collect(Collectors.toList());
    }

    @PostMapping
    public void addHomework(@RequestBody Homework homework) {
        homeworkRepository.save(homework);
    }

    @PutMapping
    public void updateHomework(@RequestParam String id, @RequestBody Homework homework) {
        homework.setId(id);
        homeworkRepository.save(homework);
    }

    @DeleteMapping
    public void deleteHomework(@RequestParam String id) {
        homeworkRepository.deleteById(id);
    }
}
