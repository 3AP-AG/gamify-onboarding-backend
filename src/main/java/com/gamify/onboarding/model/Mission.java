package com.gamify.onboarding.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Mission {
    private String id;
    private String title;
    private String description;
    private MissionStatus status;
    private Integer order;
}
