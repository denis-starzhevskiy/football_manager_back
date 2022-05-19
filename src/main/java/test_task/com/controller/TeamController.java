package test_task.com.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test_task.com.dto.PlayerRequest;
import test_task.com.dto.TeamRequest;
import test_task.com.dto.TeamResponse;
import test_task.com.model.Player;
import test_task.com.model.Team;
import test_task.com.service.TeamService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teams")
@Slf4j
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/{id}")
    public ResponseEntity<TeamResponse> getTeam(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(teamService.getTeamById(id), HttpStatus.OK);
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<TeamResponse>> getAllTeams() {
        List<TeamResponse> list = teamService.getAllTeams();
        if(list.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TeamResponse> registerTeam(@Valid @RequestBody TeamRequest teamRequest) {
        return new ResponseEntity<>(teamService.registerTeam(teamRequest), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<TeamResponse> updateTeam(@Valid @RequestBody TeamRequest teamRequest) {
        TeamResponse teamResponse = teamService.updateTeam(teamRequest);
        return teamResponse != null ?
                new ResponseEntity<>(teamResponse, HttpStatus.OK) :
                new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeam(@PathVariable Long id) {
        return teamService.deleteTeam(id) ?
                new ResponseEntity<>("The team was deleted", HttpStatus.OK) :
                new ResponseEntity<>("There is no team with a such ID", HttpStatus.BAD_REQUEST);
    }
}
