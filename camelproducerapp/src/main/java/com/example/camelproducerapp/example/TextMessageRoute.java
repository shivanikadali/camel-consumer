package com.example.camelproducerapp.example;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class TextMessageRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:start")
                .to("activemq:queue:testQueue")
                .to("mock:testQueue")
                .log("Message sent to testQueue: ${body}");
    }
    
}
