package com.gamify.onboarding.dto;

import com.gamify.onboarding.model.MissionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MissionUpdate {
    private String username;
    private MissionStatus missionStatus;
}
