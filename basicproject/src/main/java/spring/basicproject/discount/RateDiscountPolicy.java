package spring.basicproject.discount;

import org.springframework.stereotype.Component;
import spring.basicproject.annotation.MainDiscountPolicy;
import spring.basicproject.member.Grade;
import spring.basicproject.member.Member;

@Component
@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy {

    private int discountPercent = 10;


    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return price * discountPercent / 100;
        } else {
            return 0;
        }
    }
}
