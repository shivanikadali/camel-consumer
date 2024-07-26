package com.example.consumerapp.example;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class TextMessageRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:start")
                .to("activemq:queue:testQueue")
                .log("Message sent to testQueue: ${body}");
    }
}
