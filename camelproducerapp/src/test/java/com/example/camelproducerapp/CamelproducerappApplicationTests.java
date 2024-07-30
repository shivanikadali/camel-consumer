package com.example.camelproducerapp;

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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// This annotation activates the "test" profile.
// Profiles in Spring are used to segregate parts of your application
// configuration and make it only available in certain environments.
@ActiveProfiles("test")
@Testcontainers
class CamelproducerappApplicationTests {
	private static Logger logger = LoggerFactory.getLogger(CamelproducerappApplicationTests.class);

	@Autowired
	private ProducerTemplate producerTemplate;

	@Autowired
	private CamelContext camelContext;

	@SuppressWarnings("resource")
	// The container is started before the tests and stopped afterwards.
	@Container
	// GenericContainer is a generic Testcontainers class that allows you to run any
	// Docker container.
	private static final GenericContainer<?> activemqContainer = new GenericContainer<>(
			DockerImageName.parse("rmohr/activemq:latest"))
			.withExposedPorts(61616)
			.waitingFor(Wait.forListeningPort());

	@BeforeEach
	void setUp() {
		MockEndpoint mockEndpoint = camelContext.getEndpoint("mock:testQueue", MockEndpoint.class);
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
	void testMessageRoute() throws InterruptedException {
		logger.info("start method ----------------------");
		MockEndpoint mockEndpoint = camelContext.getEndpoint("mock:testQueue", MockEndpoint.class);
		// sets the expectation that the mockEndpoint will receive one message.
		mockEndpoint.expectedMessageCount(1);
		// This sets the expectation that the body of the first message received by the
		// mockEndpoint will be "Hello, this is a test message with Camel!".
		mockEndpoint.message(0).body().isEqualTo("Hello, this is a test message with Camel!");

		producerTemplate.sendBody("direct:start", "Hello, this is a test message with Camel!");

		mockEndpoint.assertIsSatisfied();
	}
}