package test_task.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test_task.com.model.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
}
