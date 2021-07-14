package spring.basicproject.order;

import spring.basicproject.discount.DiscountPolicy;
import spring.basicproject.discount.FixDiscountPolicy;
import spring.basicproject.discount.RateDiscountPolicy;
import spring.basicproject.member.Member;
import spring.basicproject.member.MemberRepository;
import spring.basicproject.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{

    /* Q. 왜 MemberService 인터페이스를 통해 접근하지 않고, 직접 Memory Member Repository를 활용하여 멤버와 관련된 서비스를 수행할 까?
     *
     * A : MemberService만 Repository에 접근하도록 제약할 이유가 없기 때문,, 하지만 시스템이 너무 커져 별도의 패키지나 컴포넌트로 분리하여 복잡해진다면 MemberService를 통해 접근하도록
     *     강제하는것이 더 나을 수도 있다.
     */
    private final MemberRepository memberRepository = new MemoryMemberRepository();

    /* 할인 정책을 변경하려면 클라이언트인 OrderServiceImpl 코드를 고쳐야함 -> OCP 위반
     *  OrderServiceImpl은 DiscountPolicy 인터페이스에 의존하며 DIP를 지킨것 같지만, 구현 클래스(Rate,Fix discount policy)에 의존하고 있다.
     */
    private DiscountPolicy discountPolicy;
    //private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        /* 단일 책임 원칙을 잘 지킨 구현 : discount policy등 캡슐화가 잘 되어있음 */
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
