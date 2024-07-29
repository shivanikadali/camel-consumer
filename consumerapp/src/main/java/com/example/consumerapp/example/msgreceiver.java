package com.example.consumerapp.example;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class msgreceiver extends RouteBuilder {
     @Override
     public void configure() throws Exception {
          from("activemq:queue:testQueue")
                    .log("log:receiving messages=> ${body}");
     }
}
