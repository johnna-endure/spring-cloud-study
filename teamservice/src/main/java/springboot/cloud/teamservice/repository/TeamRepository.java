package springboot.cloud.teamservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.cloud.teamservice.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
