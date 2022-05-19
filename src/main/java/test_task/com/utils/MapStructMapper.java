package test_task.com.utils;

import org.mapstruct.Mapper;
import test_task.com.dto.*;
import test_task.com.model.Player;
import test_task.com.model.Team;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    PlayerResponse playerToPlayerResponse(Player player);

    PlayerSlimDto playerToPlayerSlimDto(Player player);

    Player playerRequestToPlayer(PlayerRequest playerRequest) throws NullPointerException;

    TeamResponse teamToTeamResponse(Team team);

    TeamSlimDto teamToTeamSlimDto(Team team);

    Team teamRequestToTeam(TeamRequest teamRequest);


}
