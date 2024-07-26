package msgreceiver.newexample;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
// CallMethodUsingClassComponent
public class CallMethodUsingClassComponent {
    public void sendingMessage() throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {

                from("direct:start")
                        // MyService.method is printing
                        .to("class:msgreceiver.newexample.MyService?method=doSomething");
            }
        });

        // Start the context
        context.start();

        // Send a message to 'from endpoint'
        context.createProducerTemplate().sendBody("direct:start",
                "Hello, this is a test message with Camel!---------------");

        // Stop the context
        context.stop();
    }
}
