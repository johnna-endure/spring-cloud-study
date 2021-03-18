package springboot.cloud.memberservice.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;
    private int age;

    private Long teamId;

    protected Member() {}

    public Member(String name, int age) {
        this(name, age, null);
    }

    public Member(String name, int age, Long teamId) {
        this.name = name;
        this.age = age;
        this.teamId = teamId;
    }

    public void joinTeam(Long teamId) {
        this.teamId = teamId;
    }
}
