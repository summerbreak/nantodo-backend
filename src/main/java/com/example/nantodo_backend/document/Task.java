package com.example.nantodo_backend.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "task")
@Data
public class Task {
    @Id
    private String id;
    private String title;
    private String content;
    private Date releaseTime;
    private boolean starred;
    private boolean done;
    private Date deadline;
    private String userId;
    private String groupId;
}
