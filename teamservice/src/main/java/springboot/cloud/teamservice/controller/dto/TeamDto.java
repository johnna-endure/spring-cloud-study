package springboot.cloud.teamservice.controller.dto;

import lombok.Data;
import springboot.cloud.teamservice.entity.Team;

@Data
public class TeamDto {
    private Long id;
    private String name;

    public TeamDto() {}

    public TeamDto(Team m) {
        id = m.getId();
        name = m.getName();
    }
}
