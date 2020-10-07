package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component// 당연히 service에도 컴포넌트 포함
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    /**만약 다른 레퍼지토리로 갈아끼울 때 ocp가 지켜지나? 어쨋든 이 윗줄을 주석처리로 지워야한다
     * 그럼 DIP는 지켜지나? -> memorymemberrepository는 어쨌든 구현체. 인터페이스에 의존 x = 안지켜진다
     * 멤버서비스impl이 MemberRepository 추상화에도 의존하고 MemoryMemberRepository구체화에도 의존한다
     * */

    //생성자를 통해  . 지금 여기선 추상화에서만 의존 AppConfig에서 설정
    @Autowired // 자동의존관계 주입  == jpa 하면서 항상 쓰던 그대로 생성자주입으로
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    //test용
    public MemberRepository getMemberRepository()
    {
        return memberRepository;
    }
}
