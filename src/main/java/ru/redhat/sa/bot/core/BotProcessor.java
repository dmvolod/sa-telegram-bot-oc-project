package ru.redhat.sa.bot.core;

import java.util.Arrays;
import java.util.StringTokenizer;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.telegram.model.IncomingMessage;
import org.springframework.stereotype.Component;

@Component
public class BotProcessor implements Processor {
	
	private static final String MEM_COMMAND = "мем";
	private static final String LINK_COMMAND = "линк";
	private static final String[] CONTROL_COMMANDS = {MEM_COMMAND, LINK_COMMAND};
	
	public static final String STORE_MEM_COMMAND = "storemem";

	@Override
	public void process(Exchange exchange) throws Exception {
		IncomingMessage message = (IncomingMessage)exchange.getIn().getBody();
		
		exchange.getOut().setBody("", String.class);
		exchange.getOut().setHeaders(exchange.getIn().getHeaders());
		
		if (message == null) {
            return; // skip non-text messages
        }
        
        System.out.println("Message: " + message.getText());
        System.out.println("User: " + message.getFrom().getFirstName() + " " + message.getFrom().getLastName());
        
        String text = message.getText();
        String nickName = message.getFrom().getFirstName();
        
        if (text == null) {
        	
        } else if (text.startsWith("/")) {
        	StringTokenizer st = new StringTokenizer(text, " ");
        	String command = st.nextToken().substring(1);
        	String phraseName = "";
        	String phraseAlias = "";
        	if  (st.hasMoreTokens()) {
        		phraseName = st.nextToken();
        	}
        	
        	
        	String phraseText = text.substring(text.indexOf(phraseName) + phraseName.length() + 1);
        	
        	if (Arrays.asList(CONTROL_COMMANDS).contains(command)) {
        		switch (command) {
        			case MEM_COMMAND:
        				command = STORE_MEM_COMMAND;
        				break;
        			case LINK_COMMAND:
        				command = "addlink";
        				phraseAlias = phraseText;
        				break;
        		}
        		
        	} else {
        		phraseName = command;
        		command = "search";
        	}
        		
        	exchange.getOut().setHeader("command", command);
        	exchange.getOut().setHeader("phraseName", phraseName);
        	exchange.getOut().setHeader("phraseAlias", phraseAlias);
        	exchange.getOut().setHeader("phraseText", phraseText);
        	exchange.getOut().setHeader("nickName", nickName);
        	
        	System.out.println("command: " + command);
        	System.out.println("phraseName: " + phraseName);
        	System.out.println("phraseText: " + phraseText);    	
        } else {
        	
        }
        
        // exchange.getOut().setBody(text, String.class);
	}

}