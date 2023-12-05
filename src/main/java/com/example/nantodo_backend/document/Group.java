package com.example.nantodo_backend.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "group")
@Data
public class Group {
    @Id
    private String id;
    private String name;
    private String leaderId;
    private String organName;
    private List<String> members;
    private List<String> tasks;
}
