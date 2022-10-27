package com.gamify.onboarding.dto;

import com.gamify.onboarding.model.MissionStatus;
import lombok.Value;

@Value
public class MissionUpdate {
    String username;
    MissionStatus missionStatus;
}
