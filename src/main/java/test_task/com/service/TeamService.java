package test_task.com.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import test_task.com.dto.TeamRequest;
import test_task.com.dto.TeamResponse;
import test_task.com.model.Player;
import test_task.com.model.Team;
import test_task.com.repository.TeamRepository;
import test_task.com.utils.MapStructMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MapStructMapper mapStructMapper;

    public TeamResponse getTeamById(Long id) throws NullPointerException {
        Team team = teamRepository.findById(id).orElseThrow(() -> new NullPointerException("Nothing to send"));
        return mapStructMapper.teamToTeamResponse(team);
    }

    public List<TeamResponse> getAllTeams(){
        List<Team> teams = teamRepository.findAll();
        return teams.stream()
                .map(team -> mapStructMapper.teamToTeamResponse(team))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public TeamResponse registerTeam(TeamRequest teamRequest) {
        return mapStructMapper.teamToTeamResponse(teamRepository.save(mapStructMapper.teamRequestToTeam(teamRequest)));
    }

    public TeamResponse updateTeam(TeamRequest teamRequest) {
        Team team = mapStructMapper.teamRequestToTeam(teamRequest);
        if(teamRepository.existsById(team.getTeamID())){
            teamRepository.deleteById(team.getTeamID());
            return mapStructMapper.teamToTeamResponse(teamRepository.save(team));
        }else{
            return null;
        }
    }

    public boolean deleteTeam(Long id) {
        try{
            if(teamRepository.existsById(id)) {
                teamRepository.deleteById(id);
                return true;
            }else {
                return false;
            }
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            return false; new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
