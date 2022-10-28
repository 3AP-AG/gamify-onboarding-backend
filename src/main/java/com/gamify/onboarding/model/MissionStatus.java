package com.gamify.onboarding.model;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum MissionStatus {
    BACKLOG("Backlog"),
    TODO("Selected for Development"),
    IN_PROGRESS("In Progress"),
    DONE("Done");

    public final String jiraLabelValue;

    private MissionStatus(String jiraLabelValue) {
        this.jiraLabelValue = jiraLabelValue;
    }

    public String getJiraLabelValue() {
        return jiraLabelValue;
    }

    public static MissionStatus getMissionStatusByJiraLabel(String jiraLabel){
        return Arrays.stream(MissionStatus.values())
            .filter(missionStatus -> missionStatus.jiraLabelValue.equals(jiraLabel))
            .findFirst().orElse(null);
    }
}
