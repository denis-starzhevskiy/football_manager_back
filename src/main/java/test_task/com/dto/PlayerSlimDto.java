package test_task.com.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerSlimDto {

    @JsonProperty("id")
    Long playerId;

    @JsonProperty("name")
    String name;

    @JsonProperty("last_name")
    String lastName;

}
