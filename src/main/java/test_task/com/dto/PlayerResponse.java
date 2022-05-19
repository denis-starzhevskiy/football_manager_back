package test_task.com.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import test_task.com.model.Player;

import java.time.LocalDate;

@Setter
@Getter
public class PlayerResponse{

    @JsonProperty("id")
    Long playerId;

    String name;

    @JsonProperty("last_name")
    String lastName;

    LocalDate birthday;

    @JsonProperty("start_career")
    LocalDate startCareer;

    TeamSlimDto teamSlimDto;

    public PlayerResponse() { }
}
