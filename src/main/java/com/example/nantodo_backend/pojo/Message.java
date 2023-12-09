package com.example.nantodo_backend.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private String content;
    private String type;
    private Date timestamp;
}
