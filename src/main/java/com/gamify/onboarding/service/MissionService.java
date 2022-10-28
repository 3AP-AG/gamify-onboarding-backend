package com.gamify.onboarding.service;

import com.gamify.onboarding.model.Mission;
import com.gamify.onboarding.model.MissionStatus;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MissionService {

    private final JiraClientService jiraClientService;

    public MissionService(JiraClientService jiraClientService) {
        this.jiraClientService = jiraClientService;
    }

    public List<Mission> getAllUserMissions(String username) throws Exception {
        return jiraClientService.getAllMissions(username);
    }

    public Mission getCurrentUserMission(String username) {
        return new Mission("1", "Mission 1", "Description 1", MissionStatus.IN_PROGRESS);
    }

    public void startMissions(String username) throws Exception{
        final List<Mission> allMissions = jiraClientService.getAllMissions(username);
        final Mission mission = allMissions
            .stream()
            .filter(missions -> MissionStatus.TODO.equals(missions.getStatus()))
            .findFirst().orElse(null);

        jiraClientService.updateIssueStatus(mission.getId(), MissionStatus.IN_PROGRESS, username);
    }

    public void updateMissionStatus(String id, MissionStatus missionStatus, String username) {
        jiraClientService.updateIssueStatus(id, missionStatus, username);
    }
}
