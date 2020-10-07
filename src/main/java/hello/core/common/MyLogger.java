package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
@Scope(value="request")
public class MyLogger {

    /**     이렇게 만들어두면 http요청마다 새로운 request scope 빈을 만들고 요청이 끝날 때 사라진다
     *      그래서 수많은 요청을 구분하기위해 개별마다 uuid부여 */
    private String uuid;
    private String requestURL;


    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("["+uuid+"]"+"["+requestURL+"] "+ message);
    }
    @PostConstruct
    public void init()
    {
        uuid = UUID.randomUUID().toString();
        System.out.println("["+uuid+"] request scope bean create: "+this);
    }

    @PreDestroy
    public void close()
    {
        System.out.println("");
        System.out.println("["+uuid+"] request scope bean destroy: "+this);
    }
}
