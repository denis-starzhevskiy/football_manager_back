package test_task.com.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Team {

    @Id
    @Column(name = "team_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long teamID;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String country;

    @Column(nullable = false)
    String town;

    @Column(nullable = false)
    Byte commission;

    @Column(nullable = false)
    Integer budget;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Player> players;

    public Team() {
    }

    public Team(String name, String country, String town, Byte commission, Integer budget) {
        this.name = name;
        this.country = country;
        this.town = town;
        this.commission = commission;
        this.budget = budget;
    }
}
