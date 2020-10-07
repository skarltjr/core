package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
   // private final MyLogger myLogger;
    /** 스프링이 뜰 때 의존성주입을 해줘야하는데 mylogger는 request스코프 즉 요청이 없는 상태라 없는거다
     *   그럼 당연히 생성되어있지 않은것을 주입하려니 오류 이 때사용할 수 있는게 provider*/
    private final ObjectProvider<MyLogger> myLoggerProvider;
    //여기서 MyLogger를 주입받는것이아니라 필요할 때 Lookup 찾을 수 있도록 해줌


    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        MyLogger myLogger = myLoggerProvider.getObject(); // 그래서 요청이 왔을 때 = 필요할 때 get으로 꺼내사용
        String requestURL = request.getRequestURL().toString();
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }
}
