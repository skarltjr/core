package hello.core.lifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient {
    
    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출 ,url =" + url);
//        connect();
//        call("초기화 연결 메세지");
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //서비스 시작시 호출
    public void connect()
    {
        System.out.println("connect " + url);
    }
    public void call(String message)
    {
        System.out.println("call: "+url+" message= "+message);
    }

    //서비스 종료시 호출
    public void disconnect()
    {
        System.out.println("close "+url);
    }

    @PostConstruct//어노테이션 하나로 의존관계 주입이 완료되었음을
    public void init() {
        connect();
        call("초기화 연결 메세지");

        //의존관계 주입이 끝난 후 call할 것이고
    }
    @PreDestroy  //어노테이션하나로 끝낸다
    public void close(){
        disconnect();
        //bean이 종료될 때
    }
    //어노테이션으로는 간편하지만 한가지 단점은 외부라이브러리 bean에는 해당 x 이런경우에는 @Bean(initmethod=,desto~)사용
}
