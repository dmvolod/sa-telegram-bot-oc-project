package ru.redhat.sa.bot.db.mapping;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import ru.redhat.sa.bot.db.Alias;
import ru.redhat.sa.bot.db.Phrase;

public interface ControlServiceMapping {
	
	@Update("CREATE TABLE IF NOT EXISTS T_PHRASES(PHRASE_NAME VARCHAR(50), PHRASE_TEXT VARCHAR(255), PHRASE_TYPE VARCHAR(30)); "
			+ "CREATE TABLE IF NOT EXISTS T_PHRASE_ALIASES(PHRASE_NAME VARCHAR(50), ALIAS_NAME VARCHAR(50));")
	void createTables();
	
	@Insert("merge into T_PHRASES(PHRASE_NAME, PHRASE_TEXT, PHRASE_TYPE) KEY(PHRASE_NAME) values (#{phraseName}, #{phraseText}, #{phraseType})")
	void insertPhrase(Phrase phrase);
	
	@Insert("merge into T_PHRASE_ALIASES(PHRASE_NAME, ALIAS_NAME) KEY(ALIAS_NAME, PHRASE_NAME) values (#{phraseName}, #{aliasName})")
	void insertAlias(Alias alias);
	
	@Results({
        @Result(property = "phraseName", column = "PHRASE_NAME"),
        @Result(property = "aliasName", column = "ALIAS_NAME")
    })
	@Select("select PHRASE_NAME, ALIAS_NAME from T_PHRASE_ALIASES order by PHRASE_NAME, ALIAS_NAME")
	List<Alias> selectAliases();
	
	@Results({
        @Result(property = "phraseName", column = "PHRASE_NAME"),
        @Result(property = "phraseText", column = "PHRASE_TEXT"),
        @Result(property = "phraseType", column = "PHRASE_TYPE")
    })
	@Select("select PHRASE_NAME, PHRASE_TEXT, PHRASE_TYPE from T_PHRASES")
	List<Phrase> selectPhrases();
}