package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration   /**        ->  @Configuration에 싱글톤보장 비밀이있다  없어도 빈 등록은 가능하지만
                                중요한 싱글톤보장이 안된다 !!! */
public class AppConfig {

    //@Bean = 싱글톤 근데
    //@Bean memberService -> new MemoryMemberRepository()
    //@Bean orderService -> new MemoryMemberRepository() ,~
    //여기서만해도 2번인데 이러면 싱글톤이 깨지는거아닌가??




    // 매서드 뽑아냈다  - > 역할이 다 뚜렷하게 구분
    @Bean
    public MemberRepository memberRepository()
    {
        System.out.println("AppConfig.memberRepository");
        return new MemoryMemberRepository();  //그럼 여기만 갈아끼우면 된다
    }
    @Bean
    public DiscountPolicy discountPolicy()
    {
        System.out.println("AppConfig.discountPolicy");
        //return new FixDiscountPoilicy();
        return new RateDiscountPolicy();
    }

    /**     실제 MemberServiceImpl에서는 추상화에 의존하고 생성자를 통해 의존성 주입하는데
     * 그걸 위해 다른곳 . AppConfig에서 설정 */

    @Bean
    public MemberService memberService()
    {
        System.out.println("AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    /**     즉 OrderServiceImpl가보면 생성자에 MemberRepository, DiscountPolicy설정되어있고 추상화에 의존하고있다
     *      그럼 여기서 Fix -> Rate로만 바꿔주면 Impl은 추상화에만 의존함을 지키되 우리는 갈아끼울 수 있다. */
    @Bean
    public OrderService orderService()
    {
        System.out.println("AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(),discountPolicy());
    }
    /**  이러면 결과적으로 MemberServiceImpl , OrderServiceImpl은 추상화에만 의존할 수 있고
     * 객체의 생성과 연결은 AppConfig가 담당해준다. DIP*/
}
