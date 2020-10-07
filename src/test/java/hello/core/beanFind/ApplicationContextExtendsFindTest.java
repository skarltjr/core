package hello.core.beanFind;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextExtendsFindTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

    /**
     * 스프링에서 부모 빈을 찾아오면 자식들이 다 딸려온다는 것을 알아야한다.
     * 최상위 Object를 가져오면 모든 빈을 가져온다
     */

    @Configuration
    static class TestConfig {
        @Bean
        public DiscountPolicy rateDiscountPolicy() {
            return new RateDiscountPolicy();
        }

        @Bean
        public DiscountPolicy fixDiscountPolicy() {
            return new FixDiscountPolicy();
        }
    }


    @Test
    @DisplayName("부모 타입으로 조회시 자식이 2 이상있으면 중복오류발생")
    void findBeanByParentTypeDuplicate() {
        DiscountPolicy beans = ac.getBean(DiscountPolicy.class);
        // DiscountPolicy아래 2개의 자식이 이미 존재한다. 그래서 중복오류발생
        assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(DiscountPolicy.class));
    }

    @Test
    @DisplayName("부모 타입으로 조회시 자식이 2 이상있으면 중복오류발생-> 당연히 빈 이름지정해주면된다")
    void findBeanByParentTypeBeanName() {
        DiscountPolicy rateDiscountPolicy = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
        //이걸 만약DiscountPolicy 이 아닌 rateDis~로 지정하면 그건 구체화에 의존하는것 / 추상화에 의존해야한다
        assertThat(rateDiscountPolicy).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("부모타입으로 모두 조회")
    void findAllBeanByParentType() {
        Map<String, DiscountPolicy> beans = ac.getBeansOfType(DiscountPolicy.class);
        assertThat(beans.size()).isEqualTo(2);
        for (String key : beans.keySet()) {
            System.out.println(key + " --- " + beans.get(key));
        }
    }

    @Test
    @DisplayName("부모타입으로 모두조회하기")
    void findAllBeanByObject()
    {
        Map<String, Object> beans = ac.getBeansOfType(Object.class);
        for (String key : beans.keySet()) {
            System.out.println(key + " --- " + beans.get(key));
        }

        // Object는 최상위 - > 하위  스프링관련 빈까지 다 출력
    }
}
