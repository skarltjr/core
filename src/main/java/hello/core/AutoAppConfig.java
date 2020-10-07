package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        //basePackages = "hello.core",    //만약 hello.core.member이면 member 부터, 즉 멤버 하위에서만 찾는다
        excludeFilters = @ComponentScan.Filter(type= FilterType.ANNOTATION,classes = Configuration.class)
)/**    컴포넌트스캔이 컴포넌트 어노테이션이 붙은것들을 자동으로 스프링빈 등록해준다. exclude는 공부용으로 남겨둔 AppConfig 제외하기위해 + test에서 쓴것도 제외*/
public class AutoAppConfig {
    //basepackage가 디폴트로는 이 AutoAppConfig 의 맨 위에있는 저 패키지 여기서는 hello.core 가 디폴트.


    /**     근데 !! 스프링부트를 쓰면 처음 만들어져있는 . 여기서는 CoreApplication에 SpringBootApplication에 이미
     *       ComponentScan 되어있다 */


    // @Component하면 ex. MemberRepository 하면 memberRepository로 소문자로 이름잡히는데  만약
    // 자동 빈, 수동 빈등이 겹치는 경우엔 중복?? -> 빈도 오버라이딩이 된다 알아서
    // 일단 수동 빈이 우선  -> 최근에는 이런경우가 보통 실수이기때문에 스프링부트는 오류로 잡는다. 

}
