package com.example.nantodo_backend.data;

import com.example.nantodo_backend.document.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupRepository extends MongoRepository<Group, String> {
}
