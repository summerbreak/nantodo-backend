package com.example.nantodo_backend.service;

import com.example.nantodo_backend.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // 其他业务逻辑
}
