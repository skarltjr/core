package hello.core.member;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Component  // 사실 @Repository안에 컴포넌트 다 있다
public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long,Member> store=new HashMap<>();



    @Override
    public void save(Member member) {
        store.put(member.getId(),member);
        //실제론 오류처리까지 다 해줘야한다 연습용
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
