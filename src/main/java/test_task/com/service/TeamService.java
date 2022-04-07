package test_task.com.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test_task.com.dto.TeamRequest;
import test_task.com.dto.TeamResponse;
import test_task.com.model.Team;
import test_task.com.repository.PlayerRepository;
import test_task.com.repository.TeamRepository;

import java.util.List;

@Service
@Slf4j
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Transactional
    public ResponseEntity<TeamResponse> getTeamById(Long id) {
        try {
            Team teamDB = teamRepository.findById(id).orElseThrow(() -> new NullPointerException("Nothing to send"));
            System.out.println(teamDB.getPlayers());
            TeamResponse teamResponse = new TeamResponse(true, "The team was found", teamDB);
            return new ResponseEntity<>(teamResponse, HttpStatus.OK);
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            TeamResponse teamResponse = new TeamResponse(false, "THe team wasn't found by Id", null);
            return new ResponseEntity<>(teamResponse, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<Team>> getAllTeams() {
        try {
            List<Team> teams = teamRepository.findAll();
            return new ResponseEntity<>(teams, HttpStatus.OK);
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<TeamResponse> registerTeam(TeamRequest teamRequest) {
        try{
            Team team = teamRequestToTeam(teamRequest);
            TeamResponse teamResponse = new TeamResponse(true, "success", teamRepository.save(team));
            return new ResponseEntity<>(teamResponse, HttpStatus.OK);
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            TeamResponse playerResponse = new TeamResponse(false, "error", null);
            return new ResponseEntity<>(playerResponse, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<TeamResponse> updateTeam(Team team) {
        try{
            if(teamRepository.existsById(team.getTeamID())) {
                TeamResponse teamResponse = new TeamResponse(true, "Team was updated", teamRepository.save(team));
                return new ResponseEntity<>(teamResponse, HttpStatus.OK);
            }else {
                TeamResponse teamResponse = new TeamResponse(false, "There's no team with such an id", null);
                return new ResponseEntity<>(teamResponse, HttpStatus.BAD_REQUEST);
            }
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            TeamResponse playerResponse = new TeamResponse(false, "error", null);
            return new ResponseEntity<>(playerResponse, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<TeamResponse> deleteTeam(Long id) {
        try{
            if(teamRepository.existsById(id)) {
                teamRepository.deleteById(id);
                TeamResponse playerResponse = new TeamResponse(true, "Team was deleted", null);
                return new ResponseEntity<>(playerResponse, HttpStatus.OK);
            }else {
                TeamResponse playerResponse = new TeamResponse(false, "There's no team with such an id", null);
                return new ResponseEntity<>(playerResponse, HttpStatus.BAD_REQUEST);
            }
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            TeamResponse playerResponse = new TeamResponse(false, "error", null);
            return new ResponseEntity<>(playerResponse, HttpStatus.BAD_REQUEST);
        }
    }

    private Team teamRequestToTeam(TeamRequest teamRequest) {
        Team team = new Team();
        team.setBudget(teamRequest.getBudget());
        team.setCommission(teamRequest.getCommission());
        team.setName(teamRequest.getName());
        team.setCountry(teamRequest.getCountry());
        team.setTown(teamRequest.getTown());
        return team;
    }

}
