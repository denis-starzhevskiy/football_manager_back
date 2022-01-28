package test_task.com.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class PlayerRequest {

    String name;
    String lastName;
    LocalDate birthday;
    LocalDate startCareer;
    Long teamId;

}
