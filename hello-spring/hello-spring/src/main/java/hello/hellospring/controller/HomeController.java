package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /* localhost:portnum 으로 접속했을 때, Mapping으로 /가 설정되어 있는 친구가 먼저 실행이 된다*/
    @GetMapping("/")
    public String home() {
        return "home";
    }
}
