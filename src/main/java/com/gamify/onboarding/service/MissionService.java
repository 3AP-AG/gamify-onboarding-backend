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

    public List<Mission> getAllUserMissions(String username) throws Exception{

        return jiraClientService.getIssuesByUsername(username);
//        Mission mission1 = new Mission("1", "Mission 1", "Description 1", MissionStatus.IN_PROGRESS, 1);
//        Mission mission2 = new Mission("2", "Mission 2", "Description 2", MissionStatus.IN_PROGRESS, 2);
//        Mission mission3 = new Mission("3", "Mission 3", "Description 3", MissionStatus.DONE, 3);
//        Mission mission4 = new Mission("4", "Mission 4", "Description 4", MissionStatus.DONE, 4);
//        Mission mission5 = new Mission("5", "Mission 5", "Description 5", MissionStatus.TODO, 5);
//        Mission mission6 = new Mission("6", "Mission 6", "Description 6", MissionStatus.TODO, 6);
//        return List.of(mission1, mission2, mission3, mission4, mission5, mission6);
    }

    public Mission getCurrentUserMission(String username){
        return new Mission("1", "Mission 1", "Description 1", MissionStatus.IN_PROGRESS, 1);
    }

    public void startMissions(String username) {

    }

    public void updateMissionStatus(String id, MissionStatus missionStatus, String username){

    }
}
