package com.lamdbasys.microservices.camelmicroservicea.router.a;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//moving files into folders.
//@Component
public class MyFileRouter extends RouteBuilder {
	@Override
	public void configure() throws Exception {
		from("file:files/input")
			.log("${body}")
		.to("file:files/output");
		
	}
}
