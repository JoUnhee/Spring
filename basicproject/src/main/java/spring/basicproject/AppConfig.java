package spring.basicproject;

import spring.basicproject.discount.DiscountPolicy;
import spring.basicproject.discount.FixDiscountPolicy;
import spring.basicproject.discount.RateDiscountPolicy;
import spring.basicproject.member.MemberRepository;
import spring.basicproject.member.MemberService;
import spring.basicproject.member.MemberServiceImpl;
import spring.basicproject.member.MemoryMemberRepository;
import spring.basicproject.order.OrderService;
import spring.basicproject.order.OrderServiceImpl;

public class AppConfig {

    /*
     * Method call을 통해 역할이 분명하게 드러나도록 할 수 있음
     * -> 역할과 구현 클래스가 한눈에 들어옴옴     */
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }

    private MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }


    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    public OrderService orderService () {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

}
