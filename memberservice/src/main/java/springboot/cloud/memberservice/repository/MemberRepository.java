package springboot.cloud.memberservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.cloud.memberservice.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
