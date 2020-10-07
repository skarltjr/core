package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonTest {

    @Test
    @DisplayName("스프링없는 순수한 DI컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();
        //1. 조회 . 호출할 떄 마다 객체를 생성하는지
        MemberService memberService = appConfig.memberService();

        //2. 조회 . 호출할 떄 마다 객체를 생성하는지
        MemberService memberService2 = appConfig.memberService();

        //참조값이 다른지 확인
        System.out.println(memberService);
        System.out.println(memberService2);

        /**     2개의 서로다른 객체가 생성됨응 확인할 수 있다 =  매 요청마다 새로운 객체를 만들고
         * 메모리에 저장 = 안좋다는 것 */
        assertThat(memberService).isNotSameAs(memberService2);
    }

    /**
     * 위 문제점을 해결하는 가장 좋은 방법은 해당하는 객체 여기선 MemberService와 그 아래 객체들을
     * 딱 한번만 생성하고 그 이후의 요청들에게 이 객체를 공유하는 싱글톤 !!
     * <p>
     * singletonService test
     */

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체사용")
    void singletonServiceTest() {
        // new SingletonService() 가 당연히 막혀있도록 private constructor
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        assertThat(singletonService1).isSameAs(singletonService2);
        //same   == 비교
        //equal  자바에서 equals 오버라이드하는 그 비교
    }

    @Test
    @DisplayName("스프링컨테이너와 싱글톤")
    void springContainer()
    {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        //참조값이 같은지 확인
        System.out.println(memberService);
        System.out.println(memberService2);

        assertThat(memberService).isSameAs(memberService2);
    }
}


