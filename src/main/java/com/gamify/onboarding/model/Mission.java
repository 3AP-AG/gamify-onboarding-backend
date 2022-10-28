package com.gamify.onboarding.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mission implements Serializable {
    private String id;
    private String title;
    private String description;
    private MissionStatus status;
}
