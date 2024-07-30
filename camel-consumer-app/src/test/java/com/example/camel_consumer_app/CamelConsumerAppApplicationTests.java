package com.example.camel_consumer_app;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@ActiveProfiles("test")
@Testcontainers
@SpringBootTest
class CamelConsumerAppApplicationTests {
	
    private static Logger logger = LoggerFactory.getLogger(CamelConsumerAppApplicationTests.class);

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private CamelContext camelContext;

    @SuppressWarnings("resource")
    @Container
    private static final GenericContainer<?> activemqContainer = new GenericContainer<>(
            DockerImageName.parse("rmohr/activemq:latest"))
            .withExposedPorts(61616)
            .waitingFor(Wait.forListeningPort());

    @BeforeEach
    void setUp() {
        MockEndpoint mockEndpoint = camelContext.getEndpoint("mock:consume", MockEndpoint.class);
        mockEndpoint.reset();
    }

    @BeforeAll
    static void setUpClass() {
        activemqContainer.start();
        System.setProperty("spring.activemq.broker-url",
                "tcp://" + activemqContainer.getHost() + ":" + activemqContainer.getMappedPort(61616));
        System.setProperty("spring.activemq.user", "admin");
        System.setProperty("spring.activemq.password", "admin");
    }

    @Test
    public void testMessageRoute() throws InterruptedException {
        assertNotNull(producerTemplate);
        logger.info("start method ----------------------");
        MockEndpoint mockEndpoint = camelContext.getEndpoint("mock:consume", MockEndpoint.class);
        // sets the expectation that the mockEndpoint will receive one message.
        mockEndpoint.expectedMessageCount(1);
        // This sets the expectation that the body of the first message received by the
        // mockEndpoint will be "Hello, this is a test message with Camel!".
        mockEndpoint.message(0).body().isEqualTo("Hello, this is a test message with Camel!");

        producerTemplate.sendBody("activemq:queue:testQueue", "Hello, this is a test message with Camel!");

        mockEndpoint.assertIsSatisfied();
    }
}
