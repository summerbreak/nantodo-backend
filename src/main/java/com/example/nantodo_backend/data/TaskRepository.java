package com.example.nantodo_backend.data;

import com.example.nantodo_backend.document.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task, String> {
}
