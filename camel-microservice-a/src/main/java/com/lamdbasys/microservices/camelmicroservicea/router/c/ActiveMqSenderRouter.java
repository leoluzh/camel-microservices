package com.lamdbasys.microservices.camelmicroservicea.router.c;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class ActiveMqSenderRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		//timer - endpoint
		//queue - endpoint
		from("timer:active-mq-timer?period=10000")
			.transform().constant("My message for Active MQ (from endpoint a)")
			.log("${body}")
		.to("activemq:my-activemq-queue");
		
	}

}
