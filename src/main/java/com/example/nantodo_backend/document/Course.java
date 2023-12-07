package com.example.nantodo_backend.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "course")
@Data
public class Course {
    @Id
    private String id;
    private String url;
    private String name;
    private String teacher;
    private String description;
    private Integer grade;
    private Boolean open;
    private String semester;
    private List<String> groups;
    private List<String> homeworks;
    private List<String> students;
}
