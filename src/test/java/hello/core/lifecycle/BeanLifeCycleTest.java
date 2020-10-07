package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    void lifeCycleTest()
    {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(lifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }

    @Configuration
    static class lifeCycleConfig
    {
        @Bean
        public NetworkClient networkClient()
        {
            NetworkClient networkClient = new NetworkClient();
            /** 스프링은 객체를 생성한 다음에 의존성을 주입한다 그 다음부터 데이터를 사용할 수 있는 준비가 된다 .
             * 여기서도 NetworkClient가 객체를 생성할 때 connect를 하고
             * 생성한 다음에 seturl이라 이대로 실행해보면 url null로 출력된다 */
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
        /** 그러면 데이터사용을 위해선 개발자가 의존관계주입이 완료되었는지 알아야하는데 이를 위해 스프링이 의존관계 주입이
         * 완료되면 스프링 빈한테 콜백매서드를 날려준다*/

        //스프링 빈의 라이프사이클
        //스프링컨테이너 생성 -> 스프링 빈 생성 -> 의존관계주입 -> 초기화 콜백 -> 사용 -> 소멸전 콜백 -> 스프링 종료

        //★★ 외부 네트워크 컨넥션같은 무거운 일은 생성자로 처리하는게 아니라 객체의 생성과 초기화를 분리하는게 좋다(setter).
        //그럼 위에서처럼 networkclient라는 객체를 미리 만들어두고 연결은 미뤄둘 수도있고 등등 유지보수에 더 좋다
    }
}
