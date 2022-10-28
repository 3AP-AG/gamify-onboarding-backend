package com.gamify.onboarding.model;

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
}
