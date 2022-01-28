package test_task.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test_task.com.model.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
}
