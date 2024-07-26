// // package com.example.consumerapp;

// // import org.apache.camel.EndpointInject;
// // import org.apache.camel.ProducerTemplate;
// // import org.apache.camel.component.mock.MockEndpoint;
// // import org.apache.camel.test.spring.CamelSpringBootRunner;
// // import org.apache.camel.test.spring.MockEndpoints;
// // import org.junit.jupiter.api.Test;
// // import org.junit.runner.RunWith;
// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.boot.test.context.SpringBootTest;

// // @SpringBootTest
// // @RunWith(CamelSpringBootRunner.class)
// // @MockEndpoints("activemq:queue:testQueue")
// // class ConsumerappApplicationTests {

// // 	@EndpointInject(property = "mock:file:output")
// // 	MockEndpoint mock;

// // 	@Autowired
// // 	ProducerTemplate producerTemplate;

// // 	@Test
// // 	public void testMethod() throws InterruptedException {
// //         mock.expectedBodiesReceived("Hello, this is a test message with Camel!");
// // 	    producerTemplate.sendBody("direct:start", null);

// // 		mock.assertIsSatisfied();
// // 	}

// // }
// package com.example.consumerapp;

// import org.apache.camel.ProducerTemplate;
// import org.apache.camel.component.mock.MockEndpoint;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;

// @SpringBootTest  
// // @ContextConfiguration(classes = { ConsumerappApplication.class, TestConfig.class })
// public class CamelRouteTest {

//     @Autowired
//     private ProducerTemplate producerTemplate;

//     @Autowired
//     private MockEndpoint mockEndpoint;

//     @BeforeEach
//     void setUp() {
//         mockEndpoint.reset();
//     }

//     @Test
//     void testMessageRoute() throws InterruptedException {
//         mockEndpoint.expectedMessageCount(1);
//         mockEndpoint.message(0).body().isEqualTo("Hello, this is a test message with Camel!");

//         producerTemplate.sendBody("direct:start", "Hello, this is a test message with Camel!");

//         mockEndpoint.assertIsSatisfied();
//     }
// }
