package test_task.com.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import test_task.com.utils.LocalDateSerializer;
import test_task.com.utils.TeamSerializer;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@JsonIgnoreProperties("player_id")
public class Player {

    @Id
    @Column(name = "player_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long playerId;

    @Column(nullable = false)
    String name;

    @Column(name =
            "last_name", nullable = false)
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

    public Player() {
    }

    public Player(String name, String lastName, LocalDate birthday, LocalDate startCareer, Team team) {
        this.name = name;
        this.lastName = lastName;
        this.birthday = birthday;
        this.startCareer = startCareer;
        this.team = team;
    }
}
