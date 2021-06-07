package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/* Spring container에 MemberController 객체를 생성해서 넣어둠
* 기본적으로 Singleton으로 객체를 생성함 -> 유의
*/
@Controller
public class MemberController {

    /*
     * Spring이 @Service / @Repository를 Container에 등록
     * @ => Component scan 각각 service / repository / service annotation은 살펴보면 @component로 구현되어 있음
     * 하위 Package들은 spring이 알아서 scan 해주지만, 다른 Package는 대상이 되지 않음
     */

    //@Autowired private MemberService memberService; -> 필드 주입

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        /* MemberService couldn't be found... => MemberService의 객체가 Spring container에게 들어가 있지 않음
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

}
