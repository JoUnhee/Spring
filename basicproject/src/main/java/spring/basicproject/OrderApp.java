package spring.basicproject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.basicproject.member.Grade;
import spring.basicproject.member.Member;
import spring.basicproject.member.MemberService;
import spring.basicproject.member.MemberServiceImpl;
import spring.basicproject.order.Order;
import spring.basicproject.order.OrderService;
import spring.basicproject.order.OrderServiceImpl;

public class OrderApp {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);

        Long memberId = 1L;
        Member member = new Member(memberId, "조운희", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 20000);

        System.out.println("order = " + order);
    }
}
