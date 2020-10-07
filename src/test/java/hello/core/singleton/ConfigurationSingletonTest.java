package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest()
    {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        //원래는 구체타입으로 꺼내면안되지만 테스트용 함수를 구체타입에 만들어놔서 test할때만 잠깐

        MemberRepository memberRepository = memberService.getMemberRepository();
        MemberRepository memberRepository1 = orderService.getMemberRepository();

        MemberRepository memberRepositoryReal = ac.getBean("memberRepository", MemberRepository.class);

        System.out.println(memberRepository);
        System.out.println(memberRepository1);
        System.out.println(memberRepositoryReal);

        assertThat(memberService.getMemberRepository()).isEqualTo(memberRepositoryReal);
        assertThat(orderService.getMemberRepository()).isEqualTo(memberRepositoryReal);
    }

    @Test
    void configurationDepp()
    {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean =" + bean.getClass());
        /**     ~~~CGLIB이라고 나오는데
         * ★ 결국 스프링이 싱글톤 보장을 위해 진짜 Appconfig를 상속받은 클래스를 만들고 이것을
         * 빈에 등록. ->  싱글톤보장방법*/
    }
}
