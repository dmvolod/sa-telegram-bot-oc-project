package ru.redhat.sa.bot.db.mapping;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import ru.redhat.sa.bot.db.Phrase;

public interface MainProcessorMapping {
	// select * from T_PHRASES where PHRASE_NAME = 'клавд' or PHRASE_NAME = (select PHRASE_NAME from T_PHRASE_ALIASES where ALIAS_NAME = 'клавд')
	@Select("select TOP 1 PHRASE_TEXT from T_PHRASES where PHRASE_NAME = #{phraseName} "
			+ "or PHRASE_NAME = (select PHRASE_NAME from T_PHRASE_ALIASES where ALIAS_NAME = #{phraseName})")
	String searchPhrase(String phraseName);
	
	@Select("select TOP 1 PHRASE_TEXT from T_PHRASES where PHRASE_TYPE = 'MEM' order by RAND()")
	String getRandomPhrase();
	
	@Select("")
	Integer checkAliasPresent(String phraseName, String aliasName);
	
	@Results({
        @Result(property = "phraseName", column = "PHRASE_NAME"),
        @Result(property = "phraseText", column = "PHRASE_TEXT"),
        @Result(property = "phraseType", column = "PHRASE_TYPE")
    })
	@Select("select PHRASE_NAME, PHRASE_TEXT, PHRASE_TYPE from T_PHRASES where PHRASE_TYPE = 'MEM' order by PHRASE_NAME")
	List<Phrase> getAllMems();
}
