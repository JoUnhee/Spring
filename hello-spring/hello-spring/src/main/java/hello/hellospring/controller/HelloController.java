package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    /*
    * Web application에서 /hello로 진입했을 때, GetMapping annotation의 값을 보고 해당 메소드 실행
    * Spring에서 알아서 다 해주네 ㅎㅎ
    * return value를 확인하고, 해당 template에 rendering 해라 -> model을 전달해줘라
    * Spring boot의 viewResolver가 리턴 value(문자)를 확인하고 해당 화면을 찾아서 처리한다
    * ex) resources:templates/ +{ViewName} + .html
    * */
    //@GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data","hello!!");
        return "hello";
    }
    //@GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    //@GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name){
        return "That's quite a cool " + name;
    }

    @GetMapping("coolapi")
    @ResponseBody/* spring도 기본은 json으로 반환 */
    public Hello coolapi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        /* return hello ; -> json 형태로 출력 */
        return hello;
    }

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
