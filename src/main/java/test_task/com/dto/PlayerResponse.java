package test_task.com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import test_task.com.model.Player;

@Data
@AllArgsConstructor
public class PlayerResponse {

    boolean status;
    String message;
    Player player;

}
