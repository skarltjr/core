package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
//@MainDiscountPolicy   직접 만든 어노테이션으로 + 이 rate~를 가져다 사용하는 곳에서도 추가록 붙여준다 어노테이션
public class RateDiscountPolicy implements DiscountPolicy {

    private int discountPercent=10;


    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return price * discountPercent/100;
        }
        else{
            return 0;
        }
    }
}
