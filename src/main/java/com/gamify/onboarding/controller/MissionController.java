package com.gamify.onboarding.controller;

import com.gamify.onboarding.dto.MissionUpdate;
import com.gamify.onboarding.dto.UserRequest;
import com.gamify.onboarding.model.Mission;
import com.gamify.onboarding.service.MissionService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/missions")
public class MissionController {

    private final MissionService missionService;

    @GetMapping
    public ResponseEntity<List<Mission>> getAllUserMissions(@RequestBody UserRequest request) throws Exception{
        return ResponseEntity.ok(missionService.getAllUserMissions(request.getUsername()));
    }

    @PutMapping("/start")
    public void startMissions(@RequestBody UserRequest request){
        missionService.startMissions(request.getUsername());
    }

    @PutMapping("/{id}")
    public void changeMissionStatus(@PathVariable String id, @RequestBody MissionUpdate request){
        missionService.updateMissionStatus(id, request.getMissionStatus(), request.getUsername());
    }

    @GetMapping("current")
    public Mission getCurrentMission(@RequestBody UserRequest request){
        return missionService.getCurrentUserMission(request.getUsername());
    }
}
