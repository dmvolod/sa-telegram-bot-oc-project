package ru.redhat.sa.bot.db.mapping;

import org.apache.ibatis.annotations.Select;

public interface MainProcessorMapping {
	// select * from T_PHRASES where PHRASE_NAME = 'клавд' or PHRASE_NAME = (select PHRASE_NAME from T_PHRASE_ALIASES where ALIAS_NAME = 'клавд')
	@Select("select TOP 1 PHRASE_TEXT from T_PHRASES where PHRASE_NAME = #{phraseName} "
			+ "or PHRASE_NAME = (select PHRASE_NAME from T_PHRASE_ALIASES where ALIAS_NAME = #{phraseName})")
	String searchPhrase(String phraseName);
	
	@Select("")
	Integer checkAliasPresent(String phraseName, String aliasName);
}
