package hello.core.member;

import hello.core.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

    /*MemberService memberService=new MemberServiceImpl();*/
   //test에선 appConfig바로 꺼내기가 애매
    MemberService memberService;
    @BeforeEach
    public void beforeEach()
    {
        AppConfig appConfig=new AppConfig();
        memberService=appConfig.memberService();
        //요렇게 사용
    }

    @Test
    void join()
    {
        //given
        Member memberA = new Member(1L, "memberA", Grade.VIP);

        //when
        memberService.join(memberA);
        Member findMember = memberService.findMember(memberA.getId());
        //then
        Assertions.assertThat(memberA).isEqualTo(findMember);

    }
}
