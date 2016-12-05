package ru.redhat.sa.bot.control;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import ru.redhat.sa.bot.db.Alias;
import ru.redhat.sa.bot.db.Phrase;

@WebService (serviceName ="BotControlService", name = "BotControlServiceInterface")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface ControlService {
	
	@WebMethod
	public void initiateDatabase();
	
	@WebMethod
	public void loadPhrases(List<Phrase> phrases);
	
	@WebMethod
	public void loadAliases(List<Alias> phrases);
	
	@WebMethod
	public List<Phrase> unloadPhrases();
	
	@WebMethod
	public List<Alias> unloadAliases();
}
