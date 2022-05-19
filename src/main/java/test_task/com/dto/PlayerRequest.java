package test_task.com.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Setter
@Getter
public class PlayerRequest {
    @NotBlank(message = "Name is required")
    String name;
    @NotBlank(message = "Surname is required")
    String lastName;
    @NotNull(message = "Birthday is required")
    @Past
    LocalDate birthday;
    @NotNull(message = "Start career is required")
    @PastOrPresent(message = "Career start date must be no later than now")
    LocalDate startCareer;
    @NotNull(message = "Team ID is required")
    Long teamId;
}
