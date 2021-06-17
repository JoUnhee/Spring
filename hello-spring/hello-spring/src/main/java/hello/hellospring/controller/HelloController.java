package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    /*
    * ex) resources:templates/ +{ViewName} + .html
    */
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
