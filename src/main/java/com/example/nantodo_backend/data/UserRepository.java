package com.example.nantodo_backend.data;

import com.example.nantodo_backend.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByPhone(String phone);
}
