package com.example.nantodo_backend.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Application {
    private Date timestamp;
    private String userId;
    private String name;
    private String status; // 可选值: 'pending', 'accepted', 'refused，分别代表等待审核、接受、拒绝'
}
