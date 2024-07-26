package msg.sending.producer;

import java.util.Date;

import javax.jms.ConnectionFactory;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class ProducerSendingDateObj {
    public void sendingObjToQueue() throws Exception {

        try (
                CamelContext context = new DefaultCamelContext()) {
                  ConnectionFactory connectionFactory=new ActiveMQConnectionFactory();
                  context.addComponent("jms",JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
                    context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {

                    from("direct:start")
                            // from endpoint sends msg to the 'to endpoint'
                            .to("activemq:queue:testQueueCamel")
                            .log("Message sent to testQueue: ${body}");
                }
            });

            // Start the context
            context.start();

            // Send a message to 'from endpoint'
            context.createProducerTemplate().sendBody("direct:start", new Date());

            // Stop the context
            context.stop();
        }
    }
}
