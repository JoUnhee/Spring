package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/* Spring container에 MemberController 객체를 생성해서 넣어둠
* 기본적으로 Singleton으로 객체를 생성함 -> 유의
*/
@Controller
public class MemberController {

    //@Autowired private MemberService memberService; -> 필드 주입

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        /*
         * 생성자 주입 방법
         * 조립 단계에서만 접근되므로 관리상 용이
         */
        this.memberService = memberService;
    }

    /*  Setter 주입
        -> 누군가가 setting을 해줘야 하기 때문에 public으로 열려있어야함
        -> 관리상 문제가 발생할 수 있다.
        @Autowired
        public void setMemberService(MemberService memberService) {
            this.memberService = memberService;
        }
     */

    /* 생각해 보니까, 왜 Controller type이 String일 까요 ?*/

    @GetMapping("/members/new")
    public String createForm() {
        /* Get 방식을 통해, 해당 createForm 메소드로 접근되고
         * 별 다른 process 없이 createMemberForm Template으로 접근함
         * 이후 ViewResolver에 의해 해당 html이 선택이 되고 thymeleaf engine이 html을 rendering함
         * 아직 까지는 thymeleaf가 관여하는 부분이 적음
         * */
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        /* createMemberForm에서 "name"을 key값으로 post 방식으로 "/members/new"로 전달 */
        /* PostMapping으로 설정되어 있는 이 method에 접근이 됨 */
        /* 보통 data를 조회할 떄는 GetMapping을, data를 등록할 때는 PostMapping을 사용한다. */
        Member member = new Member();

        /* html로 부터 전달받은 name을 get name으로 불러오고, 이를 setName으로 member의 이름 설정정*/
       member.setName(form.getName());

        memberService.join(member);

        /* redirect와 forwarding의 차이 정리하기
         *
         */
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }
}
