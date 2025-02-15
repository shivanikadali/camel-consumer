package com.example.consumerapp.example;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MsgReceiver extends RouteBuilder {
     private final Logger LOGGER = LoggerFactory.getLogger(MsgReceiver.class);

     @Override
     public void configure() throws Exception {
          from("activemq:queue:testQueue")
                    .log("log:receiving messages=> ${body}")
                    .process(exchange -> {
                         String message = exchange.getIn().getBody(String.class);
                         // Process the message
                         LOGGER.info("Processing message: " + message);
                    })
                    .to("mock:consume");
     }
}
