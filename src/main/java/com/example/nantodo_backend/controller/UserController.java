package com.example.nantodo_backend.controller;

import com.example.nantodo_backend.document.User;
import com.example.nantodo_backend.data.UserRepository;
import com.example.nantodo_backend.pojo.Message;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping
    public User getUser(@RequestParam String id) {
        return userRepository.findById(id).orElse(null);
    }

    @GetMapping("/login")
    public User login(@RequestParam String phone, @RequestParam String password, HttpServletResponse response) {
        User user = userRepository.findByPhone(phone);
        if (user == null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
        if (user.getPassword().equals(password)) {
            response.setStatus(HttpStatus.OK.value());
            return user;
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return null;
        }
    }

    @PostMapping
    public String addUser(@RequestBody User user) {
        userRepository.save(user);
        return user.getId();
    }

    @PostMapping("/message")
    public void addMessage(@RequestParam String id, @RequestBody Message message, HttpServletResponse response) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            response.setStatus(500);
            return;
        }
        // 在开头加入新的消息
        user.getMessages().add(0, message);
        userRepository.save(user);
    }

    @PutMapping
    public void updateUser(@RequestParam String id, @RequestBody User user) {
        user.setId(id);
        userRepository.save(user);
    }

    @DeleteMapping
    public void deleteUser(@RequestParam String id) {
        userRepository.deleteById(id);
    }
}