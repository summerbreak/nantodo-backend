package com.example.nantodo_backend.controller;

import com.example.nantodo_backend.document.User;
import com.example.nantodo_backend.data.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
    public void addUser(@RequestBody User user) {
        user.setCourses(new ArrayList<>());
        user.setGroups(new ArrayList<>());
        user.setTasks(new ArrayList<>());
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