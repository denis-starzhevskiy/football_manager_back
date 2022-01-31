package test_task.com.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;
import org.springframework.web.servlet.View;
import test_task.com.service.LocalDateSerializer;
import test_task.com.service.TeamSerializer;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@JsonIgnoreProperties("player_id")
public class Player {

    @Id
    @Column(name = "player_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long playerId;

    @Column(nullable = false)
    String name;

    @Column(name = "last_name", nullable = false)
    String lastName;

    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(nullable = false)
    LocalDate birthday;

    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name="start_career", nullable = false)
    LocalDate startCareer;

    @JsonSerialize(using = TeamSerializer.class)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

}
