package test_task.com.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test_task.com.dto.PlayerRequest;
import test_task.com.dto.PlayerResponse;
import test_task.com.service.PlayerService;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/players")
@Slf4j
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/{id}")
    public ResponseEntity<PlayerResponse> getPlayer(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(playerService.getPlayerById(id), HttpStatus.OK);
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<PlayerResponse>> getAllPlayers() {
        List<PlayerResponse> list = playerService.getAllPlayers();
        if(list.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PlayerResponse> registerPlayer(@Valid @RequestBody PlayerRequest playerRequest) {
        return new ResponseEntity<>(playerService.registerPlayer(playerRequest), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<String> updatePlayer(@RequestBody PlayerRequest playerRequest) {
        return playerService.updatePlayer(playerRequest) == null ?
                new ResponseEntity<>("The player was updated", HttpStatus.OK) :
                new ResponseEntity<>("A player with a such ID doesn't exist", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable Long id) {
        return playerService.deletePlayer(id) ?
                new ResponseEntity<>("The player was deleted", HttpStatus.OK) :
                new ResponseEntity<>("There is no player with a such ID", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/transfer")
    public ResponseEntity<PlayerResponse> transferPlayer(@PathParam("playerId") Long playerId,
                                                         @PathParam("teamId") Long teamId){
        PlayerResponse playerResponse = playerService.transfer(playerId, teamId);
        return playerResponse == null ?
                new ResponseEntity<>(null, HttpStatus.OK) :
                new ResponseEntity<>(playerResponse, HttpStatus.BAD_REQUEST);
    }

}
