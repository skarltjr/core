package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {

       // AppConfig appConfig =new AppConfig();
        //MemberService memberService = appConfig.memberService();
        //그럼 여기안에는 memberserviceImpl이 들어가있고/ memberservice클릭해서 따라가보기
        //MemberService memberService=new MemberServiceImpl();

        /** ★    스프링을 이용하면 이제 */
        ApplicationContext applicationContext=new AnnotationConfigApplicationContext(AppConfig.class);
                // Apppconfig에 bean된 애들 다 스프링컨테이너에 집어넣서 관리해준다
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        // public MemberService memberService() 의 memberService라는  이름 " "으로  / 타입

        Member memberA = new Member(1L, "memberA", Grade.VIP);
        memberService.join(memberA);

        Member findMember = memberService.findMember(memberA.getId());
        System.out.println(findMember.getName());
    }
}
