package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton()
    {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //ThreadA : A사용자가 10000원을 주문
        //statefulService1.order("userA",10000); 문제 !!!
        int priceOfA = statefulService1.order("userA", 10000);

        //ThreadB : B사용자가 2만원을 주문
       // statefulService2.order("userB", 20000);  문제 !!!
        int priceOfB = statefulService2.order("userB", 20000);


        //  ThreadA : A가 주문금액조회
        //int price = statefulService1.getPrice();

        //System.out.println(price);  //당연히 2만원이나온다  기대값은 1만원인데
        //statefulService1  statefulService2    -> 1,2 둘 다 결국 하나의 싱글톤 statefulService

       // assertThat(statefulService1.getPrice()).isEqualTo(20000);

        /**     이러면 이제 a 금액이 1만원 그대로*/
        assertThat(priceOfA).isEqualTo(10000);
    }

    static class TestConfig
    {
        @Bean
        public StatefulService statefulService()
        {
            return new StatefulService();
        }
    }

}