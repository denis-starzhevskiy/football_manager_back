package test_task.com.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TeamRequest {
    @NotBlank(message = "Name is required")
    String name;
    @NotBlank(message = "Country is required")
    String country;
    @NotBlank(message = "Town is required")
    String town;
    @NotNull(message = "Commission is required")
    Byte commission;
    @NotNull(message = "Budget is required")
    Integer budget;

}
