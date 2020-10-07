package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// @RequiredArgsConstructor 롬복은 쓰던대로 여기서는 공부를 위해basic
@Component  //@Service
public class OrderServiceImpl implements OrderService {


   // private final DiscountPolicy discountPolicy=new FixDiscountPoilicy();
   // private final DiscountPolicy discountPolicy = new RateDiscountPoilicy();
    /**변경한 시점부터 이미 OCP 위반
     *  DIP가 깨졌다  = 추상인터페이스 DiscountPolicy뿐만아니라 구체화된 클래스에도 의존한다
     *  그럼 어떻게 해야하느냐? 추상화에만 의존하기위해 아래처럼
     *  */
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy; //이대로하면 당연히 nullpointerexception터지지.

    /** 당연히  자동주입에는 생성자,setter 필드 등 방법이 다양하지만 생성자주입을 사용하면  불변. final로 누락도 피할 수
     * 다양한 오류를 컴파일오류로 잡아낼 수 있다 그래서 가능하면 생성자주입 + 필요시 옵션처리 */

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository,/**@MainDiscountPolicy*/ DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
    /** OrderServiceImpl이나 MemberServiceImpl입장에서는 당연히 생성자에 어떤것이 주입될지 전혀모른다 */
    /**     autowired는 기본적으로 빈을 타입으로 조회 ac.getBean(~.class) 그럼 당연히 동일타입에 빈이 여러개있으면?
     *      1)  ex) 할인정책이 fix , rate 두개 다  component로 빈에 올려놓은 경우 타입비교 후 필드나 생성자 파라미터 이름으로
     *      가져온다 그래서 여기서 discountPolicy -> rateDiscountPolicy로 바꾸면 정률할인을 빈에서 가져온다
     *      2) @Qualifier 어노테이션
     *      3) @Primary 어노테이션 -> 중복타입있을 때 무조건 RateDiscount적용하고싶으면 rate~에 @Primary 추가
     *          그래서 자주사용하는걸 primary로 두고 가끔 사용하는걸 Qualifier 로 사용한다 많이
     *      */


    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discount = discountPolicy.discount(member, itemPrice);
        /** 지금 오더서비스입장에서 할인에 대해 알 필요없이 그냥 파라미터 던져주고 알아서 해결한 값 받아오는 좋은 설계*/

        return new Order(member.getId(), itemName, itemPrice, discount);
    }

    //test용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

}
