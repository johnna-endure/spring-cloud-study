package springboot.cloud.memberservice.controller.dto;

import lombok.Data;
import springboot.cloud.memberservice.client.dto.TeamDto;
import springboot.cloud.memberservice.entity.Member;

@Data
public class MemberDto {

    private Long id;
    private String name;
    private TeamDto team;

    public MemberDto(Member m) {
        this.id = m.getId();
        this.name = m.getName();
    }

    public MemberDto(){ }

}
