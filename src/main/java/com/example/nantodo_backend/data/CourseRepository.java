package com.example.nantodo_backend.data;

import com.example.nantodo_backend.document.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseRepository extends MongoRepository<Course, String> {
}
