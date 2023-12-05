package com.example.nantodo_backend.data;

import com.example.nantodo_backend.document.Homework;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HomeworkRepository extends MongoRepository<Homework, String> {
}
