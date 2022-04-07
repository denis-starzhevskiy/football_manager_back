package test_task.com.configuration.initialization;

import com.fasterxml.jackson.databind.util.ArrayIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import test_task.com.model.Player;
import test_task.com.model.Team;
import test_task.com.repository.PlayerRepository;
import test_task.com.repository.TeamRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class DataLoading implements ApplicationRunner {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Override
    public void run(ApplicationArguments args){
        teamRepository.saveAll(Arrays.asList(
                new Team("Demo", "Ukraine", "Lviv", (byte) 8, 10000000),
                new Team("Captains", "USA", "LA", (byte) 7, 10000000),
                new Team("Bayern", "Germany", "Frankfurt", (byte) 9, 10000000)
        ));
        playerRepository.saveAll(Arrays.asList(
                new Player("Mesut", "Ozil", LocalDate.parse("2000-05-17"), LocalDate.parse("2005-05-17"), teamRepository.getById(1L)),
                new Player("Lionel", "Messi", LocalDate.parse("1986-11-09"), LocalDate.parse("1978-11-09"), teamRepository.getById(2L)),
                new Player("Eden", "Hazard", LocalDate.parse("1999-05-06"), LocalDate.parse("1991-05-06"), teamRepository.getById(3L))
        ));
    }
}
