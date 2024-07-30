package com.example.consumerapp;

import static org.junit.Assert.assertNotNull;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
public class CamelRouteTest {

    private static Logger logger = LoggerFactory.getLogger(CamelRouteTest.class);

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
// private static Logger logger = LoggerFactory.getLogger(CamelRouteTest.class);

// @Autowired
// private ProducerTemplate producerTemplate;

// @Autowired
// private CamelContext camelContext;

// private static final Network network = Network.newNetwork();

// @BeforeEach
// void setUp() {
// logger.info("starting .................");
// assertNotNull(camelContext, "CamelContext should not be null");
// MockEndpoint mockEndpoint = camelContext.getEndpoint("mock:consume",
// MockEndpoint.class);
// mockEndpoint.reset();
// logger.info("ending .................");

// }

// @BeforeAll
// static void setUpClass() {
// activemqContainer.start();
// System.setProperty("spring.activemq.broker-url",
// "tcp://" + activemqContainer.getHost() + ":" +
// activemqContainer.getMappedPort(61616));
// System.setProperty("spring.activemq.user", "admin");
// System.setProperty("spring.activemq.password", "admin");
// }

// @SuppressWarnings("resource")
// // The container is started before the tests and stopped afterwards.
// @Container
// // GenericContainer is a generic Testcontainers class that allows you to run
// any
// // Docker container.
// private static final GenericContainer<?> activemqContainer = new
// GenericContainer<>(
// DockerImageName.parse("rmohr/activemq:latest"))
// .withNetwork(network)
// .withExposedPorts(61616)
// .waitingFor(Wait.forListeningPort());

// @Test
// public void testMessageRoute() throws InterruptedException {
// logger.info(camelContext.getStatus() + "0000000000000");

// camelContext.start();
// MockEndpoint mockEndpoint2 = camelContext.getEndpoint("mock:consume",
// MockEndpoint.class);
// mockEndpoint2.expectedMessageCount(1);
// String actual = "Hello, this is a test message with Camel!";
// mockEndpoint2.message(0).body().isEqualTo(actual);

// producerTemplate.sendBody("direct:start", "Hello, this is a test message with
// Camel!");

// String msg =
// mockEndpoint2.getExchanges().get(0).getIn().getBody(String.class);

// assertEquals(msg, actual);

// mockEndpoint2.assertIsSatisfied();
// }
// }

// private static final Logger logger =
// LoggerFactory.getLogger(CamelRouteTest.class);

// @Autowired
// private ProducerTemplate producerTemplate;

// @Autowired
// private CamelContext camelContext;

// @SuppressWarnings("resource")
// @Container
// private static final GenericContainer<?> activemqContainer = new
// GenericContainer<>(
// DockerImageName.parse("rmohr/activemq:latest"))
// .withExposedPorts(61616)
// .waitingFor(Wait.forListeningPort());

// @BeforeEach
// void setUp() {
// // Check if CamelContext is not null before accessing it
// if (camelContext == null) {
// throw new RuntimeException("CamelContext is not initialized.");
// }

// MockEndpoint mockEndpoint = camelContext.getEndpoint("mock:consume",
// MockEndpoint.class);
// mockEndpoint.reset();
// }

// @BeforeAll
// static void setUpClass() {
// activemqContainer.start();
// System.setProperty("spring.activemq.broker-url",
// "tcp://" + activemqContainer.getHost() + ":" +
// activemqContainer.getMappedPort(61616));
// System.setProperty("spring.activemq.user", "admin");
// System.setProperty("spring.activemq.password", "admin");
// }

// @Test
// public void testMessageRoute() throws InterruptedException {
// logger.info("start method ----------------------");

// MockEndpoint mockEndpoint = camelContext.getEndpoint("mock:consume",
// MockEndpoint.class);

// // Set the expectation for the mock endpoint
// mockEndpoint.expectedMessageCount(1);
// mockEndpoint.message(0).body().isEqualTo("Hello, this is a test message with
// Camel!");

// // Send a message to the activemq queue
// producerTemplate.sendBody("activemq:queue:testQueue", "Hello, this is a test
// message with Camel!");

// // Verify that the mock endpoint received the message as expected
// mockEndpoint.assertIsSatisfied();
// }
// }
// @TestConfiguration
// class configforBean {
// @Bean
// public CamelContext getContext() {
// return new CamelContext()
// }
// }

// }
