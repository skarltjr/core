package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean bean1 = ac.getBean(PrototypeBean.class);
        bean1.addcount();
        assertThat(bean1.getCount()).isEqualTo(1);

        PrototypeBean bean2 = ac.getBean(PrototypeBean.class);
        bean2.addcount();
        assertThat(bean2.getCount()).isEqualTo(1);

    }

    @Test
    void singletonClientUsePrototype()
    {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class,PrototypeBean.class);
        /** 싱글톤 빈 안에 프로토타입빈이 있을 때 사용자가 프로토타입 빈의 getCount를 할 때마다 새로운 프로토타입빈이
         * 만들어져서 반환될까? -> x 그래서 addCount를 n번 하면 count가 n이 된다. 의도와 다르다 */
        ClientBean bean1 = ac.getBean(ClientBean.class);
        int count1 = bean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean bean2 = ac.getBean(ClientBean.class);
        int count2 = bean2.logic();
        //assertThat(count2).isEqualTo(2); // 2가된다
        assertThat(count2).isEqualTo(1);// provider를 사용했으니 제대로 작동하면 매번 새로운 1
    }

    @Scope("singleton") // default가 싱글톤이라 굳이 안해줘도된다.
    static class ClientBean{
        //private final PrototypeBean prototypeBean;

        private ObjectProvider<PrototypeBean> prototypeBeansProvider;
        /** 싱글톤속에서 매 번 새로운 프로토타입을 받아올 수 있는 Provider*/
        @Autowired
        public ClientBean(ObjectProvider<PrototypeBean> prototypeBeansProvider) {
            this.prototypeBeansProvider = prototypeBeansProvider;
        }

        public int logic()
        {
            PrototypeBean prototypeBean = prototypeBeansProvider.getObject();
            //이 때 getObject로 가져올 때  스프링 컨테이너한테 요청해서 새로운 프로토타입 빈을 받는다
            prototypeBean.addcount();
            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class PrototypeBean{
        private int count =0;

        public void addcount()
        {
            count++;
        }

        public int getCount()
        {
            return count;
        }

        @PostConstruct
        public void init()
        {
            System.out.println("PrototypeBean.init"+ this);
        }
        @PreDestroy
        public void destroy()
        {
            System.out.println("PrototypeBean.destroy");
        }
    }
}

//그러나 프로토타입은 거의 쓸 일이없지만 알아는 둬야한다.