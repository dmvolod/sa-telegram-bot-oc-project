package ru.redhat.sa.bot.core;

import org.apache.camel.component.telegram.model.IncomingMessage;
import org.apache.camel.component.telegram.model.User;

import ru.redhat.sa.bot.db.Alias;
import ru.redhat.sa.bot.db.Phrase;

public class MessageBuilder {
	private static final String TEST_FIRST_NAME = "Ivan";
	private static final String TEST_LAST_NAME = "Ivanov";
	
	public IncomingMessage buildIncomingTelegramMessage(String message) {
		IncomingMessage inMessage = new IncomingMessage();
		User user = new User();
		user.setFirstName(TEST_FIRST_NAME);
		user.setLastName(TEST_LAST_NAME);
		inMessage.setFrom(user);
		inMessage.setText(message);
		
		return inMessage;
	}
	
	public Phrase buildPhraseMessage(String phraseName, String phraseText, String phraseType) {
		return new Phrase(phraseName, phraseText, phraseType);
	}
	
	public Alias buildAliasMessage(String phraseName, String aliasName) {
		return new Alias(phraseName, aliasName);
	}

}
