package springboot.cloud.teamservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.cloud.teamservice.controller.dto.TeamDto;
import springboot.cloud.teamservice.entity.Team;
import springboot.cloud.teamservice.repository.TeamRepository;

@RestController
public class TeamRestController {

    private final TeamRepository teamRepository;

    public TeamRestController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @PostMapping("/init")
    public void init() {
        teamRepository.save(new Team("teamA"));
        teamRepository.save(new Team("teamB"));
    }

    @GetMapping("/teams/{teamId}")
    public TeamDto getTeam(@PathVariable("teamId") Long teamId) {
        return teamRepository.findById(teamId)
                .map(t -> new TeamDto(t))
                .orElse(new TeamDto());
    }
}
