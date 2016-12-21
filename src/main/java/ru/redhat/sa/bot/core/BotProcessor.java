package ru.redhat.sa.bot.core;

import java.util.Arrays;
import java.util.Date;
import java.util.StringTokenizer;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.telegram.model.IncomingMessage;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;
import org.springframework.stereotype.Component;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

@Component
public class BotProcessor implements Processor {
	private static final String BOT_VERSION = "SA Telegram Bot Version 1.1.4";
	
	private static final String STORE_MEM_COMMAND = "запомни";
	private static final String LINK_MEM_COMMAND = "алиас";
	private static final String RANDOM_MEM_COMMAND = "мем";
	private static final String LIST_MEM_COMMAND = "список";
	private static final String REMOVE_MEM_COMMAND = "забудь";
	private static final String VERSION_BOT_COMMAND = "версия";
	private static final String DAY_BOT_COMMAND = "день";
	private static final String SCRIPT_RU_BOT_COMMAND = "скрипт";
	private static final String SCRIPT_EN_BOT_COMMAND = "script";
	
	
	private static final String[] CONTROL_COMMANDS = {STORE_MEM_COMMAND, LINK_MEM_COMMAND, RANDOM_MEM_COMMAND,
													  LIST_MEM_COMMAND, REMOVE_MEM_COMMAND, VERSION_BOT_COMMAND,
													  DAY_BOT_COMMAND, SCRIPT_RU_BOT_COMMAND, SCRIPT_EN_BOT_COMMAND};
	
	public static final String SEARCH_MEM_CASE = "search";
	public static final String STORE_MEM_CASE = "storemem";
	public static final String LINK_MEM_CASE = "addlink";
	public static final String RANDOM_MEM_CASE = "randmem";
	public static final String LIST_MEM_CASE = "listmem";
	public static final String REMOVE_MEM_CASE = "removemem";
	public static final String REMOVE_ALL_CASE = "removeall";
	public static final String OTHERWISE_CASE = "other";

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
        	if (text.length() > 1 && text.substring(1, 2).equals(" ")) {
        		text = "/" + SCRIPT_EN_BOT_COMMAND + " " + text.substring(2);
        	}
        	
        	StringTokenizer st = new StringTokenizer(text, " ");
        	String command = st.nextToken().substring(1);
        	String phraseName = "";
        	String phraseAlias = "";
        	String phraseText = "";
        	
        	if  (st.hasMoreTokens()) {
        		phraseName = st.nextToken();
        	}
        	
        	if  (st.hasMoreTokens()) {
        		phraseText = text.substring(text.indexOf(phraseName) + phraseName.length() + 1);
        	}
        	
        	if (Arrays.asList(CONTROL_COMMANDS).contains(command)) {
        		switch (command) {
        			case STORE_MEM_COMMAND:
        				command = STORE_MEM_CASE;
        				break;
        			case RANDOM_MEM_COMMAND:
        				command = RANDOM_MEM_CASE;
        				break;
        			case LIST_MEM_COMMAND:
        				command = LIST_MEM_CASE;
        				break;
        			case LINK_MEM_COMMAND:
        				command = LINK_MEM_CASE;
        				phraseAlias = phraseText;
        				break;
        			case REMOVE_MEM_COMMAND:
        				if (phraseText.equals("*")) {
        					command = REMOVE_ALL_CASE;
        				} else {
        					command = REMOVE_MEM_CASE;
        				}	
        				break;
        			case VERSION_BOT_COMMAND:
        				command = OTHERWISE_CASE;
        				phraseName = BOT_VERSION;
        				break;
        			case DAY_BOT_COMMAND:
        				command = OTHERWISE_CASE;
        				phraseName = (new Date()).toString();
        				break;
        			case SCRIPT_RU_BOT_COMMAND:
        			case SCRIPT_EN_BOT_COMMAND:
        				ImportCustomizer ic = new ImportCustomizer();
        				ic.addImports("org.apache.camel.component.telegram.model.IncomingMessage");
        				ic.addImports("org.apache.camel.CamelContext");

        				// Заготовленный import для скрипта помещается в конфигурацию компилятора groovy
        				CompilerConfiguration cc = new CompilerConfiguration();
        				cc.addCompilationCustomizers(ic);
        				
        				Binding binding = new Binding();
        				binding.setVariable("msg", message);
        				binding.setVariable("ctx", exchange.getContext());
        				
        				GroovyShell shell = new GroovyShell(binding, cc);
        				String script = text.substring((command).length() + 1);
        				
        				if (!script.equals("")) {
        					Object value = shell.evaluate(script);
        					phraseName = value.toString();
        				}
        				
        				command = OTHERWISE_CASE;
        				break;
        		}
        		
        	} else {
        		phraseName = command;
        		command = SEARCH_MEM_CASE;
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
