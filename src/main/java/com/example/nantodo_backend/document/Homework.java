package com.example.nantodo_backend.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document(collection = "homework")
@Data
public class Homework {
    @Id
    private String id;
    private String title;
    private String content;
    private Date releaseTime;
    private Date deadline;
    private List<String> doneGroups = new ArrayList<>();
    private String courseId;
}
