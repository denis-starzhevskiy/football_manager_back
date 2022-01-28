package test_task.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test_task.com.dto.PlayerRequest;
import test_task.com.dto.TeamRequest;
import test_task.com.dto.TeamResponse;
import test_task.com.model.Player;
import test_task.com.model.Team;
import test_task.com.service.TeamService;

import java.util.List;

@RestController
@RequestMapping("/api/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/{id}")
    public ResponseEntity<TeamResponse> getTeam(@PathVariable Long id) {
        return teamService.getTeamById(id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Team>> getAllTeams() {
        return teamService.getAllTeams();
    }

    @PostMapping("/registration")
    public ResponseEntity<TeamResponse> registerTeam(@RequestBody TeamRequest teamRequest) {
        return teamService.registerTeam(teamRequest);
    }

    @PutMapping("/update")
    public ResponseEntity<TeamResponse> updateTeam(@RequestBody Team team) {
        return teamService.updateTeam(team);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<TeamResponse> deleteTeam(@PathVariable Long id) {
        return teamService.deleteTeam(id);
    }
}
