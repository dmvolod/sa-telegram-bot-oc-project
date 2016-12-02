package ru.redhat.sa.bot.db;

public class Phrase {
	
	private String phraseName;
	private String phraseText;
	private String phraseType;
	
	public Phrase() {
		super();
	}
	
	public Phrase(String phraseName, String phraseText, String phraseType) {
		this.phraseName = phraseName;
		this.phraseText = phraseText;
		this.phraseType = phraseType;
	}
	
	public String getPhraseName() {
		return phraseName;
	}
	public void setPhraseName(String phraseName) {
		this.phraseName = phraseName;
	}
	public String getPhraseText() {
		return phraseText;
	}
	public void setPhraseText(String phraseText) {
		this.phraseText = phraseText;
	}
	public String getPhraseType() {
		return phraseType;
	}
	public void setPhraseType(String phraseType) {
		this.phraseType = phraseType;
	}

	@Override
	public String toString() {
		return "/" + phraseName + " - " + phraseText + "\n";
	}
}
	