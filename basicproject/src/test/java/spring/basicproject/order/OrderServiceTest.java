package spring.basicproject.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import spring.basicproject.member.Grade;
import spring.basicproject.member.Member;
import spring.basicproject.member.MemberService;
import spring.basicproject.member.MemberServiceImpl;

public class OrderServiceTest {

    MemberService memberService = new MemberServiceImpl ();
    OrderService orderService = new OrderServiceImpl();

    @Test
    void createOrder() {
        Long memberId = 1L;
        Member member = new Member(memberId, "조운희", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}
