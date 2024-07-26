package msgreceiver.newexample;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.SimpleRegistry;

public class CallMethodUsingBeanComponent {
    public void sendingMessage() throws Exception {
        MyService myService=new MyService();
        SimpleRegistry registry=new SimpleRegistry();
        registry.bind("myService", myService);
        

        CamelContext context = new DefaultCamelContext(registry);
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {

                from("direct:start")
                        // MyService.method is printing  using bean
                        .to("bean:myService?method=doSomething");
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
