package com.example.nantodo_backend.pojo;

import lombok.Data;

@Data
public class Tool {
    private String name;
    private String url;
    private String type; // document/git
}
