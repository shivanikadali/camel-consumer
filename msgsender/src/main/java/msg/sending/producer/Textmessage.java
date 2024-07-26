package msg.sending.producer;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class Textmessage {
    public void sendTextMessage() throws Exception {
        try (
                CamelContext context = new DefaultCamelContext()) {
            // Add route to Camel context
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {

                    from("direct:start")
                            .to("activemq:queue:testQueue");
                    // .log("Message sent to testQueue: ${body}");
                }
            });

            // Start the context
            context.start();

            // Send a message to 'from endpoint'
            context.createProducerTemplate().sendBody("direct:start", "Hello, this is a test message with Camel!");

            // Stop the context
            context.stop();
        }
    }
}
