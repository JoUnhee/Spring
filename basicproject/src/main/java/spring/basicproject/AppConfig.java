package spring.basicproject;

import spring.basicproject.discount.FixDiscountPolicy;
import spring.basicproject.member.MemberService;
import spring.basicproject.member.MemberServiceImpl;
import spring.basicproject.member.MemoryMemberRepository;
import spring.basicproject.order.OrderService;
import spring.basicproject.order.OrderServiceImpl;

public class AppConfig {

    /* Application에 대한 환경 구성을 모두 이 클래스에서 수행
     * - Application의 실제 동작에 필요한 구현 객체를 생성
     * - 생성자 주입을 통한 DIP 준수
     *
     * */
    public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    public OrderService orderService () {
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }

}
