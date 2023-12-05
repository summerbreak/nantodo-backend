package com.example.nantodo_backend.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "user")
@Data
public class User {
    @Id
    private String id;
    private String name;
    private String phone;
    private String email;
    private String studentNumber;
    private String avatarUrl;
    private String grade;
    private String password;
    private List<String> courses;
    private List<String> groups;
    private List<String> tasks;
}
