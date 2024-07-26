
package msg.sending.producer;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class ProducerProcess {
    // try with Resources
    public void modifyAndSendMsgForConsuming() throws Exception {

        try (// Create Camel context
        CamelContext context = new DefaultCamelContext()) {
            // Add route to Camel context
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                   
                    from("direct:start")
                    // processor before the msg sending to consumer i want to do some customization.
                            .process(new Processor() {
                                public void process(Exchange exchange) throws Exception {
                                    System.out.println("I am the processor");
                                    String message= exchange.getIn().getBody(String.class);
                                    message =message+"-by shivani";
                                    exchange.getOut().setBody(message);
                                 }
                            })
                            // from endpoint sends msg to the 'to endpoint'
                            .to("activemq:queue:testQueue");
                            // .to("seda:end");
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