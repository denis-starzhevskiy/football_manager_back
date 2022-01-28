package test_task.com.dto;

import lombok.Data;

@Data
public class TeamRequest {
    String name;
    String country;
    String town;
    Byte commission;
    Integer budget;

}
