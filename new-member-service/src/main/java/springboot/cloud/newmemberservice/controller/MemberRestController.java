package springboot.cloud.newmemberservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberRestController {

    @GetMapping("/hello")
    public String hello() {
        return "새로운 멤버 서비스입니다.";
    }

}
