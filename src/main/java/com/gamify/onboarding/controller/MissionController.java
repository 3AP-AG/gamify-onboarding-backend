package com.gamify.onboarding.controller;

import com.gamify.onboarding.dto.MissionUpdate;
import com.gamify.onboarding.dto.UserRequest;
import com.gamify.onboarding.model.Mission;
import com.gamify.onboarding.service.MissionService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/missions")
public class MissionController {

    private final MissionService missionService;

    @GetMapping
    public ResponseEntity<List<Mission>> getAllUserMissions(@RequestParam String username)  throws Exception {
        return ResponseEntity.ok(missionService.getAllUserMissions(username));
    }

    @PutMapping("/start")
    public void startMissions(@RequestBody UserRequest request) throws Exception{
        missionService.startMissions(request.getUsername());
    }

    @PutMapping("/{id}")
    public void changeMissionStatus(@PathVariable String id, @RequestBody MissionUpdate request){
        missionService.updateMissionStatus(id, request.getMissionStatus(), request.getUsername());
    }

    @GetMapping("/current")
    public Mission getCurrentMission(@RequestParam String username) throws Exception {
        return missionService.getCurrentUserMission(username);
    }
}
