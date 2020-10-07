package hello.core.discount;

import hello.core.member.Member;

//할인정책이라는 틀이 이 인터페이스고   -  거기에 맞춰 구현한것들이 정액, 정률 할인정책 class 즉. 언제든지 갈아끼울 수 있다
public interface DiscountPolicy {

    //얼마가 할인되는지 리턴
    int discount(Member member,int price);
}
