package msgreceiver.consumer;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class ObjReceiver {

    public void objConsuming() throws Exception {
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
                    from("activemq:queue:testQueue")
                            .log("Message sent to testQueue: ${body}");
                }
                
            });

            // Start the context
            context.start();

            // Reveive the message from 'to endpoint' but no need to write this explicitly
            // at the log it is reading the message
            ConsumerTemplate consumerTemplate = context.createConsumerTemplate();
            // what type of object we are expecting

            String message = consumerTemplate.receiveBody("activemq:queue:testQueue",
                    String.class);
            // System.out.println("___________________" + message);

            // Stop the context
            context.stop();
        }
    }
}
