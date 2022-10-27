package com.gamify.onboarding.service;

import com.gamify.onboarding.model.Mission;
import com.gamify.onboarding.model.MissionStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MissionService {

    public List<Mission> getAllUserMissions(String email){
        return null;
//        Mission mission = new Mission();
    }

    public Mission getCurrentUserMission(String email){
        return null;
//        Mission mission = new Mission();
    }

    public void startMissions(String email) {

    }

    public void updateMissionStatus(String id, MissionStatus missionStatus, String email){

    }
}
