package spring.basicproject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.basicproject.discount.DiscountPolicy;
import spring.basicproject.discount.FixDiscountPolicy;
import spring.basicproject.discount.RateDiscountPolicy;
import spring.basicproject.member.MemberRepository;
import spring.basicproject.member.MemberService;
import spring.basicproject.member.MemberServiceImpl;
import spring.basicproject.member.MemoryMemberRepository;
import spring.basicproject.order.OrderService;
import spring.basicproject.order.OrderServiceImpl;

@Configuration
public class AppConfig {

    @Bean  /* 기본적으로 Bean의 이름은 메서드 이름으로 등록된다. */
    public DiscountPolicy discountPolicy() { return new RateDiscountPolicy(); }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService () {
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

}