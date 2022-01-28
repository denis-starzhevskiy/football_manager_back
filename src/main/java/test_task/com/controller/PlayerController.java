package test_task.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test_task.com.dto.PlayerRequest;
import test_task.com.dto.PlayerResponse;
import test_task.com.model.Player;
import test_task.com.service.PlayerService;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/{id}")
    public ResponseEntity<PlayerResponse> getPlayer(@PathVariable Long id) {
        return playerService.getPlayerById(id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Player>> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @PostMapping("/registration")
    public ResponseEntity<PlayerResponse> registerPlayer(@RequestBody PlayerRequest playerRequest) {
        return playerService.registerPlayer(playerRequest);
    }

    @PutMapping("/update")
    public ResponseEntity<PlayerResponse> updatePlayer(@RequestBody Player player) {
        return playerService.updatePlayer(player);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<PlayerResponse> deletePlayer(@PathVariable Long id) {
        return playerService.deletePlayer(id);
    }

    @PutMapping("/transfer")
    public ResponseEntity<PlayerResponse> transferPlayer(@PathParam("playerId") Long playerId,
                                                         @PathParam("teamId") Long teamId){
        return playerService.transfer(playerId, teamId);
    }

}
