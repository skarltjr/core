package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

public class PrototypeTest {

    /**     기본적으로 싱글톤을 사용하고 특별한 경우에 추가적으로 프로토타입빈을 사용하는데 그럼 이 둘을 동시에 사용하게된다
     *      그럼 당연히 큰 문제가 있는데 싱글톤과 함께 사용하게되면 예상과 다르게 반환 후 스프링 컨테이너에서 더 이상
     *      관리를 하지 않게되는 프로토타입 빈이 남아있는 경우. */

    @Test
    void prototypeBeanFind()
    {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        System.out.println("find prototypeBean1");
        PrototypeBean bean1 = ac.getBean(PrototypeBean.class);
        //find prototypeBean1
        //PrototypeBean.init 까지만 나오고 destroy는 없다 즉 프로토타입은 생성이후 사용자에게 전달해주고
        //더 이상 관리를 하지 않는다 싱글톤과 다르게

        System.out.println("find prototypeBean2");
        PrototypeBean bean2 = ac.getBean(PrototypeBean.class);

        System.out.println("bean1 = " + bean1);
        System.out.println("bean2 = " + bean2);

        assertThat(bean1).isNotSameAs(bean2);

        //그래서 사용자가 직접 destroy를 해야하는 경우라면
        bean1.destroy();


        ac.close();
    }

    @Scope("prototype")
    //@Component -> 가 없는 이유는 AnnotationConfigApplicationContext(PrototypeBean.class); 파라미터로 넣으면
    // 똑같이 작동한다
    static class PrototypeBean{
        @PostConstruct
        public void init()
        {
            System.out.println("PrototypeBean.init");
        }
        @PreDestroy
        public void destroy()
        {
            System.out.println("PrototypeBean.destroy");
        }

    }
}
