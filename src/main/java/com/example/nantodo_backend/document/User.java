package com.example.nantodo_backend.document;

import com.example.nantodo_backend.pojo.Message;
import com.example.nantodo_backend.pojo.Setting;
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
    private List<String> courses = new ArrayList<>();
    private List<String> groups = new ArrayList<>();
    private List<String> pendingGroups = new ArrayList<>();
    private List<String> tasks = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();
    private Setting settings = new Setting();
}
