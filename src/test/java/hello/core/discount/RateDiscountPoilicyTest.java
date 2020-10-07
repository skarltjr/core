package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RateDiscountPoilicyTest {
    RateDiscountPolicy rateDiscountPoilicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP는 10%할인이 적용되어야한다")
    void vip_o() {
        //given
        Member member = new Member(1L, "memberVIP", Grade.VIP);
        //when
        int discount = rateDiscountPoilicy.discount(member, 10000);
        //then
        assertThat(discount).isEqualTo(1000);

    }
    //실패테스트도 중요
    @Test
    @DisplayName("VIP가 아니면 할인적용안된다")
    void vip_x()
    {
        //given
        Member member = new Member(2L, "memberBasic", Grade.BASIC);
        //when
        int discount = rateDiscountPoilicy.discount(member, 10000);
        //then
        assertThat(discount).isEqualTo(0);
    }
}