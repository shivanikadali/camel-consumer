package msg.sending;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class Producer {
    // try with Resources
    public static void main(String[] args) throws Exception {
        try (
                // Create Camel context
                CamelContext context = new DefaultCamelContext()) {
            // Add route to Camel context
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() {
                    // Define route
                    // direct is the inbuilt component in the camel
                    // from endpoint receives the msg
                    from("direct:start")
                            // from endpoint sends msg to the to endpoint
                            .to("activemq:queue:testQueue");
                            // .log("Message sent to testQueue: ${body}");
                }
            });

            // Start the context
            context.start();

            // Send a message to 'from endpoint'
            context.createProducerTemplate().sendBody("direct:start", "Hello, this is a test message with Camel!");

            // Reveive the message from 'to endpoint'
            // what type of object we are expecting
            String message = context.createConsumerTemplate().receiveBody("activemq:queue:testQueue", String.class);
            System.out.println("___________________"+message);

            // Stop the context
            context.stop();
        }
    }
}