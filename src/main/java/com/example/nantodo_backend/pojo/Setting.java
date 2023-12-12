package com.example.nantodo_backend.pojo;

import lombok.Data;

@Data
public class Setting {
    private Integer emergencyDays = 3;
    private Integer noticeFrequency = 6;
    private boolean quietMode = false;
    private Integer quietModeStart = 22;
    private Integer quietModeEnd = 8;
}
