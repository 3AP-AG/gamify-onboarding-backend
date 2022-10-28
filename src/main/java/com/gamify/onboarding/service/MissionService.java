package com.gamify.onboarding.service;

import com.gamify.onboarding.model.Mission;
import com.gamify.onboarding.model.MissionStatus;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MissionService {

    private final JiraClientService jiraClientService;

    public List<Mission> getAllUserMissions(String username) throws Exception {
        return jiraClientService.getIssuesByUsername(username);
    }

    public Mission getCurrentUserMission(String username) {
        return new Mission("1", "Mission 1", "Description 1", MissionStatus.IN_PROGRESS);
    }

    public void startMissions(String username) {

    }

    public void updateMissionStatus(String id, MissionStatus missionStatus, String username) {
    }
}
