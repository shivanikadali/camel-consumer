package example.camel_microservice.routes.a;

import java.time.LocalDateTime;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyFirstTimerRouter extends RouteBuilder {

    @Autowired
    private GetCurrentTimeBean getCurrentTimeBean;

    @Override
    public void configure() throws Exception {
        // Object LocalDateTime;
        // 2 endpoints 1 is from queue present timer
        // timer is a keywords ,first-timer is a name we are giving
        // Started route1 (timer://first-timer)
        from("timer:first-timer?repeatCount=1")
                .log("${body}")
                // timer,log are
                // to create the bean
                // .bean("getCurrentTimeBean")
                // .bean(getCurrentTimeBean) class with 1 method no need to mention method name
                .bean(getCurrentTimeBean, "getCurrentTime")
                .log("${body}")
                .to("log:first-timer").log("${body}");
    }
}

@Component
class GetCurrentTimeBean {

    public String getCurrentTime() {
        return "This now is +" + LocalDateTime.now();
    }
}