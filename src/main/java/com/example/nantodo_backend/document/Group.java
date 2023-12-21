package com.example.nantodo_backend.document;

import com.example.nantodo_backend.pojo.Application;
import com.example.nantodo_backend.pojo.Tool;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "group")
@Data
public class Group {
    @Id
    private String id;
    private String name;
    private String leaderId;
    private String organName;
    private String description;
    private String type;
    private Integer capacity;
    private String courseId;
    private List<String> members = new ArrayList<>();
    private List<String> tasks = new ArrayList<>();
    private List<Application> applications = new ArrayList<>();
    private List<Tool> tools = new ArrayList<>();
}
