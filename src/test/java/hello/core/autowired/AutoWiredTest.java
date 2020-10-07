package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutoWiredTest {
    
    @Test
    void AutowiredOption()
    {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
        //스프링 빈에 올려놓고
    }

    static class TestBean {

        /**     Member는 스프링이 관리하는게 아니다 멤버는 자바코드 그 자체.
         *      spring이 관리하지 않는 것을 주입해야할 때 방법 */

        @Autowired(required = false)//디폴트가 true = 기본으로는 autowired할려면 무조건 빈에 등록되어있는것을 사용해야한다
        public void setNoBean1(Member member) { //      즉 여기서는 디폴트였다면 멤버가 스프링이 관리하는것이 아니기때문에 오류터진다
            System.out.println(member); //null
            //그리고 false면 자동 주입할 대상 (여기서는 멤버)가 없으면(스프링 빈에) 아예 매서드호출이 실행 x
        }

        @Autowired
        public void setNoBean2(@Nullable Member member) {
            System.out.println(member);//Optional.empty
        }

        @Autowired
        public void setNoBean3(Optional<Member> member) {
            System.out.println(member);
        }

    }
}
