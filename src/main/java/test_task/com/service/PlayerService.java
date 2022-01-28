package test_task.com.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test_task.com.dto.PlayerRequest;
import test_task.com.dto.PlayerResponse;
import test_task.com.model.Player;
import test_task.com.model.Team;
import test_task.com.repository.PlayerRepository;
import test_task.com.repository.TeamRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    public ResponseEntity<PlayerResponse> getPlayerById(Long id) {
        try {
            Player playerDB = playerRepository.findById(id).orElseThrow(() -> new NullPointerException("Nothing to send"));
            PlayerResponse playerResponse = new PlayerResponse(true, "The player was found", playerDB);
            return new ResponseEntity<>(playerResponse, HttpStatus.OK);
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            PlayerResponse playerResponse = new PlayerResponse(false, "THe player wasn't found by Id", null);
            return new ResponseEntity<>(playerResponse, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<Player>> getAllPlayers() {
        try {
            List<Player> players = playerRepository.findAll();
            return new ResponseEntity<>(players, HttpStatus.OK);
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<PlayerResponse> registerPlayer(PlayerRequest playerRequest) {
        try{
//            if(playerRepository.existsById(player.getPlayerId())) {
//                PlayerResponse playerResponse = new PlayerResponse(false, "There's a player with such an id", null);
                Player player = new Player();
                player.setName(playerRequest.getName());
                player.setLastName(playerRequest.getLastName());
                player.setBirthday(playerRequest.getBirthday());
                player.setStartCareer(playerRequest.getStartCareer());
                player.setTeam(teamRepository.findById(playerRequest.getTeamId()).orElseThrow(() -> new NullPointerException("Can't find the team with ID " + playerRequest.getTeamId())));
                PlayerResponse playerResponse = new PlayerResponse(true, "success", playerRepository.save(player));
                return new ResponseEntity<>(playerResponse, HttpStatus.OK);
//            }else {
//                PlayerResponse playerResponse = new PlayerResponse(true, "Player was registered", playerRepository.save(player));
//                return new ResponseEntity<>(playerResponse, HttpStatus.OK);
//            }
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            PlayerResponse playerResponse = new PlayerResponse(false, "error", null);
            return new ResponseEntity<>(playerResponse, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<PlayerResponse> updatePlayer(Player player) {
        try{
            if(playerRepository.existsById(player.getPlayerId())) {
                PlayerResponse playerResponse = new PlayerResponse(true, "Player was updated", playerRepository.save(player));
                return new ResponseEntity<>(playerResponse, HttpStatus.OK);
            }else {
                PlayerResponse playerResponse = new PlayerResponse(false, "There's no player with such an id", null);
                return new ResponseEntity<>(playerResponse, HttpStatus.BAD_REQUEST);
            }
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            PlayerResponse playerResponse = new PlayerResponse(false, "error", null);
            return new ResponseEntity<>(playerResponse, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<PlayerResponse> deletePlayer(Long id) {
        try{
            if(playerRepository.existsById(id)) {
                playerRepository.deleteById(id);
                PlayerResponse playerResponse = new PlayerResponse(true, "Player was deleted", null);
                return new ResponseEntity<>(playerResponse, HttpStatus.OK);
            }else {
                PlayerResponse playerResponse = new PlayerResponse(false, "There's no player with such an id", null);
                return new ResponseEntity<>(playerResponse, HttpStatus.BAD_REQUEST);
            }
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            PlayerResponse playerResponse = new PlayerResponse(false, "error", null);
            return new ResponseEntity<>(playerResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity<PlayerResponse> transfer(Long playerId, Long teamId) {
        try{
            Player player = playerRepository.findById(playerId).orElseThrow(()->new NullPointerException("Can't found the player"));
            Team currentTeam = player.getTeam();
            Team nextTeam = teamRepository.findById(teamId).orElseThrow(()->new NullPointerException("Can't found the team"));

            long cost = (ChronoUnit.MONTHS.between(player.getStartCareer(), LocalDate.now()) * 100000) / ChronoUnit.YEARS.between(player.getBirthday(), LocalDate.now());

            long costWithCommission = cost + (cost / 100 * currentTeam.getCommission());
            System.out.println(costWithCommission);

            nextTeam.setBudget((int) (nextTeam.getBudget() - costWithCommission));
            currentTeam.setBudget((int) (currentTeam.getBudget() + costWithCommission));

            player.setTeam(nextTeam);

            teamRepository.save(currentTeam);
            teamRepository.save(nextTeam);
            playerRepository.save(player);
            return new ResponseEntity<>(new PlayerResponse(true, "The transfer is carried out", player), HttpStatus.OK);
        }catch (Exception ex){
            log.error(ex.getMessage());
            return new ResponseEntity<>(new PlayerResponse(false, "Can't transfer player: "+ex.getMessage(), null), HttpStatus.OK);
        }

    }


}
