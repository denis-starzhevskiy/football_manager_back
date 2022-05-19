package test_task.com.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import test_task.com.model.Team;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TeamResponse{

    @JsonProperty("id")
    Long teamID;

    String name;

    String country;

    String town;

    Byte commission;

    Integer budget;

    @JsonProperty("team_players")
    List<PlayerSlimDto> players;

}
