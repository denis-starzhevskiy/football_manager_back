package test_task.com.service;

import org.junit.Assert;
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
        int budgetTeam1 = Objects.requireNonNull(teamService.getTeamById(1L).getBody()).getTeam().getBudget();
        int budgetTeam2 = Objects.requireNonNull(teamService.getTeamById(2L).getBody()).getTeam().getBudget();
        playerService.transfer(2L, 1L);
        int budget1Team1 = teamService.getTeamById(1L).getBody().getTeam().getBudget();
        int budget1Team2 = teamService.getTeamById(2L).getBody().getTeam().getBudget();
        System.out.println(budgetTeam2 + " | " + budgetTeam1 + " | " + budget1Team2 + " | " + budget1Team1);
        Assert.assertEquals(budgetTeam2 + budgetTeam1, budget1Team2 + budget1Team1);
    }
}