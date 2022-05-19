package test_task.com.service;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

@SpringBootTest
public class PlayerServiceTest {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private TeamService teamService;

    @Test
    public void transfer() {
        int budgetTeam1 = Objects.requireNonNull(teamService.getTeamById(1L).getBudget());
        int budgetTeam2 = Objects.requireNonNull(teamService.getTeamById(2L).getBudget());
        playerService.transfer(1L, 1L);
        int budget1Team1 = Objects.requireNonNull(teamService.getTeamById(1L).getBudget());
        int budget1Team2 = Objects.requireNonNull(teamService.getTeamById(2L).getBudget());
        System.out.println(budgetTeam2 + " | " + budgetTeam1 + " | " + budget1Team2 + " | " + budget1Team1);
        Assertions.assertEquals(budgetTeam2 + budgetTeam1, budget1Team2 + budget1Team1);
    }
}