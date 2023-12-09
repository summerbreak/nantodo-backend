package com.example.nantodo_backend.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Application {
    private Date timestamp;
    private String userId;
    private String name;
    private String status;
}
