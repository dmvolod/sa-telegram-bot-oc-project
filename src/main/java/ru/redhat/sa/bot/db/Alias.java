package ru.redhat.sa.bot.db;

public class Alias {
	private String phraseName;
	private String aliasName;
	
	public Alias() {
		super();
	}
	
	public Alias(String phraseName, String aliasName) {
		this.phraseName = phraseName;
		this.aliasName = aliasName;
	}
	
	public String getPhraseName() {
		return phraseName;
	}
	public void setPhraseName(String phraseName) {
		this.phraseName = phraseName;
	}
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
}
