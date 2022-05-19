package test_task.com.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import test_task.com.dto.*;
import test_task.com.model.Player;
import test_task.com.model.Team;
import test_task.com.repository.TeamRepository;

import java.util.stream.Collectors;

@Component
public class MapStructMapperImpl implements MapStructMapper{

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public PlayerResponse playerToPlayerResponse(Player player) {
        PlayerResponse playerResponse = new PlayerResponse();
        playerResponse.setPlayerId(player.getPlayerId());
        playerResponse.setName(player.getName());
        playerResponse.setLastName(player.getLastName());
        playerResponse.setBirthday(player.getBirthday());
        playerResponse.setStartCareer(player.getStartCareer());
        playerResponse.setTeamSlimDto(teamToTeamSlimDto(player.getTeam()));
        return playerResponse;
    }

    @Override
    public PlayerSlimDto playerToPlayerSlimDto(Player player) {
        return new PlayerSlimDto(player.getPlayerId(), player.getName(), player.getLastName());
    }

    @Override
    public Player playerRequestToPlayer(PlayerRequest playerRequest) throws NullPointerException {
        Player player = new Player();
        player.setName(playerRequest.getName());
        player.setLastName(playerRequest.getLastName());
        player.setBirthday(playerRequest.getBirthday());
        player.setStartCareer(playerRequest.getStartCareer());
        player.setTeam(teamRepository.findById(playerRequest.getTeamId()).orElseThrow(() -> new NullPointerException("Can't find the team with ID " + playerRequest.getTeamId())));
        return player;
    }

    @Override
    public TeamResponse teamToTeamResponse(Team team) {
        TeamResponse teamResponse = new TeamResponse();
        teamResponse.setTeamID(team.getTeamID());
        teamResponse.setName(team.getName());
        teamResponse.setCountry(team.getCountry());
        teamResponse.setTown(team.getTown());
        teamResponse.setCommission(team.getCommission());
        teamResponse.setBudget(team.getBudget());
        teamResponse.setPlayers(team.getPlayers().stream().map(this::playerToPlayerSlimDto).collect(Collectors.toList()));
        return teamResponse;
    }

    @Override
    public TeamSlimDto teamToTeamSlimDto(Team team) {
        return new TeamSlimDto(team.getTeamID(), team.getName());
    }


    @Override
    public Team teamRequestToTeam(TeamRequest teamRequest) {
        Team team = new Team();
        team.setBudget(teamRequest.getBudget());
        team.setCommission(teamRequest.getCommission());
        team.setName(teamRequest.getName());
        team.setCountry(teamRequest.getCountry());
        team.setTown(teamRequest.getTown());
        return team;
    }
}
