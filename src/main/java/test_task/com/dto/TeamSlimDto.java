package test_task.com.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TeamSlimDto {

    @JsonProperty("id")
    Long teamID;

    @JsonProperty("name")
    String name;
}
