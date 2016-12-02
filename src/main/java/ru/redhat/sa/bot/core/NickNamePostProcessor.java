package ru.redhat.sa.bot.core;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class NickNamePostProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		// TODO Auto-generated method stub
		
		String message = (String)exchange.getIn().getBody();
		if (message != null) {
			String nickName = (String)exchange.getIn().getHeader("nickName");
		
			exchange.getOut().setHeaders(exchange.getIn().getHeaders());
			exchange.getOut().setBody(message.replace("[nickname]", nickName));
		}
	}

}
