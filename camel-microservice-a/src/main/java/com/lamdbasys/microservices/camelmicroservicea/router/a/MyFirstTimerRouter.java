package com.lamdbasys.microservices.camelmicroservicea.router.a;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//component make router avaliable to spring context
//@Component
public class MyFirstTimerRouter extends RouteBuilder {

	@Autowired
	private GetCurrentTimeBean getCurrentTimeBean;
	
	@Autowired
	private SimpleLoggingProcessingComponent loggingComponent;
	
	@Override
	public void configure() throws Exception {
		
		//here you create a route ... 
		//this is part of camel integration pattern to do on some information
		//Steps (route): 
		// - queue/timer - endpoint
		// - transform -
		// - database/log - endpoint
		
		//enpoint
		//pattern -> keyword and name
		// timer:first-timer and log:first-timer are channels
		from("timer:first-timer")
			.log("${body}") //null
		//[r://first-timer] first-timer  : Exchange[ExchangePattern: InOnly, BodyType: null, Body: [Body is null]]
		//[r://first-timer] first-timer  : Exchange[ExchangePattern: InOnly, BodyType: String, Body: My Constant Message]
		.transform().constant("My Constant Message")
			.log("${body}") //My Contant Message
		//.transform().constant("Time now is " + LocalDateTime.now())
		//spring bean do the transformation
		//.bean("getCurrentTimeBean")
		.bean(getCurrentTimeBean,"getCurrentTime")
			.log("${body}") //Time now is ...
		.bean(loggingComponent)
			.log("${body}")
		.process( new SimpleLoggingProcessor() )
		.to("log:first-timer")
			.log("${body}"); //Time now is ...;
		
		//Processing - get the message do some computation but not change the body value
		//Transformation - get the message do some computation but change the body value
		
	}

}

@Component
class GetCurrentTimeBean {
	//used in transformation 
	public String getCurrentTime() {
		return "Time now is " + LocalDateTime.now();
	}
}

@Component
class SimpleLoggingProcessingComponent {
	private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);
	//used in processing
	public void process(String message) {
		logger.info("SimpleLoggingProcessingComponent {}",message);
	}
}

@Component
class SimpleLoggingProcessor implements Processor {
	private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessor.class);
	@Override
	public void process(Exchange exchange) throws Exception {
		logger.info("SimpleLoggingProcessingComponent {}", exchange.getMessage().getBody() );	
	}
	
}

