package spring.basicproject.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import spring.basicproject.discount.FixDiscountPolicy;
import spring.basicproject.member.Grade;
import spring.basicproject.member.Member;
import spring.basicproject.member.MemoryMemberRepository;

import static org.assertj.core.api.Assertions.*;

public class OrderServiceImplTest {

    @Test
    void createOrder() {
        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L, "name", Grade.VIP));

        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new FixDiscountPolicy());
        Order order = orderService.createOrder(1L,"item",10000);
        assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}
