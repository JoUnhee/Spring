package spring.basicproject;

import spring.basicproject.member.Grade;
import spring.basicproject.member.Member;
import spring.basicproject.member.MemberService;
import spring.basicproject.member.MemberServiceImpl;
import spring.basicproject.order.Order;
import spring.basicproject.order.OrderService;
import spring.basicproject.order.OrderServiceImpl;

public class OrderApp {

    public static void main(String[] args) {

        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        OrderService orderService = appConfig.orderService();

        Long memberId = 1L;
        Member member = new Member(memberId, "조운희", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);

        System.out.println("order = " + order);
    }
}