package test_task.com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import test_task.com.model.Player;
import test_task.com.model.Team;

@Data
@AllArgsConstructor
public class TeamResponse {

    boolean status;
    String message;
    Team team;

}
