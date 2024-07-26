package msgreceiver.consumer;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class ActiveMqConsumer {
    public void objConsuming() throws Exception {
        try (
                // Create Camel context
                CamelContext context = new DefaultCamelContext()) {
            // Add route to Camel context
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() {
                    from("activemq:queue:testQueueCamelin");
                    //  .to("seda:end"); 
                }
            });

            // Start the context
            context.start();

        //    ConsumerTemplate consumerTemplate = context.createConsumerTemplate();

            // String message = consumerTemplate.receiveBody("activemq:queue:testQueueCamel",
            //         String.class);
            // System.out.println("___________________" + message);

            // Stop the context
            context.stop();
        }
    }
}
