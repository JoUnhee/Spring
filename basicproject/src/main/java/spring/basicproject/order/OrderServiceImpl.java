package spring.basicproject.order;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.stereotype.Component;
import spring.basicproject.discount.DiscountPolicy;
import spring.basicproject.member.Member;
import spring.basicproject.member.MemberRepository;

@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        /* 단일 책임 원칙을 잘 지킨 구현 : discount policy등 캡슐화가 잘 되어있음 */
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    /* To check how spring support singleton and the mechanism of @Configuration annotaion */
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
