package com.gamify.onboarding.service;

import com.atlassian.jira.rest.client.api.domain.Issue;
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

    public Mission getCurrentUserMission(String username) throws Exception {
        final List<Mission> allMissions = jiraClientService.getAllMissions(username);
        Mission mission = allMissions
            .stream()
            .filter(missions -> MissionStatus.IN_PROGRESS.equals(missions.getStatus()))
            .findFirst().orElse(null);

        if(mission == null){
            mission = allMissions
                .stream()
                .filter(missions -> MissionStatus.TODO.equals(missions.getStatus()))
                .findFirst().orElse(null);

            jiraClientService.updateIssueStatus(mission.getId(), MissionStatus.IN_PROGRESS, username);

            final Issue issue = jiraClientService.getIssue(mission.getId());

            mission = new Mission(issue.getKey(),
                issue.getSummary(),
                issue.getDescription(),
                MissionStatus.getMissionStatusByJiraLabel(issue.getStatus().getName()));
        }

        return mission;

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
