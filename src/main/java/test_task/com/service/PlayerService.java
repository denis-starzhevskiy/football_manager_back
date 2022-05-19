package test_task.com.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test_task.com.configuration.exceptions.TransferException;
import test_task.com.dto.PlayerRequest;
import test_task.com.dto.PlayerResponse;
import test_task.com.model.Player;
import test_task.com.model.Team;
import test_task.com.repository.PlayerRepository;
import test_task.com.repository.TeamRepository;
import test_task.com.utils.MapStructMapper;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MapStructMapper mapStructMapper;

    public PlayerResponse getPlayerById(Long id) {
        Player player = playerRepository.findById(id).orElseThrow(() -> new NullPointerException("Nothing to send"));
        return mapStructMapper.playerToPlayerResponse(player);
    }

    public List<PlayerResponse> getAllPlayers() {
        List<Player> players = playerRepository.findAll();
        return players.stream()
                .map(player -> mapStructMapper.playerToPlayerResponse(player))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public PlayerResponse registerPlayer(PlayerRequest playerRequest) {
        return mapStructMapper.playerToPlayerResponse(playerRepository.save(mapStructMapper.playerRequestToPlayer(playerRequest)));
    }

    public PlayerResponse updatePlayer(PlayerRequest playerRequest) {
        Player player = mapStructMapper.playerRequestToPlayer(playerRequest);
        if(playerRepository.existsById(player.getPlayerId())){
            playerRepository.deleteById(player.getPlayerId());
            playerRepository.save(player);
            return mapStructMapper.playerToPlayerResponse(player);
        }else{
            return null;
        }
    }

    public boolean deletePlayer(Long id) {
        try{
            if(playerRepository.existsById(id)) {
                playerRepository.deleteById(id);
                return true;
            }else {
                return false;
            }
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            return false;
        }
    }

    @Transactional(rollbackFor = TransferException.class)
    public PlayerResponse transfer(Long playerId, Long teamId) {
        try{
            Player player = playerRepository.findById(playerId).orElseThrow(()->new NullPointerException("Can't found the player"));
            Team currentTeam = player.getTeam();
            Team nextTeam = teamRepository.findById(teamId).orElseThrow(()->new NullPointerException("Can't found the team"));

            long cost = (ChronoUnit.MONTHS.between(player.getStartCareer(), LocalDate.now()) * 100000) / ChronoUnit.YEARS.between(player.getBirthday(), LocalDate.now());

            long costWithCommission = cost + (cost / 100 * currentTeam.getCommission());

            try {
                if (nextTeam.getBudget() < costWithCommission) {
                    throw new TransferException("Not enough money");
                } else {
                    nextTeam.setBudget((int) (nextTeam.getBudget() - costWithCommission));
                    currentTeam.setBudget((int) (currentTeam.getBudget() + costWithCommission));
                    player.setTeam(nextTeam);
                    teamRepository.save(nextTeam);
                    teamRepository.save(currentTeam);
                    playerRepository.save(player);
                    return mapStructMapper.playerToPlayerResponse(player);
                }
            }catch (TransferException ex){
                log.error(ex.getMessage());
                return null;
            }

        }catch (Exception ex){
            log.error(ex.getMessage());
            return null;
        }

    }


}
